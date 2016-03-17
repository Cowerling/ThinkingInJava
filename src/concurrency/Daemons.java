package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/16.
 */
class Daemon implements Runnable {
    private Thread[] threads = new Thread[10];

    public void run() {
        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new DaemonSpawn());
            threads[i].start();
            System.out.print("DaemonSpawn " + i + " started, ");
        }

        for(int i = 0; i < threads.length; i++)
            System.out.print("t[" + i + "].isDaemon() = " + threads[i].isDaemon() + ", ");
    }
}

class DaemonSpawn implements Runnable {
    public void run() {
        while (true)
            Thread.yield();
    }
}

public class Daemons {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Daemon());
        thread.setDaemon(true);
        thread.start();
        System.out.print("d.isDaemon() = " + thread.isDaemon() + ", ");
        TimeUnit.SECONDS.sleep(1);
    }
}
