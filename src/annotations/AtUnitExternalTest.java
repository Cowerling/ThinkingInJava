package annotations;

import util.*;

/**
 * Created by dell on 2016/3/11.
 */
public class AtUnitExternalTest extends AtUnitExample1 {
    @Test boolean _methodOne() {
        return methodOne().equals("This is methodOne");
    }

    @Test boolean _methodTwo() {
        return methodTwo() == 2;
    }

    public static void main(String[] args)throws Exception {
        OSExecute.command("java atunit.AtUnit E:\\IdeaProjects\\ThinkingInJava\\out\\production\\Java\\annotations\\AtUnitExternalTest");
    }
}
