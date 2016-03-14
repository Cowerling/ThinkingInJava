package annotations;

import java.io.*;
import atunit.*;
import util.*;

/**
 * Created by dell on 2016/3/14.
 */
public class AtUnitExample5 {
    private String text;

    public AtUnitExample5(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }

    @TestProperty static PrintWriter output;
    @TestProperty static int counter;

    @TestObjectCreate static AtUnitExample5 create() {
        String id = Integer.toString(counter++);
        try {
            output = new PrintWriter("Test" + id + ".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new AtUnitExample5(id);
    }

    @TestObjectCleanup static void cleanup(AtUnitExample5 atUnitExample5) {
        System.out.println("Running cleanup");
        output.close();
    }

    @Test boolean test1() {
        output.print("test1");
        return true;
    }

    @Test boolean test2() {
        output.print("test2");
        return true;
    }

    @Test boolean test3() {
        output.print("test3");
        return true;
    }

    public static void main(String[] args) throws Exception {
        OSExecute.command("java atunit.AtUnit E:\\IdeaProjects\\ThinkingInJava\\out\\production\\Java\\annotations\\AtUnitExample5");
    }
}
