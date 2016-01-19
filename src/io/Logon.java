package io;

import java.util.concurrent.*;
import java.io.*;
import java.util.*;

/**
 * Created by cowerling on 16-1-19.
 */
public class Logon implements Serializable {
    private Date date = new Date();
    private String userName;
    private transient String passWord;

    public Logon(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String toString() {
        return "logon info: \n username: " + userName + "\n date: " + date + "\n password: " + passWord;
    }

    public static void main(String[] args) throws Exception {
        Logon logon = new Logon("Hulk", "myLittlePony");
        System.out.println("Logon a = " + logon);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Logon.out"));
        objectOutputStream.writeObject(logon);
        objectOutputStream.close();

        TimeUnit.SECONDS.sleep(1);

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Logon.out"));
        System.out.println("Recovering object at " + new Date());
        logon = (Logon)objectInputStream.readObject();
        System.out.println("logon a = " + logon);
    }
}
