package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/4/11.
 */
public class DeadlockingDiningPhilosophers {
    public static void main(String[] args) throws Exception {
        int ponderFactor = args.length > 0 ? Integer.parseInt(args[0]) : 5;
        int size = args.length > 1 ? Integer.parseInt(args[1]) : 5;

        ExecutorService executorService = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for(int i = 0; i < size; i++)
            chopsticks[i] = new Chopstick();
        for(int i = 0; i < size; i++)
            executorService.execute(new Philosopher(chopsticks[i], chopsticks[(i+1)%size], i, ponderFactor));

        if(args.length == 3 && args[2].equals("timeout"))
            TimeUnit.SECONDS.sleep(5);
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }

        executorService.shutdownNow();
    }
}
