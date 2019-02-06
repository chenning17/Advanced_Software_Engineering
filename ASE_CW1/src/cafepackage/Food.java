package cafepackage;

import java.util.HashSet;
import java.util.Set;

public class Food extends Item {

	private static Set<String> idList = new HashSet<String>();
		
	public Food(String name, String description, double cost, String id)
			throws DuplicateIDException, InvalidIDException {
		super(name, description, cost, id);

		try {
			if (validateID() == false) {
				throw new InvalidIDException(id);
			} 
			else if (isDuplicateID() == true) {
				throw new DuplicateIDException(id);
			}
			
			idList.add(id);
		} 
		catch (InvalidIDException invalid) {
			System.out.println(invalid.getMessage());
		} 
		catch (DuplicateIDException duplicate) {
			System.out.println(duplicate.getMessage());
		}

	}

	//TODO implement validation function
	@Override
	protected boolean validateID() {
		return false;
	}
	
	/**
	 * Returns true if ID is already found in the idList HashSet.
	 * @return
	 */
	// TODO implement isDuplicateIDs function
	private boolean isDuplicateID() {
		return false;
	}

}
