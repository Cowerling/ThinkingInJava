package concurrency;

import java.util.concurrent.*;
import util.*;

/**
 * Created by dell on 2016/4/26.
 */
public abstract class Tester<C> {
    static int testReps = 10;
    static int testCycles = 1000;
    static int containerSize = 1000;
    C testContainer;
    String testId;
    int readerCount, writerCount;
    volatile long readResult = 0;
    volatile long readTime = 0, writeTime = 0;
    CountDownLatch endLatch;
    static ExecutorService executorService = Executors.newCachedThreadPool();
    Integer[] writeData;

    abstract C containerInitializer();
    abstract void startReadersAndWriters();

    Tester(String testId, int readerCount, int writerCount) {
        this.testId = testId + " " + readerCount + "r " + writerCount + "w";
        this.readerCount = readerCount;
        this.writerCount = writerCount;
        writeData = Generated.array(Integer.class, new RandomGenerator.Integer(), containerSize);

        for(int i = 0; i < testReps; i++) {
            runTest();
            readTime = writeTime = 0;
        }
    }

    void runTest() {
        endLatch = new CountDownLatch(readerCount + writerCount);
        testContainer = containerInitializer();
        startReadersAndWriters();

        try {
            endLatch.await();
        } catch (InterruptedException e) {
            System.out.println("endLatch interrupted");
        }

        System.out.printf("%-27s %14d %14d\n", testId, readTime, writeTime);
        if(readTime != 0 && writeTime != 0)
            System.out.printf("%-27s %14d\n", "readTime + writeTime =", readTime + writeTime);
    }

    abstract class TestTask implements Runnable {
        long duration;

        abstract void test();
        abstract void putResults();

        public void run() {
            long startTime = System.nanoTime();
            test();
            duration = System.nanoTime() - startTime;

            synchronized (TestTask.this) {
                putResults();
            }

            endLatch.countDown();
        }
    }

    public static void initMain(String[] args) {
        if(args.length > 0) testReps = new Integer(args[0]);
        if(args.length > 1) testCycles = new Integer(args[1]);
        if(args.length > 2) containerSize = new Integer(args[2]);

        System.out.printf("%-27s %14s %14s\n", "Type", "Read time", "Write time");
    }
}
