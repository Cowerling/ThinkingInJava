package generics;

import util.*;

/**
 * Created by cowerling on 15-11-19.
 */
public class TupleTest2 {
    static TwoTuple<String, Integer> f() {
        return Tuple.tuple("hi", 47);
    }

    static TwoTuple f2() {
        return Tuple.tuple("hi", 47);
    }

    static ThreeTuple<String, String, Integer> g() {
        return Tuple.tuple("Amphibian", "hi", 47);
    }

    public static void main(String[] args) {
        TwoTuple<String, Integer> ttsi = f();
        System.out.println(ttsi);
        System.out.println(f2());
        System.out.println(g());
    }
}
