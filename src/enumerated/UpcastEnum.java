package enumerated;

/**
 * Created by cowerling on 16-2-17.
 */
enum Search {
    HITHER, YOU
}

public class UpcastEnum {
    public static void main(String[] args) {
        Search[] vals = Search.values();
        Enum e = Search.HITHER;
        for(Enum en : e.getClass().getEnumConstants())
            System.out.println(en);
    }
}
