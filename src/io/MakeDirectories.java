package io;

import java.io.*;

/**
 * Created by dell on 2015/12/17.
 */
public class MakeDirectories {
    private static void usage() {
        System.err.println("Usage:MakeDirectories path1 ...\n"
                + "Creates each path\n"
                + "Usage:MakeDirectories -d path1 ...\n"
                + "Deletes each path\n"
                + "Usage:MakeDirectories -r path1 path2\n"
                + "Renames from path1 to path2");
        System.exit(1);
    }

    private static void fileData(File file) {
        System.out.println(
                "Absolute path: " + file.getAbsolutePath() +
                "\n Can read: " + file.canRead() +
                "\n Can write: " + file.canWrite() +
                "\n getName: " + file.getName() +
                "\n getParent: " + file.getParent() +
                "\n getPath: " + file.getPath() +
                "\n length: " + file.length() +
                "\n lastModified: " + file.lastModified());

        if(file.isFile())
            System.out.println("It's a file");
        else if(file.isDirectory())
            System.out.println("It's a directory");
    }

    public static void main(String[] args) {
        if(args.length < 1) usage();

        if(args[0].equals("-r")) {
            if(args.length != 3) usage();

            File oldName = new File(args[1]), newName = new File(args[2]);
            oldName.renameTo(newName);
            fileData(oldName);
            fileData(newName);

            return;
        }

        int count = 0;
        boolean delete = false;

        if(args[0].equals("-d"))
            delete = true;

        while (++count < args.length) {
            File file = new File(args[count]);

            if(file.exists()) {
                System.out.println(file + "exists");
                if(delete) {
                    System.out.println("deleting..." + file);
                    file.delete();
                }
            } else {
                if(!delete) {
                    file.mkdir();
                    System.out.println("created " + file);
                }
            }
        }
    }
}
