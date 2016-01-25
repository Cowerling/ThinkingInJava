package io;

import java.io.*;
import java.util.*;

/**
 * Created by cowerling on 16-1-25.
 */
abstract class Shape implements Serializable {
    public static final int RED = 1, BLUE = 2, GREEN = 3;
    private int xPos, yPos, dimension;
    private static Random rand = new Random(47);
    private static int counter = 0;

    public abstract void setColor(int color);
    public abstract int getColor();

    public Shape(int xPos, int yPos, int dimension) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.dimension = dimension;
    }

    public String toString() {
        return getClass() + "color[" + getColor() + "] xPos[" + xPos + "] yPos[" + yPos + "] dimension[" + dimension + "]\n";
    }

    public static Shape randomFactory() {
        int xPos = rand.nextInt(100), yPos = rand.nextInt(100), dimension = rand.nextInt(100);
        switch (counter++ % 3) {
            default:
            case 0: return new Circle(xPos, yPos, dimension);
            case 1: return new Square(xPos, yPos, dimension);
            case 2: return new Line(xPos, yPos, dimension);
        }
    }
}

class Circle extends Shape {
    private static int color = RED;

    public Circle(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);
    }

    public void setColor(int color) {
        Circle.color = color;
    }

    public int getColor() {
        return color;
    }
}

class Square extends Shape {
    private static int color;

    public Square(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);
        Square.color = RED;
    }

    public void setColor(int color) {
        Square.color = color;
    }

    public int getColor() {
        return color;
    }
}

class Line extends Shape {
    private static int color = RED;

    public static void serializeStaticState(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(color);
    }

    public static void deserializeStaticState(ObjectInputStream objectInputStream) throws IOException {
        Line.color = objectInputStream.readInt();
    }

    public Line(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);
    }

    public void setColor(int color) {
        Line.color = color;
    }

    public int getColor() {
        return color;
    }
}

public class StoreCADState {
    public static void main(String[] args) throws Exception {
        List<Class<? extends Shape>> shapeTypes = new ArrayList<Class<? extends Shape>>();
        shapeTypes.add(Circle.class);
        shapeTypes.add(Square.class);
        shapeTypes.add(Line.class);

        List<Shape> shapes = new ArrayList<Shape>();
        for(int i = 0; i < 10; i++)
            shapes.add(Shape.randomFactory());
        for(int i = 0; i < 10; i++)
            ((Shape)shapes.get(i)).setColor(Shape.GREEN);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("CADState.out"));
        objectOutputStream.writeObject(shapeTypes);
        Line.serializeStaticState(objectOutputStream);
        objectOutputStream.writeObject(shapes);
        System.out.println(shapes);
    }
}
