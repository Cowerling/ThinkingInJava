package io;

import java.io.*;

/**
 * Created by dell on 2015/12/18.
 */
public class FormattedMemoryInput {
    public static void main(String[] args) throws IOException {
        try {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(BufferedInputFile.read("E:\\ThinkingInJava\\src\\io\\BufferedInputFile.java").getBytes()));
            while (true) {
                System.out.print((char)in.readByte());
            }
        } catch (EOFException e) {
            System.out.println("End of stream");
        }
    }
}
