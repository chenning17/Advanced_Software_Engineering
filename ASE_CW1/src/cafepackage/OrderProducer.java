package cafepackage;


import Part_2.LogFile;

public class OrderProducer implements Runnable {
	private OrderCollection allOrders;
	private OrderQueue queue;
	private long actualSleepTime;
	private static final long DEFAULTSLEEPTIME = 1; //minimum time taken between adding orders
	
	private ItemCollection menu;
	private OnlineOrderQueue online;
	
	public OrderProducer(OrderCollection orders, long timeModifier, OrderQueue q) {
		this.allOrders = orders;
		this.queue = q;
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier; //allows changing of speed of orders being added to queue
	}

	@Override
	public void run() {
		for(Order order: allOrders) {
				try {
					while(!SimulationTime.getInstance().getCurrentDateTime().equals(order.getTimestamp())) {
						Thread.sleep(this.actualSleepTime);
						SimulationTime.getInstance().increment();
						System.out.println(SimulationTime.getInstance().getCurrentDateTime().toString());
					}					
				}
				catch (InterruptedException e) {
					//do nothing
				}
				finally {
					//TODO remember to update this value so it is never larger than the amount of customers left in 
					//the csv (currently if there are 2 customers left to be added and the random int is 4,
					//will print "A group of 4 joined the queue" when only 2 were added.
						LogFile.getInstance().writeToLogFile("1 person joined the queue");
				}
			
			queue.put(order);
			LogFile.getInstance().writeToLogFile("\tCustomer " + order.getCustomerId() + " joined the queue");
		}
		this.queue.markFinished();	
	}
}
