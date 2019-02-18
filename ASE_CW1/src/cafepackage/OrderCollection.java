package cafepackage;

import java.util.*;

public class OrderCollection implements Iterable<Order> {

	private ArrayList<Order> orderCollection;

	/**
	 * Default Constructor
	 */
	public OrderCollection() {
		orderCollection = new ArrayList<Order>();
	}

	/**
	 * Adds an order to the order collection
	 * 
	 * @param order
	 *            order item to be added
	 */
	public void add(Order order) {
		orderCollection.add(order);
	}

	/**
	 * returns an iterator of the orders
	 * 
	 * @return
	 */
	public Iterator<Order> iterator() {
		return orderCollection.iterator();
	}

	public int count() {
		return orderCollection.size();
	}

	public HashMap<Item, Integer> getItemFreq(ItemCollection menu) {

		HashMap<Item, Integer> itemCounts = new HashMap<Item, Integer>();
		for (Item i : menu) {
			itemCounts.put(i, 0);
		}

		for (Order o : this.orderCollection) {

			Item currentItem = o.getItem();

			int counts = itemCounts.get(currentItem);

			itemCounts.put(currentItem, counts + 1);
		}

		return itemCounts;
	}
}
