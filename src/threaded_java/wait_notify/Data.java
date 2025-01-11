package threaded_java.wait_notify;

public class Data {

    public String packet;
    boolean transfer = true;

    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread has been interrupted IN RECEIVE");
            }
        }

        transfer = true;
        String data = packet;
        notifyAll();
        return data;

    }

    public synchronized void send(String data) {
        while (!transfer) {
            try {
                wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread has been interrupted IN SEND + " + e.getMessage());
            }
        }
        transfer = false;
        this.packet = data;
        notifyAll();

    }

}
