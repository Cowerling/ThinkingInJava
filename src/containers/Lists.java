package containers;

import java.util.*;

/**
 * Created by cowerling on 15-12-3.
 */
public class Lists {
    private static boolean b;
    private static String s;
    private static int i;
    private static Iterator<String> it;
    private static ListIterator<String> lit;

    public static void basicTest(List<String> a) {
        a.add(1, "x");
        a.add("x");
        a.addAll(3, Arrays.asList("CIA", "DEA", "FBI"));

        b = a.contains("1");
        b = a.containsAll(Arrays.asList("DEA"));

        s = a.get(1);
        i = a.indexOf("1");
        b = a.isEmpty();

        it = a.iterator();
        lit = a.listIterator();
        lit = a.listIterator(3);

        i = a.lastIndexOf("1");

        a.remove(1);
        a.remove("3");
        a.set(1, "y");

        a.retainAll(Arrays.asList("CIA"));
        a.removeAll(Arrays.asList("DEA"));

        i = a.size();
        a.clear();
    }

    public static void iterMotion(List<String> a) {
        ListIterator<String> it = a.listIterator();
        b = it.hasNext();
        b = it.hasPrevious();
        s = it.next();
        i = it.nextIndex();
        s = it.previous();
        i = it.previousIndex();
    }

    public static void iterManipulation(List<String> a) {
        ListIterator<String> it = a.listIterator();
        it.add("47");
        it.next();
        it.remove();
        it.next();
        it.set("47");
    }

    public static void testVisual(List<String> a) {
        System.out.println(a);
        List<String> b = Arrays.asList("ABCDEFG".split(""));
        System.out.println("b = " + b);
        a.addAll(b);
        a.addAll(b);
        System.out.println(a);

        ListIterator<String> x = a.listIterator(a.size() / 2);
        x.add("one");
        System.out.println(a);
        System.out.println(x.next());
        x.remove();

        x.set("47");
        System.out.println(a);

        x = a.listIterator(a.size());
        while (x.hasPrevious())
            System.out.print(x.previous() + " ");
        System.out.println();
        System.out.println("testVisual finished");
    }

    public static void testLinkedList() {
        LinkedList<String> ll = new LinkedList<String>();
        ll.addAll(Arrays.asList("ZXCVBHY".split("")));
        System.out.println(ll);

        ll.addFirst("one");
        ll.addFirst("two");
        System.out.println(ll);
        System.out.println(ll.getFirst());

        System.out.println(ll.removeFirst());
        System.out.println(ll.removeFirst());

        System.out.println(ll.removeLast());
        System.out.println(ll);
    }

    public static void main(String[] args) {
        List ll = Arrays.asList("CHINA JAPAN KOREA".split(" "));

        LinkedList<String> lst = new LinkedList<String>(ll);
        ListIterator<String> it = lst.listIterator();
        it.add("X");

        System.out.println(lst);
        System.out.println(it.next());
        it.remove();
        System.out.println(lst);
        System.out.println(it.next());
        it.set("47");
        System.out.println(lst);

        basicTest(new LinkedList<String>(ll));
        basicTest(new ArrayList<String>(ll));

        iterMotion(new LinkedList<String>(ll));
        iterMotion(new ArrayList<String>(ll));

        iterManipulation(new LinkedList<String>(ll));
        iterManipulation(new ArrayList<String>(ll));

        testVisual(new LinkedList<String>(ll));

        testLinkedList();
    }
}
