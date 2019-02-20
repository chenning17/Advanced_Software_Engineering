package cafepackage;

public class ItemLoader extends FileInput {
	private ItemCollection menu;
	private String file;
	
	public ItemLoader(String filename, ItemCollection menu) {
		this.file = filename;
		this.menu = menu;		
	}

	
	public ItemCollection loadItems() {
		inputFile(this.file);
		return this.menu;	
	}

	/**
	 * Process the lines for the Menu file, checking item categories
	 */
	@Override
	protected void processLine(String inputLine) {

		Item item = null;
		
		try {
			String parts [] = inputLine.split(",");

			String id = parts[0];		
			String name = parts[1];
			String description = parts[2];
			double cost = Double.parseDouble(parts[3]);

			//Checks if the item is a snack 
			if(id.startsWith("snck")) {
				item = new Snack(name,description,cost,id);
			}

			//checks if the item is in the food category
			else if(id.startsWith("food")) {
				item = new Food(name,description,cost,id);			
			}

			//checks if the item is in the drink category
			else if(id.startsWith("drnk")) {
				item = new Drink(name,description,cost,id);						
			}

			//checks if the item is in the discount category
			else if(id.startsWith("disc")) {
				item = new Discount(name,description,cost,id);			
			}
			
			else {
				throw new InvalidIDException(id);
			}

			this.menu.add(item);

		}
		//to do catch better excepetions
		catch (InvalidIDException e) {
			System.out.println(e.getMessage() +"\nItem not added.");
		} catch (DuplicateIDException e) {
			System.out.println(e.getMessage() + "\nItem not added.");
		}
	}

}
