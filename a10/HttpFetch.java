import java.io.*;
import java.net.*;

/**
 * HttpFetch.java — provided for PA10.
 * Opens a raw TCP socket, sends an HTTP/1.1 GET request,
 * and prints the full response (headers + body).
 *
 * Usage:  java HttpFetch <host> <port> <path>
 *         java HttpFetch httpbin.org 80 /get
 *
 * Do NOT modify this file.
 */
public class HttpFetch {

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java HttpFetch <host> <port> <path>");
            System.exit(1);
        }
        String host = args[0];
        int    port = Integer.parseInt(args[1]);
        String path = args[2];

        try (Socket socket          = new Socket(host, port);
             PrintWriter out        = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in      = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send a minimal HTTP/1.1 GET request
            String request =
                "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n"         +
                "Connection: close\r\n"           +
                "\r\n";

            System.out.println("--- REQUEST SENT ---");
            System.out.print(request);

            out.print(request);
            out.flush();

            // Read and print the response
            System.out.println("--- RESPONSE RECEIVED ---");
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
