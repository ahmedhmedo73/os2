import java.util.Random;
import java.util.concurrent.Semaphore;

public class Worker extends Thread {
    private final int workerId;
    private final Buffer buff;
    private final Semaphore Mutex;// to check critical sections

    public Worker(int workerId, Buffer buff, Semaphore Mutex) {
        this.workerId = workerId;
        this.buff = buff;
        this.Mutex = Mutex;
    }

    public void run() {
        while (FastFood.totalOrders > FastFood.processedOrders) {
            try {

                FastFood.semWorker.acquire();

                while (buff.isEmpty()) {
                    try {
                        if (FastFood.totalOrders <= FastFood.processedOrders) {
                            FastFood.semWorker.release();
                            return;
                        }
                        sleep(100);
                    } catch (InterruptedException ex) {
                        // Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                Mutex.acquire();
                System.out.println("Worker: " + workerId + " completed order number " + buff.remove()
                        + " total orders processed so far: " + ++FastFood.processedOrders);

                FastFood.semTills.release();
                Mutex.release();
                try {
                    Random n = new Random();
                    int time = n.nextInt(100) + 1;
                    sleep(time);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}