package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/13.
 */
class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random random = new Random(47);
    private static CyclicBarrier cyclicBarrier;

    public Horse(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public synchronized int getStrides() {
        return strides;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += random.nextInt(3);
                }
                cyclicBarrier.await();
            }
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < getStrides(); i++)
            stringBuilder.append('*');
        stringBuilder.append("\uD83C\uDFC7" + id);
        return stringBuilder.toString();
    }
}

public class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<Horse>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private CyclicBarrier cyclicBarrier;

    public HorseRace(int horseNumber, final int pause) {
        cyclicBarrier = new CyclicBarrier(horseNumber, new Runnable() {
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < FINISH_LINE; i++)
                    stringBuilder.append('=');
                System.out.println(stringBuilder);

                for(Horse horse : horses)
                    System.out.println(horse.tracks());

                for(Horse horse : horses)
                    if(horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + "won!");
                        executorService.shutdownNow();
                        return;
                    }

                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (InterruptedException e) {
                    System.out.println("barrier-action sleep interrupted");
                }
            }
        });

        for(int i = 0; i < horseNumber; i++) {
            Horse horse = new Horse(cyclicBarrier);
            horses.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        int horseNumber = args.length > 0 && Integer.parseInt(args[0]) > 0 ? Integer.parseInt(args[0]) : 7;
        int pause = args.length > 1 && Integer.parseInt(args[1]) > -1 ? Integer.parseInt(args[1]) : 200;
        new HorseRace(horseNumber, pause);
    }
}
