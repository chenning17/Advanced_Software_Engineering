package cafepackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
		this.menu = itemLoader.loadItems();
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
		
		int orderCount = 0;
		for (Order o : this.orders) {
			orderCount++;
			//System.out.println(i.getName());
		}
		System.out.println("---\nOrders loaded: " + orderCount);
		
		GenerateReport();
	}
	
	void GenerateReport() {
		
		BufferedWriter writer = null;
		
		//Code based on answers at: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
		try {
			String filename = "output.txt";
			File outputFile = new File(filename);
			writer = new BufferedWriter(new FileWriter(outputFile));
			writer.write("Customer id\tItem\tDate\t\n");
			
			for(Order o : this.orders) {
				writer.write(o.getCustomerId() + "\t");
				writer.write(o.getItem().getName() + "\t");
				writer.write(o.getTimestamp().toString() + "\t");
				writer.write("\n");
			}
			
			
			System.out.println("Report saved!");
		}catch(java.io.IOException ioe) {
			System.out.println("Failed to save report." + ioe.getMessage());
		}finally {
			try {
				writer.close();
			}catch(Exception ex) {
				//Do nothing
			}
		}
	}
	
	public static void main(String[] args) {
		Manager manager = new Manager();
		manager.run();
	}
}




