package cafepackage;

import java.util.LinkedList;

public class SalesAssistant implements Runnable, Subject{
	private String currentOrderItems;
	private long actualSleepTime;
	private static final long DEFAULTSLEEPTIME = 250; //minimum time taken between adding orders
	private OrderQueue queue;
	private LinkedList<Observer> observers;
	
	public SalesAssistant(OrderQueue queue, long timeModifier) {
		this.queue = queue;
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier;
		this.observers = new LinkedList<Observer>();
		this.currentOrderItems = "No current items";
	}
	
	@Override
	public void run() {
		System.out.println("Running");
		while(true) {
			try {
				Thread.sleep(actualSleepTime);
				Order currentOrder = this.queue.get();
				
				this.currentOrderItems = "" + currentOrder.getCustomerId();
				/*
				for(Item i: currentOrder.getItems()) {
					this.currentOrderItems += "\n" + i.getName();
				}
				*/
				
			} catch (InterruptedException e) {
				//do nothing
			}
		}
		//orderCompleted();
		//notifyObservers();
		
	}
	
	public void orderCompleted() {
		this.currentOrderItems = "This Order has been completed";
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
		return this.currentOrderItems;
	}

}
