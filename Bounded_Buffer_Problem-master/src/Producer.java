import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producer extends Thread {
	private final Semaphore Mutex;// to check critical sections
	private Semaphore fillcount;
	private Semaphore emptycount;
	private Random randGen;
	private Queue<Integer> queue;
	private int n = 1;

	public Producer(Semaphore Mutex, Semaphore fillcount, Semaphore emptycount, Queue<Integer> queue) {
		this.Mutex = Mutex;
		this.fillcount = fillcount;
		this.emptycount = emptycount;
		randGen = new Random(1);
		this.queue = queue;
	}

	@Override
	public void run() {
		while (this.n <= 10) {
			try {
				Mutex.acquire();
				if (emptycount.availablePermits() > 0) {

					emptycount.acquire();

					queue.add(this.n);
					this.n ++;
					fillcount.release();

				} else {
					System.out.println("The queue is full producer can't produce");
				}
				Mutex.release();
				sleep(Math.abs(randGen.nextInt()) % 1000 + 1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
