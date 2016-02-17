package enumerated;

/**
 * Created by cowerling on 16-2-17.
 */
public class NonEnum {
    public static void main(String[] args) {
        Class<Integer> intClass = Integer.class;
        try {
            for(Object en : intClass.getEnumConstants())
                System.out.println(en);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
