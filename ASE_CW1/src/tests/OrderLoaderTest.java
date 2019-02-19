package tests;

import static org.junit.Assert.*;

import java.awt.Menu;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.OrderLoader;
import cafepackage.Item;
import cafepackage.ItemCollection;
import cafepackage.ItemLoader;
import cafepackage.Order;;

public class OrderLoaderTest {
	private BufferedWriter writer;
	private ItemLoader itemLoader;
	private OrderLoader orderLoader;
	private ItemCollection menu;
	private String order1;
	private String order2;

	@Before
	public void setUp() {

		try {
			writer = new BufferedWriter(new FileWriter("testFile.csv"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		order1 = "2018-06-16T09:12:00.000,111111,snck364";
		order2 = "2018-06-16T09:12:00.000,1176112,snck364";
		this.menu = new ItemCollection();
		itemLoader = new ItemLoader("Menu.csv", this.menu);
		orderLoader = new OrderLoader("testFile.csv", this.menu);
	}

	public void loadReadOrder(String csvLine,int customerId) {
		int id1 = customerId;
		
		try {
			writer.write(csvLine);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		

		//this. = this.orderLoader.loadOrders();
		ItemCollection menu = this.menu;
		int testOrder = Order.getCurrentCustomerID();
		if (testOrder == 0) {
			fail();
		}
		int id2 = Order.getCurrentCustomerID();
		//assertEquals("Failed to read in item",customerId,Order.getCurrentCustomerID());
		//assertEquals("test", id1, id2);
		//assertTrue(0 == id1-id2);
	}
	}
	
// Tests that orders are placed and loaded correctly to the correct customer
	@Test
	public void testOrder() {
		loadReadOrder(this.order1, 111111);
		assertEquals("Failed to read in item",111111,Order.getCurrentCustomerID());
	}
	
//Tests constructor when an invalid customerId is passed for an order
	@Test
	public void testOrderFail() {
		loadReadOrder(this.order2, 11112);
	}

	@After
	public void tearDown() {
		try {
			writer.close();

		} catch (Exception e) {
			// Do Nothing
		}
	}
}