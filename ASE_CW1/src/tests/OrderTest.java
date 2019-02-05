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

	@Test
	public void test_getTimestamp() {
		Item testItem = new Snack();
		int testCustomerID = 1;
		
		Date date1 = new Date(1); //Invokes constructor which uses date in milliseconds
		Order order1 = new Order(date1, testCustomerID, testItem);
		String message1 = "Failed for date: " + date1.toString();
		assertEquals(message1, date1, order1.getTimestamp());
	
		Date date2 = new Date();  //Current date
		Order order2 = new Order(date2, testCustomerID, testItem);
		String message2 = "Failed for date: " + date2.toString();
		assertEquals(message2, date2, order2.getTimestamp());
	}
	
}
