package io;

import java.nio.*;

/**
 * Created by dell on 2016/1/11.
 */
public class IntBufferDemo {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BSIZE);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();

        intBuffer.put(new int[]{ 11, 42, 47, 99, 143, 811, 1016 });
        System.out.println(intBuffer.get(3));

        intBuffer.put(3, 1811);
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            int i = intBuffer.get();
            System.out.println(i);
        }
    }
}
