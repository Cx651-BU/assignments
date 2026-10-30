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

    private static final int CONNECT_TIMEOUT_MS = 10000;
    private static final int READ_TIMEOUT_MS = 10000;

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java HttpFetch <host> <port> <path>");
            System.exit(1);
        }
        String host = args[0];
        int    port = Integer.parseInt(args[1]);
        String path = args[2];

        try (Socket socket = new Socket()) {
            // Establish TCP within 10 seconds (after hostname resolution).
            try {
                socket.connect(new InetSocketAddress(host, port), CONNECT_TIMEOUT_MS);
            } catch (SocketTimeoutException e) {
                System.err.println("Connection timed out after 10 seconds: " + host + ":" + port);
                System.err.println("Save this error output for your assignment.");
                return;
            }
            // Limit each blocking socket read; this is not a total request deadline.
            socket.setSoTimeout(READ_TIMEOUT_MS);

            try (PrintWriter out        = new PrintWriter(socket.getOutputStream(), true);
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
            } catch (SocketTimeoutException e) {
                System.err.println("Read timed out: no response data received for 10 seconds.");
                System.err.println("The response may be incomplete. Save any output above and this error for your assignment.");
            }
        }
    }
}
