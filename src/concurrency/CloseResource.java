package concurrency;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;

/**
 * Created by dell on 2016/4/1.
 */
public class CloseResource {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8080);
        InputStream socketInput = new Socket("localhost", 8080).getInputStream();
        executorService.execute(new IOBlocked(socketInput));
        executorService.execute(new IOBlocked(System.in));

        TimeUnit.MILLISECONDS.sleep(100);

        System.out.println("Shutting down all threads");
        executorService.shutdown();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Closing " + socketInput.getClass().getName());
        socketInput.close();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Closing " + System.in.getClass().getName());
        System.in.close();
    }
}
