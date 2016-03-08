package annotations.database;

import java.lang.annotation.*;

/**
 * Created by dell on 2016/3/8.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    public String name() default "";
}
