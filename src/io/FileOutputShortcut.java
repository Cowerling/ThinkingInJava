package io;

import java.io.*;

/**
 * Created by dell on 2016/1/4.
 */
public class FileOutputShortcut {
    static String file = "FileOutputShortcut.out";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("E:\\ThinkingInJava\\src\\io\\FileOutputShortcut.java")));

        PrintWriter out = new PrintWriter(file);
        int lineCount = 1;
        String s;
        while ((s=in.readLine()) != null)
            out.println(lineCount++ + ": " + s);
        out.close();

        System.out.println(BufferedInputFile.read(file));
    }
}
