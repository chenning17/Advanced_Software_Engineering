package cafepackage;

import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;

public class OrderLoader extends FileInput {
	private OrderCollection orders;
	private ItemCollection menu;
	private String file;
	
	public OrderLoader(String filename) {
		this.orders = new OrderCollection();
		this.file = filename;
		this.menu = new ItemCollection();
	}
	
	public OrderCollection loadOrders() {
		inputFile(this.file);
		return this.orders;
	}

	//Basic line processor for orders
	@Override
	protected void processLine(String inputLine) {
		
		try {
			String parts [] = inputLine.split(",");

			Instant time = Instant.parse(parts[0]); //First value on line is time
			Date timestamp = Date.from(time);
			int customerID = Integer.parseInt(parts[1]); //Second value on line is date
			
			ArrayList<Item> orderItems = new ArrayList<Item>();
			
			//Remaining values on line are items in order
			for(int i = 2; i < parts.length; i++) {
				String itemId = parts[i];
				orderItems.add(this.menu.findItemById(itemId));
			}
			
			Order order = new Order(timestamp, customerID, orderItems);
			this.orders.add(order);
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}		
}
