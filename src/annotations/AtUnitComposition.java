package annotations;

import util.*;

/**
 * Created by dell on 2016/3/11.
 */
public class AtUnitComposition {
    AtUnitExample1 testObject = new AtUnitExample1();

    @Test boolean _methodOne() {
        return testObject.methodOne().equals("This is methodOne");
    }

    @Test boolean _methodTwo() {
        return testObject.methodTwo() == 2;
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command("java atunit.AtUnit E:\\IdeaProjects\\ThinkingInJava\\out\\production\\Java\\annotations\\AtUnitComposition");
    }
}
