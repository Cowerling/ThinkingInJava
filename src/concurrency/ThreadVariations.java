package concurrency;

import java.util.concurrent.*;

/**
 * Created by dell on 2016/3/17.
 */
class InnerThread1 {
    private int countDown = 5;
    private Inner inner;

    private class Inner extends Thread {
        Inner(String name) {
            super(name);
            start();
        }

        public void run() {
            try {
                while (true) {
                    System.out.println(this);
                    if(--countDown == 0) return;
                    sleep(10);
                }
            } catch (InterruptedException e) {
                System.out.println("sleep() Interrupted");
            }
        }

        public String toString() {
            return getName() + ": " + countDown;
        }
    }

    public InnerThread1(String name) {
        inner = new Inner(name);
    }
}

class InnerThread2 {
    private int countDown = 5;
    private Thread thread;

    public InnerThread2(String name) {
        thread = new Thread(name) {
            public void run() {
                try {
                    while (true) {
                        System.out.println(this);
                        if(--countDown == 0) return;
                        sleep(10);
                    }
                } catch (InterruptedException e) {
                    System.out.println("sleep() Interrupted");
                }
            }

            public String toString() {
                return getName() + ": " + countDown;
            }
        };

        thread.start();
    }
}

class InnerRunnable1 {
    private int countDown = 5;
    private Inner inner;

    private class Inner implements Runnable {
        Thread thread;

        Inner(String name) {
            thread = new Thread(this, name);
            thread.start();
        }

        public void run() {
            try {
                while (true) {
                    System.out.println(this);
                    if(--countDown == 0) return;
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                System.out.println("sleep() Interrupted");
            }
        }

        public String toString() {
            return thread.getName() + ": " + countDown;
        }
    }

    public InnerRunnable1(String name) {
        inner = new Inner(name);
    }
}

class InnerRunnable2 {
    private int countDown = 5;
    private Thread thread;

    public InnerRunnable2(String name) {
        thread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        System.out.println(this);
                        if(--countDown == 0) return;
                        TimeUnit.MILLISECONDS.sleep(10);
                    }
                } catch (InterruptedException e) {
                    System.out.print("sleep() Interrupted");
                }
            }

            public String toString() {
                return Thread.currentThread().getName() + ": " + countDown;
            }
        }, name);

        thread.start();
    }
}

class ThreadMethod {
    private int countDown = 5;
    private Thread thread;
    private String name;

    public ThreadMethod(String name) {
        this.name = name;
    }

    public void runTask() {
        if(thread == null) {
            thread = new Thread(name) {
                public void run() {
                    try {
                        while (true) {
                            System.out.println(this);
                            if(--countDown == 0) return;
                            sleep(10);
                        }
                    } catch (InterruptedException e) {
                        System.out.print("sleep() Interrupted");
                    }
                }

                public String toString() {
                    return getName() + ": " + countDown;
                }
            };

            thread.start();
        }
    }
}

public class ThreadVariations {
    public static void main(String[] args) {
        new InnerThread1("InnerThread1");
        new InnerThread2("InnerThread2");
        new InnerRunnable1("InnerRunnable1");
        new InnerRunnable2("InnerRunnable2");
        new ThreadMethod("ThreadMethod").runTask();
    }
}
