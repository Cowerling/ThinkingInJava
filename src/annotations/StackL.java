package annotations;

import java.util.*;

/**
 * Created by dell on 2016/3/14.
 */
public class StackL<T> {
    private LinkedList<T> list = new LinkedList<T>();

    public void push(T element) {
        list.addFirst(element);
    }

    public T top() {
        return list.getFirst();
    }

    public T pop() {
        return list.removeFirst();
    }
}
