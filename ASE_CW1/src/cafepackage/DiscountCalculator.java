package cafepackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCalculator {

	// add discount item to input order
	public static void applyDiscount(OrderCollection orderCollection) {

		HashMap<Integer, ArrayList<Item>> groupedOrders = orderCollection.getGroupedOrders();

		ArrayList<Order> discountList = new ArrayList<Order>();

		int discountID = 0;
		for (HashMap.Entry<Integer, ArrayList<Item>> entry : groupedOrders.entrySet()) {

			Integer customerID = entry.getKey();
			ArrayList<Item> itemList = entry.getValue();

			double mealDeal = applyMealDeal(itemList);

			// double bogofDeal = applyBOGOFDeal(itemList);
			// double someotherDeal = applSomeotherDeal(itemList);

			// TODO add other deals and check which returns the largest value as this is
			// then the best deal,
			// also make sure bestDeal > 0
			
			//TODO should already have all discount choices as elements in input menu file / already stored in program and added to itemCollection
			//therefore this will avoid the duplication of discounts
			//at the minute discountID counter is just used to create unique IDs when run once (second time will use previous IDs) but 
			//this is not the prefered method -> is much better to just create another order of discounts using existing discount item
			
			double bestDeal = mealDeal;

			if (bestDeal > 0) {
				discountID++;
				// create a discount item and add it to the order
				try {
					String discountIDString = String.format("disc%03d", discountID);
					Item discount = new Discount("mealDeal", "£3.50 meal deal", bestDeal, discountIDString );

					// TODO this date should match the given order creation date
					Date date = new Date();
					Order discountOrder = new Order(date, customerID, discount);

					discountList.add(discountOrder);

				} catch (DuplicateIDException e) {
					e.printStackTrace();
				} catch (InvalidIDException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		// now add each discount found to the orders list
		for (Order order : discountList) {
			//orderCollection.add(order);
			//System.out.println("A discount item order has been added to orderCollection");
			Item item = order.getItem();
			System.out.printf("discount details name: %s, savings value: £%.2f, ID: %s\n", item.getName(), item.getCost(), item.getID());
		}

	}

	public static double applyMealDeal(ArrayList<Item> itemList) {

		double dealValue = 0;

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
			// intended meal
			// deal price of £3.50
			dealValue = (maxFood + maxDrink + maxSnack) - 3.50;

			// make sure that the deal value is a positive value in case item combo is
			// already cheaper than meal deal
			if (dealValue < 0) {
				dealValue = 0;
			}
		}

		return dealValue;
	}

}
