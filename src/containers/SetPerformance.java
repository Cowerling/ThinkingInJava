package containers;

import java.util.*;

/**
 * Created by dell on 2015/12/11.
 */
public class SetPerformance {
    static List<Test<Set<Integer>>> tests = new ArrayList<Test<Set<Integer>>>();

    static {
        tests.add(new Test<Set<Integer>>("add") {
            int test(Set<Integer> set, TestParam testParam) {
                int loops = testParam.loops;
                int size = testParam.size;

                for(int i = 0; i < loops; i++) {
                    set.clear();
                    for(int j = 0; j < size; j++)
                        set.add(j);
                }

                return loops * size;
            }
        });

        tests.add(new Test<Set<Integer>>("contains") {
            int test(Set<Integer> set, TestParam testParam) {
                int loops = testParam.loops;
                int span = testParam.size / 2;

                for(int i = 0; i < loops; i++)
                    for(int j = 0; j < span; j++)
                        set.contains(j);

                return loops * span;
            }
        });

        tests.add(new Test<Set<Integer>>("iterate") {
            int test(Set<Integer> set, TestParam testParam) {
                int loops = testParam.loops * 10;

                for(int i = 0; i < loops; i++) {
                    Iterator<Integer> it = set.iterator();
                    while (it.hasNext())
                        it.next();
                }

                return loops * set.size();
            }
        });
    }

    public static void main(String[] args) {
        if(args.length > 0)
            Tester.defaultParams = TestParam.array(args);

        Tester.fieldWidth = 10;
        Tester.run(new TreeSet<Integer>(), tests);
        Tester.run(new HashSet<Integer>(), tests);
        Tester.run(new LinkedHashSet<Integer>(), tests);
    }
}
