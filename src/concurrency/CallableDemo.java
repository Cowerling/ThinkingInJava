package concurrency;

import java.util.concurrent.*;
import java.util.*;

/**
 * Created by dell on 2016/3/15.
 */
class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    public String call() {
        return "result of TaskWithResult " + id;
    }
}

public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();
        for(int i = 0; i < 10; i++)
            results.add(executorService.submit(new TaskWithResult(i)));
        for(Future<String> result : results) {
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                return;
            } catch (ExecutionException e) {
                System.out.println(e);
            } finally {
                executorService.shutdown();
            }
        }
    }
}
