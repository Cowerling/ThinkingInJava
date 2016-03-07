package enumerated;

/**
 * Created by dell on 2016/3/7.
 */
import java.util.*;
import static enumerated.Outcome.*;

interface Item {
    public Outcome compete(Item it);
    public Outcome eval(Paper paper);
    public Outcome eval(Scissors scissors);
    public Outcome eval(Rock rock);
}

class Paper implements Item {
    public Outcome compete(Item it) {
        return it.eval(this);
    }

    public Outcome eval(Paper paper) {
        return DRAW;
    }

    public Outcome eval(Scissors scissors) {
        return WIN;
    }

    public Outcome eval(Rock rock) {
        return LOSE;
    }

    public String toString() {
        return "Paper";
    }
}

class Scissors implements Item {
    public Outcome compete(Item it) {
        return it.eval(this);
    }

    public Outcome eval(Paper paper) {
        return LOSE;
    }

    public Outcome eval(Scissors scissors) {
        return DRAW;
    }

    public Outcome eval(Rock rock) {
        return WIN;
    }

    public String toString() {
        return "Scissors";
    }
}

class Rock implements Item {
    public Outcome compete(Item it) {
        return it.eval(this);
    }

    public Outcome eval(Paper paper) {
        return WIN;
    }

    public Outcome eval(Scissors scissors) {
        return LOSE;
    }

    public Outcome eval(Rock rock) {
        return DRAW;
    }

    public String toString() {
        return "Rock";
    }
}

public class RoShamBo1 {
    static final int SIZE = 20;
    private static Random random = new Random(47);

    public static Item newItem() {
        switch (random.nextInt(3)) {
            default:
            case 0:
                return new Scissors();
            case 1:
                return new Paper();
            case 2:
                return new Rock();
        }
    }

    public static void match(Item a, Item b) {
        System.out.println(a + " vs. " + b + ": " + a.compete(b));
    }

    public static void main(String[] args) {
        for(int i = 0; i < SIZE; i++)
            match(newItem(), newItem());
    }
}
