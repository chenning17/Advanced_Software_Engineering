package cafepackage;

import java.sql.Date;

public class OrderLoader extends FileInput {
	private OrderCollection orders;
	private ItemCollection menu;
	private String file;
	
	public OrderLoader(String filename, ItemCollection Menu) {
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
		

		OrderCollection orderCollection = new OrderCollection(); 
		
		try {
			String parts [] = inputLine.split(",");

			Date timestamp = Date.valueOf(parts[0]);	
			int customerID = Integer.parseInt(parts[1]);
			String itemId = parts[2]; //error- should be Item not String
			Item item = this.menu.findItemById(itemId);
			
			

			Order order = new Order(timestamp, customerID, item);
			orderCollection.add(order);
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}		
}
