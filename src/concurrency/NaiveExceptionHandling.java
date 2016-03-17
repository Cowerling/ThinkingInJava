package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/17.
 */
public class NaiveExceptionHandling {
    public static void main(String[] args) {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(new ExceptionThread());
        } catch (RuntimeException e) {
            System.out.println("Exception has been handled!");
        }
    }
}
