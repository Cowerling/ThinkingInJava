package util;

/**
 * Created by dell on 2016/2/18.
 */
import java.util.*;

public class Enums {
    private static Random random = new Random(47);

    public static <T extends Enum<T>> T random(Class<T> enumClass) {
        return random(enumClass.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[random.nextInt(values.length)];
    }
}
