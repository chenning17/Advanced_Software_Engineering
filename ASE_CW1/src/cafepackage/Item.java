package cafepackage;

public abstract class Item {

	private String name;
	private String description;
	private double cost;
	private String id;

	/**
	 * Item constructor, creates item instance and updates values for name, description, cost and id.
	 * @param name string representing item's name
	 * @param description string representing item's description
	 * @param cost float representing item's cost
	 * @param id string representing item's id
	 */
	public Item(String name, String description, double cost, String id) throws InvalidIDException, DuplicateIDException {
		this.setName(name);
		this.setDescription(description);
		this.setCost(cost);
		this.setID(id);
	}

	/**
	 * Function used to update an item's name
	 * @param name input string used to update item name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns string representing item's name
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Function used to update an item's description
	 * @param description input string used to update item description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns string representing item's description
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Function used to update an item's cost
	 * @param cost input string used to update item cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Returns float value for item's cost
	 * @return
	 */
	public double getCost() {
		return this.cost;
	}

	/**
	 * Function used to update an item's ID
	 * @param id input string used to update item ID
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * Returns string representing item's ID
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
	 * Called in constructor of inheriting class, returns true if input id to constructor is valid 
	 * @return
	 */
	protected abstract boolean validateID();

}
