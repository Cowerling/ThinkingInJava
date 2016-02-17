package enumerated;

/**
 * Created by cowerling on 16-2-17.
 */
import util.OSExecute;

import java.lang.reflect.*;
import java.util.*;

enum Explore {
    HERE, THERE
}

public class Reflection {
    public static Set<String> analyze(Class<?> enumClass) {
        System.out.println("----- Analyzing " + enumClass + " -----");
        System.out.println("Interfaces:");
        for(Type type : enumClass.getGenericInterfaces())
            System.out.println(type);
        System.out.println("Base: " + enumClass.getSuperclass());
        System.out.println("Methods: ");
        Set<String> methods = new TreeSet<String>();
        for(Method method : enumClass.getMethods())
            methods.add(method.getName());
        System.out.println(methods);
        return methods;
    }

    public static void main(String[] args) {
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);

        System.out.println("Explore.containAll(Enum)? " + exploreMethods.containsAll(enumMethods));

        System.out.print("Explore.removeAll(Enum): ");
        exploreMethods.removeAll(enumMethods);
        System.out.print(exploreMethods);

        OSExecute.command("javap enumerated.Explore");
    }
}
