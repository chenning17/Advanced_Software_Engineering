package cafepackage;

import java.util.TreeSet;
import java.security.InvalidParameterException;
import java.util.*;

public class ItemCollection implements Iterable<Item> {

	private TreeSet<Item> itemCollection;

	/**
	 * Default Constructor
	 */
	public ItemCollection() {
		itemCollection = new TreeSet<Item>();
		this.addDiscountItems();
	}

	/**
	 * Adds an item to the item collection
	 * 
	 * @param item
	 *            item to be added
	 */
	public void add(Item item) {
		this.itemCollection.add(item);
	}

	private void addDiscountItems() {
		Item testDeal;
		try {
			testDeal = new Discount("testDeal", "-£2 test deal", 2, "disc999");
			this.itemCollection.add(testDeal);
		} catch (DuplicateIDException | InvalidIDException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns an iterator of the items
	 * 
	 * @return
	 */
	public Iterator<Item> iterator() {
		return itemCollection.iterator();
	}

	/**
	 * Search for an item in collection by id
	 * 
	 * @param id
	 *            of item to look for
	 * @return item if it is found, otherwise null
	 */
	public Item findItemById(String id) {
		Iterator<Item> iterator = itemCollection.iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			if (item.getID().equals(id)) {
				return item;
			}
		}
		return null;
	}

	public int count() {
		return itemCollection.size();
	}

}
