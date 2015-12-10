package generics;

import generics.coffee.*;
import util.Generator;

import java.util.*;

/**
 * Created by cowerling on 15-11-30.
 */
interface Addable<T> {
    void add(T t);
}

class Fill2 {
    public static <T> void fill(Addable<T> addable, Class<? extends T> classToken, int size) {
        for(int i = 0; i < size; i++)
            try {
                addable.add(classToken.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    //public static <T> void fill(Addable<T> addable, Generator<T> generator, int size)
}

public class Fill2Test {
}
