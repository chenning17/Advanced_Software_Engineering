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

import cafepackage.OrderLoader;
import cafepackage.Snack;
import cafepackage.exceptions.DuplicateIDException;
import cafepackage.exceptions.InvalidIDException;
import cafepackage.Item;
import cafepackage.ItemCollection;
import cafepackage.ItemLoader;
import cafepackage.Order;
import cafepackage.OrderCollection;;

public class OrderLoaderTest {
	private BufferedWriter writer;
	private OrderCollection orders;
	private OrderLoader orderLoader;
	private ItemCollection menu;
	private String order1;
	private String order2;
	private Snack testSnack;

	@Before
	public void setUp() {

		try {
			writer = new BufferedWriter(new FileWriter("testFile.csv"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		order1 = "2018-06-16T09:12:00.00Z,111111,snck364,1";
		order2 = "2018-06-16T09:12:00.00Z,1176112,snck364,1";
		try {
			testSnack = new Snack("KitKat", "Chocolate bar", 3.00, "snck364",1);
		} catch (DuplicateIDException | InvalidIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.menu = new ItemCollection();
		this.menu.add(testSnack);
		//itemLoader = new ItemLoader("testFile.csv", this.menu);
		orderLoader = new OrderLoader("testFile.csv", this.menu);
	}
	
// Tests that orders are placed and loaded correctly to the correct customer
	@Test
	public void testOrder() {
		try {
			writer.write(this.order1);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.orders = this.orderLoader.loadOrders();
		//no getters, so using iterator
		Order order1 = null;
		for(Order o : this.orders) {
			order1 = o;
		}
		if (order1 == null) {
			fail();
		} else {
			assertEquals("Incorrect order ID", 111111, order1.getCustomerId());
		}
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