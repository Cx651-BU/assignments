import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * EchoServer.java — provided for PA10.
 * Multi-threaded TCP echo server.
 *
 * Usage:  java EchoServer <port>
 *         java EchoServer 7777
 *
 * Do NOT modify this file.
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port>");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Echo server listening on port " + port);
            while (true) {
                Socket client = server.accept();
                pool.submit(() -> handle(client));
            }
        }
    }

    private static void handle(Socket client) {
        String remote = client.getRemoteSocketAddress().toString();
        System.out.println("[+] Connected: " + remote);
        try (client;
             BufferedReader in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter    out = new PrintWriter(client.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                out.println(line);          // echo back unchanged
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("[-] Disconnected: " + remote);
    }
}
