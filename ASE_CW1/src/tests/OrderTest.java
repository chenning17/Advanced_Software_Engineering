package tests;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import cafepackage.Item;
import cafepackage.Snack;
import cafepackage.Food;
import cafepackage.Discount;
import cafepackage.InvalidIDException;
import cafepackage.Drink;
import cafepackage.DuplicateIDException;
import cafepackage.Order;
import cafepackage.Date;

public class OrderTest {

	private static Item testItemSnack;
	private static Item testItemFood;
	private static Item testItemDrink;
	private static Item testItemDiscount;
	private static ArrayList<Item> testItemList;

	@BeforeClass
	public static void oneTimeSetUp() {
		try {
			testItemList = new ArrayList<Item>();
			
			testItemSnack = new Snack("Apple", "Granny smith", 1.20, "snck100",1);
			testItemList.add(testItemSnack);
			testItemFood = new Food("Panini", "Ham and cheese", 4.45, "food100",1);
			testItemList.add(testItemFood);
			testItemDrink = new Drink("Apple Juice", "Still", 1.20, "drnk100",1);
			testItemList.add(testItemDrink);
			testItemDiscount = new Discount("Half price", "50% off order", 5.50, "disc100",1);
			testItemList.add(testItemDiscount);
			
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();
		}
	}
	
	//Tests method to return timestamp as a java.util.Date object
	@Test
	public void test_getTimestamp() {
		int testCustomerId = 1;
		
		Date date1 = new Date(); //Invokes constructor which uses date in milliseconds
		Order order1 = new Order(date1, testCustomerId, testItemList);
		String message1 = "Failed for date: Millisecond time 1";
		assertEquals(message1, date1, order1.getTimestamp());
	
		Date date2 = new Date();  //Current date
		Order order2 = new Order(date2, testCustomerId, testItemList);
		String message2 = "Failed for date: Current time";
		assertEquals(message2, date2, order2.getTimestamp());
	}
	
	//Tests method to return customer id as an int
	@Test
	public void test_getCustomerId() {
		Date testDate = new Date(); 
		
		int customerId1 = 1;
		Order order1 = new Order(testDate, customerId1, testItemList);
		String message1 = "Failed for customer id: 1";
		assertEquals(message1, customerId1, order1.getCustomerId());
		
		int customerId2 = 99;
		Order order2 = new Order(testDate, customerId2, testItemList);
		String message2 = "Failed for customer id: 99";
		assertEquals(message2, customerId2, order2.getCustomerId());	
	}
	
	//Tests method to return item stored within the order. Returned object will be a subclass of item
	@Test
	public void test_getItem() {
		Date testDate = new Date();
		int testCustomerId = 1;
		
		Order order1 = new Order(testDate, testCustomerId, testItemList);
		String message1 = "Failed for item of type snack";
		assertEquals(message1, testItemList, order1.getItems());
	}
	
	//Tests constructor when null is passed instead of an item object
	@Test (expected = IllegalArgumentException.class)
	public void test_constructor_nullItem() {
		Date testDate = new Date();
		int testCustomerId = 1;
		
		Order order1 = new Order(testDate, testCustomerId, null);
	}
	
	//Tests constructor when null is passed instead of a date object
	@Test (expected = IllegalArgumentException.class)
	public void test_constructor_nullDate() {

		int testCustomerId = 1;
		
		Order order1 = new Order(null, testCustomerId, testItemList);
	}
	
	//Tests constructor when an invalid customer id (negative number) is passed
	@Test (expected = IllegalArgumentException.class) 
	public void test_constructor_invalidCustomerId() {
		Date testDate = new Date();

		
		Order order1 = new Order(testDate, -1, testItemList);
	}
}

