package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/15.
 */
class CheckoutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }

    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + "checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + "checking in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {

        }
    }

    public String toString() {
        return "CheckoutTask " + id + " ";
    }
}

public class SemaphoreDemo {
    final static int SIZE = 25;

    public static void main(String[] args) throws Exception {
        final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0; i < SIZE; i++)
            executorService.execute(new CheckoutTask<Fat>(pool));
        System.out.println("All CheckoutTasks created");

        List<Fat> list = new ArrayList<Fat>();
        for(int i = 0; i < SIZE; i++) {
            Fat fat = pool.checkOut();
            System.out.print(i + ": main() thread checked out ");
            fat.operation();
            list.add(fat);
        }

        Future<?> blocked = executorService.submit(new Runnable() {
            public void run() {
                try {
                    pool.checkOut();
                } catch (InterruptedException e) {
                    System.out.println("checkOut() Interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true);

        System.out.println("Checking in objects in " + list);
        for(Fat fat : list)
            pool.checkIn(fat);
        for(Fat fat : list)
            pool.checkIn(fat);

        executorService.shutdown();
    }
}
