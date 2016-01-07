package util;

import java.io.*;

/**
 * Created by cowerling on 16-1-7.
 */
public class BinaryFile {
    public static byte[] read(File file) throws IOException {
        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(file));
        try {
            byte[] data = new byte[bf.available()];
            bf.read(data);
            return data;
        } finally {
            bf.close();
        }
    }

    public static byte[] read(String file) throws IOException {
        return read(new File(file).getAbsoluteFile());
    }
}
