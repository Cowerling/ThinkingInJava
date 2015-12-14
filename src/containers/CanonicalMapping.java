package containers;

import java.util.*;

/**
 * Created by cowerling on 15-12-14.
 */
class Element {
    private String ident;

    public Element(String ident) {
        this.ident = ident;
    }

    public String toString() {
        return ident;
    }

    public int hashCode() {
        return ident.hashCode();
    }

    public boolean equals(Object r) {
        return r instanceof Element && ((Element)r).equals(ident);
    }

    protected void finalize() {
        System.out.println("Finalizing " + getClass().getSimpleName() + " " + ident);
    }
}

class Key extends Element {
    public Key(String ident) {
        super(ident);
    }
}

class Value extends Element {
    public Value(String ident) {
        super(ident);
    }
}

public class CanonicalMapping {
     public static void main(String[] args) {
         int size = 1000;
         if(args.length > 0)
             size = new Integer(args[0]);

         Key[] keys = new Key[size];
         WeakHashMap<Key, Value> map = new WeakHashMap<Key, Value>();
         for(int i = 0; i < size; i++) {
             Key key = new Key(Integer.toString(i));
             Value value = new Value(Integer.toString(i));
             if(i % 3 == 0)
                 keys[i] = key;
             map.put(key, value);
         }

         System.gc();
     }
}
