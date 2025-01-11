package threaded_java.wait_notify;

import java.util.concurrent.ThreadLocalRandom;

public class Receiver implements Runnable {
    private Data data;
    public Receiver(Data data){this.data = data;}

    @Override
    public void run() {

        for (String packetReceived = data.receive();
             !packetReceived.equals("{STOP}");
             packetReceived = data.receive()
        ) {
            System.out.println(packetReceived);


            try {
               Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted IN RECEIVER");
            }

        }
    }
}
