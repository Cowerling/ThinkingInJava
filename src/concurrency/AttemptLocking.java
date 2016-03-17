package concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Created by dell on 2016/3/17.
 */
public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();

        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }

    public void timed() {
        boolean captured = false;

        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) {
        final AttemptLocking attemptLocking = new AttemptLocking();
        attemptLocking.untimed();
        attemptLocking.timed();

        new Thread() {
            { setDaemon(true); }

            public void run() {
                attemptLocking.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.yield();
        attemptLocking.untimed();
        attemptLocking.timed();
    }
}
