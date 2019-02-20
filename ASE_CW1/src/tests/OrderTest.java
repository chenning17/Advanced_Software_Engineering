package tests;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;

import cafepackage.Item;
import cafepackage.Snack;
import cafepackage.Food;
import cafepackage.Discount;
import cafepackage.InvalidIDException;
import cafepackage.Drink;
import cafepackage.DuplicateIDException;
import cafepackage.Order;

public class OrderTest {

	private static Item testItemSnack;
	private static Item testItemFood;
	private static Item testItemDrink;
	private static Item testItemDiscount;

	@BeforeClass
	public static void oneTimeSetUp() {
		try {
			testItemSnack = new Snack("Apple", "Granny smith", 1.20, "snck001");
			testItemFood = new Food("Panini", "Ham and cheese", 4.45, "food001");
			testItemDrink = new Drink("Apple Juice", "Still", 1.20, "drnk001");
			testItemDiscount = new Discount("Half price", "50% off order", 5.50, "disc001");
		
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();
		}
	}
	
	//Tests method to return timestamp as a java.util.Date object
	@Test
	public void test_getTimestamp() {
		int testCustomerId = 1;
		
		Date date1 = new Date(1); //Invokes constructor which uses date in milliseconds
		Order order1 = new Order(date1, testCustomerId, testItemSnack);
		String message1 = "Failed for date: Millisecond time 1";
		assertEquals(message1, date1, order1.getTimestamp());
	
		Date date2 = new Date();  //Current date
		Order order2 = new Order(date2, testCustomerId, testItemSnack);
		String message2 = "Failed for date: Current time";
		assertEquals(message2, date2, order2.getTimestamp());
	}
	
	//Tests method to return customer id as an int
	@Test
	public void test_getCustomerId() {
		Date testDate = new Date(); 
		
		int customerId1 = 1;
		Order order1 = new Order(testDate, customerId1, testItemSnack);
		String message1 = "Failed for customer id: 1";
		assertEquals(message1, customerId1, order1.getCustomerId());
		
		int customerId2 = 99;
		Order order2 = new Order(testDate, customerId2, testItemSnack);
		String message2 = "Failed for customer id: 99";
		assertEquals(message2, customerId2, order2.getCustomerId());	
	}
	
	//Tests method to return item stored within the order. Returned object will be a subclass of item
	@Test
	public void test_getItem() {
		Date testDate = new Date();
		int testCustomerId = 1;
		
		Order order1 = new Order(testDate, testCustomerId, testItemSnack);
		String message1 = "Failed for item of type snack";
		assertEquals(message1, testItemSnack, order1.getItem());
		
		Order order2 = new Order(testDate, testCustomerId, testItemFood);
		String message2 = "Failed for item of type food";
		assertEquals(message2, testItemFood, order2.getItem());
		
		Order order3 = new Order(testDate, testCustomerId, testItemDrink);
		String message3 = "Failed for item of type drink";
		assertEquals(message3, testItemDrink, order3.getItem());
		
		Order order4 = new Order(testDate, testCustomerId, testItemDiscount);
		String message4 = "Failed for item of type drink";
		assertEquals(message4, testItemDiscount, order4.getItem());
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
		
		Order order1 = new Order(null, testCustomerId, testItemFood);
	}
	
	//Tests constructor when an invalid customer id (negative number) is passed
	@Test (expected = IllegalArgumentException.class) 
	public void test_constructor_invalidCustomerId() {
		Date testDate = new Date();

		
		Order order1 = new Order(testDate, -1, testItemFood);
	}
}

