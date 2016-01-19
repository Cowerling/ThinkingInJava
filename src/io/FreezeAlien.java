package io;

import java.io.*;

/**
 * Created by cowerling on 16-1-19.
 */
public class FreezeAlien {
    public static void main(String[] args) throws Exception {
        ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream("X.file"));
        Alien quellek = new Alien();
        objectOutput.writeObject(quellek);
        objectOutput.close();
    }
}
