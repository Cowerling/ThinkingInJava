package util;

import java.io.*;

/**
 * Created by dell on 2015/12/17.
 */
public class ProcessFiles {
    public interface Strategy {
        void process(File file);
    }

    private Strategy strategy;
    private String extension;

    public ProcessFiles(Strategy strategy, String extension) {
        this.strategy = strategy;
        this.extension = extension;
    }

    public void start(String[] args) {
        try {
            if(args.length == 0)
                processDirectoryTree(new File("."));
            else
                for(String arg : args) {
                    File fileArg = new File(arg);
                    if(fileArg.isDirectory())
                        processDirectoryTree(fileArg);
                    else {
                        if(!arg.endsWith("." + extension))
                            arg += "." + extension;
                        strategy.process(new File(arg).getCanonicalFile());
                    }
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processDirectoryTree(File root) throws IOException {
        for(File file : Directory.walk(root.getCanonicalPath(), ".*\\." + extension))
            strategy.process(file.getCanonicalFile());
    }

    public static void main(String[] args) {
        new ProcessFiles(new ProcessFiles.Strategy() {
            public void process(File file) {
                System.out.println(file);
            }
        }, "java").start(args);
    }
}
