package concurrency;

import java.util.concurrent.*;
import java.util.*;

import generics.BasicGenerator;
import util.*;

/**
 * Created by dell on 2016/4/19.
 */
class ExchangerProducer<T> implements Runnable {
    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;

    ExchangerProducer(Exchanger<List<T>> exchanger, Generator<T> generator, List<T> holder) {
        this.exchanger = exchanger;
        this.generator = generator;
        this.holder = holder;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                for(int i = 0; i < ExchangerDemo.size; i++)
                    holder.add(generator.next());
                holder = exchanger.exchange(holder);
            }
        } catch (InterruptedException e) {

        }
    }
}

class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;

    ExchangerConsumer(Exchanger<List<T>> exchanger, List<T> holder) {
        this.exchanger = exchanger;
        this.holder = holder;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                for(T x : holder) {
                    value = x;
                    holder.remove(x);
                }
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Final value: " + value);
    }
}

public class ExchangerDemo {
    static int size = 10;
    static int delay = 5;

    public static void main(String[] args) throws Exception {
        size = args.length > 0 ? new Integer(args[0]) : size;
        delay = args.length > 1 ? new Integer(args[1]) : delay;

        ExecutorService executorService = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> exchanger = new Exchanger<List<Fat>>();
        List<Fat> producerList = new CopyOnWriteArrayList<Fat>(), consumerList = new CopyOnWriteArrayList<Fat>();

        executorService.execute(new ExchangerProducer<Fat>(exchanger, BasicGenerator.create(Fat.class), producerList));
        executorService.execute(new ExchangerConsumer<Fat>(exchanger, consumerList));
        TimeUnit.SECONDS.sleep(delay);
        executorService.shutdownNow();
    }
}
