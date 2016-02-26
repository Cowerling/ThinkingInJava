package enumerated;

/**
 * Created by cowerling on 16-2-25.
 */
import util.*;
import java.util.*;
import static enumerated.Input.*;

enum Category {
    MONEY(NICKEL, DIME, QUARTER, DOLLAR),
    ITEM_SELECTION(TOOTHPASTE, CHIPS, SODA, SOAP),
    QUIT_TRANSACTION(ABORT_TRANSACTION),
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
    private static State state = State.RESTING;
    private static int amount = 0;
    private static Input selection = null;

    enum StateDuration {
        TRANSIENT
    }

    enum State {
        RESTING {
            void next(Input input) {
                switch(Category.categorize(input)) {
                    case MONEY:
                        amount += input.amount();
                        state = ADDING_MONEY;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                        break;
                    default:
                        break;
                }
            }
        },
        ADDING_MONEY {
            void next(Input input) {
                switch (Category.categorize(input)) {
                    case MONEY:
                        amount += input.amount();
                        break;
                    case ITEM_SELECTION:
                        selection = input;
                        if(amount < selection.amount())
                            System.out.println("Insufficient money for " + selection);
                        else
                            state = DISPENSING;
                        break;
                    case QUIT_TRANSACTION:
                        state = GIVING_CHANGE;
                        break;
                    case SHUT_DOWN:
                        state = TERMINAL;
                        break;
                    default:
                        break;
                }
            }
        },
        DISPENSING(StateDuration.TRANSIENT) {
            void next() {
                System.out.println("here is your " + selection);
                amount -= selection.amount();
                state = GIVING_CHANGE;
            }
        },
        GIVING_CHANGE(StateDuration.TRANSIENT) {
            void next() {
                if(amount > 0) {
                    System.out.println("Your change: " + amount);
                    amount = 0;
                }
                state = RESTING;
            }
        },
        TERMINAL {
            void output() {
                System.out.println("Halted");
            }
        };

        private boolean isTransient = false;

        State() {}
        State(StateDuration stateDuration) {
            isTransient = true;
        }

        void next(Input input) {
            throw new RuntimeException("Only call " + "next(Input input) for non-transient states");
        }

        void next() {
            throw new RuntimeException("Only call " + "next(Input input) for StateDuration.TRANSIENT states");
        }

        void output() {
            System.out.println(amount);
        }
    }

    static void run(Generator<Input> generator) {
        while(state != State.TERMINAL) {
            state.next(generator.next());
            while (state.isTransient)
                state.next();
            state.output();
        }
    }

    public static void main(String[] args) {
        Generator<Input> generator = new RandomInputGenerator();

        if(args.length == 1)
            generator = new FileInputGenerator(args[0]);

        run(generator);
    }
}

class RandomInputGenerator implements Generator<Input> {
    public Input next() {
        return Input.randomSelection();
    }
}

class FileInputGenerator implements Generator<Input> {
    private Iterator<String> input;

    public FileInputGenerator(String fileName) {
        input = new TextFile(fileName, ";").iterator();
    }

    public Input next() {
        if(!input.hasNext())
            return null;

        return Enum.valueOf(Input.class, input.next().trim());
    }
}
