package threaded_java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class Hive {
    public static void main(String[] args) throws InterruptedException, IOException {
        var start = System.currentTimeMillis();
        var queen = new QueenBee();

        List<Flower> flowers = new ArrayList<>();
        for (var i = 0; i < 10; i++) {
            var flower = new Flower(i);
            flowers.add(flower);
        }

        List<WorkerBee> workerBees = new ArrayList<>();
        for (var i = 0; i <3; i++) {
            workerBees.add(new WorkerBee(i, flowers, queen));
        }
        int total = 0;
        for (Flower f : flowers) {
            total += f.nectarNr;
        }
        System.out.println("Sum of nectar is " + total);
        Thread.sleep(750);

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
        System.out.println("Bee performace: " + queen.beePerformanceMap);
        total = 0;
        for (var entries: queen.beePerformanceMap.entrySet()){
            total += entries.getValue();
        }
        System.out.println("Entries total: " + total);
        var end = System.currentTimeMillis();
        var totalMs = end - start;
        double performance = (total / (double)totalMs * 1000);
        System.out.println("Bees performace " +   performance);
        System.out.println(totalMs + " ms");

    }
}


class QueenBee {

    int nectar = 0;
    Map<Integer, Integer> beePerformanceMap = new ConcurrentHashMap<>();
    public synchronized void store(int nectarQty, WorkerBee bee) {
        nectar += nectarQty;
        beePerformanceMap.merge(bee.id, nectarQty, Integer::sum);
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

    public void recollect() throws IOException {
        for (var f : flowers) {
            var permits = f.semaphore.availablePermits();

            if (permits == 0 || !f.hasNectar()) {
                continue;
            }
            while (f.hasNectar() && f.semaphore.availablePermits() == 1) {
                try {
                    //  System.out.println("Bee " + id + " is now recollecting the flower "+ f.id + " with " + f.nectarNr + " nectar units with permits " + f.semaphore.availablePermits() );
                    final int nectar = f.getNectar();
                    recollectedNectar += nectar;
                    queen.store(recollectedNectar, this);
                    recollectedNectar = 0;
                  //  var recollection = "Bee " + id + " is returning back to flower " + f.id + " with " + nectar + " nectar units in thread " + Thread.currentThread().getName();
                  //  System.out.println(recollection);
                } catch (Exception ignore) {
                }
            }
        }

    }

    @Override
    public void run() {
        //System.out.println("Starting a new Thread for recollection " + Thread.currentThread().getName());
        try {
            recollect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        if (nectarNr == 0 || semaphore.availablePermits() == 0) return nectarQty;
        try {
            semaphore.acquire();
            //System.out.println("Recollecting flower " + id + " with nectar qty: " + nectarNr);
            nectarQty = Math.min(nectarNr, 5);
            nectarNr -= nectarQty;
            Thread.sleep(15);


        } catch (Exception ignore) {
        } finally {
            semaphore.release();
        }

        return nectarQty;
    }

    public synchronized boolean hasNectar() {
        return nectarNr > 0;
    }


}
