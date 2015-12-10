package containers;

import java.util.*;

/**
 * Created by cowerling on 15-12-3.
 */
class SetType {
    int i;

    public SetType(int n) {
        i = n;
    }

    public boolean equals(Object object) {
        return object instanceof SetType && i == ((SetType)object).i;
    }

    public String toString() {
        return Integer.toString(i);
    }
}

class HashSetType extends SetType {
    public HashSetType(int n) {
        super(n);
    }

    public int hasCode() {
        return i;
    }
}

class TreeSetType extends SetType implements Comparable<TreeSetType> {
    public TreeSetType(int n) {
        super(n);
    }

    public int compareTo(TreeSetType arg) {
        return arg.i < i ? -1 : (arg.i == i ? 0 : 1);
    }
}

public class TypesForSets {
    static <T> Set<T> fill(Set<T> set, Class<T> type) {
        try {
            for(int i = 0; i < 10; i++)
                set.add(type.getConstructor(int.class).newInstance(i));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return set;
    }

    static <T> void test(Set<T> set, Class<T> type) {
        fill(set, type);
        fill(set, type);
        fill(set, type);

        System.out.println(set);
    }

    public static void main(String[] args) {
        test(new HashSet<HashSetType>(), HashSetType.class);
        test(new LinkedHashSet<HashSetType>(), HashSetType.class);
        test(new TreeSet<TreeSetType>(), TreeSetType.class);

        test(new HashSet<SetType>(), SetType.class);
        test(new HashSet<TreeSetType>(), TreeSetType.class);
        test(new LinkedHashSet<SetType>(), SetType.class);
        test(new LinkedHashSet<TreeSetType>(), TreeSetType.class);

        try {
            test(new TreeSet<SetType>(), SetType.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            test(new TreeSet<HashSetType>(), HashSetType.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
