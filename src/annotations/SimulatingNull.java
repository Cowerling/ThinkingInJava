package annotations;

import java.lang.annotation.*;

/**
 * Created by dell on 2016/3/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimulatingNull {
    public int id() default -1;
    public String description() default "";
}
