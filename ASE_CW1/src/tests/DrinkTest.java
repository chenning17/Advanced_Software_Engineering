package tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.Drink;

public class DrinkTest {

	private static Drink drink1get;
	private static Drink drink1set;

	@BeforeClass
	public static void oneTimeSetUp() {
		try {
			drink1get = new Drink("Apple Juice", "Still", 1.20, "drnk001");
			drink1set = new Drink("Apple Juice", "Still", 1.20, "drnk002");
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();
		}
	}

	@Test
	public void test_getName() {
		String message = "Failed to getName() for drink with name \"Apple Juice\"";
		assertEquals(message, "Apple Juice", drink1get.getName());
	}

	@Test
	public void test_setName() {
		String message = "Failed to setName() to \"Banana Juice\"";
		drink1set.setName("Banana Juice");
		assertEquals("Banana Juice", drink1set.getName());
	}

	@Test
	public void test_getDescription() {
		String message = "Failed to getDescription() for \"Still\"";
		assertEquals(message, "Still", drink1get.getDescription());
	}

	@Test
	public void test_setDescription() {
		String message = "Failed to setDescription() to \"Sparkling\"";
		drink1set.setDescription("Sparkling");
		assertEquals(message, "Sparkling", drink1set.getDescription());
	}

	@Test
	public void test_getCost() {
		String message = "Failed to getCost() for value 1.20";
		assertEquals(message, 1.20, drink1get.getCost(), 0.001);
	}

	@Test
	public void test_setCost() {
		String message = "Failed to setCost() to 4.00";
		drink1set.setCost(4.00);
		assertEquals(message, 4.00, drink1set.getCost(), 0.001);
	}

	@Test
	public void test_getID() {
		String message = "Failed to getID() for id of \"drnk001\"";
		assertEquals(message, "drnk001", drink1get.getID());
	}

	@Test
	public void test_setID() {
		String message = "Failed to setID() to \"drnk123";
		drink1set.setID("drnk123");
		assertEquals(message, "drnk123", drink1set.getID());
	}

	// for tests using constructors, need to do them this way instead of with
	// "(expected = ...)"
	// because there are 2 exceptions the constructor can throw that need to be
	// handled
	@Test
	public void test_validateIDwithDuplicateID() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", 5.00, "drnk003");
			Drink Milkshake = new Drink("Milkshake", "Chocolate", 3.00, "drnk003");
			fail();
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
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("drnk00"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}

	@Test
	public void test_validateIDwithInvalidIDTooManyNumbers() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", 5.00, "drnk0000");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("drnk0000"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}

	@Test
	public void test_validateIDwithInvalidIDCharactersNotNumbers() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", 5.00, "drnkaaa");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("drnkaaa"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_ConstructorPriceValidation() {
		try {
			Drink smoothie = new Drink("Smoothie", "Blueberry", -1.00, "drnk003");
			fail();
		} catch (InvalidIDException e) {
			fail();
		} catch (DuplicateIDException e) {
			fail();
		}
	}
}
