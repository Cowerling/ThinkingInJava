package enumerated.menu;

/**
 * Created by dell on 2016/2/18.
 */
import util.*;

public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;

    private Course(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }

    public Food randomSelection() {
        return Enums.random(values);
    }
}
