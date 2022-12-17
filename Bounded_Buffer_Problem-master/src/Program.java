import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Program {

	public static final int BUFFER_SIZE = 3;
	private final Semaphore Mutex;//to check critical sections
	private Semaphore fillcount;
	private Semaphore emptycount;
	private Queue<Integer> queue;

	public Program() {
		Mutex = new Semaphore(1,true);// to solve starvation
		fillcount = new Semaphore(0);
		emptycount = new Semaphore(BUFFER_SIZE);
		queue = new LinkedList<>();
		Producer prod = new Producer(Mutex,fillcount, emptycount, queue);
		Consumer cons = new Consumer(Mutex,fillcount, emptycount, queue);
		prod.start();
		cons.start();
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Program prog = new Program();
	}
	
}
