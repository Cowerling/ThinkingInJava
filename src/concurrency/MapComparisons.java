package concurrency;

import java.util.concurrent.*;
import java.util.*;
import util.*;

/**
 * Created by cowerling on 16-4-27.
 */
abstract class MapTest extends Tester<Map<Integer, Integer>> {
    MapTest(String testId, int readCount, int writeCount) {
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
                    testContainer.put(index, writeData[index]);
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

class SynchronizedHashMapTest extends MapTest {
    Map<Integer, Integer> containerInitializer() {
        return Collections.synchronizedMap(new HashMap<Integer, Integer>(MapData.map(new CountingGenerator.Integer(), new CountingGenerator.Integer(), containerSize)));
    }

    SynchronizedHashMapTest(int readCount, int writeCount) {
        super("Synched HashMap", readCount, writeCount);
    }
}

class ConcurrentHashMapTest extends MapTest {
    Map<Integer, Integer> containerInitializer() {
        return new ConcurrentHashMap<Integer, Integer>(MapData.map(new CountingGenerator.Integer(), new CountingGenerator.Integer(), containerSize));
    }

    ConcurrentHashMapTest(int readCount, int writeCount) {
        super("ConcurrentHashMap", readCount, writeCount);
    }
}

public class MapComparisons {
    public static void main(String[] args) {
        Tester.initMain(args);

        new SynchronizedHashMapTest(10, 0);
        new SynchronizedHashMapTest(9, 1);
        new SynchronizedHashMapTest(5, 5);
        new ConcurrentHashMapTest(10, 0);
        new ConcurrentHashMapTest(9, 1);
        new ConcurrentHashMapTest(5, 5);

        Tester.executorService.shutdown();
    }
}
