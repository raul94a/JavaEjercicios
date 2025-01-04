package threaded_java;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureMain {

    public static void main(String[] args) {

        // No cancellation
        System.out.println("Starting CompletableFuture with NO Cancellation");
        final float number = 5.5f;

        CompletableFuture<Float> cf = new CompletableFuture<>();
        cf.thenApply(x -> x * x)
                .thenAccept(x -> System.out.println("Number is " + x));

        System.out.println("Computation before CompletableFuture is done or cancelled");
        printCompletableFutureStatus(cf,number);
        System.out.println();
        // Cancellation
        var str = "ABCDEFG";
        System.out.println("Starting CompletableFuture with Cancellation");

        CompletableFuture<String> cfString = new CompletableFuture<>();

        cfString.thenApply(x -> str.repeat(2)).thenAccept(x -> {

            System.out.println("String doubled " + x);
        });


        cfString.thenApply(x -> str.repeat(3)).thenAccept(x -> System.out.println("String tripled " + x ));
        System.out.println("Cancellation: This will be printed in first place");
        System.out.println("Cancelling!!!" + cfString.cancel(true));

        printCompletableFutureStatus(cfString,str);





    }

    public static <T> void printCompletableFutureStatus(CompletableFuture<T> cf, T value){
        var completed = cf.complete(value);
        System.out.println("Has been cancelled? " + cf.isCancelled());
        System.out.println("Is done? "  + cf.isDone());
        System.out.println("Is Completed? " + completed);
    }
}
