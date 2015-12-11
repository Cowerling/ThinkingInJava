package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2015/12/2.
 */
public class Generated {
    public static <T> T[] array(T[] a, Generator<T> gen) {
        List<T> list = new ArrayList<T>();
        for(int i = 0; i < a.length; i++)
            list.add(gen.next());

        return list.toArray(a);
    }

    public static <T> T[] array(Class<T> type, Generator<T> gen, int size) {
        T[] a = (T[])java.lang.reflect.Array.newInstance(type, size);
        return array(a, gen);
    }
}
