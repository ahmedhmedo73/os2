import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Consumer extends Thread{
	
	private Semaphore fillcount;
	private Semaphore emptycount;
	private Random randGen;
	private Queue<Integer> queue;
	
	public Consumer(Semaphore fillcount, Semaphore emptycount, Queue<Integer> queue) {
		this.fillcount = fillcount;
		this.emptycount = emptycount;
		randGen = new Random(1);
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				if (fillcount.availablePermits()>0) {
					System.out.println("Consumer consume " + queue.poll());
					fillcount.acquire();
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
