/**
 * IntMath.java — provided for PA8 bytecode observation.
 *
 * Compile:    javac IntMath.java
 * Disassemble: javap -c IntMath
 *
 * Do NOT modify this file.
 */
public class IntMath {

    /** Returns the sum of a and b. */
    public static int add(int a, int b) {
        return a + b;
    }

    /** Returns n squared (n * n). */
    public static int square(int n) {
        return n * n;
    }

    /**
     * Returns n! (n factorial).
     * Precondition: n >= 0.
     * factorial(0) == 1, factorial(1) == 1.
     */
    public static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    public static void main(String[] args) {
        System.out.println("add(3, 4)    = " + add(3, 4));
        System.out.println("square(5)    = " + square(5));
        System.out.println("factorial(6) = " + factorial(6));
    }
}
