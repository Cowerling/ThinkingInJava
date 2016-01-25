package io;

import java.io.*;
import java.util.*;

/**
 * Created by cowerling on 16-1-25.
 */
public class RecoverCADState {
    /*@SuppressWarnings("unchecked")*/
    public static void main(String[] args) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("CADState.out"));
        List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>)objectInputStream.readObject();
        Line.deserializeStaticState(objectInputStream);
        List<Shape> shapes = (List<Shape>)objectInputStream.readObject();
        System.out.println(shapes);
    }
}
