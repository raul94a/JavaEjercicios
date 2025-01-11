package threaded_java.wait_notify;

import java.util.concurrent.ThreadLocalRandom;

public class Sender implements  Runnable {
    private Data data;
    public Sender(Data data){this.data = data;}
    @Override
    public void run(){
        String[] packets = new String[]{
                "¡Bienvenido a las puertas de Moria!",
                "Habla amigo",
                "y....",
                "¡ENTRA!",
                "{STOP}"
        };

        for(String packet : packets){

            try{
                data.send(packet);
                Thread.sleep(ThreadLocalRandom.current().nextInt(500,1500));
            }catch (Exception e){
               // Thread.currentThread().interrupt();
                System.out.println("Thread interrupted IN SENDER");
            }
        }
    }
}
