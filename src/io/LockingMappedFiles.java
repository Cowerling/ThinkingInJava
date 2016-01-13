package io;

import java.nio.*;
import java.nio.channels.*;
import java.io.*;

/**
 * Created by cowerling on 16-1-13.
 */
public class LockingMappedFiles {
    static final int LENGTH = 0x8000000;
    static FileChannel fileChannel;

    public static void main(String[] args) throws Exception {
        fileChannel = new RandomAccessFile("test.dat", "rw").getChannel();
        MappedByteBuffer out = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);
        for(int i = 0; i < LENGTH; i++)
            out.put((byte)'x');

        new LockAndModify(out, 0, 0 + LENGTH / 3);
        new LockAndModify(out, LENGTH / 2, LENGTH / 2 + LENGTH / 4);
    }

    private static class LockAndModify extends Thread {
        private ByteBuffer buff;
        private int start, end;

        LockAndModify(ByteBuffer mbb, int start, int end) {
            this.start = start;
            this.end = end;

            mbb.limit(end);
            mbb.position(start);
            buff = mbb.slice();
            start();
        }

        public void run() {
            try {
                FileLock fileLock = fileChannel.lock(start, end - start, false);
                System.out.println("Locked: " + start + " to " + end);
                while (buff.position() < buff.limit() - 1)
                    buff.put((byte)(buff.get() + 1));
                fileLock.release();
                System.out.println("Release: " + start + " to " + end);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
