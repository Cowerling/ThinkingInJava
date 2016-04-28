package concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

/**
 * Created by dell on 2016/4/28.
 */
public class ReaderWriterList<T> {
    private ArrayList<T> lockedList;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public ReaderWriterList(int size, T initialValue) {
        lockedList = new ArrayList<T>(Collections.nCopies(size, initialValue));
    }

    public T set(int index, T element) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            return lockedList.set(index, element);
        } finally {
            writeLock.unlock();
        }
    }

    public T get(int index) {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            if(lock.getReadLockCount() > 1)
                System.out.println(lock.getReadLockCount());
            return lockedList.get(index);
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        new ReaderWriterListTest(30, 1);
    }
}

class ReaderWriterListTest {
    ExecutorService executorService = Executors.newCachedThreadPool();
    private final static int SIZE = 100;
    private Random random = new Random(47);
    private ReaderWriterList<Integer> list = new ReaderWriterList<Integer>(SIZE, 0);

    private class Writer implements Runnable {
        public void run() {
            try {
                for(int i = 0; i < 20; i++) {
                    list.set(i, random.nextInt());
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {

            }

            System.out.println("Writer finished, shutting down");
            executorService.shutdownNow();
        }
    }

    private class Reader implements Runnable {
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    for(int i = 0; i < SIZE; i++) {
                        list.get(i);
                        TimeUnit.MILLISECONDS.sleep(1);
                    }
                }
            } catch (InterruptedException e) {

            }
        }
    }

    public ReaderWriterListTest(int readers, int writers) {
        for(int i = 0; i < readers; i++)
            executorService.execute(new Reader());

        for(int i = 0; i < writers; i++)
            executorService.execute(new Writer());
    }
}
