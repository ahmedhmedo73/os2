public class Producer extends Thread {
	private final Buffer buffer;
	private final int id;

	public Producer(Buffer buffer, int id) {
		this.buffer = buffer;
		this.id = id;
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.buffer.insert(System.currentTimeMillis(), this.id);
				sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}