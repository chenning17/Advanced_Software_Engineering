package cafepackage;

import java.util.ArrayList;
import java.util.LinkedList;

import Part_2.LogFile;

public class SalesAssistant implements Runnable, Subject{
	private String displayString;
	private int id;

	private OrderQueue queue; //normal queue of customers
	private OnlineOrderQueue onlineQueue; //online orders to be collected
	private LinkedList<Observer> observers;
	private Report report;
	private Order currentOrder;
	private Discount currentDiscount;

	private static final int DEFAULTSLEEPTIME = 250; //Default time taken between adding orders
	private static final int DEFAULTWAKEUPTIME = 1100; //Default time to wait before thread becomes active


	//Actual wait times are the default multiplied by the simulation speed
	private int actualSleepTime;
	private int actualWakeUpTime;
	
	static ArrayList<SalesAssistant> assistants;
	boolean done = false;

	public SalesAssistant(OrderQueue queue, int timeModifier, int id, OnlineOrderQueue onlineQueue, Report report) {
		this.queue = queue;
		this.observers = new LinkedList<Observer>();
		this.updateDisplay("");
		this.onlineQueue = onlineQueue;
				this.report = report;

		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier;
		this.actualWakeUpTime = DEFAULTWAKEUPTIME * timeModifier;

		if(assistants == null) {
			assistants = new ArrayList<SalesAssistant>();
		}
		assistants.add(this);

		this.id = id;

	}

	@Override
	public void run() {

		//Wait a short time before taking first order
		try {
			Thread.sleep(actualWakeUpTime);
		}catch (InterruptedException e) {
			//do nothing
		}

		while(queue.isDone() == false || queue.isEmpty() == false || this.onlineQueue.isDone() == false) {
			//Wait a short time before taking next order
			try {
				Thread.sleep(actualWakeUpTime);
			}catch (InterruptedException e) {
				//do nothing
			}
			checkForOrders();
		}
		
		this.done = true;
		System.out.println("Assistant finished" + this.id);
		boolean makeReport = true;

		for(int i = 0; i< assistants.size(); i++) {
			try {
				if(!assistants.get(i).getDone()) {
					makeReport = false;
				}
			}catch(NullPointerException npe) {
				//Do nothing if assistant already deleted
			}

		}

		if(makeReport) {
			System.out.println("report made");
			report.generateReport();
			System.exit(0);
		}
	}

	/**
	 * Method where sales assistant checks for an order to process. Prioritise online orders: prepare them, then hand them over, then serve normal customers
	 */
	private void checkForOrders() {
		try {
			if(this.onlineQueue.needsProcessed()) {
				if(this.onlineQueue.isPending()) {
					this.prepareOnlineOrder();
				} else if(!this.onlineQueue.isEmpty()) {
					this.provideOnlineOrder();
				}
			} else if(!this.queue.isEmpty()){
				
					processOrder();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			try {
				Thread.sleep(actualSleepTime);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * Method used when online order is being collected. Takes very little time, as order has already been prepared
	 * @return boolean value representing success of operation
	 * @throws InterruptedException
	 */
	private boolean provideOnlineOrder() throws InterruptedException {
		currentOrder = this.onlineQueue.get();
		this.updateDisplay("Providing pre-order: ");
		//remove from prepared orders
		boolean foundCustomer = false;

		//while loop fixes bug where they're looking for a customer who hasn't joined the queue yet?
		while(!foundCustomer) {
			for(int i = 0; i < this.onlineQueue.getProcessed().size(); i++) {
				Order temp = this.onlineQueue.getProcessed().get(i);
				if(temp.getCustomerId() == currentOrder.getCustomerId()) {
					this.onlineQueue.removePreparedAt(i);
					logMessage("handing over online order " + currentOrder.getCustomerId());
					this.currentDiscount = getDiscount();
					report.addOrder(currentOrder);
					this.orderCompleted();
					this.currentDiscount = null;
					return true;
				}
			}
			Thread.sleep(actualSleepTime);
		}

		return false;
	}

	/**
	 * Take an order from the pending orders, spend time "preparing" it, then add it to the processed orders list
	 * @return boolean value representing success
	 * @throws InterruptedException
	 */
	private boolean prepareOnlineOrder() throws InterruptedException {
		if(this.onlineQueue.isPending()) {
			currentOrder = this.onlineQueue.getPending();
			currentDiscount = this.getDiscount();
			logMessage("preparing online order " + currentOrder.getCustomerId());
			this.updateDisplay("Preparing online order: ");
			Thread.sleep(actualSleepTime * currentOrder.getItems().size());
			this.onlineQueue.addProcessed(currentOrder);
			orderCompleted();
			this.currentDiscount = null;
			return true;
		}
		return false;
	}
	public boolean getDone() {
		return this.done;
	}

	private void processOrder() throws InterruptedException{
		currentOrder = this.queue.get();
		currentDiscount = getDiscount();
		
		logMessage("serving customer " + currentOrder.getCustomerId());
		
		//Gives a wait time for taking the order, relative to the size of the order
		this.takeOrderDisplay();
		Thread.sleep(actualSleepTime*(currentOrder.getItems().size()));
		
		this.updateDisplay("Preparing order for customer ");
		
		//changes the processing times based on the menu item
		int totalTime = 0;
		for (int i = 0; i<currentOrder.getItems().size(); i++) {
			int time = currentOrder.getItems().get(i).getProcessTime();
			totalTime = totalTime + time;
		}
		int processSleepTime = actualSleepTime * totalTime;
		
		Thread.sleep(processSleepTime);
		currentOrder.addItemToOrder(this.currentDiscount);
		report.addOrder(currentOrder);
		orderCompleted();
		currentDiscount = null;
	}

	/**
	 * Used when finished processing an order
	 * @throws InterruptedException
	 */
	private void orderCompleted() throws InterruptedException{
		this.currentOrder = null;
		updateDisplay("");
		Thread.sleep(actualSleepTime);
	}

	
	private void takeOrderDisplay() {
		if(currentOrder != null) {
			this.displayString = "Taking customer " + currentOrder.getCustomerId() + "'s order";
		}else {
			this.displayString = "No current item.";
		}
		notifyObservers();
	}

	/**
	 * Construct a string to be displayed in GUI, then notifies obervsers.
	 * String will be ignored if no current order is present.
	 * @param currentAction String describing what sales assistant is doing
	 */
	private void updateDisplay(String currentAction) {

		if(currentOrder != null) {
			this.displayString = currentAction + currentOrder.getCustomerId();

			ArrayList<Item> OrderItems = currentOrder.getItems();

			for(Item item : OrderItems) {
				this.displayString += "\n" + item.getName();
			}
			this.displayString += String.format("\nTotal cost: £%.2f", this.currentOrder.getCost());
			if(this.currentDiscount!= null) {
				this.displayString += String.format("\nDiscount: £%.2f", this.currentDiscount.getCost());
			}
			
		}else {
			this.displayString =  "No current item.";
		}
		notifyObservers();
	}
	/**
	 * Gets instance of singleton and writes message to log file
	 * @param message to be written to log file
	 */
	private void logMessage(String message) {
		LogFile.getInstance().writeToLogFile("Server " +this.id+" : " + message);
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);

	}

	@Override
	public void notifyObservers() {
		for(Observer observer : this.observers) {
			observer.Update();
		}
	}

	public String getCurrentOrder() {
		return this.displayString;
	}
	
	public Discount getDiscount() {
		ArrayList<Item> items = (ArrayList<Item>)this.currentOrder.getItems().clone();
		Discount discount = (DiscountCalculator.getBestDeal(items));
		return discount;
	}

}
