package generics;

import java.util.*;

/**
 * Created by cowerling on 15-11-30.
 */
public class Fill {
    public static <T> void fill(Collection<T> collection, Class<? extends T> classToken, int size) {
        for(int i = 0; i < size; i++)
            try {
                collection.add(classToken.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}

class Contract {
    private static long counter = 0;
    private final long id = counter++;

    public String toString() {
        return getClass().getName() + " " + id;
    }
}

class TitleTransfer extends Contract {}

class FillTest {
    public static void main(String[] args) {
        List<Contract> contracts = new ArrayList<Contract>();
        Fill.fill(contracts, Contract.class, 3);
        Fill.fill(contracts, TitleTransfer.class, 2);

        for(Contract c : contracts)
            System.out.println(c);
    }
}
