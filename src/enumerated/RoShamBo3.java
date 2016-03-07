package enumerated;

import static enumerated.Outcome.*;

/**
 * Created by dell on 2016/3/7.
 */
public enum RoShamBo3 implements Competitor<RoShamBo3> {
    PAPER {
        public Outcome compete(RoShamBo3 item) {
            switch (item) {
                default:
                case PAPER:
                    return DRAW;
                case SCISSORS:
                    return LOSE;
                case ROCK:
                    return WIN;
            }
        }
    },
    SCISSORS {
        public Outcome compete(RoShamBo3 item) {
            switch (item) {
                default:
                case PAPER:
                    return WIN;
                case SCISSORS:
                    return DRAW;
                case ROCK:
                    return LOSE;
            }
        }
    },
    ROCK {
        public Outcome compete(RoShamBo3 item) {
            switch (item) {
                default:
                case PAPER:
                    return LOSE;
                case SCISSORS:
                    return WIN;
                case ROCK:
                    return DRAW;
            }
        }
    };

    public abstract Outcome compete(RoShamBo3 item);

    public static void main(String[] args) {
        RoShamBo.play(RoShamBo3.class, 20);
    }
}
