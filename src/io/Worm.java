package io;

import java.io.*;
import java.util.*;

/**
 * Created by dell on 2016/1/15.
 */
class Data implements Serializable {
    private int n;

    public Data(int n) { this.n = n; }
    public String toString() { return Integer.toString(n); }
}

public class Worm implements Serializable {
    private static Random rand = new Random(47);
    private Data[] datas = {
        new Data(rand.nextInt(10)),
        new Data(rand.nextInt(10)),
        new Data(rand.nextInt(10))
    };
    private Worm next = null;
    private char c;

    public Worm(int i, char x) {
        System.out.println("Worm constructor: " + i);
        c = x;
        if(--i > 0)
            next = new Worm(i, (char)(x + 1));
    }

    public Worm() {
        System.out.println("Default constructor");
    }

    public String toString() {
        StringBuilder result = new StringBuilder(":");
        result.append(c);
        result.append("(");
        for(Data data : datas)
            result.append(data);
        result.append(")");
        if(next != null)
            result.append(next);
        return result.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

    }
}
