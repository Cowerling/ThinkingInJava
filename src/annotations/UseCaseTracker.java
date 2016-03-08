package annotations;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created by dell on 2016/3/8.
 */
public class UseCaseTracker {
    public static void trackUseCase(List<Integer> useCases, Class<?> cl) {
        for(Method method : cl.getDeclaredMethods()) {
            UseCase useCase = method.getAnnotation(UseCase.class);
            if(useCase != null) {
                System.out.println("Found Use Case:" + useCase.id() + " " + useCase.description());
                useCases.remove(new Integer(useCase.id()));
            }
        }

        for(int i : useCases) {
            System.out.println("Warning: Missing use case-" + i);
        }
    }

    public static void main(String[] args) {
        List<Integer> useCases = new ArrayList<Integer>();
        Collections.addAll(useCases, 47, 48, 49, 50);
        trackUseCase(useCases, PasswordUtils.class);
    }
}
