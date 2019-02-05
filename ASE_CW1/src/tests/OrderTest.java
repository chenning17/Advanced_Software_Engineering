package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Date;

import cafepackage.Item;
import cafepackage.Snack;
import cafepackage.Food;
import cafepackage.Drink;
import cafepackage.Order;

public class OrderTest {

	//Tests method to return timestamp as a java.util.Date object
	@Test
	public void test_getTimestamp() {
		Item testItem = new Snack();
		int testCustomerId = 1;
		
		Date date1 = new Date(1); //Invokes constructor which uses date in milliseconds
		Order order1 = new Order(date1, testCustomerId, testItem);
		String message1 = "Failed for date: Millisecond time 1";
		assertEquals(message1, date1, order1.getTimestamp());
	
		Date date2 = new Date();  //Current date
		Order order2 = new Order(date2, testCustomerId, testItem);
		String message2 = "Failed for date: Current time";
		assertEquals(message2, date2, order2.getTimestamp());
	}
	
	//Tests method to return customer id as an int
	@Test
	public void test_getCustomerId() {
		Item testItem = new Snack();
		Date testDate = new Date(); 
		
		int customerId1 = 1;
		Order order1 = new Order(testDate, customerId1, testItem);
		String message1 = "Failed for customer id: 1";
		assertEquals(message1, testDate, order1.getCustomerId());
		
		int customerId2 = 99;
		Order order2 = new Order(testDate, customerId2, testItem);
		String message2 = "Failed for customer id: 99";
		assertEquals(message2, testDate, order2.getCustomerId());	
	}
	
	//Tests method to return item stored within the order. Returned object will be a subclass of item
	@Test
	public void test_getItem() {
		Date testDate = new Date();
		int testCustomerId = 1;
		
		Item item1 = new Snack();
		Order order1 = new Order(testDate, testCustomerId, item1);
		String message1 = "Failed for item of type snack";
		assertEquals(message1, item1, order1.getItem());
		
		Item item2 = new Food();
		Order order2 = new Order(testDate, testCustomerId, item2);
		String message2 = "Failed for item of type food";
		assertEquals(message2, item2, order2.getItem());
		
		Item item3 = new Drink();
		Order order3 = new Order(testDate, testCustomerId, item2);
		String message3 = "Failed for item of type drink";
		assertEquals(message3, item3, order3.getItem());
	}
}
