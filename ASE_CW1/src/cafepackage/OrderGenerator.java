package cafepackage;

import java.util.ArrayList;
import java.util.Date;

public class OrderGenerator {
	private ItemCollection menu;
	
	public OrderGenerator(ItemCollection menu) {
		this.menu = menu;
	}
	
	/**
	 * Generates a random order.
	 * Will use latest customer id, current date and random items
	 * @param menu The collection of items to selected randomly from
	 * @return The randomly generated order
	 */
	public Order GenerateOrder() {
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
}
