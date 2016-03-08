package annotations;

import java.lang.annotation.*;

/**
 * Created by dell on 2016/3/8.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ExtractInterface {
    public String value();
}
