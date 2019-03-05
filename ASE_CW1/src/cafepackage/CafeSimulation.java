package cafepackage;

public class CafeSimulation {
	public static void main(String[] args) {
		
		//filenames
		//TODO: Allow user to set these 3 variable before simulation starts
		String menuFile = "Menu.csv";
		String orderFile = "OrderList.csv";
		long timeModifier = 1; //CHANGE ME - to change speed of addition
		
		//load in items from csvs
		ItemLoader itemLoader = new ItemLoader(menuFile);
		OrderLoader orderLoader = new OrderLoader(orderFile);
		ItemCollection menu = itemLoader.loadItems();
		OrderCollection orders = orderLoader.loadOrders();

		//create an empty queue
		OrderQueue queue = new OrderQueue();
		
		CafeStateGUI gui = new CafeStateGUI(7, queue);
		
		//create and run the producer
		OrderProducer p = new OrderProducer(orders, timeModifier, queue);
		p.run();
		
		
		/*
		//print finished queue
		System.out.println("\n--==PRINTING QUEUE==--\n");
		queue.printQueue();
		
		Order o1 = queue.get();
		System.out.println("\nRemoving first element: " + o1.getCustomerId());
		System.out.println("Order contains: ");
		for(Item i :o1.getItems()) {
			System.out.println(i.getName());
		}
		
		
		
		System.out.println("\n--==PRINTING QUEUE==--\n");

		queue.printQueue();
		*/

	}

}
