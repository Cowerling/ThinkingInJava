package containers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dell on 2015/12/11.
 */
public class ListSortSearch {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(Utilities.list);
        System.out.println(list);

        Collections.shuffle(list, new Random(47));
        System.out.println("Shuffled: " + list);

        ListIterator<String> it = list.listIterator(5);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        System.out.println("Trimmed: " + list);

        Collections.sort(list);
        System.out.println("Sorted: " + list);

        String key = list.get(3);
        int index = Collections.binarySearch(list, key);
        System.out.println("Location of " + key + " is " + index + ", list.get(" + index + ") = " + list.get(index));

        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        System.out.println("Location of " + key + " is " + index + ", list.get(" + index + ") = " + list.get(index));
    }
}
