package generics;

import generics.watercolors.*;
import util.Sets;

import java.util.*;
import static generics.watercolors.Watercolors.*;

/**
 * Created by cowerling on 15-11-19.
 */
public class WatercolorSets {
    public static void main(String[] args) {
        Set<Watercolors> set1 = EnumSet.range(BRILLIANT_RED, VIRIDIAN_HUE);
        Set<Watercolors> set2 = EnumSet.range(CERULEAN_BLUE_HUE, BURNT_UMBER);

        System.out.println("set1: " + set1);
        System.out.println("set2: " + set2);

        System.out.println("union(set1, set2): " + Sets.union(set1, set2));

        Set<Watercolors> subset = Sets.interection(set1, set2);
        System.out.println("interection(set1, set2): " + subset);

        System.out.println("difference(set1, subset): " + Sets.difference(set1, subset));
        System.out.println("difference(set2, subset): " + Sets.difference(set2, subset));

        System.out.println("complement(set1, set2): " + Sets.complement(set1, set2));
    }
}
