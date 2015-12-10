package generics;

import java.util.Iterator;

/**
 * Created by cowerling on 15-11-18.
 */
public class IterableFibonacci extends Fibonacci implements Iterable<Integer> {
    private int count;

    public IterableFibonacci(int count) {
        this.count = count;
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return count > 0;
            }

            @Override
            public Integer next() {
                count--;
                return IterableFibonacci.this.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        for(int i : new IterableFibonacci(18))
            System.out.print(i + " ");
    }
}
