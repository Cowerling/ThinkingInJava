package io;

import java.io.*;

/**
 * Created by cowerling on 16-1-19.
 */
public class Blip3 implements Externalizable {
    private int i;
    private String s;

    public Blip3() {
        System.out.println("Blip3 Constructor");
    }

    public Blip3(String x, int a) {
        System.out.println("Blip3(String x, int a)");
        s = x;
        i = a;
    }

    public String toString() {
        return s + i;
    }

    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        System.out.println("Blip3.writeExternal");
        objectOutput.writeObject(s);
        objectOutput.writeInt(i);
    }

    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        System.out.println("Blip3.readExternal");
        s = (String)objectInput.readObject();
        i = objectInput.readInt();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Constructing objects:");
        Blip3 blip3 = new Blip3("A String", 47);
        System.out.println(blip3);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Blip3.out"));
        System.out.println("Saving object:");
        objectOutputStream.writeObject(blip3);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Blip3.out"));
        System.out.println("Recovering b3:");
        blip3 = (Blip3)objectInputStream.readObject();
        System.out.println(blip3);
    }
}
