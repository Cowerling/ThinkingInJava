/**
 * Created by cowerling on 15-11-16.
 */
import java.util.*;

public class Staff extends ArrayList<Position> {
    public void add(String title, Person person) {
        add(new Position(title, person));
    }

    public void add(String... titles) {
        for(String title : titles)
            add(new Position(title));
    }

    public Staff(String... titles) {
        add(titles);
    }

    public boolean positionAvailable(String title) {
        for(Position position : this)
            if(position.getTitle().equals(title) && position.getPerson() == Person.NULL)
                return true;

        return false;
    }

    public void fillPosition(String title, Person hire) {
        for(Position position : this)
            if(position.getTitle().equals(title) && position.getPerson() == Person.NULL) {
                position.setPerson(hire);
                return;
            }

        throw new RuntimeException("Position " + title + " not avaiable");
    }

    public static void main(String[] args) {
        Staff staff = new Staff("President", "CTO", "Marketing Manager", "Product Manager");
        staff.fillPosition("President", new Person("Me", "Last", "The Top, Lonely At"));
        staff.fillPosition("CTO", new Person("Janet", "Planner", "The Burbs"));
        if(staff.positionAvailable("Marketing Manager"))
            staff.fillPosition("Marketing Manager", new Person("Bob", "Coder", "Bright Light City"));

        System.out.println(staff);
    }
}
