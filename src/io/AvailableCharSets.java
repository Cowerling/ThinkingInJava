package io;

import java.nio.charset.*;
import java.util.*;

/**
 * Created by dell on 2016/1/11.
 */
public class AvailableCharSets {
    public static void main(String[] args) {
        SortedMap<String, Charset> charSets = Charset.availableCharsets();
        Iterator<String> it = charSets.keySet().iterator();
        while (it.hasNext()) {
            String charSetName = it.next();
            System.out.print(charSetName);

            Iterator aliases = charSets.get(charSetName).aliases().iterator();
            if(aliases.hasNext())
                System.out.print(": ");
            while (aliases.hasNext()) {
                System.out.print(aliases.next());
                if(aliases.hasNext())
                    System.out.print(", ");
            }
            System.out.println();
        }

        System.out.println(System.getProperty("file.encoding"));
    }
}
