package threaded_java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Hive {
    public static void main(String[] args) throws InterruptedException {
        var queen = new QueenBee();
        List<Flower> flowers = new ArrayList<>();
        for (var i = 0; i < 50000; i++) {
            flowers.add(new Flower(i));
        }
        List<WorkerBee> workerBees = new ArrayList<>();
        for (var i = 0; i < 10; i++) {
            workerBees.add(new WorkerBee(i, flowers,queen));
        }
        int total = 0;
        for (Flower f : flowers) {
            total += f.nectarNr;
        }
        System.out.println("Sum of nectar is " + total);
        Thread.sleep(2000);

        workerBees.forEach(WorkerBee::start);

        workerBees.forEach((b) -> {
            try {
                b.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Recollected nectar is " + queen.nectar);
        total = 0;
        for (Flower f : flowers) {
            total += f.nectarNr;
        }
        System.out.println("Remaining nectar is " + total);


    }
}


class QueenBee {

    int nectar = 0;

    public synchronized void store(int nectarQty) {
        nectar += nectarQty;
    }


}


class WorkerBee extends Thread {
    List<Flower> flowers;
    QueenBee queen;
    int recollectedNectar = 0;
    int id;

    public WorkerBee(int id, List<Flower> flowers, QueenBee queen) {
        this.id = id;
        this.flowers = flowers;
        this.queen = queen;
    }

    public void recollect() {
        for (var f : flowers) {
            var permits = f.semaphore.availablePermits();
            if (f.semaphore.availablePermits() == 0) {
                continue;
            }
            while (f.hasNectar()) {
                try {
                    System.out.println("Bee " + id + " is now recollecting");
                    recollectedNectar += f.getNectar();
                    queen.store(recollectedNectar);
                    recollectedNectar = 0;
                    System.out.println("Bee " + id + " is returning back to flowers in thread " + Thread.currentThread().getName());
                //  Thread.sleep(500);
                } catch (Exception ignore) {
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Starting a new Thread for recollection " + Thread.currentThread().getName());
        recollect();
    }

}

class Flower {
    final int id;
    public int nectarNr;
    public final Semaphore semaphore = new Semaphore(1);

    public Flower(int id) {
        this.id = id;
        this.nectarNr = (int) (Math.random() * 20) + 1;
    }


    public int getNectar() {
        int nectarQty = 0;
        try {
            semaphore.acquire();


            System.out.println("Recollecting flower " + id + " with nectar qty: " + nectarNr);
            nectarQty = Math.min(nectarNr, 3);
            nectarNr -= nectarQty;


        } catch (Exception ignore) {
        } finally {
            semaphore.release();
        }

        return nectarQty;
    }

    public boolean hasNectar() {
        return nectarNr > 0;
    }


}
