package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cafepackage.DuplicateIDException;
import cafepackage.InvalidIDException;
import cafepackage.OrderLoader;
import cafepackage.ItemLoader;
import cafepackage.Item;
import cafepackage.ItemCollection;
import cafepackage.Snack;
import cafepackage.Food;
import cafepackage.Discount;
import cafepackage.Drink;
import cafepackage.Order;;

public class ItemLoaderTest {
	private BufferedWriter writer;
	private ItemLoader itemLoader;
	private ItemCollection menu;
	private String food1;
	private String drink1;
	private String drink2;
	private String snack1;
	private String discount1;
	
	
@Before
	public void setUp() {
	
	try {
		writer = new BufferedWriter(new FileWriter("testFile.csv"));
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	food1 = "food001,Haggis,Haggis with Potato and Turnip,8.50";
	drink1 = "drnk001,Fanta,Soft Drink,1.25";
	snack1 = "snck001,Smokey Bacon Crisps,Crisps,0.90";
	discount1 = "disc001,Meal Deal, Food offer,5.50";
	drink2 = "drin123,Fanta,Soft Drink,1.25";
	this.menu = new ItemCollection();
	itemLoader = new ItemLoader("testFile.csv", this.menu);
		}   	
	
	public void loadReadItem(String csvLine, String itemId, String itemName) {
		try {
			writer.write(csvLine);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.menu = this.itemLoader.loadItems();
		Item testItem = menu.findItemById(itemId);
		if (testItem == null) {
			fail();
		}
		assertEquals("Failed to read in food item", itemName, testItem.getName());
	}
// Checks that Food items are loaded in correctly
	@Test
	public void testLoadInFood() {
		loadReadItem(this.food1, "food001", "Haggis");
	}

// Checks that Drink items are loaded in correctly
	@Test
	public void testLoadInDrink() {
		loadReadItem(this.drink1, "drnk001", "Fanta");
	}

// Checks that Snack items are loaded in correctly
	@Test
	public void testLoadInSnack() {
		loadReadItem(this.snack1, "snck001", "Smokey Bacon Crisps");
	}
// Checks that Discounts are loaded in correctly
	@Test
	public void testLoadInDiscount() {
		loadReadItem(this.discount1, "disc001", "Meal Deal");
	}
	

//Tests constructor when an invalid drink id (drin) is passed
	@Test //(expected = InvalidIDException.class)
	public void testLoadInDrink2() {
		loadReadItem(this.drink2, "drin123", "Fanta");
	}
@After
public void tearDown() {
	try {
		writer.close();
		
	} catch (Exception e) {
		//Do Nothing 
	}
}
}
