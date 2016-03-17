package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/16.
 */
public class SleepingTask extends LiftOff {
    public void run() {
        try {
            while (countDown-- > 0) {
                System.out.print(status());
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++)
            executorService.execute(new SleepingTask());
        executorService.shutdown();
    }
}
