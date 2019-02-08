package cafepackage;

import java.util.HashSet;
import java.util.Set;

public class Discount extends Item {

	private static HashSet<String> idList = new HashSet<String>();
	private static final String itemIdentifier = "disc";

	/**
	 * Discount item constructor, will raise DuplicateID or InvalidID Exceptions if
	 * input id is incorrect, will raise an IllegalArgumentException if cost is a
	 * negative value.
	 * 
	 * @param name
	 *            discount item's name
	 * @param description
	 *            brief description of discount item
	 * @param cost
	 *            discount item's cost
	 * @param id
	 *            string containing discount item's id
	 * @throws DuplicateIDException
	 *             if id already exists in idList
	 * @throws InvalidIDException
	 *             if id is of an incorrect format
	 */
	public Discount(String name, String description, double cost, String id)
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