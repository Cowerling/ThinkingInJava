package concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.*;

/**
 * Created by dell on 2016/4/28.
 */
public class FastSimulation {
    static final int N_ELEMENTS = 100000;
    static final int N_GENES = 30;
    static final int N_EVOLVERS = 50;
    static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENES];
    static Random random = new Random(47);

    static class Evolver implements Runnable {
        public void run() {
            while (!Thread.interrupted()) {
                int element = random.nextInt(N_ELEMENTS);

                for(int i = 0; i < N_GENES; i++) {
                    int previous = element - 1 >= 0 ? element - 1 : N_ELEMENTS - 1, next = element + 1 < N_ELEMENTS ? element + 1 : 0;
                    int oldValue = GRID[element][i].get();
                    int newValue = (oldValue + GRID[previous][i].get() + GRID[next][i].get()) / 3;

                    if(!GRID[element][i].compareAndSet(oldValue, newValue)) {
                        System.out.println("Old value changed from " + oldValue);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0; i < N_ELEMENTS; i++)
            for(int j = 0; j < N_GENES; j++)
                GRID[i][j] = new AtomicInteger(random.nextInt(1000));

        for(int i = 0; i < N_EVOLVERS; i++)
            executorService.execute(new Evolver());

        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
