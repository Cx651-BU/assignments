/**
 * Trace3.java — PA9 provided file: checked vs. unchecked exceptions.
 * Do NOT modify this file.
 */
import java.io.*;

public class Trace3 {

    static void riskyIO() throws IOException {
        throw new IOException("disk full");
    }

    static void riskyRuntime() {
        throw new IllegalArgumentException("bad input");
    }

    public static void main(String[] args) {
        try {
            riskyIO();
        } catch (IOException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        riskyRuntime();
        System.out.println("This line runs only if no exception escapes.");
    }
}
