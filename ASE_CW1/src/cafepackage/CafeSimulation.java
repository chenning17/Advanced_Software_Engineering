package cafepackage;

import java.util.ArrayList;

import cafepackage.fileWriting.Report;

public class CafeSimulation {

	public static void main(String[] args) {

		SimulationSettings settings = new SimulationSettings();

		StartGUI startUp = new StartGUI(settings);

		while(startUp.isVisible()) {
			//wait for window to close before moving on
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		//Load in data from CSVs
		ItemLoader itemLoader = new ItemLoader(settings.getMenuFile());
		ItemCollection menu = itemLoader.loadItems();
		OrderLoader orderLoader = new OrderLoader(settings.getOrderFile(), menu);
		OrderCollection orders = orderLoader.loadOrders();

		//Create the model
		OrderQueue queue = new OrderQueue();
		OnlineOrderQueue onlineOrders = new OnlineOrderQueue();
		Report report = new Report(menu);
		ArrayList<SalesAssistant> salesAssistants = createAssistants(settings.getAssistantsCount(), settings.getTimeModifier(), queue, onlineOrders, report);

		//Create the view
		CafeStateGUI gui = new CafeStateGUI(salesAssistants, queue, onlineOrders);

		//TODO: Instantiate controller and pass it view and model

		//Run the order producer to begin the simulation
		OrderProducer p = new OrderProducer(orders, settings.getTimeModifier(), queue);
		OnlineOrderProducer o = new OnlineOrderProducer(menu, settings.getTimeModifier(), onlineOrders, queue);

		Thread producerThread1 = new Thread(p);
		Thread producerThread2 = new Thread(o);
		producerThread1.start();
		producerThread2.start();

		for(SalesAssistant assistant: salesAssistants) {
			Thread assistantThread = new Thread(assistant);
			assistantThread.start();
		}
	}

	static ArrayList<SalesAssistant> createAssistants(int count, int timeModifier, OrderQueue queue, OnlineOrderQueue onlineOrders, Report report){
		ArrayList<SalesAssistant> assistants = new ArrayList<SalesAssistant>();

		for(int i = 0; i < count; i++) {
			assistants.add(new SalesAssistant(queue,timeModifier,i, onlineOrders, report));
		}

		return assistants;
	}

}
