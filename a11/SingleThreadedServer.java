import java.io.*;
import java.net.*;

/**
 * SingleThreadedServer.java — provided for PA11.
 * Accepts and fully handles one TCP connection at a time.
 * A second client that connects while the first is being served
 * is queued by the OS but not processed until handle() returns.
 *
 * Usage:  java SingleThreadedServer <port>
 * Do NOT modify this file.
 */
public class SingleThreadedServer {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) { System.err.println("Usage: java SingleThreadedServer <port>"); System.exit(1); }
        int port = Integer.parseInt(args[0]);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("[SingleThreaded] Listening on port " + port);
            while (true) {
                Socket client = server.accept();          // blocks until a client connects
                handle(client);                           // blocks until this client is done
            }
        }
    }

    private static void handle(Socket client) {
        String remote = client.getRemoteSocketAddress().toString();
        System.out.println("[+] Handling: " + remote);
        try (client;
             BufferedReader in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter    out = new PrintWriter(client.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("    received: " + line);
                out.println("Echo: " + line);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("[-] Done: " + remote);
    }
}
