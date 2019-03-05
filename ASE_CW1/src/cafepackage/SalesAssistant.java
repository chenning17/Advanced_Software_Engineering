package cafepackage;

import java.util.LinkedList;

public class SalesAssistant implements Runnable, Subject{
	
	//-	Take order, sleep for constant amount of time, send order complete message to log file.
	private long actualSleepTime;
	private static final long DEFAULTSLEEPTIME = 250; //minimum time taken between adding orders
	private OrderQueue queue;
	private LinkedList<Observer> observers;
	
	public SalesAssistant(OrderQueue queue, long timeModifier) {
		
		this.queue = queue;
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier;
		this.observers = new LinkedList<Observer>();
	}
	
	@Override
	public void run() {
		while(!this.queue.isDone() && !this.queue.isEmpty()) {
			try {
				Thread.sleep(actualSleepTime);
				Order currentOrder = this.queue.get();
			} catch (InterruptedException e) {
				//do nothing
			}
		}
	}
	
	public String orderCompleted() {
		return "This Order has been completed";
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

}
