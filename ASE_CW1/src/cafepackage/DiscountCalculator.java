package cafepackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCalculator {

	static int discountID = 0;

	/**
	 * Given a list of items, will find best discount available and return it.
	 * If no discounts available then returns null.
	 * Any items used in discount are removed from list.
	 * 
	 * @param itemList
	 *				list of items to check for deal validity
	 * @return discount
	 * 				Best available discount
	 */
	public static Discount getBestDeal(ArrayList<Item> itemList) {

		// call all available deals to compare their values
		MealDeal mealDeal = applyMealDeal(itemList);
		BogofSnack bogofSnackDeal = applyBOGOFSnackDeal(itemList);
		
		if(mealDeal == null && bogofSnackDeal == null) {
			//No applicable deals
			return null;
		}
		
		Discount discount = null;
		discountID++; //won't increment if no deal
		String discountIDString = String.format("disc%03d", discountID);
		
		if(mealDeal != null && bogofSnackDeal == null) {
			//Return meal deal if no Bogof Snack deal
			discount = createDiscountItem(mealDeal.NAME +" (-" + mealDeal.getValue() + ")", mealDeal.DESCRIPTION, mealDeal.getValue(), discountIDString, 0);
			mealDeal.Apply();
		}else if(mealDeal == null && bogofSnackDeal != null) {
			//Return bogof deal if no meal deal
			discount = createDiscountItem(bogofSnackDeal.NAME +" (-" + bogofSnackDeal.getValue() + ")", bogofSnackDeal.DESCRIPTION, bogofSnackDeal.getValue(), discountIDString, 0);
			bogofSnackDeal.Apply();
		}else {
			//return best deal
			if(mealDeal.getValue() > bogofSnackDeal.getValue()) {
				discount = createDiscountItem(mealDeal.NAME +" (-" + mealDeal.getValue() + ")", mealDeal.DESCRIPTION, mealDeal.getValue(), discountIDString, 0);
				mealDeal.Apply();
			}else {
				discount = createDiscountItem(bogofSnackDeal.NAME +" (-" + bogofSnackDeal.getValue( )+ ")", bogofSnackDeal.DESCRIPTION, bogofSnackDeal.getValue(), discountIDString, 0);
				bogofSnackDeal.Apply();
			}
		}

		return discount;
	}

	/**
	 * Given an input list of items, will return the best meal deal available.
	 * Returns null if deal not applicable
	 * 
	 * @param basket
	 *			List of items to check for deals
	 * @return
	 * 			Best available meal deal
	 */			
	static MealDeal applyMealDeal(ArrayList<Item> basket) {
		// values used to hold the value of the most expensive item of each category
		// in order to apply the best deal
		Food maxFood = null;
		Drink maxDrink = null;
		Snack maxSnack = null;

		//Loop through items to find most expensive food, drink and snack.
		for (Item item : basket) {
			double cost = item.getCost();

			if (item instanceof Food && (maxFood == null || cost > maxFood.getCost())) {
				maxFood = (Food)item;
			} else if (item instanceof Drink && (maxDrink == null || cost > maxDrink.getCost())) {
				maxDrink = (Drink)item;
			} else if (item instanceof Snack && (maxSnack == null || cost > maxSnack.getCost())) {
				maxSnack = (Snack)item;
			}
		}

		//If at least one of each type of item is present then return a deal
		if (maxFood != null && maxDrink != null && maxSnack != null) {
			MealDeal tempMealDeal = new MealDeal(maxFood, maxDrink, maxSnack, basket);
			if(tempMealDeal.getValue() <= 0.0) {
				return null;
			}else {
				return tempMealDeal;
			}
			
		}else {
			return null;
		}
	}

	/**
	 * Given an input list of items, will return a buy one get one free deal on snacks.
	 * If no deal is available returns null. 
	 * Cheapest snack is free and most expensive snack is kept.
	 * 
	 * @param basket
	 *			list of items in an order
	 * @return 
	 * 			The items in the discount
	 */
	static BogofSnack applyBOGOFSnackDeal(ArrayList<Item> basket) {
		int snackCounter = 0;
		Snack cheapestSnack = null;
		Snack mostExpensiveSnack = null;
		
		for (Item item : basket) {
			if (item instanceof Snack) { //if the item is a snack
				snackCounter++;
				
				//Shortcutting ORs to get around possibility of nulls
				if(cheapestSnack == null) {
					cheapestSnack = (Snack) item;
				}else if(item.getCost() < cheapestSnack.getCost()){
					//If we find a new cheapest snack check if the old cheapest snack becomes the most expensive snack 
					if(mostExpensiveSnack == null) {
						mostExpensiveSnack = cheapestSnack;
					}
					
					cheapestSnack = (Snack) item; //No problem casting because we already checked instance of
				}else if(mostExpensiveSnack == null) {
					mostExpensiveSnack = (Snack) item;
				}else if(item.getCost()>mostExpensiveSnack.getCost()) {
					mostExpensiveSnack = (Snack) item;
				}
			}
		}
		
		if(snackCounter >= 2) {
			return new BogofSnack(cheapestSnack, mostExpensiveSnack, basket);
		}else {
			return null;
		}
	}
		
	/**
	 * Create and return a new Discount item with the given parameters passed to the
	 * Discount item constructor.
	 * 
	 * @param discountName
	 *            string holding name of the discount
	 * @param discountDescription
	 *            string holding the description for the discount item
	 * @param bestDeal
	 *            double value holding the value of the discount item
	 * @param discountIDString
	 *            string used as the ID for a discount item
	 * @param processTime
	 * 			  integer used as the process time of the discount
	 * @return
	 */
	private static Discount createDiscountItem(String discountName, String discountDescription, double bestDeal,
			String discountIDString, int processTime) {
		try {
			Discount discount = new Discount(discountName, discountDescription, bestDeal, discountIDString, processTime);
			return discount;
		} catch (DuplicateIDException | InvalidIDException e) {
			e.printStackTrace();
		}

		return null;
	}

}

//Object to store details of a Bogof snack deal
class BogofSnack{
	public final String NAME = "*BOGOF DEAL";
	public final String DESCRIPTION = "Buy one get one free on all snacks";
	private Snack cheapSnack, expensiveSnack;
	private ArrayList<Item> basket;
	
	public BogofSnack(Snack cheapSnack, Snack expensiveSnack, ArrayList<Item> basket) {
		this.cheapSnack = cheapSnack;
		this.expensiveSnack = expensiveSnack;
		this.basket = basket;
	}
	
	public double getValue() {
		return cheapSnack.getCost();
	}
	
	//Remove deal items from basket
	public void Apply() {
		basket.remove(cheapSnack);
		basket.remove(expensiveSnack);
	}
}

//Object to store details of a meal deal
class MealDeal{
	public final String NAME = "*MEAL DEAL";
	public final String DESCRIPTION = "£5.50 meal deal";
	private Food food;
	private Drink drink;
	private Snack snack;
	private ArrayList<Item> basket;
	
	public MealDeal(Food food, Drink drink, Snack snack, ArrayList<Item> basket) {
		this.food = food;
		this.drink = drink;
		this.snack = snack;
		this.basket = basket;
	}
	
	public double getValue() {
		double totalPrice = food.getCost() + drink.getCost() + snack.getCost();
		totalPrice -= 5.5;
		totalPrice = Math.round(totalPrice * 100) / 100.0;
		return totalPrice;
	}
	
	//Remove deal items from basket
	public void Apply() {
		basket.remove(food);
		basket.remove(drink);
		basket.remove(snack);
	}
}