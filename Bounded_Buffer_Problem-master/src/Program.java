
public class Program {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Started");
		Buffer buffer = new Buffer(3);

		Consumer c1 = new Consumer(buffer, 1);
		Consumer c2 = new Consumer(buffer, 2);
		Producer p1 = new Producer(buffer, 1);
		Producer p2 = new Producer(buffer, 2);

		c1.start();
		c2.start();
		p1.start();
		p2.start();

		c1.join();
		c2.join();
		p1.join();
		p2.join();
	}
}
