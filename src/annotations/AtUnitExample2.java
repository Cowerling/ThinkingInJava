package annotations;

import util.*;
import java.io.*;

/**
 * Created by dell on 2016/3/11.
 */
public class AtUnitExample2 {
    public String methodOne() {
        return "This is methodOne";
    }

    public int methodTwo() {
        System.out.println("This is methodTwo");
        return 2;
    }

    @Test void assertExample() {
        assert methodOne().equals("This is methodOne");
    }

    @Test void assertFailureExample() {
        assert 1 == 2 : "What a surprise";
    }

    @Test void exceptionExample() throws IOException {
        new FileInputStream("nofile.txt");
    }

    @Test boolean assertAndReturn() {
        assert methodTwo() == 2 : "methodTwo must equal 2";
        return methodOne().equals("This is methodOne");
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command("java atunit.AtUnit E:\\IdeaProjects\\ThinkingInJava\\out\\production\\Java\\annotations\\AtUnitExample2");
    }
}
