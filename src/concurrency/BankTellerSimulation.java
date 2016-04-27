package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/20.
 */
class Customer {
    private final int serviceTime;

    public Customer(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public String toString() {
        return "[" + serviceTime + "]";
    }
}

class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    public String toString() {
        if(this.size() == 0)
            return "[Empty]";

        StringBuilder result = new StringBuilder();
        for(Customer customer : this)
            result.append(customer);

        return result.toString();
    }
}

class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private static Random rand = new Random(47);

    public CustomerGenerator(CustomerLine customers) {
        this.customers = customers;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
                customers.put(new Customer(rand.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator interrupted");
        }
        System.out.println("CustomerGenerator terminating");
    }
}

class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;
    private int servedCustomerCount = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    public Teller(CustomerLine customers) {
        this.customers = customers;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());

                synchronized (this) {
                    servedCustomerCount++;
                    while (!servingCustomerLine)
                        wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "terminating");
    }

    public synchronized void doSomethingElse() {
        servedCustomerCount = 0;
        servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine() {
        assert !servingCustomerLine : "already serving: " + this;
        servingCustomerLine = true;
        notifyAll();
    }

    public String toString() {
        return "Teller " + id + " ";
    }

    public String shortString() {
        return "T" + id;
    }

    public synchronized int compareTo(Teller teller) {
        return servedCustomerCount < teller.servedCustomerCount ? -1 : (servedCustomerCount == teller.servedCustomerCount ? 0 : 1);
    }
}

class TellerManager implements Runnable {
    private ExecutorService executorService;
    private CustomerLine customers;
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();
    private Queue<Teller> dongOtherThingsTellers = new LinkedList<Teller>();
    private int adjustmentPeriod;
    private static Random random = new Random(47);

    public TellerManager(ExecutorService executorService, CustomerLine customers, int adjustmentPeriod) {
        this.executorService = executorService;
        this.customers = customers;
        this.adjustmentPeriod = adjustmentPeriod;

        Teller teller = new Teller(customers);
        executorService.execute(teller);
        workingTellers.add(teller);
    }

    public void adjustTellerNumber() {
        if(customers.size() / workingTellers.size() > 2) {
            if(dongOtherThingsTellers.size() > 0) {
                Teller teller = dongOtherThingsTellers.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller);
                return;
            }

            Teller teller = new Teller(customers);
            executorService.execute(teller);
            workingTellers.add(teller);
            return;
        }

        if(workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2)
            reassignOneTeller();

        if(customers.size() == 0)
            while (workingTellers.size() > 1)
                reassignOneTeller();

    }

    private void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        dongOtherThingsTellers.offer(teller);
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                adjustTellerNumber();
                System.out.print(customers + " { ");
                for(Teller teller : workingTellers)
                    System.out.print(teller.shortString() + " ");
                System.out.println("}");
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "termianting");
    }

    public String toString() {
        return "TellerManager";
    }
}

public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        executorService.execute(new CustomerGenerator(customers));
        executorService.execute(new TellerManager(executorService, customers, ADJUSTMENT_PERIOD));

        if(args.length > 0)
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }

        executorService.shutdownNow();

    }
}
