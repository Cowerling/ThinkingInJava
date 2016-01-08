package io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

/**
 * Created by dell on 2016/1/8.
 */
public class BufferToText {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {
        FileChannel fileChannel = new FileOutputStream("data2.txt").getChannel();
        fileChannel.write(ByteBuffer.wrap("Some text".getBytes()));
        fileChannel.close();

        fileChannel = new FileInputStream("data2.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fileChannel.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding + ": " + Charset.forName(encoding).decode(buffer));

        fileChannel = new FileOutputStream("data2.txt").getChannel();
        fileChannel.write(ByteBuffer.wrap("Some text".getBytes("UTF-16BE")));
        fileChannel.close();

        fileChannel = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fileChannel.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());

        fileChannel = new FileOutputStream("data2.txt").getChannel();
        buffer = ByteBuffer.allocate(24);
        buffer.asCharBuffer().put("Some text");
        fileChannel.write(buffer);
        fileChannel.close();

        fileChannel = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fileChannel.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }
}
