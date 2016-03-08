package annotations.database;

/**
 * Created by dell on 2016/3/8.
 */
public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}
