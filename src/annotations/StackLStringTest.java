package annotations;

import atunit.*;
import util.*;

/**
 * Created by dell on 2016/3/14.
 */
public class StackLStringTest extends StackL<String> {
    @Test void _push() {
        push("one");
        assert top().equals("one");
        push("two");
        assert top().equals("two");
    }

    @Test void _pop() {
        push("one");
        push("two");
        assert pop().equals("two");
        assert pop().equals("one");
    }

    @Test void _top() {
        push("A");
        push("B");
        assert top().equals("B");
        assert top().equals("B");
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command("java atunit.AtUnit E:\\IdeaProjects\\ThinkingInJava\\out\\production\\Java\\annotations\\StackLStringTest");
    }
}
