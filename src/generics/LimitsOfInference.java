package generics;

import util.New;

import java.util.*;

/**
 * Created by cowerling on 15-11-18.
 */
public class LimitsOfInference {
    static void f(Map<String, List<? extends String>> map) {}

    public static void main(String[] args) {
        f(New.<String, List<? extends String>>map());
    }
}
