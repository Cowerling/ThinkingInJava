package enumerated.cartoons;

/**
 * Created by dell on 2016/2/18.
 */
import util.Generator;

import java.util.*;
import java.util.concurrent.SynchronousQueue;

enum CartoonCharacter implements Generator<CartoonCharacter> {
    SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;

    private Random random = new Random(47);

    public CartoonCharacter next() {
        return values()[random.nextInt(values().length)];
    }
}

public class EnumImplementation {
    public static <T> void printNext(Generator<T> generator) {
        System.out.print(generator.next() + ", ");
    }

    public static void main(String[] args) {
        CartoonCharacter cartoonCharacter = CartoonCharacter.BOB;
        for(int i = 0; i < 10; i++)
            printNext(cartoonCharacter);
    }
}
