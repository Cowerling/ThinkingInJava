package concurrency;

/**
 * Created by dell on 2016/4/14.
 */
public class Fat {
    private volatile double d;
    private static int counter = 0;
    private final int id = counter++;

    public Fat() {
        for(int i = 0; i < 10000; i++) {
            d += (Math.PI + Math.E) / i;
        }
    }

    public void operation() {
        System.out.println(this);
    }

    public String toString() {
        return "Fat id: " + id;
    }
}
