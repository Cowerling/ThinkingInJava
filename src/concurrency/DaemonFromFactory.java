package concurrency;

import java.util.concurrent.*;
import util.*;

/**
 * Created by dell on 2016/3/16.
 */
public class DaemonFromFactory implements Runnable {
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for(int i = 0; i < 10; i++)
            executorService.execute(new DaemonFromFactory());
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(500);
    }
}
