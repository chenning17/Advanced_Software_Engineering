package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.Discount;

public class DiscountTest {
		
	private static Discount discount1get;
	private static Discount discount1set;

	@BeforeClass
	public static void oneTimeSetUp() {
		try {
			discount1get = new Discount("Half price", "50% off order", 5.50, "disc001");
			discount1set = new Discount("Half price", "50% off order", 5.50, "disc002");
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();
		}
	}
	

	@Test
	public void test_getName() {
		String message = "Failed to getName() for discount with name \"Half price\"";
		assertEquals(message, "Half price", discount1get.getName());
	}
	
	@Test
	public void test_setName() {
		String message = "Failed to setName() to \"3 for 2\"";
		discount1set.setName("3 for 2");
		assertEquals("3 for 2", discount1set.getName());
	}
	
	@Test
	public void test_getDescription() {
		String message = "Failed to getDescription() for \"50% off order\"";
		assertEquals(message, "50% off order", discount1get.getDescription());
	}
	
	@Test
	public void test_setDescription() {
		String message = "Failed to setDescription() to \"massive savings\"";
		discount1set.setDescription("massive savings"); 
		assertEquals(message, "massive savings", discount1set.getDescription());
	}
	
	@Test
	public void test_getCost() {
		String message = "Failed to getCost() for value 5.50";
		assertEquals(message, 5.50, discount1get.getCost(), 0.001);
	}
	
	@Test
	public void test_setCost() {
		String message = "Failed to setCost() to 4.00";
		discount1set.setCost(4.00);
		assertEquals(message, 4.00, discount1set.getCost(), 0.001);
	}
	
	@Test
	public void test_getID() {
		String message = "Failed to getID() for id of \"disc001\"";
		assertEquals(message, "disc001", discount1get.getID());
	}
	
	@Test
	public void test_setID() {
	String message = "Failed to setID() to \"disc123";
	discount1set.setID("disc123");
		assertEquals(message, "disc123", discount1set.getID());
	}
	
	//for tests using constructors, need to do them this way instead of with "(expected = ...)"
	//because there are 2 exceptions the constructor can throw that need to be handled
	@Test
	public void test_validateIDwithDuplicateID() {
		try {
			Discount bargain = new Discount("bargain", "free drink", 2.00, "disc003");
			Discount hugeSavings = new Discount("hugeSavings", "99% off", 3.00, "disc003");
			fail();
		} catch (DuplicateIDException e) {
			assertTrue(e.getMessage().contains("disc003"));
		} catch (InvalidIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDNotEnoughNumbers() {
		try {
			Discount bargain = new Discount("bargain", "free drink", 2.00, "disc00");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("disc00"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDTooManyNumbers() {
		try {
			Discount bargain = new Discount("bargain", "free drink", 2.00, "disc0000");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("disc0000"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDCharactersNotNumbers() {
		try {
			Discount bargain = new Discount("bargain", "free drink", 2.00, "discaaa");
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("discaaa"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_ConstructorPriceValidation() {
		try {
			Discount bargain = new Discount("bargain", "free drink", -2.00, "disc003");
			fail();
		} catch (InvalidIDException e) {
			fail();
		} catch (DuplicateIDException e) {
			fail();
		}
	}
}
