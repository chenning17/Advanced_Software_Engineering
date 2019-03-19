package cafepackage;

import java.util.ArrayList;

public class OnlineOrderProducer implements Runnable {
	private ItemCollection menu;
	private OnlineOrderQueue onlineOrders;
	private OrderQueue orders;
	
	public OnlineOrderProducer(ItemCollection menu, OnlineOrderQueue online, OrderQueue orders) {
		this.menu = menu;
		this.onlineOrders = online;
		this.orders = orders;
	}
	
	/**
	 * Generates a random order.
	 * Will use latest customer id, current date and random items
	 * @param menu The collection of items to selected randomly from
	 * @return The randomly generated order
	 */
	private Order GenerateOrder() {
		int id = Order.getCurrentCustomerID(); //Get latest customer id
		Date date = SimulationTime.getInstance().getCurrentDateTime(); //Use current date
		ArrayList<Item> items = new ArrayList<Item>();
		int numItems = (int) Math.ceil(Math.random() * 4);
		
		for(int i = 0; i < numItems; i++) {
			items.add(this.menu.randomItem());
		}
		
		Order newOrder = new Order(date, id, items);
		return newOrder;
	}

	@Override
	/**
	 * Randomly generates an order and adds it to the OnlineOrderQueue as long as there are still normal orders
	 * to process
	 */
	public void run() {
		while(!this.orders.isDone() || this.orders.isEmpty()) {
			if(SimulationTime.getInstance().getCurrentDateTime().getHours() < 15) {
				try {
					Thread.sleep(500);
					Order currentOrder = GenerateOrder();
					System.out.println("Generated Order : " + currentOrder.getCustomerId());
					this.onlineOrders.addPending(currentOrder);
					Thread.sleep(2000);
					this.onlineOrders.put(currentOrder);
					
				} catch (Exception e) {
					//do something
				}
			}
			
		}
		System.out.println("Finished Generating Orders");
		this.onlineOrders.markFinished();
	}
	
}
