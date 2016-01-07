package io;

import java.io.*;

/**
 * Created by dell on 2015/12/18.
 */
public class BasicFileOutput {
    static String file = "C:\\Users\\dell\\Desktop\\BufferedInputFile.out";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read("E:\\ThinkingInJava\\src\\io\\BufferedInputFile.java")));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        int lineCount = 1;
        String s;
        while((s = in.readLine()) != null)
            out.println(lineCount++ + ": " + s);
        out.close();
        System.out.println(BufferedInputFile.read(file));
    }
}
