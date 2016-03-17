package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/17.
 */
public class EvenChecker implements Runnable {
    private IntGenerator intGenerator;
    private final int id;

    public EvenChecker(IntGenerator intGenerator, int id) {
        this.intGenerator = intGenerator;
        this.id = id;
    }

    public void run() {
        while (!intGenerator.isCanceled()) {
            int value = intGenerator.next();
            if(value % 2 != 0) {
                System.out.println(value + " not even!");
                intGenerator.cancel();
            }
        }
    }

    public static void test(IntGenerator intGenerator, int count) {
        System.out.println("Press Control-C to exit");
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < count; i++)
            executorService.execute(new EvenChecker(intGenerator, i));
        executorService.shutdown();
    }

    public static void test(IntGenerator intGenerator) {
        test(intGenerator, 10);
    }
}
