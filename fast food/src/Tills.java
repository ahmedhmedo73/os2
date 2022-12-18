import java.util.concurrent.Semaphore;

public class Tills extends Thread {
    private final Buffer buff;
    private final int foodId;
    private final int tillId;
    private final Semaphore Mutex;// to check critical sections

    public Tills(int tillId, int foodId, Buffer buff, Semaphore Mutex) {
        this.tillId = tillId;
        this.foodId = foodId;
        this.buff = buff;
        this.Mutex = Mutex;
    }

    public void run() {
        while (FastFood.startOrders < FastFood.totalOrders) {
            try {
                FastFood.semTills.acquire();

                while (buff.isFull()) {
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        // Logger.getLogger(Tills.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                try {

                    Mutex.acquire();

                    if (FastFood.startOrders >= FastFood.totalOrders) {
                        FastFood.semTills.release();

                        Mutex.release();
                        return;
                    }
                    Order v = new Order(foodId, tillId);
                    System.out.println("Till number " + tillId + " created a new order " + foodId + " to be processed"
                            + " order " + ++FastFood.startOrders);
                    buff.insert(v);

                    FastFood.semWorker.release();

                    Mutex.release();
                } catch (Exception e) {
                    System.out.println(e);
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}