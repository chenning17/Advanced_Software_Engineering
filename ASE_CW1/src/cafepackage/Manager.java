package cafepackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class Manager {
	private ItemCollection menu;
	private OrderCollection orders;

	public Manager() {
		this.menu = new ItemCollection();
		this.orders = new OrderCollection();

	}

	public void run() {
		// load items from csv, print count to check right number entered
		String menuFile = "Menu.csv";
		System.out.println("Inputting Items from: " + menuFile);
		ItemLoader itemLoader = new ItemLoader(menuFile);
		this.menu = itemLoader.loadItems();

		System.out.println("---\nItems loaded into menu: " + this.menu.count());

		// load orders from csv, ...
		String orderFile = "OrderList.csv";
		System.out.println("\nInputing orders from: " + orderFile);
		OrderLoader orderLoader = new OrderLoader(orderFile, this.menu);
		this.orders = orderLoader.loadOrders();

		
    System.out.println("---\nOrders loaded: " + this.orders.count());
    
		try {
			cafeGUI frame = new cafeGUI(this.menu, this); //create gui giving it a menu and the manager itself
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//When should this be called
	public void generateReport() {
		

		BufferedWriter writer = null;

		HashMap<Item, Integer> itemCounts = this.orders.getItemFreq(this.menu);

		// Code based on answers at:
		// https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
		try {
			String filename = "Report " + new Date().toString().substring(0, 10) + ".txt";
			File outputFile = new File(filename);
			writer = new BufferedWriter(new FileWriter(outputFile));
			
//			writer.write(String.format("%-15s\t%-25s\t%-30s\t\n", "Customer id", "Item", "Date"));
//
//			for (Order o : this.orders) {
//				writer.write(String.format("%-15s\t", "" + o.getCustomerId()));
//				writer.write(String.format("%-25s\t", o.getItem().getName()));
//				writer.write(String.format("%-30s\t", o.getTimestamp().toString()));
//				writer.write("\n");
//			}
			
			writer.write(String.format("Total Value Report\ncreated on: %s \n\n", new Date().toString().substring(0, 10)));
			writer.write(String.format("%-35s\t%-10s\t%-10s\t\n", "Item", "Count", "Total Value"));
			
			double totalIncome = 0;
			for (HashMap.Entry<Item, Integer> entry : itemCounts.entrySet()) {
				String itemName = entry.getKey().getName();
				int itemCount = entry.getValue();
				double totalValue = entry.getKey().getCost() * entry.getValue();
				totalIncome += totalValue;
				writer.write(String.format("%-35s\t %-10d\t �%-10.2f\t\n", itemName, itemCount,	totalValue));
			}
			writer.write(String.format("\nTotal Earnings: %34s%.2f", "�",totalIncome));
				
			System.out.println("Report saved!");
		} catch (java.io.IOException ioe) {
			System.out.println("Failed to save report." + ioe.getMessage());
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				// Do nothing
			}
		}
	}

	public void addOrder(ArrayList<Order> newOrders) {
		for (Order newOrder : newOrders) {
			this.orders.add(newOrder);
		}
	}
}
