package io;

import java.nio.*;

/**
 * Created by dell on 2016/1/12.
 */
public class UsingBuffers {
    private static void symmetricScramble(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            buffer.mark();
            char c1 = buffer.get(), c2 = buffer.get();
            buffer.reset();
            buffer.put(c2).put(c1);
        }
    }

    public static void main(String[] args) {
        char[] data = "UsingBuffers".toCharArray();
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 2);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        charBuffer.put(data);

        System.out.println(charBuffer.rewind());

        symmetricScramble(charBuffer);
        System.out.println(charBuffer.rewind());

        symmetricScramble(charBuffer);
        System.out.println(charBuffer.rewind());
    }
}
