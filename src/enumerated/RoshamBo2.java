package enumerated;

import static enumerated.Outcome.*;

/**
 * Created by dell on 2016/3/7.
 */
public enum RoShamBo2 implements Competitor<RoShamBo2> {
    PAPER(DRAW, LOSE, WIN),
    SCISSORS(WIN, DRAW, LOSE),
    ROCK(LOSE, WIN, DRAW);

    private Outcome vPAPER, vSCISSORS, vROCK;

    RoShamBo2(Outcome paper, Outcome scissors, Outcome rock) {
        vPAPER = paper;
        vSCISSORS = scissors;
        vROCK = rock;
    }

    public Outcome compete(RoShamBo2 item) {
        switch (item) {
            default:
            case PAPER:
                return vPAPER;
            case SCISSORS:
                return vSCISSORS;
            case ROCK:
                return vROCK;
        }
    }

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo2.class, 10);
    }
}
