package io;

import java.nio.*;
import java.nio.channels.*;
import java.io.*;

/**
 * Created by dell on 2016/1/8.
 */
public class GetChannel {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws Exception {
        FileChannel fileChannel = new FileOutputStream("data.txt").getChannel();
        fileChannel.write(ByteBuffer.wrap("Some text ".getBytes()));
        fileChannel.close();

        fileChannel = new RandomAccessFile("data.txt", "rw").getChannel();
        fileChannel.position(fileChannel.size());
        fileChannel.write(ByteBuffer.wrap("Some more".getBytes()));
        fileChannel.close();

        fileChannel = new FileInputStream("data.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fileChannel.read(buffer);
        buffer.flip();
        while(buffer.hasRemaining())
            System.out.print((char)buffer.get());
    }
}
