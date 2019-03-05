package cafepackage;

public class CafeSimulation {
	public static void main(String[] args) {
		
		//filenames
		//TODO: Allow user to set these 3 variable before simulation starts
		String menuFile = "Menu.csv";
		String orderFile = "OrderList.csv";
		long timeModifier = 1; //CHANGE ME - to change speed of addition
		
		//Load in data from CSVs
		ItemLoader itemLoader = new ItemLoader(menuFile);
		OrderLoader orderLoader = new OrderLoader(orderFile);
		ItemCollection menu = itemLoader.loadItems();
		OrderCollection orders = orderLoader.loadOrders();

		//create an empty queue
		OrderQueue queue = new OrderQueue();
		
		CafeStateGUI gui = new CafeStateGUI(7, queue);
		
		//TODO: Instantiate controller and pass it view and model
		
		//Run the order producer to begin the simulation
		OrderProducer p = new OrderProducer(orders, timeModifier, queue);
		p.run();
	}

}
