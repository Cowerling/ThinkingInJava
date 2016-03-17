package concurrency;

/**
 * Created by dell on 2016/3/17.
 */
class UnresponsiveUI {
    private volatile double d = 1;

    public UnresponsiveUI() throws Exception {
        while (d > 0)
            d += (Math.PI + Math.E) / d;
        System.in.read();
    }
}

public class ResponsiveUI extends Thread {
    private static volatile double d = 1;

    private ResponsiveUI() {
        setDaemon(true);
        start();
    }

    public void run() {
        while (true) {
            d += (Math.PI + Math.E) / d;
        }
    }

    public static void main(String[] args) throws Exception {
        new ResponsiveUI();
        System.in.read();
        System.out.println(d);
    }
}
