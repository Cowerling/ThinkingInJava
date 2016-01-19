package io.xfiles;

import java.io.*;

/**
 * Created by cowerling on 16-1-19.
 */
public class ThawAlien {
    public static void main(String[] args) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("/home/cowerling/IdeaProjects", "X.file")));
        Object mystery = objectInputStream.readObject();
        System.out.println(mystery.getClass().newInstance());
    }
}
