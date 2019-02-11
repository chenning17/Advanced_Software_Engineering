package cafepackage;

import java.util.Iterator;

public class Manager {
	private ItemCollection menu;
	private OrderCollection orders;
	
	public Manager() {
		this.menu = new ItemCollection();
		this.orders = new OrderCollection();
		
	}
	
	public void run() {
		//load items from csv, print count to check right number entered
		String menuFile = "Menu.csv";
		System.out.println("Inputting Items from: " + menuFile);
		ItemLoader itemLoader = new ItemLoader(menuFile);
		this. menu = itemLoader.loadItems();
		//System.out.println("Printing names");
		int count = 0;
		for (Item i : this.menu) {
			count++;
			//System.out.println(i.getName());
		}
		System.out.println("---\nItems loaded into menu: " + count);
		
		//load orders from csv, ...
		String orderFile = "OrderList.csv";
		System.out.println("\nInputing orders from: " + orderFile);
		OrderLoader orderLoader = new OrderLoader(orderFile, this.menu);
		this.orders = orderLoader.loadOrders();
		
		try {
			cafeGUI frame = new cafeGUI(this.menu);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}




