package cafepackage;

public class Discount extends Item {

	public Discount(String name, String description, double cost, String id) {
		super(name, description, cost, id);
	}

	//TODO implement validation function
	@Override
	public boolean validateID() {
		return false;
	}

}
