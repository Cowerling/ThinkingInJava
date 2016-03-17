package util;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/16.
 */
public class DaemonThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }
}
