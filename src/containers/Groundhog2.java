package containers;

/**
 * Created by cowerling on 15-12-7.
 */
public class Groundhog2 extends Groundhog {
    public Groundhog2(int number) {
        super(number);
    }

    public int hashCode() {
        return number;
    }

    public boolean equals(Object object) {
        return object instanceof Groundhog2 && number == ((Groundhog2)object).number;
    }
}
