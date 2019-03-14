package cafepackage;

import java.util.Random;

import Part_2.LogFile;

public class OrderProducer implements Runnable {
	private OrderCollection allOrders;
	private OrderQueue queue;
	private long actualSleepTime;
	private static final long DEFAULTSLEEPTIME = 1000; //minimum time taken between adding orders
	private int maxGroupSize = 4; //max no of customers added to queue at once
	private int delayModifier = 2; //multiplies delay between adding customers by a random number between 1 and delayModifier
	
	private ItemCollection menu;
	private OnlineOrderQueue online;
	
	public OrderProducer(OrderCollection orders, long timeModifier, OrderQueue q) {
		this.allOrders = orders;
		this.queue = q;
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier; //allows changing of speed of orders being added to queue
	}

	@Override
	public void run() {
		int groupSize = 1;
		for(Order order: allOrders) {
			groupSize--;
			if(groupSize == 0) {
				try {
					Thread.sleep(this.actualSleepTime * getRandomInt(delayModifier));
				}
				catch (InterruptedException e) {
					//do nothing
				}
				finally {
					//TODO remember to update this value so it is never larger than the amount of customers left in 
					//the csv (currently if there are 2 customers left to be added and the random int is 4,
					//will print "A group of 4 joined the queue" when only 2 were added.
					groupSize = getRandomInt(this.maxGroupSize);
					if(groupSize == 1) {
						LogFile.getInstance().writeToLogFile("1 person joined the queue");
					} else {
						LogFile.getInstance().writeToLogFile("A group of " + groupSize + " joined the queue");
					}
				}
			}
			
			queue.put(order);
			LogFile.getInstance().writeToLogFile("\tCustomer " + order.getCustomerId() + " joined the queue");
		}
		this.queue.markFinished();	
	}
	
	/**
	 * Get a random integer between 1 and the upper bound
	 * @param upperBound Largest int that can be returned
	 * @return random int between one and upperBound
	 */
	private int getRandomInt(int upperBound) {
		Random r = new Random();
		return r.ints(1, (upperBound + 1)).limit(1).findFirst().getAsInt();
	}
}
