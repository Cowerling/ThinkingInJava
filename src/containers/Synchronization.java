package containers;

import util.*;

import java.util.*;

/**
 * Created by dell on 2015/12/14.
 */
public class Synchronization {
    public static void main(String[] args) {
        List<String> data = Countries.names(6);

        Collection<String> c = Collections.synchronizedCollection(data);
        List<String> list = Collections.synchronizedList(data);
        Set<String> set = Collections.synchronizedSet(new HashSet<String>(data));
        Set<String> sortedSet = Collections.synchronizedSortedSet(new TreeSet<String>(data));
        Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>(Countries.capitals(6)));
        Map<String, String> sm = Collections.synchronizedSortedMap(new TreeMap<String, String>(Countries.capitals(6)));

        c.add("ss");

        System.out.println(c);
        System.out.println(list);
        System.out.println(set);
    }
}
