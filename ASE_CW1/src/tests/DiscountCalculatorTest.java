package tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.BeforeClass;
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
	

	static Item food1;
	static Item food2;
	static Item food3;
	static Item snack1;
	static Item snack2;
	static Item snack3;
	static Item snack4;
	static Item drink1;
	static Item drink2;
	static Item drink3;
	
	@BeforeClass
	public static void setUp() throws DuplicateIDException, InvalidIDException {
		food1 = new Food("Food","",3.50,"food711");
		food2 = new Food("Food","",5.00,"food712");
		food3 = new Food("Food","",7.29,"food713");
		snack1 = new Snack("Snack","",0.50,"snck711");
		snack2 = new Snack("Snack","",0.75,"snck712");
		snack3 = new Snack("Snack","",0.54,"snck713");
		snack4 = new Snack("Snack","",0.75,"snck714");
		drink1 = new Drink("Drink","",1.51,"drnk711");
		drink2 = new Drink("Drink","",1.75,"drnk712");
		drink3 = new Drink("Drink","",0.5,"drnk713");
	}
	
	
	
	@Test
	public void test_getBestDealMealDeal() {
		
		String message1 = "Failed to get a Meal Deal discount at name";
		String message2 = "Failed to get a Meal Deal discount at cost";
		
		ArrayList<Item> itemList1 = new ArrayList<Item>();
		itemList1.add(food1);
		itemList1.add(drink1);
		itemList1.add(snack1);
		
		Discount discount = DiscountCalculator.getBestDeal(itemList1);
		
		assertEquals(message1,"***MEAL DEAL DISCOUNT***",discount.getName());
		assertEquals(message2,0.01,discount.getCost(),0.001);
	}	
	
	@Test
	public void test_getBestDealBOGOF() {
		
		String message1 = "Failed to get a BOGOF discount at name";
		String message2 = "Failed to get a BOGOF discount at cost";
		
		ArrayList<Item> itemList2 = new ArrayList<Item>();
		itemList2.add(snack1);
		itemList2.add(snack2);
		
		Discount discount2 = DiscountCalculator.getBestDeal(itemList2);
		
		assertEquals(message1,"***BOGOF DEAL DISCOUNT***",discount2.getName());
		assertEquals(message2,0.50,discount2.getCost(),0.001);
	}
	
	@Test
	public void test_getBestDealMultipleMealDeal() {
		
		String message1 = "Failed to get a Meal Deal discount at name";
		String message2 = "Failed to get a Meal Deal discount at cost";
		
		ArrayList<Item> itemList3 = new ArrayList<Item>();
		itemList3.add(food1);
		itemList3.add(food2);
		itemList3.add(snack1);
		itemList3.add(snack2);
		itemList3.add(drink1);
		itemList3.add(drink2);
		
		
		Discount discount3 = DiscountCalculator.getBestDeal(itemList3);
		Discount discount4 = DiscountCalculator.getBestDeal(itemList3);
		
		assertEquals(message1+" in Deal One","***MEAL DEAL DISCOUNT***",discount3.getName());
		assertEquals(message2+" in Deal One",2.0,discount3.getCost(),0.001);
		
		assertEquals(message1+" in Deal Two","***MEAL DEAL DISCOUNT***",discount4.getName());
		assertEquals(message2+" in Deal Two",0.01,discount4.getCost(),0.001);
	}
	
	@Test
	public void test_getBestDealMultipleBOGOF() {
		
		String message1 = "Failed to get a BOGOF discount at name";
		String message2 = "Failed to get a BOGOF discount at cost";
		
		ArrayList<Item> itemList4 = new ArrayList<Item>();
		itemList4.add(snack1);
		itemList4.add(snack2);
		itemList4.add(snack3);
		itemList4.add(snack4);
		
		Discount discount5 = DiscountCalculator.getBestDeal(itemList4);
		Discount discount6 = DiscountCalculator.getBestDeal(itemList4);
		
		assertEquals(message1+" in Deal One","***BOGOF DEAL DISCOUNT***",discount5.getName());
		assertEquals(message2+" in Deal One",0.50,discount5.getCost(),0.001);
		
		assertEquals(message1+" in Deal Two","***BOGOF DEAL DISCOUNT***",discount6.getName());
		assertEquals(message2+" in Deal Two",0.54,discount6.getCost(),0.001);
	}
	
	@Test
	public void test_getBestDealMultipleDealTypes() {
		
		String message1 = "Failed to get a discount at name";
		String message2 = "Failed to get a discount at cost";
		
		ArrayList<Item> itemList5 = new ArrayList<Item>();
		itemList5.add(food1);
		itemList5.add(drink1);
		itemList5.add(snack1);
		itemList5.add(snack2);
		itemList5.add(snack3);
		
		Discount discount7 = DiscountCalculator.getBestDeal(itemList5);
		Discount discount8 = DiscountCalculator.getBestDeal(itemList5);
		
		assertEquals(message1+" in Deal One","***BOGOF DEAL DISCOUNT***",discount7.getName());
		assertEquals(message2+" in Deal One",0.5,discount7.getCost(),0.001);
		
		assertEquals(message1+" in Deal Two","***MEAL DEAL DISCOUNT***",discount8.getName());
		assertEquals(message2+" in Deal Two",0.05,discount8.getCost(),0.001);
	}
	
	
	@Test
	public void test_getBestDealMealDealPriceHigher() {
		
		String message = "Meal Deal should not have been applied";
		
		ArrayList<Item> itemList6 = new ArrayList<Item>();
		itemList6.add(food1);
		itemList6.add(drink3);
		itemList6.add(snack1);
		
		assertEquals(message,null,DiscountCalculator.getBestDeal(itemList6));
	}

}

