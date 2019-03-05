package cafepackage;

public class SalesAssistant implements Runnable{
	
	//-	Take order, sleep for constant amount of time, send order complete message to log file.
	
	private OrderQueue queue;
	
	public SalesAssistant(OrderQueue queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 8; i++) {
			try { Thread.sleep(100); }
			catch (InterruptedException e) {}
			queue.get();
			}
	}
	
	public String orderCompleted() {
		return "This Order has been completed";
	}

}
