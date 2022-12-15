public class Consumer extends Thread {
	private final Buffer buffer;
	private final int id;

	public Consumer(Buffer buffer, int id) {
		this.buffer = buffer;
		this.id = id;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.buffer.extract(this.id);
				sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}