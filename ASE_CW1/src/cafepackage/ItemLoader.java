package cafepackage;

public class ItemLoader extends FileInput {

	/**
	 * Process the lines for the Menu file, checking item categories
	 */
	@Override
	protected void processLine(String inputLine) {

		ItemCollection itemCollection = new ItemCollection(); 
		Item item = null;
		
		try {
			String parts [] = inputLine.split(",");

			String id = parts[0];		
			String name = parts[1];
			String description = parts[2];
			double cost = Double.parseDouble(parts[3]);

			//Checks if the item is a snack 
			if(id.startsWith("SNCK")) {
				item = new Snack(name,description,cost,id);
			}

			//checks if the item is in the food category
			if(id.startsWith("FOOD")) {
				item = new Food(name,description,cost,id);			
			}

			//checks if the item is in the drink category
			if(id.startsWith("DRNK")) {
				item = new Drink(name,description,cost,id);						
			}

			//checks if the item is in the discount category
			if(id.startsWith("DISC")) {
				item = new Discount(name,description,cost,id);			
			}

			itemCollection.add(item);

		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

}
