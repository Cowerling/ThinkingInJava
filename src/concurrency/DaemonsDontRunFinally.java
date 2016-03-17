package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/16.
 */
class ADaemon implements Runnable {
    public void run() {
        try {
            System.out.println("Starting ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        } finally {
            System.out.println("This should always run?");
        }
    }
}

public class DaemonsDontRunFinally {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new ADaemon());
        thread.setDaemon(true);
        thread.start();
    }
}
