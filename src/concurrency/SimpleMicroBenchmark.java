package concurrency;

import java.util.concurrent.locks.*;

/**
 * Created by dell on 2016/4/22.
 */
abstract class Incrementable {
    protected long counter = 0;

    public abstract void increment();
}

class SynchronizingTest extends Incrementable {
    public synchronized void increment() {
        ++counter;
    }
}

class LockingTest extends Incrementable {
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();

        try {
            ++counter;
        } finally {
            lock.unlock();
        }
    }
}

public class SimpleMicroBenchmark {
    static long test(Incrementable incrementable) {
        long start = System.nanoTime();
        for(long i = 0; i < 10000000L; i++)
            incrementable.increment();
        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        long synchTime = test(new SynchronizingTest());
        long lockTime = test(new LockingTest());
        System.out.printf("synchronized: %1$10d\n", synchTime);
        System.out.printf("lock:         %1$10d\n", lockTime);
        System.out.printf("Lock/synchronized = %1$.3f", (double)lockTime / synchTime);
    }
}
