import java.util.ArrayList;

public class Buffer {
    private ArrayList<Long> list = new ArrayList<>();
    private final int bufferSize;

    public Buffer(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public synchronized void insert(long item, int prodId) throws InterruptedException {
        while (list.size() == this.bufferSize) {
            System.out.println("BUFFER FULL :: Insert waiting for consumption");
            wait();
        }

        System.out.println("Producer " + prodId + " Inserted Item " + item);
        list.add(item);
        System.out.println("ITEM COUNT :: " + list.size());

        notifyAll();
    }

    public synchronized void extract(int consId) throws InterruptedException {
        while (list.isEmpty()) {
            System.out.println("BUFFER EMPTY :: Extract waiting for production");
            wait();
        }

        System.out.println("Consumer " + consId + " Consumed " + list.get(0));
        list.remove(0);
        System.out.println("ITEM COUNT :: " + list.size());

        notifyAll();
    }
}