package io;

import java.io.*;

/**
 * Created by cowerling on 16-1-7.
 */
public class ChangeSystemOut {
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("Hello, world!");
    }
}
