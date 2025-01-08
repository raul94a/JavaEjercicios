package threaded_java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceMain {
    public static void main(String[] args) {

        Runnable runnable = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("Calling runnable in thread " + Thread.currentThread().getName());
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        };

        Callable<Integer> callableTask = () -> {
            try {
                Thread.sleep(300);
                System.out.println("Calling callableTask in thread " + Thread.currentThread().getName());
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
                return -1;
            }
            return 100;
        };

        var executor = new ThreadPoolExecutor(7, 7, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        List<Callable<Integer>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        executor.submit(runnable);
        Future<Integer> data = executor.submit(callableTask);
        List<Runnable> notExecutedTasks = new ArrayList<>();
       // executor.shutdown();

        try {
            System.out.println("Data contains " + data.get());

           // notExecutedTasks = executor.shutdownNow();

            List<Future<Integer>> listData = executor.invokeAll(callableTasks);
            for (Future<Integer> futureData: listData){
                System.out.println("Fetching future data: " + futureData.get());
            }

        } catch (Exception e) {
            System.out.println("Error fetching future data");
        }
        System.out.println("Number of not executed tasks: " + notExecutedTasks.size());
        // recommended by oracle
        // combination of await termination + shutdown + shutdownNow
        executor.shutdown();
        try {
            if (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        executor.shutdownNow();



    }
}
