package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/15.
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 5; i++)
            executorService.execute(new LiftOff());
        executorService.shutdown();
    }
}
