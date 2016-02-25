package enumerated;

/**
 * Created by cowerling on 16-2-25.
 */
import java.util.*;
import static enumerated.Input.*;

enum Category {
    MONEY(NICKEL, DIME, QUARTER, DOLLAR),
    ITEM_SELECTION(TOOTHPASTE, CHIPS, SODA, SOAP),
    QUIT_TRANSACTION(ABOUT_TRANSACTION),
    SHUT_DOWN(STOP);

    private Input[] values;

    Category(Input... types) {
        values = types;
    }

    private static EnumMap<Input, Category> categories = new EnumMap<Input, Category>(Input.class);

    static {
        for(Category category : Category.class.getEnumConstants())
            for(Input type : category.values)
                categories.put(type, category);
    }

    public static Category categorize(Input input) {
        return categories.get(input);
    }
}

public class VendingMachine {
    private static

    enum StateDuration {
        TRANSIENT
    }


}
