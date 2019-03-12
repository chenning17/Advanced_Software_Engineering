package cafepackage;

import java.util.ArrayList;

public class CafeSimulation {
	public static void main(String[] args) {
		
		//filenames
		//TODO: Allow user to set these variables before simulation starts
		String menuFile = "Menu.csv";
		String orderFile = "OrderList.csv";
		long timeModifier = 1; //Simulation speed
		int assistantsCount = 5;
		
		//Load in data from CSVs
		ItemLoader itemLoader = new ItemLoader(menuFile);
		ItemCollection menu = itemLoader.loadItems();
		OrderLoader orderLoader = new OrderLoader(orderFile, menu);
		OrderCollection orders = orderLoader.loadOrders();

		//Create the model
		OrderQueue queue = new OrderQueue();
		OnlineOrderQueue onlineOrders = new OnlineOrderQueue();
		ArrayList<SalesAssistant> salesAssistants = createAssistants(assistantsCount, timeModifier, queue, onlineOrders);
		
		//Create the view
		CafeStateGUI gui = new CafeStateGUI(salesAssistants, queue, onlineOrders);
		
		//TODO: Instantiate controller and pass it view and model
		
		//Run the order producer to begin the simulation
		OrderProducer p = new OrderProducer(orders, timeModifier, queue);
		OnlineOrderProducer o = new OnlineOrderProducer(menu, onlineOrders, queue);
		
		Thread producerThread1 = new Thread(p);
		Thread producerThread2 = new Thread(o);
		producerThread1.start();
		producerThread2.start();
		
		for(SalesAssistant assistant: salesAssistants) {
			Thread assistantThread = new Thread(assistant);
			assistantThread.start();
		}
	}
	
	static ArrayList<SalesAssistant> createAssistants(int count, long timeModifier, OrderQueue queue, OnlineOrderQueue onlineOrders){
		ArrayList<SalesAssistant> assistants = new ArrayList<SalesAssistant>();
		
		for(int i = 0; i < count; i++) {
			assistants.add(new SalesAssistant(queue,4,i, onlineOrders));
		}
		
		return assistants;
	}

}
