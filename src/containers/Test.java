package containers;

/**
 * Created by dell on 2015/12/9.
 */
public abstract class Test<C> {
    String name;

    public Test(String name) {
        this.name = name;
    }

    abstract int test(C container, TestParam testParam);
}
