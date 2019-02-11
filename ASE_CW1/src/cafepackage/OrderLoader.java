package cafepackage;

import java.util.Date;
import java.time.Instant;

public class OrderLoader extends FileInput {
	private OrderCollection orders;
	private ItemCollection menu;
	private String file;
	
	public OrderLoader(String filename, ItemCollection Menu) {
		this.orders = new OrderCollection();
		this.file = filename;
		this.menu = Menu;
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

			Instant time = Instant.parse(parts[0]);
			Date timestamp = Date.from(time);
//		    Instant.
//			Date timestamp = Date.UTC(year, month, date, hrs, min, sec)
			int customerID = Integer.parseInt(parts[1]);
			String itemId = parts[2];
			Item item = this.menu.findItemById(itemId);
			
			

			Order order = new Order(timestamp, customerID, item);
			orderCollection.add(order);
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}		
}
