package util;

import sun.reflect.generics.tree.Tree;

import java.util.concurrent.SynchronousQueue;
import java.util.regex.*;
import java.io.*;
import java.util.*;

/**
 * Created by dell on 2015/12/17.
 */
public final class Directory {
    public static File[] local(File dir, final String regex) {
        return dir.listFiles(new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);

            public boolean accept(File dir, String name) {
                return false;
            }
        });
    }

    public static File[] local(String path, final String regex) {
        return local(new File(path), regex);
    }

    public static class TreeInfo implements Iterable<File> {
        public List<File> files = new ArrayList<File>();
        public List<File> dirs = new ArrayList<File>();

        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        public String toString() {
            return "dirs: " + PPrint.format(dirs) + "\n\nfiles: " + PPrint.format(files);
        }
    }

    public static TreeInfo walk(String start, String regex) {
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(File start, String regex) {
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(String start) {
        return recurseDirs(new File(start), ".*");
    }

    public static TreeInfo walk(File start) {
        return recurseDirs(start, ".*");
    }

    static TreeInfo recurseDirs(File startDir, String regex) {
        TreeInfo result = new TreeInfo();

        for(File item : startDir.listFiles()) {
            if(item.isDirectory()) {
                result.dirs.add(item);
                result.addAll(recurseDirs(item, regex));
            } else {
                if(item.getName().matches(regex))
                    result.files.add(item);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        if(args.length == 0)
            System.out.println(walk("."));
        else
            for(String arg : args)
                System.out.println(walk(arg));
    }
}
