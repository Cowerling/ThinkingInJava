package io;

import com.sun.corba.se.impl.dynamicany.DynAnyBasicImpl;

import java.io.*;
import java.util.*;

/**
 * Created by cowerling on 16-1-25.
 */
class House implements Serializable {}

class Animal implements Serializable {
    private String name;
    private House preferredHouse;

    Animal(String name, House house) {
        this.name = name;
        this.preferredHouse = house;
    }

    public String toString() {
        return name + "[" + super.toString() + "], " + preferredHouse + "\n";
    }
}

public class MyWorld {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        House house = new House();
        List<Animal> animals = new ArrayList<Animal>();
        animals.add(new Animal("Bosco the dog", house));
        animals.add(new Animal("Ralph the hamster", house));
        animals.add(new Animal("Molly the cat", house));
        System.out.println("animal: " + animals);

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(byteArrayOutputStream1);
        objectOutputStream1.writeObject(animals);
        objectOutputStream1.writeObject(animals);

        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream2);
        objectOutputStream2.writeObject(animals);

        ObjectInputStream objectInputStream1 = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream1.toByteArray()));
        ObjectInputStream objectInputStream2 = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream2.toByteArray()));
        List animals1 = (List)objectInputStream1.readObject(), animals2 = (List)objectInputStream1.readObject(), animals3 = (List)objectInputStream2.readObject();
        System.out.println("animals1: " + animals1);
        System.out.println("animals2: " + animals2);
        System.out.println("animals3: " + animals3);
    }
}
