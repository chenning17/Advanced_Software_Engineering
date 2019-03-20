package cafepackage;

import java.util.HashSet;

import cafepackage.exceptions.DuplicateIDException;
import cafepackage.exceptions.InvalidIDException;

public class Drink extends Item {

	private static HashSet<String> idList = new HashSet<String>();
	private static final String itemIdentifier = "drnk";

	/**
	 * Drink item constructor, will raise DuplicateID or InvalidID Exceptions if
	 * input id is incorrect, will raise an IllegalArgumentException if cost is a
	 * negative value.
	 * 
	 * @param name
	 *            drink item's name
	 * @param description
	 *            brief description of drink item
	 * @param cost
	 *            drink item's cost
	 * @param id
	 *            string containing drink item's id
	 * @throws DuplicateIDException
	 *             if id already exists in idList
	 * @throws InvalidIDException
	 *             if id is of an incorrect format
	 */
	public Drink(String name, String description, double cost, String id, int processTime)
			throws DuplicateIDException, InvalidIDException {

		super(name, description, cost, id, processTime);

		if (validateID(id, itemIdentifier) == false) {
			throw new InvalidIDException(id);
		} else if (!idList.add(id)) {
			throw new DuplicateIDException(id);
		}
	}
		
}