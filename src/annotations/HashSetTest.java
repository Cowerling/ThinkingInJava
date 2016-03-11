package annotations;

import util.*;
import java.util.*;

/**
 * Created by dell on 2016/3/11.
 */
public class HashSetTest {
    HashSet<String> testObject = new HashSet<String>();

    @Test void initialization() {
        assert testObject.isEmpty();
    }

    @Test void _contains() {
        testObject.add("one");
        assert testObject.contains("one");
    }

    @Test void _remove() {
        testObject.add("one");
        testObject.remove("one");
        assert testObject.isEmpty();
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command("java atunit.AtUnit E:\\IdeaProjects\\ThinkingInJava\\out\\production\\Java\\annotations\\HashSetTest");
    }
}
