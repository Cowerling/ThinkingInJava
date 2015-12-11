package containers;

import java.util.*;

/**
 * Created by dell on 2015/12/11.
 */
public class MapPerformance {
    static List<Test<Map<Integer, Integer>>> tests = new ArrayList<Test<Map<Integer, Integer>>>();

    static {
        tests.add(new Test<Map<Integer, Integer>>("put") {
            int test(Map<Integer, Integer> map, TestParam testParam) {
                int loops = testParam.loops;
                int size = testParam.size;

                for(int i = 0; i < loops; i++) {
                    map.clear();
                    for(int j = 0; j < size; j++)
                        map.put(j ,j);
                }

                return loops * size;
            }
        });

        tests.add(new Test<Map<Integer, Integer>>("get") {
            int test(Map<Integer, Integer> map, TestParam testParam) {
                int loops = testParam.loops;
                int span = testParam.size / 2;

                for(int i = 0; i < loops; i++) {
                    for(int j = 0; j < span; j++)
                        map.get(j);
                }

                return loops * span;
            }
        });

        tests.add(new Test<Map<Integer, Integer>>("iterate") {
            int test(Map<Integer, Integer> map, TestParam testParam) {
                int loops = testParam.loops * 10;

                for(int i = 0; i < loops; i++) {
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext())
                        it.next();
                }

                return loops * map.size();
            }
        });
    }

    public static void main(String[] args) {
        if(args.length > 0)
            Tester.defaultParams = TestParam.array(args);

        Tester.run(new TreeMap<Integer, Integer>(), tests);
        Tester.run(new HashMap<Integer, Integer>(), tests);
        Tester.run(new LinkedHashMap<Integer, Integer>(), tests);
        Tester.run(new IdentityHashMap<Integer, Integer>(), tests);
        Tester.run(new WeakHashMap<Integer, Integer>(), tests);
        Tester.run(new Hashtable<Integer, Integer>(), tests);
    }
}
