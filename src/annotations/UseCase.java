package annotations;

import java.lang.annotation.*;

/**
 * Created by dell on 2016/3/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    public int id();
    public String description() default "no description";
}
