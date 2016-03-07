package enumerated;

import java.util.*;
import static enumerated.Outcome.*;

/**
 * Created by dell on 2016/3/7.
 */
public enum RoShamBo5 implements Competitor<RoShamBo5> {
    PAPER, SCISSORS, ROCK;

    static EnumMap<RoShamBo5, EnumMap<RoShamBo5, Outcome>> table = new EnumMap<RoShamBo5, EnumMap<RoShamBo5, Outcome>>(RoShamBo5.class);
    static {
        for(RoShamBo5 item : RoShamBo5.values())
            table.put(item, new EnumMap<RoShamBo5, Outcome>(RoShamBo5.class));

        initRow(PAPER, DRAW, LOSE, WIN);
        initRow(SCISSORS, WIN, DRAW, LOSE);
        initRow(ROCK, LOSE, WIN, DRAW);
    }

    static void initRow(RoShamBo5 item, Outcome vPAPER, Outcome vSCISSORS, Outcome vROCK) {
        EnumMap<RoShamBo5, Outcome> row = table.get(item);
        row.put(PAPER, vPAPER);
        row.put(SCISSORS, vSCISSORS);
        row.put(ROCK, vROCK);
    }

    public Outcome compete(RoShamBo5 item) {
        return table.get(this).get(item);
    }

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo5.class, 20);
    }
}
