package io;

import java.io.*;

/**
 * Created by dell on 2015/12/18.
 */
public class MemoryInput {
    public static void main(String[] args) throws IOException {
        StringReader in = new StringReader(BufferedInputFile.read("E:\\ThinkingInJava\\src\\io\\BufferedInputFile.java"));
        int c;
        while ((c = in.read()) != -1)
            System.out.print((char)c);
    }
}
