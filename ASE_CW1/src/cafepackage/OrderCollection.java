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

	/**
	 * Returns map of customer id to associated list of the items ordered by them.
	 * order, for use in discount calculations.
	 * 
	 * @return
	 */
	public HashMap<Integer, ArrayList<Item>> getGroupedOrders() {

		HashMap<Integer, ArrayList<Item>> groupedOrders = new HashMap<Integer, ArrayList<Item>>();

		
		//loop through every order in the order collection and map each customerId to associated
		//list of order items
		for (Order order : this.orderCollection) {

			Integer customerId = order.getCustomerId();
			Item orderItem = order.getItem();

			if (groupedOrders.containsKey(customerId)) {
				// add order item to list of items currently held for given customerId
				ArrayList<Item> itemList = groupedOrders.get(customerId);
				itemList.add(orderItem);
				groupedOrders.put(customerId, itemList);
			} else {
				// add order item and customerId to map in case where customerId has not already
				// been read in
				ArrayList<Item> itemList = new ArrayList<Item>();
				itemList.add(orderItem);
				groupedOrders.put(customerId, itemList);
			}
		}

		return groupedOrders;
		
	}

}
