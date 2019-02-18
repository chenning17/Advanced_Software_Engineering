package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cafepackage.Discount;
import cafepackage.DiscountCalculator;
import cafepackage.Drink;
import cafepackage.DuplicateIDException;
import cafepackage.Food;
import cafepackage.InvalidIDException;
import cafepackage.Item;
import cafepackage.Snack;

public class DiscountCalculatorTest {
	

	
	
//	@Before
	
	
	
	@Test
	public void test_createDiscountItemWithDuplicateID() {
	//	message 
		Item bargain = DiscountCalculator.createDiscountItem("bargain", "free drink", 2.00, "disc003");
		Item savings = DiscountCalculator.createDiscountItem("savings", "99% off", 3.00, "disc003");
		assertEquals(null, null, savings);
	}
	
	@Test
	public void test_createDiscountItemWithWrongID() {
		Item savings = DiscountCalculator.createDiscountItem("savings", "99% off", 3.00, "dis003");
		assertEquals(null, null, savings);
	}
	
	
	@Test
	public void test_applyMealDeal() throws DuplicateIDException, InvalidIDException {
		Item sandwich = new Food("Sandwich", "", 10.00, "food123");
		Item coffee = new Drink("Coffee", "", 0.30, "drnk123");
		Item apple = new Snack("Apple", "", 0.23, "snck123");
		ArrayList<Item> itemList = new ArrayList<Item>();
		itemList.add(sandwich);
		itemList.add(coffee);
		itemList.add(apple);
		assertEquals(null,5.03,DiscountCalculator.applyMealDeal(itemList),0.001);
	}
	
	@Test
	public void test_applyMealDealLessThanCost() throws DuplicateIDException, InvalidIDException {
		Item sandwich = new Food("Sandwich", "", 1.00, "food133");
		Item coffee = new Drink("Coffee", "", 0.30, "drnk133");
		Item apple = new Snack("Apple", "", 0.23, "snck133");
		ArrayList<Item> itemList2 = new ArrayList<Item>();
		itemList2.add(sandwich);
		itemList2.add(coffee);
		itemList2.add(apple);
		assertEquals(null,-1,DiscountCalculator.applyMealDeal(itemList2),0.001);
	}
	
	@Test
	public void test_applyMealDealNoFood() throws DuplicateIDException, InvalidIDException {
		Item sandwich = new Snack("Sandwich", "", 1.00, "snck143");
		Item coffee = new Drink("Coffee", "", 0.30, "drnk143");
		Item apple = new Snack("Apple", "", 0.23, "snck134");
		ArrayList<Item> itemList3 = new ArrayList<Item>();
		itemList3.add(sandwich);
		itemList3.add(coffee);
		itemList3.add(apple);
		assertEquals(null,-1,DiscountCalculator.applyMealDeal(itemList3),0.001);
	}
	
	
}
