package concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.*;

/**
 * Created by dell on 2016/3/29.
 */
public class AtomicIntegerTest implements Runnable {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public int getValue() {
        return atomicInteger.get();
    }

    private void evenIncrement() {
        atomicInteger.addAndGet(2);
    }

    public void run() {
        while (true)
            evenIncrement();
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            public void run() {
                System.err.println("Aborting");
                System.exit(0);
            }
        }, 5000);

        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicIntegerTest atomicIntegerTest = new AtomicIntegerTest();
        executorService.execute(atomicIntegerTest);

        while (true) {
            int value = atomicIntegerTest.getValue();
            if(value % 2 != 0) {
                System.out.println(value);
                System.exit(0);
            }
        }
    }
}
