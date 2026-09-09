import java.util.ArrayList;
import java.util.List;

/**
 * HeapStress.java — provided for PA8 GC log observation.
 *
 * Compile:  javac HeapStress.java
 * Run:      java -Xms16m -Xmx64m \
 *               -Xlog:gc*:file=gc.log:time,uptime,level,tags \
 *               HeapStress
 *
 * The flags constrain the heap so that GC events occur quickly and are
 * easy to observe.  Do NOT modify this file.
 */
public class HeapStress {

    private static final int KB = 1024;

    public static void main(String[] args) throws InterruptedException {

        List<byte[]> survivors = new ArrayList<>();

        // Phase 1 ── rapid short-lived allocations
        // Most arrays are allocated and immediately eligible for collection;
        // every 100th allocation is kept alive in `survivors`.
        System.out.println("[Phase 1] Short-lived allocations ...");
        for (int i = 0; i < 5_000; i++) {
            byte[] shortLived = new byte[2 * KB];   // discarded each iteration
            if (i % 100 == 0) {
                survivors.add(new byte[10 * KB]);   // retained
            }
        }

        // Phase 2 ── pause so survivors can be promoted to the Old Generation
        System.out.println("[Phase 2] Holding " + survivors.size() + " survivors ...");
        Thread.sleep(300);

        // Phase 3 ── larger allocations to put pressure on the Old Generation
        System.out.println("[Phase 3] Large allocations ...");
        List<byte[]> large = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            large.add(new byte[512 * KB]);
        }

        // Phase 4 ── release all references; heap should become reclaimable
        System.out.println("[Phase 4] Releasing all references ...");
        survivors.clear();
        large.clear();

        System.out.println("[Done] Check gc.log for GC events.");
    }
}
