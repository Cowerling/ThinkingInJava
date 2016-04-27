package concurrency;

import arrays.CountingIntegerList;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/4/27.
 */
abstract class ListTest extends Tester<List<Integer>> {
    ListTest(String testId, int readCount, int writeCount) {
        super(testId, readCount, writeCount);
    }

    class Reader extends TestTask {
        long result = 0;

        void test() {
            for(long i = 0; i < testCycles; i++)
                for(int index = 0; index < containerSize; index++)
                    result += testContainer.get(index);
        }

        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {
        void test() {
            for(long i = 0; i < testCycles; i++)
                for(int index = 0; index < containerSize; index++)
                    testContainer.set(index, writeData[index]);
        }

        void putResults() {
            writeTime += duration;
        }
    }

    void startReadersAndWriters() {
        for(int i = 0; i < readerCount; i++)
            executorService.execute(new Reader());
        for(int i = 0; i < writerCount; i++)
            executorService.execute(new Writer());
    }
}

class SynchronizedArrayListTest extends ListTest {
    List<Integer> containerInitializer() {
        return Collections.synchronizedList(new ArrayList<Integer>(new CountingIntegerList(containerSize)));
    }

    SynchronizedArrayListTest(int readCount, int writeCount) {
        super("Synched ArrayList", readCount, writeCount);
    }
}

class CopyOnWriteArrayListTest extends ListTest {
    List<Integer> containerInitializer() {
        return new CopyOnWriteArrayList<Integer>(new CountingIntegerList(containerSize));
    }

    CopyOnWriteArrayListTest(int readCount, int writeCount) {
        super("CopyOnWriteArrayList", readCount, writeCount);
    }
}

public class ListComparisons {
    public static void main(String[] args) {
        Tester.initMain(args);

        new SynchronizedArrayListTest(10, 0);
        new SynchronizedArrayListTest(9, 1);
        new SynchronizedArrayListTest(5, 5);
        new CopyOnWriteArrayListTest(10, 0);
        new CopyOnWriteArrayListTest(9, 1);
        new CopyOnWriteArrayListTest(5, 5);

        Tester.executorService.shutdown();
    }
}
