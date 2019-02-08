package cafepackage;

import java.util.TreeSet;
import java.util.*;

public class ItemCollection implements Iterable<Item> {

	private TreeSet<Item> itemCollection;

	/**
	 * Default Constructor
	 */
	public ItemCollection() {
		itemCollection = new TreeSet<Item>();
	}
	/**
	 * Adds an item to the item collection
	 * @param item item to be added
	 */
	public void add(Item item) {
		itemCollection.add(item);
	}

	/**
	 * returns an iterator of the items
	 * @return 
	 */
	public Iterator<Item> iterator() {
		return itemCollection.iterator();
	}

}
