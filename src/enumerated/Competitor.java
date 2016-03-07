package enumerated;

/**
 * Created by dell on 2016/3/7.
 */
public interface Competitor<T extends Competitor<T>> {
    Outcome compete(T competitor);
}
