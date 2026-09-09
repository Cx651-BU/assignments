import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * LoadClient.java — CX651 Assignment 14
 *
 * A concurrent load client that fires N simultaneous GET requests to the
 * server and prints the time each one takes.
 *
 * Usage:
 *   java LoadClient <host> <port> <genre> <N>
 *
 * Example:
 *   java LoadClient localhost 7070 Fiction 10
 *
 * Each thread opens its own TCP connection, sends a GET request, reads
 * the full response body, and records the elapsed time.
 */
public class LoadClient {

    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.err.println("Usage: java LoadClient <host> <port> <genre> <N>");
            System.exit(1);
        }

        String host  = args[0];
        int    port  = Integer.parseInt(args[1]);
        String genre = URLEncoder.encode(args[2], "UTF-8");
        int    n     = Integer.parseInt(args[3]);

        ExecutorService pool   = Executors.newFixedThreadPool(n);
        CountDownLatch  latch  = new CountDownLatch(n);
        long            start  = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            final int id = i + 1;
            pool.submit(() -> {
                long t0 = System.currentTimeMillis();
                try {
                    Socket sock = new Socket(host, port);
                    PrintWriter  out = new PrintWriter(sock.getOutputStream());
                    out.print("GET /books?genre=" + genre + " HTTP/1.1\r\n");
                    out.print("Host: " + host + ":" + port + "\r\n");
                    out.print("Connection: close\r\n");
                    out.print("\r\n");
                    out.flush();

                    // Read full response
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) sb.append(line);
                    sock.close();

                    long elapsed = System.currentTimeMillis() - t0;
                    System.out.printf("Request %2d: %d ms  (%d bytes)%n",
                            id, elapsed, sb.length());
                } catch (Exception e) {
                    System.err.printf("Request %2d FAILED: %s%n", id, e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        pool.shutdown();
        long total = System.currentTimeMillis() - start;
        System.out.printf("All %d requests completed in %d ms%n", n, total);
    }
}
