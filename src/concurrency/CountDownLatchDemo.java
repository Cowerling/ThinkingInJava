package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/12.
 */
class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random random = new Random(47);
    private final CountDownLatch countDownLatch;

    TaskPortion(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            doWork();
            countDownLatch.countDown();
        } catch (InterruptedException e) {

        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println(this + "completed");
    }

    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch countDownLatch;

    WaitingTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            countDownLatch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }

    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

public class CountDownLatchDemo {
    static final int SIZE = 100;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for(int i = 0; i < 10; i++)
            executorService.execute(new WaitingTask(countDownLatch));
        for(int i = 0; i < SIZE; i++)
            executorService.execute(new TaskPortion(countDownLatch));
        System.out.println("Launched all tasks");

        executorService.shutdown();
    }
}
