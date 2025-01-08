package threaded_java;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private final Semaphore semaphore;

    public SemaphoreExample(int permits) {
        this.semaphore = new Semaphore(permits);
    }

    public void accessMethod() {
        try {
            // Attempt to acquire a permit
            semaphore.acquire();
            try {
                System.out.println(Thread.currentThread().getName() + " is accessing the method.");
                // Simulate some work
                Thread.sleep(1000);
            } finally {
                // Ensure the permit is released even if an exception occurs
                semaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        SemaphoreExample example = new SemaphoreExample(1); // Allow 2 threads to access at once

        // Starting 5 threads to show the effect of semaphore
        for (int i = 0; i < 5; i++) {
            new Thread(example::accessMethod, "Thread " + i).start();
        }
    }
}