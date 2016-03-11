package atunit;

import java.io.*;
import java.util.*;
import util.*;

/**
 * Created by dell on 2016/3/10.
 */
public class ClassNameFinder {
    public static String thisClass(byte[] classBytes) {
        Map<Integer, Integer> offsetTable = new HashMap<Integer, Integer>();
        Map<Integer, String> classNameTable = new HashMap<Integer, String>();

        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(classBytes));

            int magic = dataInputStream.readInt();
            int minorVersion = dataInputStream.readShort();
            int majorVersion = dataInputStream.readShort();
            int constant_pool_count = dataInputStream.readShort();

            for(int i = 1; i < constant_pool_count; i++) {
                int tag = dataInputStream.read();

                switch (tag) {
                    case 1:
                        int length = dataInputStream.readShort();
                        char[] bytes = new char[length];
                        for(int k = 0; k < length; k++)
                            bytes[k] = (char)dataInputStream.read();
                        String className = new String(bytes);
                        classNameTable.put(i, className);
                        break;
                    case 5:
                    case 6:
                        dataInputStream.readLong();
                        i++;
                        break;
                    case 7:
                        int offset = dataInputStream.readShort();
                        offsetTable.put(i, offset);
                        break;
                    case 8:
                        dataInputStream.readShort();
                        break;
                    case 3:
                    case 4:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        dataInputStream.readInt();
                        break;
                    default:
                        throw new RuntimeException("Bad tag " + tag);
                }
            }

            short access_flags = dataInputStream.readShort();
            int this_class = dataInputStream.readShort();
            int super_class = dataInputStream.readShort();

            return classNameTable.get(offsetTable.get(this_class)).replace('/', '.');
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length > 0) {
            for(String arg : args)
                System.out.println(thisClass(BinaryFile.read(arg)));
        } else {
            for(File classFile : Directory.walk(".", ".*\\.class"))
                System.out.println(thisClass(BinaryFile.read(classFile)));
        }
    }
}
