package cafepackage;

import java.util.ArrayList;

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
		ArrayList<SalesAssistant> servers = new ArrayList<SalesAssistant>();
		SalesAssistant s1 = new SalesAssistant(queue, 4);
		SalesAssistant s2 = new SalesAssistant(queue, 4);
		servers.add(s1);
		servers.add(s2);
		
		CafeStateGUI gui = new CafeStateGUI(servers, queue);
		
		//TODO: Instantiate controller and pass it view and model
		
		//Run the order producer to begin the simulation
		OrderProducer p = new OrderProducer(orders, timeModifier, queue);
		Thread number1 = new Thread(p);
		number1.start();
		for(SalesAssistant s: servers) {
			Thread temp = new Thread(s);
			temp.start();
		}
	}

}
