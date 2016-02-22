package enumerated;

/**
 * Created by dell on 2016/2/22.
 */
import static enumerated.AlarmPoints.*;
import java.util.*;

public class EnumSets {
    public static void main(String[] args) {
        EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class);

        points.add(BATHROOM);
        System.out.println(points);

        points.addAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);

        points = EnumSet.allOf(AlarmPoints.class);
        points.removeAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);

        points.removeAll(EnumSet.range(OFFICE1, OFFICE4));
        System.out.println(points);

        points = EnumSet.complementOf(points);
        System.out.println(points);
    }
}
