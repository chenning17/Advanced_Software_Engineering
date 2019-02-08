package cafepackage;

import java.util.HashSet;
import java.util.Set;

public class Snack extends Item {

	private static Set<String> idList = new HashSet<String>();

	public Snack(String name, String description, double cost, String id)
			throws DuplicateIDException, InvalidIDException {

		super(name, description, cost, id);

		// try {
		if (validateID(id) == false) {
			throw new InvalidIDException(id);
		} else if (isDuplicateID(id) == true) {
			throw new DuplicateIDException(id);
		}

		idList.add(id);
		System.out.println("idList: " + idList.toString());
		// }
		// catch (InvalidIDException invalid) {
		// System.out.println(invalid.getMessage());
		// }
		// catch (DuplicateIDException duplicate) {
		// System.out.println(duplicate.getMessage());
		// }

	}

	// TODO implement validation function
	@Override
	protected boolean validateID(String id) {

		// check id is 7 characters long
		if (id.length() == 7) {
			// split into first 4 characters and last 3 characters, checking first 4 equal
			// "snck" and last 3 are digits
			System.out.println("getIDStart: " + getIDStart(id).toString());
			System.out.println("getIDEnd: " + getIDEnd(id).toString());
			System.out.println("isDigit(): " + isDigit(getIDEnd(id)));
			if (getIDStart(id).equals("snck") && isDigit(getIDEnd(id))) {
				System.out.println("VALID");
				return true;
			}

		}
		System.out.println("length not 7 or not digits / valid");
		return false;

	}

	private static String getIDStart(String id) {
		// split into first 4 and last 3 character strings
		String temp = "";
		for (int i = 0; i < 4; i++) {
			temp += id.charAt(i);
		}
		return temp;
	}

	private static String getIDEnd(String id) {
		// split into first 4 and last 3 character strings
		String temp = "";
		for (int i = 4; i < id.length(); i++) {
			temp += id.charAt(i);
		}
		return temp;
	}

	private static boolean isDigit(String idCode) {
		try {
			Integer.parseInt(idCode);
		} catch (NumberFormatException e) {
			System.out.println("is Digit = false");
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
	private static boolean isDuplicateID(String id) {
		if (idList.contains(id)) {
			return true;
		}
		return false;
	}

}
