package cafepackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCalculator {

	static int discountID = 0;

	/**
	 * Given a list of items, will attempt to apply all available deals to this
	 * list, and will return the best discount value possible. This function is
	 * provided for use with the main applyDiscount function in this
	 * DiscountCalculator class.
	 * 
	 * @param itemList
	 *            list of items to check for deal validity
	 * @param discountName
	 *            input string to be overwritten with the name of the best discount
	 * @param discountDescription
	 *            input string to be overwritten with the description of the best
	 *            discount
	 * @return
	 */
	public static Discount getBestDeal(ArrayList<Item> itemList) {

		// call all available deals to compare their values
		double mealDeal = applyMealDeal(itemList);
		double bogofSnackDeal = applyBOGOFSnackDeal(itemList);
		String discountName = "";
		String discountDescription = "";
		discountID++;
		String discountIDString = String.format("disc%03d", discountID);

		double bestDeal = 0;

		if (mealDeal > bogofSnackDeal && mealDeal > 0) {
			bestDeal = mealDeal;
			discountName = "***MEAL DEAL DISCOUNT***";
			discountDescription = "£5.50 meal deal";

		} else if (bogofSnackDeal > mealDeal && bogofSnackDeal > 0) {
			bestDeal = bogofSnackDeal;
			discountName = "***BOGOF DEAL DISCOUNT***";
			discountDescription = "buy one get one free on all snacks";

		}
		if (bestDeal > 0) {
			Discount discount = createDiscountItem(discountName, discountDescription, bestDeal, discountIDString);
			return discount;
		}

		else {
			return null;
		}

	}

	/**
	 * Given an input list of items, will return the value of savings that a meal
	 * deal would save the user. Returns a value of -1 if deal is not applicable or
	 * if the price of items is not enough to save money using the deal.
	 * 
	 * @param itemList
	 *            list of items in an order
	 * @return
	 */
	public static double applyMealDeal(ArrayList<Item> itemList) {

		// value used to hold the amount of savings applied by the deal
		double dealValue = -1;

		// values used to hold the value of the most expensive item of each category
		// in order to apply the best deal
		double maxFood = -1;
		double maxDrink = -1;
		double maxSnack = -1;

		double itemCost;

		for (Item item : itemList) {
			itemCost = item.getCost();

			if (item instanceof Food && itemCost > maxFood) {
				maxFood = itemCost;
			} else if (item instanceof Drink && itemCost > maxDrink) {
				maxDrink = itemCost;
			} else if (item instanceof Snack && itemCost > maxSnack) {
				maxSnack = itemCost;
			}
		}

		if (maxFood > 0 && maxDrink > 0 && maxSnack > 0) {
			// set the deal saving value to be the difference between the combo and the
			// intended meal deal price of £5.50
			dealValue = (maxFood + maxDrink + maxSnack) - 5.50;

			// make sure that the deal value is a positive value in case item combo is
			// already cheaper than meal deal
			if (dealValue < 0) {
				dealValue = -1;
			}
		}

		if (dealValue > 0) {
			return dealValue;
		} else {
			return -1;
		}
	}

	/**
	 * Given an input list of items, will return the value of savings that a buy one
	 * get one free deal would save the user. Returns a value of -1 if deal is
	 * not applicable, will apply deal two multiples of 2, i.e. 4 items would give a
	 * discount equivalent to 2, but 3 would give a discount equivalent to 1 free
	 * item. Applies discount in order that items appear. The cost of every second snack
	 * will be added to the discount.
	 * 
	 * @param itemList
	 *            list of items in an order
	 * @return value of discount
	 */
	public static double applyBOGOFSnackDeal(ArrayList<Item> itemList) {
		boolean snack1 = false;
		boolean snack2 = false;
		
		double dealValue = 0;
		
		for (Item item : itemList) {
			//for each item in the list
			if (item instanceof Snack) { //if the item is a snack
				if(!snack1) { //and if no snacks have been found yet, say we've found 1 snack
					snack1 = true;
				}
				else if(!snack2) { //otherwise if a snack has already been found, say we've found a second
					snack2 = true;
				}
				
				if(snack1 && snack2) { //if we've found 2 snacks
					dealValue += item.getCost(); //add the cost of the second to the discount
					
					//reset values to look for more snacks
					snack1 = false;
					snack2 = false;
				}
			}
		}
		return dealValue;
	}
		
		
		/*
		// value used to hold the amount of savings applied by the deal
		double dealValue = 0;
		int counts;
		HashMap<Item, Integer> itemCounts = new HashMap<Item, Integer>();

		for (Item item : itemList) {

			if (itemCounts.containsKey(item)) {
				counts = itemCounts.get(item);
				itemCounts.put(item, counts + 1);
			} else {
				itemCounts.put(item, 1);
			}
		}

		Item tempItem;
		// evaluate each item count to see if it qualifies for a bogof snack deal
		// and add the correct (number of free items) * (item cost) to the dealValue
		for (HashMap.Entry<Item, Integer> entry : itemCounts.entrySet()) {
			tempItem = entry.getKey();
			counts = entry.getValue();

			if (tempItem instanceof Snack && counts > 1) {
				dealValue += ((counts - counts % 2) / 2) * tempItem.getCost();
			}
		}
		if (dealValue > 0) {
			return dealValue;
		} else {
			return -1;
		}
	}*/

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
	 * @return
	 */
	private static Discount createDiscountItem(String discountName, String discountDescription, double bestDeal,
			String discountIDString) {
		Discount discount = null;
		try {
			discount = new Discount(discountName, discountDescription, bestDeal, discountIDString);
			return discount;
		} catch (DuplicateIDException | InvalidIDException e) {
			e.printStackTrace();
		}

		return discount;
	}

}
