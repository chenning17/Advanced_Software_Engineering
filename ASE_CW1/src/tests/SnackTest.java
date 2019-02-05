package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.Item;

import cafepackage.Snack;

public class SnackTest {
	Snack snack1;

	@Before
	public void setUp() {
		Snack snack1 = new Snack("Apple", "Granny smith", 1.20, "snck001");

	}

	@Test
	public void test_getName() {
		String message = "Failed to getName() for snack with name \"Apple\"";
		assertEquals(message, "Apple", snack1.getName());
	}
	
	@Test
	public void test_setName() {
		String message = "Failed to setName() to \"Banana\"";
		snack1.setName("Banana");
		assertEquals("Banana", snack1.getName());
	}
	
	@Test
	public void test_getDescription() {
		String message = "Failed to getDescription() for \"Granny smith\"";
		assertEquals(message, "Granny smith", snack1.getDescription());
	}
	
	@Test
	public void test_setDescription() {
		String message = "Failed to setDescription() to \"A big one\"";
		snack1.setDescription("A big one")
		assertEquals(message, "A big one", snack1.getDescription());
	}
	
	@Test
	public void test_getCost() {
		String message = "Failed to getCost() for value 1.20";
		assertEquals(message, 1.20, snack1.getCost());
	}
	
	@Test
	public void test_setCost() {
		String message = "Failed to setCost() to 4.00";
		snack1.setCost(4.00);
		assertEquals(message, 4.00, snack1.getCost());
	}
	
	@Test
	public void test_getID() {
		String message = "Failed to getID() for id of \"snck001\"";
		assertEquals(message, "snck001", snack1.getID());
	}
	
	@Test
	public void test_setID() {
		String message = "Failed to setID() to \"snck123";
		snack1.setID();
		assertEquals(message, 4.00, snack1.getID());
	}
	
	@Test (expected = DuplicateIDException.class)
	public void test_validateIDwithDuplicateID() {
		Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snck003");
		Snack crips = new Snack("Crisps", "Plain", 3.00, "snck003");
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDNotEnoughNumbers() {
		Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snck00");
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDTooManyNumbers() {
		Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snck0000");
	}
	
	@Test (expected = InvalidIDException.class)
	public void test_validateIDwithInvalidIDCharactersNotNumbers() {
		Snack donut = new Snack("Donut", "Chocolate filled", 5.00, "snckaaa");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_ConstructorPriceValidation(){
			Snack orange = new Snack("Orange", "Small boy", -1.00, "snck004");
	}

}
