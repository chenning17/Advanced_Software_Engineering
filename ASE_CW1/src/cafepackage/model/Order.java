package cafepackage.model;

import java.util.ArrayList;

import cafepackage.model.items.Discount;
import cafepackage.model.items.Item;

public class Order {

	private Date timeStamp;
	private int customerId;
	private ArrayList<Item> orderItems;
	// tracks customerIDs assigned to orders, in order to automatically assign IDs to newly created orders
	private static int currentCustomerID = 0;

	// constructor used to read in existing orders from CSV files
	public Order(Date timeStamp, int customerId, ArrayList<Item> orderItems) {

		// Check valid parameters have been passed
		if (timeStamp == null) {
			throw new IllegalArgumentException("Date for an order can't be null");
		}
		if (orderItems == null) {
			throw new IllegalArgumentException("Order item can't be null");
		}
		if (customerId <= 0) {
			throw new IllegalArgumentException("Customer ID must be a positive int");
		}

		// Want to clone objects so that if the original object is somehow modified it
		// doesn't change this order
		this.timeStamp = timeStamp;
		this.customerId = customerId;
		this.orderItems = (ArrayList<Item>) orderItems.clone();

		// highest pre-existing customer ID ends up being set to the current ID + 1
		if (this.customerId >= currentCustomerID) {
			currentCustomerID = customerId + 1;
		}
		else if (this.customerId == currentCustomerID) {
			currentCustomerID++;
		}
	}
	
	public static int getCurrentCustomerID() {
		return currentCustomerID;
	}

	public Date getTimestamp() {
		return this.timeStamp;
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public ArrayList<Item> getItems() {
		return this.orderItems;
	}
	
	/**
	 * Add an item to the order
	 * @param i item to add
	 */
	public void addItemToOrder(Item i) {
		this.orderItems.add(i);
	}
	
	public double getCost() {
		double cost = 0;
		for(Item i : this.orderItems) {
			if (i instanceof Discount) {
				cost += i.getCost() * -1;
			} else {
				cost += i.getCost();
			}
		}
		return cost;
	}

}
