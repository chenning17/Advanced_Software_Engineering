package cafepackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Report {

	HashMap<Item, Integer> itemCounts;
	ItemCollection menu;
/**
 * writes report of items sold	
 */
	
	public Report(ItemCollection menu) {
		itemCounts = new HashMap<Item, Integer>();
		this.menu = menu;
	}
	
	public void addOrder(Order order) {
		
		for (Item item : order.getItems()) {
			if(itemCounts.containsKey(item)) {
				itemCounts.put(item, itemCounts.get(item)+1);
			}else {
				itemCounts.put(item, 1);
			}
			
		}
		
	}
	
	public void generateReport() {
		BufferedWriter writer = null;
		
		
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

		
	}
