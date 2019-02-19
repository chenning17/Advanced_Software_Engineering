/**package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.OrderLoader;
import cafepackage.Item;
import cafepackage.Snack;
import cafepackage.Food;
import cafepackage.Discount;
import cafepackage.Drink;
import cafepackage.Order;;

public class OrderLoaderTest {
	private static OrderLoader orderLoader1get;
	private static OrderLoader orderLoader1set;

	@BeforeClass
public static void oneTimeSetUP() {
		try { 
			orderLoader1get = new OrderLoader(Date, "1", "item");
			orderLoader1set = new OrderLoader(Date, "1", "item");
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();	
		}
		}	
	@Test 
public void test_getOrderLoader() { 
		
	}
	//Tests constructor when an invalid order (negative number) is passed
		@Test (expected = IllegalArgumentException.class) 
		public void test_constructor_invalidOrder() {
			Date testDate = new Date();

			
			Order order1 = new Order(testDate, -1, testItemFood);
	} **/