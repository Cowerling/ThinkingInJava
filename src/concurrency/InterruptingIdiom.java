package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/4/5.
 */
class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int id) {
        this.id = id;
        System.out.println("NeedCleanup " + id);
    }

    public void cleanup() {
        System.out.println("Cleaning up " + id);
    }
}

class Blocked3 implements Runnable {
    private volatile double d = 0.0;

    public void run() {
        try {
            while (!Thread.interrupted()) {
                NeedsCleanup needsCleanup1 = new NeedsCleanup(1);
                try {
                    System.out.println("Sleeping");
                    TimeUnit.SECONDS.sleep(1);

                    NeedsCleanup needsCleanup2 = new NeedsCleanup(2);
                    try {
                        System.out.println("Calculating");
                        for(int i = 0; i < 2500000; i++)
                            d = d + (Math.PI + Math.E) / d;
                        System.out.println("Finished time-consuming operation");
                    } finally {
                        needsCleanup2.cleanup();
                    }
                } finally {
                    needsCleanup1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        }
    }
}

public class InterruptingIdiom {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Blocked3());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(100);
        thread.interrupt();
    }
}
