package cafepackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import Part_2.LogFile;

public class SalesAssistant implements Runnable, Subject{
	private String displayString;
	private int id;

	private OrderQueue queue; //normal queue of customers
	private OnlineOrderQueue onlineQueue; //online orders to be collected
	private LinkedList<Observer> observers;
	
	private Order currentOrder;
	
	private static final long DEFAULTSLEEPTIME = 250; //Default time taken between adding orders
	private static final long DEFAULTWAKEUPTIME = 1100; //Default time to wait before thread becomes active
	
	//Actual wait times are the default multiplied by the simulation speed
	private long actualSleepTime;
	private long actualWakeUpTime;
	
	public SalesAssistant(OrderQueue queue, long timeModifier, int id, OnlineOrderQueue onlineQueue) {
		this.queue = queue;
		this.observers = new LinkedList<Observer>();
		this.updateDisplay("");
		this.onlineQueue = onlineQueue;
		
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier;
		this.actualWakeUpTime = DEFAULTWAKEUPTIME * timeModifier;
		
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
		
		while(!queue.isDone() || !queue.isEmpty() || !this.onlineQueue.isDone()) {
			checkForOrders();			
		}
		for(Order o: this.onlineQueue.getProcessed()) {
			System.out.println(o.getCustomerId());
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
			} else {
					processOrder();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method used when online order is being collected. Takes very little time, as order has already been prepared
	 * @return boolean value representing success of operation
	 * @throws InterruptedException
	 */
	private boolean provideOnlineOrder() throws InterruptedException {
		currentOrder = this.onlineQueue.get();
		//remove from prepared orders
		boolean foundCustomer = false;
		
		//while loop fixes bug where they're looking for a customer who hasn't joined the queue yet?
		while(!foundCustomer) {
			for(int i = 0; i < this.onlineQueue.getProcessed().size(); i++) {
				Order temp = this.onlineQueue.getProcessed().get(i);
				if(temp.getCustomerId() == currentOrder.getCustomerId()) {
					this.onlineQueue.removePreparedAt(i);
					logMessage("handing over online order " + currentOrder.getCustomerId());
					this.updateDisplay("Providing pre-order: ");
					this.orderCompleted();
					return true;
				}
			}
			Thread.sleep(500);
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
			logMessage("preparing online order " + currentOrder.getCustomerId());
			this.updateDisplay("Preparing online order: ");
			Thread.sleep(actualSleepTime * currentOrder.getItems().size());
			this.onlineQueue.addProcessed(currentOrder);
			orderCompleted();
			return true;
		}
		return false;
	}
	
	/**
	 * Process customer from regular queue
	 * @throws InterruptedException
	 */
	private void processOrder() throws InterruptedException{
		currentOrder = this.queue.get();
		logMessage("serving customer " + currentOrder.getCustomerId());
		this.updateDisplay("Serving customer: ");
		Thread.sleep(actualSleepTime * currentOrder.getItems().size());
		orderCompleted();
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
		}else {
			this.displayString = "No current item.";
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

}
