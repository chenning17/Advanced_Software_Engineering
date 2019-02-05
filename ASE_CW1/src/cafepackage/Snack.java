package cafepackage;

public class Snack extends Item {

	public Snack(String name, String description, double cost, String id) {
		super(name, description, cost, id);
	}

	//TODO implement validation function
	@Override
	public boolean validateID() {
		return false;
	}

}
