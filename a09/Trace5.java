/**
 * Trace5.java — PA9 provided file: generics and type erasure.
 * Do NOT modify this file.
 */
import java.util.*;

public class Trace5 {

    static <T> void printFirstType(List<T> list) {
        if (!list.isEmpty()) {
            Object first = list.get(0);
            System.out.println(first.getClass().getName());
        }
    }

    public static void main(String[] args) {
        List<String>  strings  = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        List<Double>  doubles  = new ArrayList<>();

        strings.add("hello");
        integers.add(42);
        doubles.add(3.14);

        printFirstType(strings);
        printFirstType(integers);
        printFirstType(doubles);

        System.out.println(strings.getClass() == integers.getClass());
        System.out.println(strings.getClass().getName());
    }
}
