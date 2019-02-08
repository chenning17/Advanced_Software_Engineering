package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.Snack;


public class SnackTest {
	private static Snack snack1get;
	private static Snack snack1set;

	@BeforeClass
	public static void oneTimeSetUp() {
		try {
			snack1get = new Snack("Apple", "Granny smith", 1.20, "snck001");
			snack1set = new Snack("Apple", "Granny smith", 1.20, "snck002");
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_getName() {
		String message = "Failed to getName() for snack with name \"Apple\"";
		assertEquals(message, "Apple", snack1get.getName());
	}
	
	@Test
	public void test_setName() {
		String message = "Failed to setName() to \"Banana\"";
		snack1set.setName("Banana");
		assertEquals(message, "Banana", snack1set.getName());
	}
	
	@Test
	public void test_getDescription() {
		String message = "Failed to getDescription() for \"Granny smith\"";
		System.out.println("getDescription test" + snack1get.getDescription());
		assertEquals(message, "Granny smith", snack1get.getDescription());
	}
	
	@Test
	public void test_setDescription() {
		String message = "Failed to setDescription() to \"A big one\"";
		snack1set.setDescription("A big one");
		assertEquals(message, "A big one", snack1set.getDescription());
	}


	@Test
	public void test_getCost() {
		String message = "Failed to getCost() for value 1.20";
		assertEquals(message, 1.20, snack1get.getCost(), 0.001);
		
	}
	
	@Test
	public void test_setCost() {
		String message = "Failed to setCost() to 4.00";
		snack1set.setCost(4.00);
		assertEquals(message, 4.00, snack1set.getCost(), 0.001);
	}
	
	@Test
	public void test_getID() {
		String message = "Failed to getID() for id of \"snck001\"";
		assertEquals(message, "snck001", snack1get.getID());
	}
		
	@Test
	public void test_setID() {
		String message = "Failed to setID() to \"snck123";
	snack1set.setID("snck123");
		assertEquals(message, "snck123", snack1set.getID());
	}
	
	//for tests using constructors, need to do them this way instead of with "(expected = ...)"
	//because there are 2 exceptions the constructor can throw that need to be handled
	@Test
	public void test_validateIDwithDuplicateID() {
		try {
			Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snck003");
			Snack crisps = new Snack("Crisps", "Plain", 3.00, "snck003");
			fail();
		} catch (DuplicateIDException e) {
			assertTrue(e.getMessage().contains("snck003"));
		} catch (InvalidIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDNotEnoughNumbers() {
		try {
			Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snck00");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("snck00"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDTooManyNumbers() {
		try {
			Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snck0000");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("snck0000"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDCharactersNotNumbers() {
		try {
			Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snckaaa");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("snckaaa"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_ConstructorPriceValidation() {
		try {
			Snack orange = new Snack("Orange", "Small boy", -1.00, "snck004");
		} catch (InvalidIDException e) {
			fail();
		} catch (DuplicateIDException e) {
			fail();
		}
	}
}
