package cafepackage;

import java.util.ArrayList;
import java.util.Date;

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
		Date date = new Date(); //Use current date
		ArrayList<Item> items = new ArrayList<Item>();
		int numItems = (int) Math.ceil(Math.random() * 4);
		
		for(int i = 0; i < numItems; i++) {
			items.add(this.menu.randomItem());
		}
		
		Order newOrder = new Order(date, id, items);
		return newOrder;
	}

	@Override
	public void run() {
		while(!this.orders.isDone()) {
			try {
				Thread.sleep(2000);
				Order currentOrder = GenerateOrder();
				System.out.println("Generated Order : " + currentOrder.getCustomerId());
				//for(Item i : currentOrder.getItems()) {
				//	System.out.println(i.getName());
				//}
				this.onlineOrders.addPending(currentOrder);
				
			} catch (Exception e) {
				//do something
			}
		}
		System.out.println("Finished Generating Orders");
		this.onlineOrders.printPending();

	}
	
}
