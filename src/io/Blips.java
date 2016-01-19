package io;

import java.io.*;

/**
 * Created by cowerling on 16-1-19.
 */
class Blip1 implements Externalizable {
    public Blip1() {
        System.out.println("Blip1 Constructor");
    }

    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        System.out.println("Blip1.writeExternal");
    }

    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        System.out.println("Blip1.readExternal");
    }
}

class Blip2 implements Externalizable {
    Blip2() {
        System.out.println("Blip2 Constructor");
    }

    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        System.out.println("Blip2.writeExternal");
    }

    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        System.out.println("Blip2.readExternal");
    }
}

public class Blips {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Constructing objects:");
        Blip1 blip1 = new Blip1();
        Blip2 blip2 = new Blip2();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Blips.out"));
        System.out.println("Saving objects");
        objectOutputStream.writeObject(blip1);
        objectOutputStream.writeObject(blip2);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Blips.out"));
        System.out.println("Recovering b1:");
        blip1 = (Blip1)objectInputStream.readObject();
        System.out.println("Recovering b2:");
        blip2 = (Blip2)objectInputStream.readObject();
    }
}
