package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/17.
 */
class ExceptionThread2 implements Runnable {
    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("run() by" + thread);
        System.out.println("en = " + thread.getUncaughtExceptionHandler());

        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread thread, Throwable e) {
        System.out.println("caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable runnable) {
        System.out.println(this + " creating new Thread");
        Thread thread = new Thread(runnable);
        System.out.println("created " + thread);
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh = " + thread.getUncaughtExceptionHandler());
        return thread;
    }
}

public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
        executorService.execute(new ExceptionThread2());
    }
}
