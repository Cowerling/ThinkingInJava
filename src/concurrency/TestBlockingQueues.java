package concurrency;

import java.util.concurrent.*;
import java.io.*;

/**
 * Created by dell on 2016/4/8.
 */
class LiftOffRunner implements Runnable {
    private BlockingQueue<LiftOff> rockets;

    public LiftOffRunner(BlockingQueue<LiftOff> queue) {
        rockets = queue;
    }

    public void add(LiftOff rocket) {
        try {
            rockets.put(rocket);
        } catch (InterruptedException e) {
            System.out.println("Interrupted during put()");
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                LiftOff rocket = rockets.take();
                rocket.run();
            }
        } catch (InterruptedException e) {
            System.out.println("Waking from take()");
        }
        System.out.println("Exiting LiftOffRunner");
    }
}

public class TestBlockingQueues {
    static void getKey() {
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void getKey(String message) {
        System.out.println(message);
        getKey();
    }

    static void test(String message, BlockingQueue<LiftOff> queue) {
        System.out.println(message);
        LiftOffRunner runner = new LiftOffRunner(queue);
        Thread thread = new Thread(runner);
        thread.start();
        for(int i = 0; i < 5; i++)
            runner.add(new LiftOff(5));
        getKey("Press 'Enter' (" + message + ")");
        thread.interrupt();
        System.out.println("Finished " + message + " test");
    }

    public static void main(String[] args) {
        test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
        test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
        test("SynchronousQueue", new SynchronousQueue<LiftOff>());
    }
}
