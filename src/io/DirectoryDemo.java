package io;

import util.*;
import java.io.*;

/**
 * Created by dell on 2015/12/17.
 */
public class DirectoryDemo {
    public static void main(String[] args) {
        PPrint.print(Directory.walk(".").dirs);
        System.out.println("-------------------------");
        for(File file : Directory.local(".", "T.*"))
            System.out.println(file);
        System.out.println("-------------------------");
        for(File file : Directory.walk(".", "T.*\\.java"))
            System.out.println(file);
        System.out.println("=========================");
        for(File file : Directory.walk(".", ".*[Zz].*\\.class"))
            System.out.println(file);
    }
}
