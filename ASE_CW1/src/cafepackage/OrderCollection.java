package cafepackage;

import java.util.*;

public class OrderCollection {

	private ArrayList<Order> orderCollection;

	/**
	 * Default Constructor
	 */
	public OrderCollection() {
		orderCollection = new ArrayList<Order>();
	}

	/**
	 * Adds an order to the order collection
	 * @param order order item to be added
	 */
	public void add(Order order) {
		orderCollection.add(order);
	}
	
	/**
	 * returns an iterator of the orders
	 * @return 
	 */
	public Iterator<Order> iterator() {
		return orderCollection.iterator();
	}
}
