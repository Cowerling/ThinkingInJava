package io;

import java.io.*;

/**
 * Created by dell on 2015/12/18.
 */
public class BufferedInputFile {
    public static String read(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine()) != null)
            sb.append(s + "\n");
        in.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(read("E:\\ThinkingInJava\\src\\io\\BufferedInputFile.java"));
    }
}
