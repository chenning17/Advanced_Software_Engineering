package tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import cafepackage.exceptions.DuplicateIDException;
import cafepackage.exceptions.InvalidIDException;
import cafepackage.model.DiscountCalculator;
import cafepackage.model.items.Discount;
import cafepackage.model.items.Drink;
import cafepackage.model.items.Food;
import cafepackage.model.items.Item;
import cafepackage.model.items.Snack;



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
		food1 = new Food("Food","",3.50,"food711",1);
		food2 = new Food("Food","",5.00,"food712",1);
		food3 = new Food("Food","",7.29,"food713",1);
		snack1 = new Snack("Snack","",0.50,"snck711",1);
		snack2 = new Snack("Snack","",0.75,"snck712",1);
		snack3 = new Snack("Snack","",0.54,"snck713",1);
		snack4 = new Snack("Snack","",0.75,"snck714",1);
		drink1 = new Drink("Drink","",1.51,"drnk711",1);
		drink2 = new Drink("Drink","",1.75,"drnk712",1);
		drink3 = new Drink("Drink","",0.5,"drnk713",1);
	}
	
	
	//Test to determine that a Meal Deal gets applied
	@Test
	public void test_getBestDealMealDeal() {
		
		String message1 = "Failed to get a Meal Deal discount at name";
		String message2 = "Failed to get a Meal Deal discount at cost";
		
		ArrayList<Item> itemList1 = new ArrayList<Item>();
		itemList1.add(food1);
		itemList1.add(drink1);
		itemList1.add(snack1);
		
		Discount discount = DiscountCalculator.getBestDeal(itemList1);
		
		assertEquals(message1,"*MEAL DEAL (-0.01)",discount.getName());
		assertEquals(message2,0.01,discount.getCost(),0.001);
	}	
	
	//Test to determine that a Buy One Get One Free snack Deal is applied
	@Test
	public void test_getBestDealBOGOF() {
		
		String message1 = "Failed to get a BOGOF discount at name";
		String message2 = "Failed to get a BOGOF discount at cost";
		
		ArrayList<Item> itemList2 = new ArrayList<Item>();
		itemList2.add(snack1);
		itemList2.add(snack2);
		
		Discount discount2 = DiscountCalculator.getBestDeal(itemList2);
		
		assertEquals(message1,"*BOGOF DEAL (-0.5)",discount2.getName());
		assertEquals(message2,0.50,discount2.getCost(),0.001);
	}
	
	//Test to ensure multiple Meal Deals can be applied
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
		
		assertEquals(message1+" in Deal One","*MEAL DEAL (-2.0)",discount3.getName());
		assertEquals(message2+" in Deal One",2.0,discount3.getCost(),0.001);
		
		assertEquals(message1+" in Deal Two","*MEAL DEAL (-0.01)",discount4.getName());
		assertEquals(message2+" in Deal Two",0.01,discount4.getCost(),0.001);
	}
	
	//Test to ensure multiple BOGOF snack Deals can be applied
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
		
		assertEquals(message1+" in Deal One","*BOGOF DEAL (-0.5)",discount5.getName());
		assertEquals(message2+" in Deal One",0.50,discount5.getCost(),0.001);
		
		assertEquals(message1+" in Deal Two","*BOGOF DEAL (-0.54)",discount6.getName());
		assertEquals(message2+" in Deal Two",0.54,discount6.getCost(),0.001);
	}
	
	//Test to ensure both a Meal Deal and a BOGOF can be applied
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
		
		assertEquals(message1+" in Deal One","*BOGOF DEAL (-0.5)",discount7.getName());
		assertEquals(message2+" in Deal One",0.5,discount7.getCost(),0.001);
		
		assertEquals(message1+" in Deal Two","*MEAL DEAL (-0.05)",discount8.getName());
		assertEquals(message2+" in Deal Two",0.05,discount8.getCost(),0.001);
	}
	
	//Test to make sure a Meal Deal is not applied if the total cost is less than the Meal Deal cost
	@Test
	public void test_getBestDealMealDealPriceHigher() {
		
		String message = "Meal Deal should not have been applied";
		
		ArrayList<Item> itemList6 = new ArrayList<Item>();
		itemList6.add(food1);
		itemList6.add(drink3);
		itemList6.add(snack1);
		
		assertEquals(message,null,DiscountCalculator.getBestDeal(itemList6));
	}
	
	//Test to make sure only one snack is taken off if three snacks are added
	@Test 
	public void test_getBestDealBOGOF3Snack() {
		
		String message1 = "Failure in first BOGOF deal";
		String message2 = "Only one deal should be applied";
		
		ArrayList<Item> itemList7 = new ArrayList<Item>();
		itemList7.add(snack1);
		itemList7.add(snack2);
		itemList7.add(snack3);
		
		Discount discount8 = DiscountCalculator.getBestDeal(itemList7);
		Discount discount9 = DiscountCalculator.getBestDeal(itemList7);
		
		assertEquals(message1,"*BOGOF DEAL (-0.5)",discount8.getName());
		assertEquals(message1,0.5,discount8.getCost(),0.001);
		assertEquals(message2,null,discount9);
	}

}

