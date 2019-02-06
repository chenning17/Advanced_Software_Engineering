package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.Food;

public class FoodTest {
	Food food1;

	@Before
	public void setUp() {
		food1 = new Food("Panini", "Ham and cheese", 4.45, "food001");
	}

	@Test
	public void test_getName() {
		String message = "Failed to getName() for food with name \"Panini\"";
		assertEquals(message, "Apple Juice", food1.getName());
	}
	
	@Test
	public void test_setName() {
		String message = "Failed to setName() to \"Sandwich\"";
		food1.setName("Sandwich");
		assertEquals("Sandwich", food1.getName());
	}
	
	@Test
	public void test_getDescription() {
		String message = "Failed to getDescription() for \"Ham and cheese\"";
		assertEquals(message, "Ham and cheese", food1.getDescription());
	}
	
	@Test
	public void test_setDescription() {
		String message = "Failed to setDescription() to \"Veggie Surprise\"";
		food1.setDescription("Veggie Surprise"); 
		assertEquals(message, "Veggie Surprise", food1.getDescription());
	}
	
	@Test
	public void test_getCost() {
		String message = "Failed to getCost() for value 4.45";
		assertEquals(message, 4.45, food1.getCost(), 0.001);
	}
	
	@Test
	public void test_setCost() {
		String message = "Failed to setCost() to 4.00";
		food1.setCost(4.00);
		assertEquals(message, 4.00, food1.getCost(), 0.001);
	}
	
	@Test
	public void test_getID() {
		String message = "Failed to getID() for id of \"food001\"";
		assertEquals(message, "food001", food1.getID());
	}
	
	@Test
	public void test_setID() {
	String message = "Failed to setID() to \"food123";
	food1.setID("food123");
		assertEquals(message, "food123", food1.getID());
	}
	

	@Test (expected = DuplicateIDException.class)
	public void test_validateIDwithDuplicateID() {
		Food sandwich = new Food("Sandwich", "Pickle", 5.00, "food003");
		Food chips = new Food("Chips", "Very salty", 3.00, "food003");
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDNotEnoughNumbers() {
		Food sandwich = new Food("Sandwich", "Pickle", 5.00, "food00");
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDTooManyNumbers() {
		Food sandwich = new Food("Sandwich", "Pickle", 5.00, "food0000");
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDCharactersNotNumbers() {
		Food sandwich = new Food("Sandwich", "Pickle", 5.00, "foodaaa");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_ConstructorPriceValidation(){
		Food sandwich = new Food("Sandwich", "Pickle", -5.00, "food004");
	}
}
