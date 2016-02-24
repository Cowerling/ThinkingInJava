package enumerated;

/**
 * Created by cowerling on 16-2-24.
 */
public enum OverrideConstantSpecific {
    NUT, BOLT,
    WASHER {
        void f() {
            System.out.println("Overridden method");
        }
    };

    void f() {
        System.out.println("default behavior");
    }

    public static void main(String[] args) {
        for(OverrideConstantSpecific overrideConstantSpecific : values()) {
            System.out.print(overrideConstantSpecific + ": ");
            overrideConstantSpecific.f();
        }
    }
}
