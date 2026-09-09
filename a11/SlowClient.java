import java.io.*;
import java.net.*;

/**
 * SlowClient.java — provided for PA11.
 * Connects, sends an initial message, sleeps 15 seconds to hold the connection
 * open, then sends a second message and disconnects.
 * This slow behavior makes blocking in SingleThreadedServer easy to observe.
 *
 * Usage:  java SlowClient <host> <port> <name>
 *         java SlowClient localhost 9000 "Client A"
 * Do NOT modify this file.
 */
public class SlowClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 3) { System.err.println("Usage: java SlowClient <host> <port> <name>"); System.exit(1); }
        String host = args[0];
        int    port = Integer.parseInt(args[1]);
        String name = args[2];

        try (Socket socket          = new Socket(host, port);
             PrintWriter    out     = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in      = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println(name + ": connected to " + host + ":" + port);
            out.println(name + ": first message");
            System.out.println(name + ": sent first message — now sleeping 15 seconds ...");
            String reply1 = in.readLine();
            System.out.println(name + ": server replied: " + reply1);

            Thread.sleep(15_000);   // hold the connection open

            out.println(name + ": second message (after 15-second sleep)");
            String reply2 = in.readLine();
            System.out.println(name + ": server replied: " + reply2);
            System.out.println(name + ": disconnecting.");
        }
    }
}
