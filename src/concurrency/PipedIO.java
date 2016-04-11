package concurrency;

import java.util.concurrent.*;
import java.io.*;
import java.util.*;

/**
 * Created by dell on 2016/4/11.
 */
class Sender implements Runnable {
    private Random random = new Random(47);
    private PipedWriter out = new PipedWriter();

    public PipedWriter getPipedWriter() {
        return out;
    }

    public void run() {
        try {
            while (true)
                for(char c = 'A'; c <= 'Z'; c++) {
                    out.write(c);
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
                }
        } catch (IOException e) {
            System.out.println(e + " Sender write exception");
        } catch (InterruptedException e) {
            System.out.println(e + " Sender sleep interrupted");
        }
    }
}

class Receiver implements Runnable {
    private PipedReader in;

    public Receiver(Sender sender) throws IOException {
        in = new PipedReader(sender.getPipedWriter());
    }

    public void run() {
        try {
            while (true)
                System.out.print("Read: " + (char)in.read() + ", ");
        } catch (IOException e) {
            System.out.println(e + " Receiver read exception");
        }
    }
}

public class PipedIO {
    public static void main(String[] args) throws Exception {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(sender);
        executorService.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        executorService.shutdownNow();
    }
}
