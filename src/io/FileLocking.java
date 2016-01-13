package io;

import java.nio.channels.*;
import java.util.concurrent.*;
import java.io.*;

/**
 * Created by cowerling on 16-1-13.
 */
public class FileLocking {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("file.txt");
        FileLock fileLock = fileOutputStream.getChannel().tryLock();
        if(fileLock != null) {
            System.out.println("Locked file");
            TimeUnit.MILLISECONDS.sleep(100);
            fileLock.release();
            System.out.println("Release Lock");
        }
        fileOutputStream.close();
    }
}
