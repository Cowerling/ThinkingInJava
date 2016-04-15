package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/13.
 */
class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private Random random = new Random(47);
    private static int counter = 0;
    private final int id = counter++;
    private final int priority;
    protected static List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    public int compareTo(PrioritizedTask prioritizedTask) {
        return priority < prioritizedTask.priority ? 1 : (priority > prioritizedTask.priority ? -1 : 0);
    }

    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(250));
        } catch (InterruptedException e) {

        }
        System.out.println(this);
    }

    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService executorService;

        public EndSentinel(ExecutorService executorService) {
            super(-1);
            this.executorService = executorService;
        }

        public void run() {
            int count = 0;
            for(PrioritizedTask prioritizedTask : sequence) {
                System.out.print(prioritizedTask.summary());
                if(++count % 5 == 0)
                    System.out.println();
            }
            System.out.println("\n" + this + " Calling shutdownNow()");
            executorService.shutdownNow();
        }
    }
}

class PrioritizedTaskProducer implements Runnable {
    private Random random = new Random(47);
    private PriorityBlockingQueue<Runnable> queue;
    private ExecutorService executorService;

    public PrioritizedTaskProducer(PriorityBlockingQueue<Runnable> queue, ExecutorService executorService) {
        this.queue = queue;
        this.executorService = executorService;
    }

    public void run() {
        for(int i = 0; i < 20; i++) {
            queue.add(new PrioritizedTask(random.nextInt(10)));
            Thread.yield();
        }

        try {
            for(int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }

            for(int i = 0; i < 10; i++)
                queue.add(new PrioritizedTask(i));

            queue.add(new PrioritizedTask.EndSentinel(executorService));
        } catch (InterruptedException e) {

        }
        System.out.println("Finished PriorizedTaskProducer");
    }
}

class PrioritizedTaskConsumer implements Runnable {
    private PriorityBlockingQueue<Runnable> queue;

    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!Thread.interrupted())
                queue.take().run();
        } catch (InterruptedException e) {

        }
        System.out.println("Finished PriorizedTaskConsumer");
    }
}

public class PriorityBlockingQueueDemo {
    public static void main(String[] args) throws Exception {
        Random random = new Random(47);
        ExecutorService executorService = Executors.newCachedThreadPool();
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
        executorService.execute(new PrioritizedTaskProducer(queue, executorService));
        executorService.execute(new PrioritizedTaskConsumer(queue));
    }
}
