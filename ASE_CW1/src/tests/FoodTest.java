package tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cafepackage.exceptions.DuplicateIDException;
import cafepackage.exceptions.InvalidIDException;
import cafepackage.model.items.Food;

public class FoodTest {

	private static Food food1get;
	private static Food food1set;

	@BeforeClass
	public static void oneTimeSetUp() {
		try {
			food1get = new Food("Panini", "Ham and cheese", 4.45, "food001",1);
			food1set = new Food("Panini", "Ham and cheese", 4.45, "food002",1);
		} catch (DuplicateIDException | InvalidIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_getName() {
		String message = "Failed to getName() for food with name \"Panini\"";
		assertEquals(message, "Panini", food1get.getName());
	}
	
	@Test
	public void test_setName() {
		String message = "Failed to setName() to \"Sandwich\"";
		food1set.setName("Sandwich");
		assertEquals("Sandwich", food1set.getName());
	}
	
	@Test
	public void test_getDescription() {
		String message = "Failed to getDescription() for \"Ham and cheese\"";
		assertEquals(message, "Ham and cheese", food1get.getDescription());
	}
	
	@Test
	public void test_setDescription() {
		String message = "Failed to setDescription() to \"Veggie Surprise\"";
		food1set.setDescription("Veggie Surprise"); 
		assertEquals(message, "Veggie Surprise", food1set.getDescription());
	}
	
	@Test
	public void test_getCost() {
		String message = "Failed to getCost() for value 4.45";
		assertEquals(message, 4.45, food1get.getCost(), 0.001);
	}
	
	@Test
	public void test_setCost() {
		String message = "Failed to setCost() to 4.00";
		food1set.setCost(4.00);
		assertEquals(message, 4.00, food1set.getCost(), 0.001);
	}
	
	@Test
	public void test_getID() {
		String message = "Failed to getID() for id of \"food001\"";
		assertEquals(message, "food001", food1get.getID());
	}
	
	@Test
	public void test_setID() {
	String message = "Failed to setID() to \"food123";
	food1set.setID("food123");
		assertEquals(message, "food123", food1set.getID());
	}
	
	//for tests using constructors, need to do them this way instead of with "(expected = ...)"
	//because there are 2 exceptions the constructor can throw that need to be handled
	@Test
	public void test_validateIDwithDuplicateID() {
		try {
			Food sandwich = new Food("Sandwich", "Pickle", 5.00, "food003",1);
			Food chips = new Food("Chips", "Very salty", 3.00, "food003",1);
			fail();
		} catch (DuplicateIDException e) {
			assertTrue(e.getMessage().contains("food003"));
		} catch (InvalidIDException e) {
			fail();
		}
	}

	@Test
	public void test_validateIDwithInvalidIDNotEnoughNumbers() {
		try {
			Food sandwich = new Food("Sandwich", "Pickle", 5.00, "food00",1);
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("food00"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDTooManyNumbers() {
		try {
			Food sandwich = new Food("Sandwich", "Pickle", 5.00, "food0000",1);
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("food0000"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test
	public void test_validateIDwithInvalidIDCharactersNotNumbers() {
		try {
			Food sandwich = new Food("Sandwich", "Pickle", 5.00, "foodaaa",1);
			fail();
		} catch (InvalidIDException e) {
			assertTrue(e.getMessage().contains("foodaaa"));
		} catch (DuplicateIDException e) {
			fail();
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_ConstructorPriceValidation() {
		try {
			Food sandwich = new Food("Sandwich", "Pickle", -5.00, "food004",1);
			fail();
		} catch (InvalidIDException e) {
			fail();
		} catch (DuplicateIDException e) {
			fail();
		}
	}
}
