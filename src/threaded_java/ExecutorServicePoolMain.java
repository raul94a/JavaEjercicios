package threaded_java;

import java.util.concurrent.*;

public class ExecutorServicePoolMain {
    public static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (var i = 0; i < 20; i++){
            int finalI = i;
            executorService.submit(()->{
                //System.out.println("Ejecutando el número " + finalI + " en Thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.shutdown();



        executorService.shutdown();

    }
}
