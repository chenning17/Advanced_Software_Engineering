package cafepackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCalculator {

	/**
	 * Given the list of all orders, apply a discount to each if applicable and if
	 * not already added.
	 * 
	 * @param orderCollection
	 *            list of all orders currently made to the system
	 * @param menu
	 *            list of all items currently available, passed here in order to be
	 *            able to add new discount items to it (for report generation)
	 */
	public static void applyDiscount(OrderCollection orderCollection, ItemCollection menu) {

		HashMap<Integer, ArrayList<Item>> groupedOrders = orderCollection.getGroupedOrders();

		// temporary list used to hold the new discount orders made
		ArrayList<Order> discountList = new ArrayList<Order>();

		int discountID = 0;
		for (HashMap.Entry<Integer, ArrayList<Item>> entry : groupedOrders.entrySet()) {

			Integer customerID = entry.getKey();
			ArrayList<Item> itemList = entry.getValue();

			double mealDeal = applyMealDeal(itemList);
			double bogofSnackDeal = applyBOGOFSnackDeal(itemList);

			// double someotherDeal = applSomeotherDeal(itemList);

			// TODO add other deals and check which returns the largest value as this is
			// then the best deal,
			// also make sure bestDeal > 0

			// TODO should already have all discount choices as elements in input menu file
			// / already stored in program and added to itemCollection
			// therefore this will avoid the duplication of discounts
			// at the minute discountID counter is just used to create unique IDs when run
			// once (second time will use previous IDs) but
			// this is not the prefered method -> is much better to just create another
			// order of discounts using existing discount item

			// TODO this method of checking for existing discount relies on items / orders
			// being in same order as before, therefore need to be able to check which
			// discount applies to which order
			// to make this more robust

			double bestDeal;
			String discountName;
			String discountDescription;
			
			if (mealDeal > bogofSnackDeal) {
				bestDeal = mealDeal;
				discountName = "***MEAL DEAL DISCOUNT***";
				discountDescription = "£5.50 meal deal";
			} else {
				bestDeal = bogofSnackDeal;
				discountName = "***BOGOF DEAL DISCOUNT***";
				discountDescription = "buy one get one free on all snacks";
			}

			if (bestDeal > 0) {
				discountID++;
				// create a discount item and add it to menu and order if it does not already
				// exist
				String discountIDString = String.format("disc%03d", discountID);
				if (menu.findItemById(discountIDString) == null) {
					try {
						Item discount = new Discount(discountName, discountDescription, bestDeal, discountIDString);
						menu.add(discount);

						// TODO this date should match the given order creation date
						Date date = new Date();
						// create new order of discount item for given customer id
						Order discountOrder = new Order(date, customerID, discount);

						// add order to temporary list of discount orders
						discountList.add(discountOrder);
					} catch (DuplicateIDException | InvalidIDException e) {
						e.printStackTrace();
					}

				}
			}
		}

		// now add each discount from the temporary discount list to the inputted orders
		// list
		for (Order order : discountList) {
			orderCollection.add(order);
			System.out.println("A discount item order has been added to orderCollection");
			Item item = order.getItem();
			System.out.printf("discount details -> name: %s, savings value: £%.2f, ID: %s\n", item.getName(),
					item.getCost(), item.getID());
		}

	}

	/**
	 * Given an input list of items, will return the value of savings that a meal
	 * deal would save the user. Returns a value of 0 if deal is not applicable or
	 * if the price of items is not enough to save money using the deal.
	 * 
	 * @param itemList
	 *            list of items in an order
	 * @return
	 */
	public static double applyMealDeal(ArrayList<Item> itemList) {

		// value used to hold the amount of savings applied by the deal
		double dealValue = 0;

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
				dealValue = 0;
			}
		}
		return dealValue;
	}

	/**
	 * Given an input list of items, will return the value of savings that a buy one
	 * get one free deal would save the user. This deal applies only to orders where
	 * two identical snack items have been ordered. Returns a value of 0 if deal is
	 * not applicable, will apply deal two multiples of 2, i.e. 4 items would give a
	 * discount equivalent to 2, but 3 would give a discount equivalent to 1 free
	 * item.
	 * 
	 * @param itemList
	 *            list of items in an order
	 * @return
	 */
	public static double applyBOGOFSnackDeal(ArrayList<Item> itemList) {
		// value used to hold the amount of savings applied by the deal
		double dealValue = 0;

		HashMap<Item, Integer> itemCounts = new HashMap<Item, Integer>();

		for (Item item : itemList) {

			if (itemCounts.containsKey(item)) {
				int counts = itemCounts.get(item);
				itemCounts.put(item, counts + 1);
			} else {
				itemCounts.put(item, 1);
			}
		}

		Item tempItem;
		int counts;
		// evaluate each item count to see if it qualifies for a bogof snack deal
		//and add the correct (number of free items) * (item cost) to the dealValue
		for (HashMap.Entry<Item, Integer> entry : itemCounts.entrySet()) {
			tempItem = entry.getKey();
			counts = entry.getValue();

			if (tempItem instanceof Snack && counts > 1) {
				dealValue += ((counts - counts % 2) / 2) * tempItem.getCost();
			}
		}

		return dealValue;
	}
}
