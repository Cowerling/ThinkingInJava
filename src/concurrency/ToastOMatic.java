package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/11.
 */
class Toast {
    public enum Status {
        DRY, BUTTERED, JAMMED
    }

    private Status status = Status.DRY;
    private final int id;

    public Toast(int id) {
        this.id = id;
    }

    public void butter() {
        status = Status.BUTTERED;
    }

    public void jam() {
        status = Status.JAMMED;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Toast " + id + ": " + status;
    }
}

class ToastQueue extends LinkedBlockingDeque<Toast> {}

class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count = 0;
    private Random random = new Random(47);

    public Toaster(ToastQueue toastQueue) {
        this.toastQueue = toastQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(500));
                Toast toast = new Toast(count++);
                System.out.println(toast);
                toastQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println("Toaster interrupted");
        }
        System.out.println("Toaster off");
    }
}

class Butterer implements Runnable {
    private ToastQueue dryQueue, butteredQueue;

    public Butterer(ToastQueue dryQueue, ToastQueue butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = dryQueue.take();
                toast.butter();
                System.out.println(toast);
                butteredQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println("Butterer interrupted");
        }
        System.out.println("Butterer off");
    }
}

class Jammer implements Runnable {
    private ToastQueue butteredQueue, finishedQueue;

    public Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = butteredQueue.take();
                toast.jam();
                System.out.println(toast);
                finishedQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}

class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int counter = 0;

    public Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = finishedQueue.take();
                if(toast.getId() != counter++ || toast.getStatus() != Toast.Status.JAMMED) {
                    System.out.println(">>>>Error: " + toast);
                    System.exit(1);
                } else {
                    System.out.println("Chomp! " + toast);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}

public class ToastOMatic {
    public static void main(String[] args) throws Exception {
        ToastQueue dryQueue = new ToastQueue(), butteredQueue = new ToastQueue(), finishedQueue = new ToastQueue();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Toaster(dryQueue));
        executorService.execute(new Butterer(dryQueue, butteredQueue));
        executorService.execute(new Jammer(butteredQueue, finishedQueue));
        executorService.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
