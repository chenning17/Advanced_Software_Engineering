package cafepackage;

import java.util.HashSet;

public abstract class Item implements Comparable<Item> {

	private String name;
	private String description;
	private double cost;
	private String id;

	/**
	 * Item constructor, creates item instance and updates values for name,
	 * description, cost and id.
	 * 
	 * @param name
	 *            string representing item's name
	 * @param description
	 *            string representing item's description
	 * @param cost
	 *            float representing item's cost
	 * @param id
	 *            string representing item's id
	 */
	public Item(String name, String description, double cost, String id)
			throws InvalidIDException, DuplicateIDException, IllegalArgumentException {

		if (cost < 0) {
			throw new IllegalArgumentException("Item price must be a positive value");
		}

		this.setName(name);
		this.setDescription(description);
		this.setCost(cost);
		this.setID(id);
	}

	/**
	 * Function used to update an item's name
	 * 
	 * @param name
	 *            input string used to update item name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns string representing item's name
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Function used to update an item's description
	 * 
	 * @param description
	 *            input string used to update item description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns string representing item's description
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Function used to update an item's cost
	 * 
	 * @param cost
	 *            input string used to update item cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Returns float value for item's cost
	 * 
	 * @return
	 */
	public double getCost() {
		return this.cost;
	}

	/**
	 * Function used to update an item's ID
	 * 
	 * @param id
	 *            input string used to update item ID
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * Returns string representing item's ID
	 * 
	 * @return
	 */
	public String getID() {
		return this.id;
	}

	@Override
	public boolean equals(Object inputObject) {
		// check if input object is the same instance
		if (inputObject == this) {
			return true;
		}
		// if input object is an Item, check that the IDs of each match
		else if (inputObject instanceof Item) {
			if (((Item) inputObject).getID().equals(this.getID())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Given an id string, will return the first 4 characters as a string, in order
	 * to test equality against item identifying 4 letter code.
	 * 
	 * @param id
	 *            input id string for item
	 * @return
	 */
	protected static String getIDStart(String id) {
		// get first 4 characters from id string
		String temp = "";
		for (int i = 0; i < 4; i++) {
			temp += id.charAt(i);
		}
		return temp;
	}

	/**
	 * Given an id string, will return the last 3 characters as a string, in order
	 * to test that the last 3 characters in id code are digits.
	 * 
	 * @param id
	 *            input id string for item
	 * @return
	 */
	protected static String getIDEnd(String id) {
		// get last 3 characters from id string
		String temp = "";
		for (int i = 4; i < id.length(); i++) {
			temp += id.charAt(i);
		}
		return temp;
	}

	/**
	 * Given an idCode string (the last 3 characters of an id), will return true if
	 * these characters are all digits.
	 * 
	 * @param idCode
	 *            last 3 characters from an id string that should contain 3 digits
	 * @return
	 */
	protected static boolean isDigit(String idCode) {
		try {
			Integer.parseInt(idCode);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if ID is already found in the idList HashSet, returns false
	 * otherwise.
	 * 
	 * @return
	 */
	protected static boolean isDuplicateID(String id, HashSet<String> idList) {
		if (idList.contains(id)) {
			return true;
		}
		return false;
	}

	/**
	 * Called in constructor of inheriting class, returns true if input id to
	 * constructor is valid
	 * 
	 * @return
	 */
	protected static boolean validateID(String id, String itemIdentifier) {
		// check id is 7 characters long
		if (id.length() == 7) {
			// split into first 4 characters and last 3 characters, 
			//checking first 4 equal item identifier and last 3 are digits
			if (getIDStart(id).equals(itemIdentifier) && isDigit(getIDEnd(id))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method from Comparable interface, items compared based on their id string
	 */
	public int compareTo(Item other) {
		return this.id.compareTo(other.id);
	}
	
	/**
	 * Displays name and cost of an item instead of memory location
	 */
	@Override
	public String toString() {
		String displayInfo = String.format("%s : %5s", this.name, this.cost);
		return displayInfo;
	}

}
