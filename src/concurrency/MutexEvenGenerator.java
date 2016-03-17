package concurrency;

import java.util.concurrent.locks.*;

/**
 * Created by dell on 2016/3/17.
 */
public class MutexEvenGenerator extends IntGenerator {
    private int currentEventValue = 0;
    private Lock lock = new ReentrantLock();

    public int next() {
        lock.lock();
        try {
            ++currentEventValue;
            Thread.yield();
            ++currentEventValue;
            return currentEventValue;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenGenerator());
    }
}
