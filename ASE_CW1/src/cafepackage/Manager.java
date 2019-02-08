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
		System.out.println("Inputting Items");
		ItemLoader itemLoader = new ItemLoader("Menu.csv");
		this. menu = itemLoader.loadItems();
		System.out.println("Printing names");
		for (Item i : this.menu) {
			System.out.println(i.getName());
		}
		
	}
}




