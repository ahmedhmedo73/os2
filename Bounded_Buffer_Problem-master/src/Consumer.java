import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Consumer extends Thread {
	private final Semaphore Mutex;// to check critical sections
	private Semaphore fillcount;
	private Semaphore emptycount;
	private Random randGen;
	private Queue<Integer> queue;

	public Consumer(Semaphore Mutex, Semaphore fillcount, Semaphore emptycount, Queue<Integer> queue) {
		this.Mutex = Mutex;
		this.fillcount = fillcount;
		this.emptycount = emptycount;
		randGen = new Random(1);
		this.queue = queue;
	}

	@Override
	public void run() {
		for (int i = 0; i <= 10; i++) {
			try {
				if (fillcount.availablePermits() > 0) {

					fillcount.acquire();
					Mutex.acquire();

					System.out.println("Consumer consume " + queue.poll());

					Mutex.release();
					emptycount.release();

				} else {
					System.out.println("The queue is empty consumer can't consume");
				}
				sleep(Math.abs(randGen.nextInt()) % 2000 + 1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
