import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Server.java — CX651 Assignment 14
 *
 * A minimal multi-threaded HTTP server backed by a PostgreSQL database.
 * Handles one URL pattern: GET /books?genre=<genre>
 *
 * Uses HikariCP for connection pooling.
 * Delegates all database work to BookDAO.
 *
 * Configuration (edit to match your local PostgreSQL setup):
 *   DB_URL      — JDBC connection URL
 *   DB_USER     — database username
 *   DB_PASSWORD — database password (empty string for peer auth on macOS)
 *   PORT        — HTTP port the server listens on
 *   MAX_POOL    — maximum pool size
 *   CONN_TIMEOUT_MS — connection timeout in milliseconds
 */
public class Server {

    // ── Configuration ──────────────────────────────────────────────────────
    private static final String DB_URL         = "jdbc:postgresql://localhost:5432/books_server";
    private static final String DB_USER        = System.getProperty("user.name"); // OS username (macOS peer auth)
    private static final String DB_PASSWORD    = "";
    private static final int    PORT           = 7070;
    private static final int    MAX_POOL       = 10;
    private static final int    CONN_TIMEOUT_MS = 3000;
    // ────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) throws Exception {
        // Build the connection pool
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setMaximumPoolSize(MAX_POOL);
        config.setConnectionTimeout(CONN_TIMEOUT_MS);

        HikariDataSource dataSource = new HikariDataSource(config);
        BookDAO dao = new BookDAO(dataSource);

        ExecutorService pool = Executors.newFixedThreadPool(MAX_POOL);
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server listening on port " + PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { serverSocket.close(); } catch (IOException ignored) {}
            pool.shutdown();
            dataSource.close();
            System.out.println("Server stopped.");
        }));

        while (!serverSocket.isClosed()) {
            try {
                Socket client = serverSocket.accept();
                pool.submit(() -> handle(client, dao));
            } catch (IOException e) {
                if (!serverSocket.isClosed()) e.printStackTrace();
            }
        }
    }

    /** Handle one HTTP request on the given socket. */
    private static void handle(Socket client, BookDAO dao) {
        try (client;
             BufferedReader in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter    out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            // Drain headers
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) { /* skip */ }

            // Parse: GET /books?genre=Fiction HTTP/1.1
            String[] parts  = requestLine.split(" ");
            String   method = parts.length > 0 ? parts[0] : "";
            String   path   = parts.length > 1 ? parts[1] : "";

            if (!"GET".equals(method) || !path.startsWith("/books")) {
                sendResponse(out, 404, "Not Found", "text/plain", "404 Not Found");
                return;
            }

            String genre = extractQueryParam(path, "genre");
            if (genre == null || genre.isBlank()) {
                sendResponse(out, 400, "Bad Request", "text/plain",
                        "Missing required query parameter: genre");
                return;
            }

            List<Book> books = dao.findByGenre(genre);

            // Build JSON array
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < books.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(books.get(i).toJson());
            }
            sb.append("]");

            sendResponse(out, 200, "OK", "application/json", sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Extract a single query-string parameter by name. Returns null if absent. */
    private static String extractQueryParam(String path, String param) {
        int q = path.indexOf('?');
        if (q < 0) return null;
        String query = path.substring(q + 1);
        for (String kv : query.split("&")) {
            String[] pair = kv.split("=", 2);
            if (pair.length == 2 && pair[0].equals(param)) {
                return URLDecoder.decode(pair[1], java.nio.charset.StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    /** Write a minimal HTTP response. */
    private static void sendResponse(PrintWriter out, int code, String status,
                                     String contentType, String body) {
        byte[] bodyBytes = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        out.print("HTTP/1.1 " + code + " " + status + "\r\n");
        out.print("Content-Type: " + contentType + "; charset=utf-8\r\n");
        out.print("Content-Length: " + bodyBytes.length + "\r\n");
        out.print("Connection: close\r\n");
        out.print("\r\n");
        out.print(body);
        out.flush();
    }
}
