package io;

import java.nio.*;
import java.util.*;

/**
 * Created by dell on 2016/1/12.
 */
public class Endians {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[12]);
        byteBuffer.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(byteBuffer.array()));

        byteBuffer.rewind();
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        byteBuffer.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(byteBuffer.array()));

        byteBuffer.rewind();
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.asCharBuffer().put("abcdef");
        System.out.print(Arrays.toString(byteBuffer.array()));
    }
}
