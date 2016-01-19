package io;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

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
        Worm worm = new Worm(6, 'a');
        System.out.println("w = " + worm);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("worm.out"));
        objectOutputStream.writeObject("Worm storage\n");
        objectOutputStream.writeObject(worm);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("worm.out"));
        String string = (String)objectInputStream.readObject();
        Worm worm2 = (Worm)objectInputStream.readObject();
        System.out.println(string + "w2 = " + worm2);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream2.writeObject("Worm storage\n");
        objectOutputStream2.writeObject(worm);
        objectOutputStream2.flush();

        ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        string = (String)objectInputStream2.readObject();
        Worm worm3 = (Worm)objectInputStream2.readObject();
        System.out.println(string + "w3 = " + worm3);
    }
}
