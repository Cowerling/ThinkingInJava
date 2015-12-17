package util;

import java.util.*;

/**
 * Created by dell on 2015/12/17.
 */
public class PPrint {
    public static String format(Collection<?> collection) {
        if(collection.size() == 0) return "[]";

        StringBuilder result = new StringBuilder("[");
        for(Object element : collection) {
            if(collection.size() != 1)
                result.append("\n ");
            result.append(element);
        }

        if(collection.size() != 1)
            result.append("\n");
        result.append("]");
        return result.toString();
    }

    public static void print(Collection<?> collection) {
        System.out.println(format(collection));
    }

    public static void print(Object[] objects) {
        System.out.println(format(Arrays.asList(objects)));
    }
}
