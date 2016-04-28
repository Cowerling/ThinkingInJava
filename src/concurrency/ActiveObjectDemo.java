package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/28.
 */
public class ActiveObjectDemo {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Random random = new Random(47);

    private void pause(int factor) {
        try {
            TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(factor));
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    public Future<Integer> calculateInt(final int x, final int y) {
        return executorService.submit(new Callable<Integer>() {
           public Integer call() {
               System.out.println("starting " + x + " + " + y);
               pause(500);
               return x + y;
           }
        });
    }

    public Future<Float> calculateFloat(final float x, final float y) {
        return executorService.submit(new Callable<Float>() {
            public Float call() {
                System.out.println("starting " + x + " + " + y);
                pause(2000);
                return x + y;
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        ActiveObjectDemo activeObjectDemo = new ActiveObjectDemo();
        List<Future<?>> results = new CopyOnWriteArrayList<Future<?>>();

        for(float f = 0.0f; f < 1.0f; f += 0.2f)
            results.add(activeObjectDemo.calculateFloat(f, f));

        for(int i = 0; i < 5; i++)
            results.add(activeObjectDemo.calculateInt(i, i));

        System.out.println("All asynch calls made");

        while (results.size() > 0) {
            for(Future<?> future : results)
                if(future.isDone()) {
                    try {
                        System.out.println(future.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    results.remove(future);
                }
        }

        activeObjectDemo.shutdown();
    }
}
