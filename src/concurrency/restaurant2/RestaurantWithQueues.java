package concurrency.restaurant2;

import enumerated.menu.*;
import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/21.
 */
class Order {
    private static int counter = 0;
    private final int id = counter++;
    private final Customer customer;
    private final WaitPerson waitPerson;
    private final Food food;

    public Order(Customer customer, WaitPerson waitPerson, Food food) {
        this.customer = customer;
        this.waitPerson = waitPerson;
        this.food = food;
    }

    public Food item() {
        return food;
    }

    public Customer getCustomer() {
        return customer;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    public String toString() {
        return "Order: " + id + " item: " + food + " for: " + customer + " served by: " + waitPerson;
    }
}

class Plate {
    private final Order order;
    private final Food food;

    public Plate(Order order, Food food) {
        this.order = order;
        this.food = food;
    }

    public Order getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    public String toString() {
        return food.toString();
    }
}

class Customer implements Runnable {
    private static int couter = 0;
    private final int id = couter++;
    private final WaitPerson waitPerson;
    private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<Plate>();

    public Customer(WaitPerson waitPerson) {
        this.waitPerson = waitPerson;
    }

    public void deliver(Plate plate) throws InterruptedException {
        placeSetting.put(plate);
    }

    public void run() {
        for(Course course : Course.values()) {
            Food food = course.randomSelection();
            try {
                waitPerson.placeOrder(this, food);
                System.out.println(this + "eating " + placeSetting.take());
            } catch (InterruptedException e) {
                System.out.println(this + "waiting for " + course + " interrupted");
                break;
            }
        }

        System.out.println(this + "finished meal, leaving");
    }

    public String toString() {
        return "Customer " + id + " ";
    }
}

class WaitPerson implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    BlockingQueue<Plate> filledOrders = new LinkedBlockingDeque<Plate>();

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void placeOrder(Customer customer, Food food) {
        try {
            restaurant.orders.put(new Order(customer, this, food));
        } catch (InterruptedException e) {
            System.out.println(this + " placeOrder interrupted");
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Plate plate = filledOrders.take();
                System.out.println(this + "received " + plate + " delivering to " + plate.getOrder().getCustomer());
                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }

        System.out.println(this + " off duty");
    }

    public String toString() {
        return "WaitingPerson " + id + " ";
    }
}

class Chef implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    private static Random random = new Random(47);

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Order order = restaurant.orders.take();
                Food requestedItem = order.item();
                TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
                Plate plate = new Plate(order, requestedItem);
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }

        System.out.println(this + " off duty");
    }

    public String toString() {
        return "Chef " + id + " ";
    }
}

class Restaurant implements Runnable {
    private List<WaitPerson> waitPersons = new ArrayList<WaitPerson>();
    private List<Chef> chefs = new ArrayList<Chef>();
    private ExecutorService executorService;
    private Random random = new Random(47);
    BlockingQueue<Order> orders = new LinkedBlockingQueue<Order>();

    public Restaurant(ExecutorService executorService, int waitPersonCount, int chefCount) {
        this.executorService = executorService;

        for(int i = 0; i < waitPersonCount; i++) {
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
            executorService.execute(waitPerson);
        }

        for(int i = 0; i < chefCount; i++) {
            Chef chef = new Chef(this);
            chefs.add(chef);
            executorService.execute(chef);
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                WaitPerson waitPerson = waitPersons.get(random.nextInt(waitPersons.size()));
                Customer customer = new Customer(waitPerson);
                executorService.execute(customer);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Restaurant interrupted");
        }

        System.out.println("Restaurant closing");
    }
}

public class RestaurantWithQueues {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(executorService, 5, 2);
        executorService.execute(restaurant);

        if(args.length > 0)
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }

        executorService.shutdownNow();
    }
}
