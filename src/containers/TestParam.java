package containers;

/**
 * Created by dell on 2015/12/9.
 */
public class TestParam {
    public final int size;
    public final int loops;

    public TestParam(int size, int loops) {
        this.size = size;
        this.loops =loops;
    }

    public static TestParam[] array(int... values) {
        int size = values.length / 2;
        TestParam[] result = new TestParam[size];
        for (int i = 0; i < size; i++)
            result[i] = new TestParam(values[i * 2], values[i * 2 + 1]);
        return result;
    }

    public static TestParam[] array(String[] values) {
        int[] vals = new int[values.length];
        for(int i = 0; i < vals.length; i++)
            vals[i] = Integer.decode(values[i]);
        return array(vals);
    }
}
