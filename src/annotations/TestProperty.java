package annotations;

import java.lang.annotation.*;

/**
 * Created by dell on 2016/3/10.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestProperty {}
