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
	 * Exposes iterator of private ArrayList
	 * 
	 * @return an iterator of the orders
	 */
	public Iterator<Order> iterator() {
		return orderCollection.iterator();
	}

	/**
	 * 
	 * @return size of arrayList
	 */
	public int count() {
		return orderCollection.size();
	}

	/**
	 * Gets the frequency of item sales
	 * @param menu 
	 * @return TreeMap with Items as key, and integer of counts as value
	 */
	public TreeMap<Item, Integer> getItemFreq(ItemCollection menu) {

		TreeMap<Item, Integer> itemCounts = new TreeMap<Item, Integer>();
		for (Item i : menu) {
			itemCounts.put(i, 0);
		}

		for (Order o : this.orderCollection) {
			for(Item currentItem : o.getItems()) {
				int counts = itemCounts.get(currentItem);

				itemCounts.put(currentItem, counts + 1);
			}
		}

		return itemCounts;
	}
}
