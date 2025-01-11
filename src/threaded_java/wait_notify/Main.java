package threaded_java.wait_notify;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var data = new Data();

        var sender = new Thread(new Sender(data));
        var receiver = new Thread(new Receiver(data));

        sender.start();
        receiver.start();

    }
}
