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

import cafepackage.exceptions.DuplicateIDException;
import cafepackage.exceptions.InvalidIDException;
import cafepackage.fileReading.ItemLoader;
import cafepackage.fileReading.OrderLoader;
import cafepackage.model.Order;
import cafepackage.model.items.Discount;
import cafepackage.model.items.Drink;
import cafepackage.model.items.Food;
import cafepackage.model.items.Item;
import cafepackage.model.items.ItemCollection;
import cafepackage.model.items.Snack;;

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
	food1 = "food010,Haggis,Haggis with Potato and Turnip,8.50,1";
	drink1 = "drnk010,Fanta,Soft Drink,1.25,1";
	snack1 = "snck010,Smokey Bacon Crisps,Crisps,0.90,1";
	discount1 = "disc010,Meal Deal, Food offer,5.50,1";
	drink2 = "drin231,Fanta,Soft Drink,1.25,1";
	itemLoader = new ItemLoader("testFile.csv");
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
		loadReadItem(this.food1, "food010", "Haggis");
	}

// Checks that Drink items are loaded in correctly
	@Test
	public void testLoadInDrink() {
		loadReadItem(this.drink1, "drnk010", "Fanta");
	}

// Checks that Snack items are loaded in correctly
	@Test
	public void testLoadInSnack() {
		loadReadItem(this.snack1, "snck010", "Smokey Bacon Crisps");
	}
// Checks that Discounts are loaded in correctly
	@Test
	public void testLoadInDiscount() {
		loadReadItem(this.discount1, "disc010", "Meal Deal");
	}
	

//Tests constructor when an invalid drink id (drin) is passed
	@Test 
	public void testLoadInDrink2() {
				try {
			writer.write(this.drink2);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.menu = this.itemLoader.loadItems();
		Item testItem = menu.findItemById("drin231");
		if (testItem != null) {
			fail();
		}
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
