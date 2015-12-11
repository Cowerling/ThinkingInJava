package containers;

import java.util.*;
import static java.lang.System.out;

/**
 * Created by dell on 2015/12/11.
 */
public class Utilities {
    static List<String> list = Arrays.asList("one Two three Four five six one".split(" "));

    public static void main(String[] args) {
        out.println(list);
        out.println("'list' disjoint (Four)?: " + Collections.disjoint(list, Collections.singleton("Four")));
        out.println("max: " + Collections.max(list));
        out.println("min: " + Collections.min(list));
        out.println("max w/ comparator: " + Collections.max(list, String.CASE_INSENSITIVE_ORDER));
        out.println("min w/ comparator: " + Collections.min(list, String.CASE_INSENSITIVE_ORDER));
        List<String> subList = Arrays.asList("Four five six".split(" "));
        out.println("indexOfSubList: " + Collections.indexOfSubList(list, subList));
        out.println("lastIndexOfSubList: " + Collections.lastIndexOfSubList(list, subList));
        Collections.replaceAll(list, "one", "Yo");
        out.println("replaceAll: " + list);
        Collections.reverse(list);
        out.println("reverse: " + list);
        Collections.rotate(list, 3);
        out.println("rotate: " + list);
        List<String> source = Arrays.asList("in the matrix".split(" "));
        Collections.copy(list, source);
        out.println("copy: " + list);
        Collections.swap(list, 0, list.size() - 1);
        out.println("swap: " + list);
        Collections.shuffle(list, new Random(47));
        out.println("shuffled: " + list);
        Collections.fill(list, "pop");
        out.println("fill: " + list);
        out.println("frequence of 'pop': " + Collections.frequency(list, "pop"));
        List<String> dups = Collections.nCopies(3, "snap");
        out.println("dups: " + dups);
        out.println("'list' disjoint 'dups'?: " + Collections.disjoint(list, dups));
        Enumeration<String> e = Collections.enumeration(dups);
        Vector<String> v = new Vector<String>();
        while (e.hasMoreElements())
            v.addElement(e.nextElement());
        ArrayList<String> arrayList = Collections.list(v.elements());
        out.println("arrayList: " + arrayList);
    }
}
