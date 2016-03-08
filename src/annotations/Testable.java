package annotations;

/**
 * Created by dell on 2016/3/8.
 */
public class Testable {
    public void execute() {
        System.out.println("Executing...");
    }

    @Test void testExecute() {
        execute();
    }
}
