package cafepackage;

public class Manager {
	private ItemCollection menu;
	private OrderCollection orders;
	
	public Manager() {
		this.menu = new ItemCollection();
		this.orders = new OrderCollection();
		
	}
	
	public void run() {
		System.out.println("Inputting Items");
		ItemLoader itemLoader = new ItemLoader("Menu.csv");
		itemLoader.loadItems();
		for (Item i : menu) {
			System.out.println(i.getName());
		}
		
	}
}




