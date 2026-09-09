/**
 * Trace2.java — PA9 provided file: pass-by-value with a mutable object.
 * Do NOT modify this file.
 */
public class Trace2 {

    static void tryToDouble(int x) {
        x = x * 2;
    }

    static void appendWorld(StringBuilder sb) {
        sb.append(" world");
    }

    static void replaceRef(StringBuilder sb) {
        sb = new StringBuilder("replaced");
    }

    public static void main(String[] args) {
        int n = 5;
        tryToDouble(n);
        System.out.println(n);

        StringBuilder greeting = new StringBuilder("hello");
        appendWorld(greeting);
        System.out.println(greeting);

        replaceRef(greeting);
        System.out.println(greeting);
    }
}
