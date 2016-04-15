package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/13.
 */
class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();

    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    public int compareTo(Delayed delayed) {
        DelayedTask delayedTask = (DelayedTask)delayed;

        if(trigger < delayedTask.trigger)
            return -1;
        else if(trigger > delayedTask.trigger)
            return 1;
        else
            return 0;
    }

    public void run() {
        System.out.print(this + " ");
    }

    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService executorService;

        public EndSentinel(int delay, ExecutorService executorService) {
            super(delay);
            this.executorService = executorService;
        }

        public void run() {
            for(DelayedTask delayedTask : sequence) {
                System.out.print(delayedTask.summary());
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            executorService.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> queue;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!Thread.interrupted())
                queue.take().run();
        } catch (InterruptedException e) {

        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}

public class DelayQueueDemo {
    public static void main(String[] args) {
        Random random = new Random(47);
        ExecutorService executorService = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();

        for(int i = 0; i < 20; i++)
            queue.put(new DelayedTask(random.nextInt(5000)));
        queue.add(new DelayedTask.EndSentinel(5000, executorService));

        executorService.execute(new DelayedTaskConsumer(queue));
    }
}
