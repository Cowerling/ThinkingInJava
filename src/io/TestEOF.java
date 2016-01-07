package io;

import java.io.*;

/**
 * Created by dell on 2015/12/18.
 */
public class TestEOF {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("E:\\ThinkingInJava\\src\\io\\BufferedInputFile.java")));
        while (in.available() != 0)
            System.out.print((char)in.readByte());
    }
}
