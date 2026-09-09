import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * ThreadPoolServer.java — provided for PA11.
 * Accepts connections in a loop and dispatches each to a cached thread pool,
 * so multiple clients are handled concurrently.
 *
 * Usage:  java ThreadPoolServer <port>
 * Do NOT modify this file.
 */
public class ThreadPoolServer {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) { System.err.println("Usage: java ThreadPoolServer <port>"); System.exit(1); }
        int port = Integer.parseInt(args[0]);
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[ThreadPool] Listening on port " + port);
            while (true) {
                Socket client = server.accept();
                pool.submit(() -> handle(client));        // handle on a pool thread; main thread returns to accept()
            }
        }
    }

    private static void handle(Socket client) {
        String remote = client.getRemoteSocketAddress().toString();
        System.out.println("[+] Handling: " + remote + "  (thread: " + Thread.currentThread().getName() + ")");
        try (client;
             BufferedReader in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter    out = new PrintWriter(client.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("    received from " + remote + ": " + line);
                out.println("Echo: " + line);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("[-] Done: " + remote);
    }
}
