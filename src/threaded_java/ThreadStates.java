package threaded_java;

/**
 * THREAD STATES
 *
 * NEW
 * RUNNABLE
 * BLOCKED
 * WAITING
 * TIMED_WAITING
 * TERMINATED
 */

public class ThreadStates {
    public static void main(String[] args) {
        var t1 = new Thread(new DemoBlockedRunnable());
        var t2 = new Thread(new DemoBlockedRunnable());

        t1.start();
        t2.start();

        System.out.println("T1 STATE => " + t1.getState());
        // t2 is blocked because commonResources is synchronized and it executes a infinite loop
        System.out.println("T2 STATE => " + t2.getState());
        System.exit(0);


    }
}

class DemoBlockedRunnable implements Runnable {
    @Override
    public void run() {
        commonResources();
    }

    // Synchronized allows to access once in a row for different threads
    // e.g: First Thread1, when it finished Thread2 start this method...
    public static synchronized void commonResources (){
        while   (true) {

        }
    }
}