package util;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by cowerling on 15-11-19.
 */
public class ContainerMethodDifferences {
    static Set<String> methodSet(Class<?> type) {
        Set<String> result = new TreeSet<String>();

        for(Method method : type.getMethods())
            result.add(method.getName());

        return result;
    }

    static void interfaces(Class<?> type) {
        System.out.print("Interface in " + type.getSimpleName() + ": ");

        List<String> result = new ArrayList<String>();
        for(Class<?> c : type.getInterfaces())
            result.add(c.getSimpleName());
        System.out.println(result);
    }

    static Set<String> object = methodSet(Object.class);
    static { object.add("clone"); }

    static void difference(Class<?> superset, Class<?> subset) {
        System.out.print(superset.getSimpleName() + " extends " + subset.getSimpleName() + ", adds: ");
        Set<String> comp = Sets.difference(methodSet(superset), methodSet(subset));
        comp.removeAll(object);
        System.out.println(comp);
        interfaces(superset);
    }

    public static void main(String[] args) {
        System.out.println("Collection: " +methodSet(Collection.class));
        interfaces(Collection.class);
        difference(Set.class, Collection.class);
    }
}
