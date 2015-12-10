/**
 * Created by cowerling on 15-11-16.
 */
import java.util.*;

public interface Robot {
    String name();
    String model();
    List<Operation> operations();

    class Test {
        public static void test(Robot robot) {
            if(robot instanceof Null)
                System.out.println("[Null Robot]");
            System.out.println("Robot name: " + robot.name());
            System.out.println("Robot model: " + robot.model());
            for(Operation operation : robot.operations()) {
                System.out.println(operation.description());
                operation.command();
            }
        }
    }
}
