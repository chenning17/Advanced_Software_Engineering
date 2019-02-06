package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.Drink;

public class DrinkTest {
	Drink drink1;

	@Before
	public void setUp() {
		try {
			drink1 = new Drink("Apple Juice", "Still", 1.20, "drnk001");
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();
		}
	}

	@Test
	public void test_getName() {
		String message = "Failed to getName() for drink with name \"Apple Juice\"";
		assertEquals(message, "Apple Juice", drink1.getName());
	}
	
	@Test
	public void test_setName() {
		String message = "Failed to setName() to \"Banana Juice\"";
		drink1.setName("Banana Juice");
		assertEquals("Banana Juice", drink1.getName());
	}
	
	@Test
	public void test_getDescription() {
		String message = "Failed to getDescription() for \"Still\"";
		assertEquals(message, "Still", drink1.getDescription());
	}
	
	@Test
	public void test_setDescription() {
		String message = "Failed to setDescription() to \"Sparkling\"";
		drink1.setDescription("Sparkling"); 
		assertEquals(message, "Sparkling", drink1.getDescription());
	}
	
	@Test
	public void test_getCost() {
		String message = "Failed to getCost() for value 1.20";
		assertEquals(message, 1.20, drink1.getCost(), 0.001);
	}
	
	@Test
	public void test_setCost() {
		String message = "Failed to setCost() to 4.00";
		drink1.setCost(4.00);
		assertEquals(message, 4.00, drink1.getCost(), 0.001);
	}
	
	@Test
	public void test_getID() {
		String message = "Failed to getID() for id of \"drnk001\"";
		assertEquals(message, "drnk001", drink1.getID());
	}
	
	@Test
	public void test_setID() {
	String message = "Failed to setID() to \"drnk123";
	drink1.setID("drnk123");
		assertEquals(message, "drnk123", drink1.getID());
	}
	
	//for tests using constructors, need to do them this way instead of with "(expected = ...)"
	//because there are 2 exceptions the constructor can throw that need to be handled
	@Test
	public void test_validateIDwithDuplicateID() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", 5.00, "drnk003");
			Drink Milkshake = new Drink("Milkshake", "Chocolate", 3.00, "drnk003");
		} catch (DuplicateIDException e) {
			assertTrue(e.getMessage().contains("drnk003"));
		} catch (InvalidIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDNotEnoughNumbers() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", 5.00, "drnk00");
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("drnk00"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDTooManyNumbers() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", 5.00, "drnk0000");
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("drnk0000"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDCharactersNotNumbers() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", 5.00, "drnkaaa");
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("drnkaaa"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_ConstructorPriceValidation() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", -1.00, "drnk003");
		} catch (InvalidIDException e) {
			fail();
		} catch (DuplicateIDException e) {
			fail();
		}
	}
}
