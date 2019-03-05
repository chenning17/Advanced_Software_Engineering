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
import java.util.TreeMap;

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
		ItemLoader itemLoader = new ItemLoader(menuFile, this.menu);
		this.menu = itemLoader.loadItems();

		System.out.println("---\nItems loaded into menu: " + this.menu.count());

		// load orders from csv
		String orderFile = "OrderList.csv";
		System.out.println("\nInputing orders from: " + orderFile);
		OrderLoader orderLoader = new OrderLoader(orderFile, this.menu);
		this.orders = orderLoader.loadOrders();

		System.out.println("---\nOrders loaded: " + this.orders.count());

		try {
			//cafeGUI frame = new cafeGUI(this.menu, this); // create gui giving it a menu and the manager itself
			//frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * writes report of items sold and takings to file
	 */
	public void generateReport() {

		// apply discounts to orders
		// DiscountCalculator.applyDiscount(this.orders, this.menu);

		BufferedWriter writer = null;

		TreeMap<Item, Integer> itemCounts = this.orders.getItemFreq(this.menu);

		// Code based on answers at:
		// https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
		try {
			String filename = "Report " + new Date().toString().substring(0, 10) + ".txt";
			File outputFile = new File(filename);
			writer = new BufferedWriter(new FileWriter(outputFile));

			writer.write(
					String.format("Total Value Report\ncreated on: %s \n\n", new Date().toString().substring(0, 10)));
			writer.write(String.format("%-35s\t%-10s\t%-10s\t\n", "Item", "Count", "Total Value"));

			double totalIncome = 0;
			int i = 0;

			writer.write("\n--==Food==--\n");
			for (Item item : this.menu) {
				if (item instanceof Food) {
					String name = item.getName();
					int count = itemCounts.get(item);
					double totalValue = item.getCost() * count;
					totalIncome += totalValue;
					writer.write(String.format("%-35s\t %-10d\t £% -10.2f\t\n", name, count, totalValue));
				}
			}

			writer.write("\n--==Snack==--\n");
			for (Item item : this.menu) {
				if (item instanceof Snack) {
					String name = item.getName();
					int count = itemCounts.get(item);
					double totalValue = item.getCost() * count;
					totalIncome += totalValue;
					writer.write(String.format("%-35s\t %-10d\t £% -10.2f\t\n", name, count, totalValue));
				}
			}

			writer.write("\n--==Drink==--\n");
			for (Item item : this.menu) {
				if (item instanceof Drink) {
					String name = item.getName();
					int count = itemCounts.get(item);
					double totalValue = item.getCost() * count;
					totalIncome += totalValue;
					writer.write(String.format("%-35s\t %-10d\t £% -10.2f\t\n", name, count, totalValue));
				}
			}

			writer.write("\n--==Discount==--\n");
			for (Item item : this.menu) {
				if (item instanceof Discount) {
					String name = item.getName();
					int count = itemCounts.get(item);
					double totalValue = item.getCost() * count * -1;
					totalIncome += totalValue;
					writer.write(String.format("%-35s\t %-10d\t £% -10.2f\t\n", name, count, totalValue));
				}
			}

			writer.write(String.format("\nTotal Earnings: %34s%.2f", "£", totalIncome));

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
