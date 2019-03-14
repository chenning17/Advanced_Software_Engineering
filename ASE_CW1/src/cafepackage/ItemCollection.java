package cafepackage;

import java.util.TreeSet;
import java.security.InvalidParameterException;
import java.util.*;

public class ItemCollection implements Iterable<Item> {

	private TreeSet<Item> itemCollection;
	private ArrayList<String> ids; //Used for selecting a random item

	/**
	 * Default Constructor
	 */
	public ItemCollection() {
		itemCollection = new TreeSet<Item>();
		ids = new ArrayList<String>();
	}

	/**
	 * Adds an item to the item collection
	 * 
	 * @param item
	 *            item to be added
	 */
	public void add(Item item) {
		this.itemCollection.add(item);
		this.ids.add(item.getID());
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

	/**
	 * Exposes size() method of underlying TreeSet
	 * @return size as int
	 */
	public int count() {
		return itemCollection.size();
	}
	
	/**
	 * Gets a random item from the set
	 * @return The item selected randomly
	 */
	public Item randomItem() {
		int itemIndex = (int) Math.ceil(Math.random() * itemCollection.size());
		return this.findItemById(ids.get(itemIndex));
	}

}
