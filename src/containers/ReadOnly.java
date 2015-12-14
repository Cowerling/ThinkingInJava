package containers;

import util.*;
import java.util.*;

/**
 * Created by dell on 2015/12/14.
 */
public class ReadOnly {
    static Collection<String> data = new ArrayList<String>(Countries.names(6));

    public static void main(String[] args) {
        Collection<String> c = Collections.unmodifiableCollection(data);
        data.add("Tian");
        System.out.println(c);

        List<String> a = Collections.unmodifiableList(new ArrayList<String>(new ArrayList<String>(data)));
        ListIterator<String> lit = a.listIterator();
        System.out.println(lit.next());

        Set<String> s = Collections.unmodifiableSet(new HashSet<String>(data));
        System.out.println(s);

        Set<String> ss = Collections.unmodifiableSortedSet(new TreeSet<String>(data));

        Map<String, String> m = Collections.unmodifiableMap(new HashMap<String, String>(Countries.capitals(6)));
        System.out.println(m);

        Map<String, String> sm = Collections.unmodifiableSortedMap(new TreeMap<String, String>(Countries.capitals(6)));
    }
}
