package enumerated;

/**
 * Created by dell on 2016/2/22.
 */
import java.util.*;
import static enumerated.AlarmPoints.*;

interface Command {
    void action();
}

public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<AlarmPoints, Command> enumMap = new EnumMap<AlarmPoints, Command>(AlarmPoints.class);

        enumMap.put(KITCHEN, new Command() {
            @Override
            public void action() {
                System.out.println("Kitchen fire!");
            }
        });

        enumMap.put(BATHROOM, new Command() {
            @Override
            public void action() {
                System.out.println("Bathroom alert!");
            }
        });

        for(Map.Entry<AlarmPoints, Command> e : enumMap.entrySet()) {
            System.out.print(e.getKey() + ": ");
            e.getValue().action();
        }

        try {
            enumMap.get(UTILITY).action();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
