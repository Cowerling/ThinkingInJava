package concurrency;

/**
 * Created by dell on 2016/3/16.
 */
public class SelfManaged implements Runnable {
    private int countDown = 5;
    private Thread thread = new Thread(this);

    public SelfManaged() {
        thread.start();
    }

    public String toString() {
        return Thread.currentThread().getName() + "(" + countDown + "), ";
    }

    public void run() {
        while (true) {
            System.out.print(this);
            if(--countDown == 0)
                return;
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++)
            new SelfManaged();
    }
}
