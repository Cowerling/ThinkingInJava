package io;

import java.nio.channels.*;
import java.io.*;

/**
 * Created by dell on 2016/1/8.
 */
public class TransferTo {
    public static void main(String[] args) throws Exception {
        if(args.length != 2) {
            System.out.println("arguments: sourcefile destfile");
            System.exit(1);
        }

        FileChannel in = new FileInputStream(args[0]).getChannel(), out = new FileOutputStream(args[1]).getChannel();
        in.transferTo(0, in.size(), out);
    }
}
