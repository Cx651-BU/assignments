import java.io.*;
import java.net.*;

/**
 * EchoClient.java — provided for PA10.
 * Connects to the EchoServer, sends lines from stdin, prints echoed replies.
 *
 * Usage:  java EchoClient <host> <port>
 *         java EchoClient localhost 7777
 *
 * Do NOT modify this file.
 */
public class EchoClient {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host> <port>");
            System.exit(1);
        }
        String host = args[0];
        int    port = Integer.parseInt(args[1]);

        try (Socket socket               = new Socket(host, port);
             BufferedReader netIn        = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter    netOut       = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleIn    = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to " + host + ":" + port);
            System.out.println("Type messages (Ctrl-D / Ctrl-Z+Enter to quit):");

            String line;
            while ((line = consoleIn.readLine()) != null) {
                netOut.println(line);
                System.out.println("Echo: " + netIn.readLine());
            }
            System.out.println("Connection closed.");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
