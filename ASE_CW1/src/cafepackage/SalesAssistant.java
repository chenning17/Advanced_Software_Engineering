package cafepackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import Part_2.LogFile;

public class SalesAssistant implements Runnable, Subject{
	private String displayString;
	private int id;

	private OrderQueue queue;
	private OnlineOrderQueue onlineQueue;
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
		this.updateDisplay();
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
		
		while(!queue.isDone() || !queue.isEmpty()) {
			//try {
				checkForOrders();
			//} catch (InterruptedException e) {
				//do nothing
			//} catch (NoSuchElementException e) {
			//	System.out.println("No such element");
			//}
			
		}
	}
	private void checkForOrders() {
		try {
			if(this.onlineQueue.needsWork()) {
				if(this.onlineQueue.arePending()) {
					this.prepareOnlineOrder();
				} else if(!this.onlineQueue.isEmpty()) {
					this.provideOnlineOrder();
				}
			} else {
					processOrder();
			}
		} catch (Exception e) {
			//do nothing
		}
		
	}
	
	private boolean provideOnlineOrder() throws InterruptedException {
		currentOrder = this.onlineQueue.get();
		LogFile.getInstance().writeToLogFile("Server " +this.id+" : handing over online order " + currentOrder.getCustomerId());
		//remove from prepared orders
		for(int i = 0; i < this.onlineQueue.getProcessed().size(); i++) {
			Order temp = this.onlineQueue.getProcessed().get(i);
			if(temp.getCustomerId() == currentOrder.getCustomerId()) {
				this.onlineQueue.removePreparedAt(i);
				this.updateDisplay1();
				this.orderCompleted();
				return true;
			}
		}
		return false;		
	}
	
	private boolean prepareOnlineOrder() throws InterruptedException {
		if(this.onlineQueue.arePending()) {
			currentOrder = this.onlineQueue.getPending();
			LogFile.getInstance().writeToLogFile("Server " +this.id+" preparing online order: " + currentOrder.getCustomerId());
			this.updateDisplay();
			Thread.sleep(actualSleepTime * currentOrder.getItems().size());
			this.onlineQueue.addProcessed(currentOrder);
			orderCompleted();
			return true;
		}
		return false;
	}
	
	private void processOrder() throws InterruptedException{
		currentOrder = this.queue.get();
		LogFile.getInstance().writeToLogFile("Server " +this.id+" : " + currentOrder.getCustomerId());
		this.updateDisplay();
		Thread.sleep(actualSleepTime * currentOrder.getItems().size());
		orderCompleted();
	}
	
	private void orderCompleted() throws InterruptedException{
		this.currentOrder = null;
		updateDisplay();
		Thread.sleep(actualSleepTime);
	}
	
	private void updateDisplay() {
		if(currentOrder != null) {
			this.displayString = "Serving customer " + currentOrder.getCustomerId();
			
			ArrayList<Item> OrderItems = currentOrder.getItems();
			
			for(Item item : OrderItems) {
				this.displayString += "\n" + item.getName();
			}
		}else {
			this.displayString = "No current item.";
		}
		notifyObservers();
	}
	private void updateDisplay1() {
		if(currentOrder != null) {
			this.displayString = "Fetching online order for: " + currentOrder.getCustomerId();
			
			ArrayList<Item> OrderItems = currentOrder.getItems();
			
			for(Item item : OrderItems) {
				this.displayString += "\n" + item.getName();
			}
		}else {
			this.displayString = "No current item.";
		}
		notifyObservers();
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
