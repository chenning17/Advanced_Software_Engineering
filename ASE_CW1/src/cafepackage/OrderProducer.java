package cafepackage;

public class OrderProducer implements Runnable {
	private OrderCollection allOrders;
	private OrderQueue queue;
	private long actualSleepTime;
	private static final long DEFAULTSLEEPTIME = 250; //minimum time taken between adding orders
	
	public OrderProducer(OrderCollection orders, long timeModifier, OrderQueue q) {
		this.allOrders = orders;
		this.queue = q;
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier; //allows changing of speed of orders being added to queue
	}

	@Override
	public void run() {
		for(Order o: allOrders) {
			try {
				Thread.sleep(this.actualSleepTime);
			}
			catch (InterruptedException e) {
				//do nothing
			}
			queue.put(o);
			System.out.println("Adding: " + o.getItem().getName());
		}
		this.queue.markFinished();
		
	}
}
