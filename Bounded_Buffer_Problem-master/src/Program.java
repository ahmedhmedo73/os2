import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Program {

	public static final int BUFFER_SIZE = 3;
	private Semaphore fillcount;
	private Semaphore emptycount;
	private Queue<Integer> queue;

	public Program() {
		fillcount = new Semaphore(0);
		emptycount = new Semaphore(BUFFER_SIZE);
		queue = new LinkedList<>();
		Producer prod = new Producer(fillcount, emptycount, queue);
		Consumer cons = new Consumer(fillcount, emptycount, queue);
		prod.start();
		cons.start();
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Program prog = new Program();
	}
	
}
