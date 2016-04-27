package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/22.
 */
class Car {
    private final int id;
    private boolean engine = false, driveTrain = false, wheels = false;

    public Car(int id) {
        this.id = id;
    }

    public Car() {
        this.id = -1;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void addEngine() {
        engine = true;
    }

    public synchronized void addDriveTrain() {
        driveTrain = true;
    }

    public synchronized void addWheels() {
        wheels = true;
    }

    public synchronized String toString() {
        return "Car " + id + " [ engine: " + engine + " driveTrain: " + driveTrain + " wheels: " + wheels + " ]";
    }
}

class CarQueue extends LinkedBlockingQueue<Car> {}

class ChassisBuilder implements Runnable {
    private CarQueue carQueue;
    private int counter = 0;

    public ChassisBuilder(CarQueue carQueue) {
        this.carQueue = carQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(500);
                Car car = new Car(counter++);
                System.out.println("ChassisBuilder created " + car);
                carQueue.put(car);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted: ChassisBuilder");
        }

        System.out.println("ChassisBuilder off");
    }
}

class Assembler implements Runnable {
    private CarQueue chassisQueue, finishingQueue;
    private Car car;
    private CyclicBarrier barrier = new CyclicBarrier(4);
    private RobotPool robotPool;

    public Assembler(CarQueue chassisQueue, CarQueue finishingQueue, RobotPool robotPool) {
        this.chassisQueue = chassisQueue;
        this.finishingQueue = finishingQueue;
        this.robotPool = robotPool;
    }

    public Car car() {
        return car;
    }

    public CyclicBarrier barrier() {
        return barrier;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                car = chassisQueue.take();
                robotPool.hire(EngineRobot.class, this);
                robotPool.hire(DriveTrainRobot.class, this);
                robotPool.hire(WheelRobot.class, this);

                barrier.await();

                finishingQueue.put(car);
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting Assembler via interrupt");
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Assembler off");
    }
}

class Reporter implements Runnable {
    private CarQueue carQueue;

    public Reporter(CarQueue carQueue) {
        this.carQueue = carQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(carQueue.take());
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting Reporter via interrupt");
        }

        System.out.println("Reporter off");
    }
}

abstract class Robot implements Runnable {
    private RobotPool pool;
    protected Assembler assembler;
    private boolean engage = false;

    public Robot(RobotPool pool) {
        this.pool = pool;
    }

    public Robot assignAssembler(Assembler assembler) {
        this.assembler = assembler;
        return this;
    }

    public synchronized void engage() {
        engage = true;
        notifyAll();
    }

    abstract protected void performService();

    public void run() {
        try {
            powerDown();
            while (!Thread.interrupted()) {
                performService();
                assembler.barrier().await();
                powerDown();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting " + this + " via interrupt");
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this + " off");
    }

    private synchronized void powerDown() throws InterruptedException {
        engage = false;
        assembler = null;
        pool.release(this);
        System.out.println("Power down " + this);

        while (engage == false)
            wait();
    }

    public String toString() {
        return getClass().getName();
    }
}

class EngineRobot extends Robot {
    public EngineRobot(RobotPool pool) {
        super(pool);
    }

    protected void performService() {
        System.out.println(this + " installing Engine");
        assembler.car().addEngine();
    }
}

class DriveTrainRobot extends Robot {
    public DriveTrainRobot(RobotPool pool) {
        super(pool);
    }

    protected void performService() {
        System.out.println(this + " installing DriveTrain");
        assembler.car().addDriveTrain();
    }
}

class WheelRobot extends Robot {
    public WheelRobot(RobotPool pool) {
        super(pool);
    }

    protected void performService() {
        System.out.println(this + " installing Wheels");
        assembler.car().addWheels();
    }
}

class RobotPool {
    private Set<Robot> pool = new HashSet<Robot>();

    public synchronized void hire(Class<? extends Robot> robotType, Assembler assembler) throws InterruptedException {
        for(Robot robot : pool) {
            if(robot.getClass().equals(robotType)) {
                pool.remove(robot);
                robot.assignAssembler(assembler);
                robot.engage();
                return;
            }
        }

        wait();

        hire(robotType, assembler);
    }

    public synchronized void release(Robot robot) {
        pool.add(robot);
        notifyAll();
    }
}

public class CarBuilder {
    public static void main(String[] args) throws Exception {
        CarQueue chassisQueue = new CarQueue(), finishingQueue = new CarQueue();
        ExecutorService executorService = Executors.newCachedThreadPool();
        RobotPool robotPool = new RobotPool();

        executorService.execute(new EngineRobot(robotPool));
        executorService.execute(new DriveTrainRobot(robotPool));
        executorService.execute(new WheelRobot(robotPool));
        executorService.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
        executorService.execute(new Reporter(finishingQueue));
        executorService.execute(new ChassisBuilder(chassisQueue));

        TimeUnit.SECONDS.sleep(7);
        executorService.shutdownNow();
    }
}
