package cafepackage.model;

import java.util.ArrayList;

import cafepackage.model.items.Item;
import cafepackage.model.items.ItemCollection;

public class OrderLoader extends FileInput {
	private OrderCollection orders;
	private ItemCollection menu;
	private String file;
	
	public OrderLoader(String filename, ItemCollection menu) {
		this.orders = new OrderCollection();
		this.file = filename;
		this.menu = menu;
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
			int year = Integer.parseInt(parts[0]);
			int month = Integer.parseInt(parts[1]);
			int day = Integer.parseInt(parts[2]);
			int hour = Integer.parseInt(parts[3]);
			int minute = Integer.parseInt(parts[4]);
			int seconds= Integer.parseInt(parts[5]);
			
			Date timestamp = new Date(hour, minute, seconds, year, month, day);
			
			int customerID = Integer.parseInt(parts[6]);
			
			ArrayList<Item> orderItems = new ArrayList<Item>();
			
			//Remaining values on line are items in order
			for(int i = 7; i < parts.length; i++) {
				String itemId = parts[i];
				Item temp = this.menu.findItemById(itemId);
				orderItems.add(temp);
			}
			
			Order order = new Order(timestamp, customerID, orderItems);
			this.orders.add(order);
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}		
}
