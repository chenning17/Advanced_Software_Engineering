package cafepackage;

import java.util.Date;

public class Order {

	private Date timeStamp;
	private int customerId;
	private Item orderItem;
	private static int currentCustomerID = 0; // tracks customerIDs assigned to orders, in order to automatically assign
												// IDs to newly created orders
	// changed orderItem to string (above and below)

	// constructor used to read in existing orders from CSV files
	public Order(Date timeStamp, int customerId, Item orderItem) {

		// Check valid parameters have been passed
		if (timeStamp == null) {
			throw new IllegalArgumentException("Date for an order can't be null");
		}
		if (orderItem == null) {
			throw new IllegalArgumentException("Order item can't be null");
		}
		if (customerId <= 0) {
			throw new IllegalArgumentException("Customer ID must be a positive int");
		}

		// Want to clone date so that if the original object is somehow modified it
		// doesn't change this order
		this.timeStamp = (Date) timeStamp.clone();
		this.customerId = customerId;
		this.orderItem = orderItem;

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
		return (Date) this.timeStamp.clone(); // Return a clone so date can't be modified
	}

	public int getCustomerId() {
		return this.customerId;
	}

	// changed to string
	public Item getItem() {
		return this.orderItem;
	}

}
