package io;

import java.io.*;
import java.util.zip.*;
import java.util.*;

/**
 * Created by cowerling on 16-1-14.
 */
public class ZipCompress {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStreamle = new FileOutputStream("test.zip");
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStreamle, new Adler32());
        ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
        zipOutputStream.setComment("A test of Java Zipping");

        for(String arg : args) {
            System.out.println("Writing file " + arg);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(arg));
            zipOutputStream.putNextEntry(new ZipEntry(arg));

            int c;
            while ((c = bufferedReader.read()) != -1)
                bufferedOutputStream.write(c);

            bufferedReader.close();
            bufferedOutputStream.flush();
        }
        bufferedOutputStream.close();
        System.out.println("Checksum: " + checkedOutputStream.getChecksum().getValue());

        System.out.println("Reading file");
        FileInputStream fileInputStream = new FileInputStream("test.zip");
        CheckedInputStream checkedInputStream = new CheckedInputStream(fileInputStream, new Adler32());
        ZipInputStream zipInputStream = new ZipInputStream(checkedInputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            System.out.println("Reading file " + zipEntry);
            int x;
            while ((x = bufferedInputStream.read()) != -1)
                System.out.write(x);
        }

        if(args.length == 1)
            System.out.println("Checksum: " + checkedInputStream.getChecksum().getValue());

        bufferedInputStream.close();

        ZipFile zipFile = new ZipFile("test.zip");
        Enumeration e = zipFile.entries();
        while (e.hasMoreElements()) {
            ZipEntry zipEntry2 = (ZipEntry)e.nextElement();
            System.out.println("File: " + zipEntry2);
        }
    }
}
