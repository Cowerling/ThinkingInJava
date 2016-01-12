package io;

import java.nio.*;
import java.nio.channels.*;
import java.io.*;

/**
 * Created by dell on 2016/1/12.
 */
public class MappedIO {
    private static int numOfInts = 4000000;
    private static int numOfUbuffInts = 200000;

    private abstract static class Tester {
        private String name;

        public Tester(String name) { this.name = name; }

        public void runTest() {
            System.out.print(name + ": ");
            try {
                long start = System.nanoTime();
                test();
                long duration = System.nanoTime() - start;
                System.out.format("%.2f\n", duration / 1.0e9);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public abstract void test() throws IOException;
    }

    private static Tester[] tests = {
        new Tester("Stream Write") {
            @Override
            public void test() throws IOException {
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("temp.tmp"))));
                for(int i = 0; i < numOfInts; i++)
                    dataOutputStream.writeInt(i);
                dataOutputStream.close();
            }
        },
        new Tester("Mapped Write") {
            @Override
            public void test() throws IOException {
                FileChannel fileChannel = new RandomAccessFile("temp.tmp", "rw").getChannel();
                IntBuffer intBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size()).asIntBuffer();
                for(int i = 0; i < numOfInts; i++)
                    intBuffer.put(i);
                fileChannel.close();
            }
        },
        new Tester("Stream Read") {
            @Override
            public void test() throws IOException {
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream("temp.tmp")));
                for(int i = 0; i < numOfInts; i++)
                    dataInputStream.readInt();
                dataInputStream.close();
            }
        },
        new Tester("Mapped Read") {
            @Override
            public void test() throws IOException {
                FileChannel fileChannel = new FileInputStream(new File("temp.tmp")).getChannel();
                IntBuffer intBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size()).asIntBuffer();
                while (intBuffer.hasRemaining())
                    intBuffer.get();
                fileChannel.close();
            }
        },
        new Tester("Stream Read/Write") {
            @Override
            public void test() throws IOException {
                RandomAccessFile randomAccessFile = new RandomAccessFile(new File("temp.tmp"), "rw");
                randomAccessFile.writeInt(1);
                for(int i = 0; i < numOfUbuffInts; i++) {
                    randomAccessFile.seek(randomAccessFile.length() - 4);
                    randomAccessFile.writeInt(randomAccessFile.readInt());
                }
                randomAccessFile.close();
            }
        },
        new Tester("Mapped Read/Write") {
            @Override
            public void test() throws IOException {
                FileChannel fileChannel = new RandomAccessFile(new File("temp.tmp"), "rw").getChannel();
                IntBuffer intBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size()).asIntBuffer();
                intBuffer.put(0);
                for(int i = 1; i < numOfUbuffInts; i++)
                    intBuffer.put(intBuffer.get(i - 1));
                fileChannel.close();
            }
        }
    };

    public static void main(String[] args) {
        for(Tester test : tests)
            test.runTest();
    }
}
