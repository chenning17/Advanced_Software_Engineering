package cafepackage;

import java.util.HashSet;

public class Snack extends Item {

	private static HashSet<String> idList = new HashSet<String>();
	private static final String itemIdentifier = "snck";
	
	/**
	 * Snack item constructor, will raise DuplicateID or InvalidID Exceptions if
	 * input id is incorrect, will raise an IllegalArgumentException if cost is a
	 * negative value.
	 * 
	 * @param name
	 *            snack item's name
	 * @param description
	 *            brief description of snack item
	 * @param cost
	 *            snack item's cost
	 * @param id
	 *            string containing snack item's id
	 * @throws DuplicateIDException
	 *             if id already exists in idList
	 * @throws InvalidIDException
	 *             if id is of an incorrect format
	 */
	public Snack(String name, String description, double cost, String id)
			throws DuplicateIDException, InvalidIDException {

		super(name, description, cost, id);

		if (validateID(id, itemIdentifier) == false) {
			throw new InvalidIDException(id);
		} else if (isDuplicateID(id, idList) == true) {
			throw new DuplicateIDException(id);
		}

		idList.add(id);
	}
	
}
