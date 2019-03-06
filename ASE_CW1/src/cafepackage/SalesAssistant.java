package cafepackage;

import java.util.LinkedList;

public class SalesAssistant implements Runnable, Subject{
	private String displayString;

	private OrderQueue queue;
	private LinkedList<Observer> observers;
	
	private Order currentOrder;
	
	private static final long DEFAULTSLEEPTIME = 250; //Default time taken between adding orders
	private static final long DEFAULTWAKEUPTIME = 500; //Default time to wait before thread becomes active
	
	//Actual wait times are the default multiplied by the simulation speed
	private long actualSleepTime;
	private long actualWakeUpTime;
	
	public SalesAssistant(OrderQueue queue, long timeModifier) {
		this.queue = queue;
		this.observers = new LinkedList<Observer>();
		this.updateDisplay();
		
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier;
		this.actualWakeUpTime = DEFAULTWAKEUPTIME * timeModifier;
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
			try {
				currentOrder = this.queue.get();
				this.updateDisplay();
				Thread.sleep(actualSleepTime);
				orderCompleted();
			} catch (InterruptedException e) {
				//do nothing
			}
		}
	}
	
	public void orderCompleted() throws InterruptedException{
		this.currentOrder = null;
		updateDisplay();
		Thread.sleep(actualSleepTime);
	}
	
	private void updateDisplay() {
		if(currentOrder != null) {
			this.displayString = "" + currentOrder.getCustomerId();
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
