package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/17.
 */
public class ExceptionThread implements Runnable {
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ExceptionThread());
    }
}
