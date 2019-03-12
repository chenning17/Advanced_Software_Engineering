package cafepackage;

import java.util.ArrayList;
import java.util.LinkedList;

public class SalesAssistant implements Runnable, Subject{
	private String displayString;

	private OrderQueue queue;
	private LinkedList<Observer> observers;
	private Report report;
	private Order currentOrder;
	
	private static final long DEFAULTSLEEPTIME = 250; //Default time taken between adding orders
	private static final long DEFAULTWAKEUPTIME = 100; //Default time to wait before thread becomes active
	
	//Actual wait times are the default multiplied by the simulation speed
	private long actualSleepTime;
	private long actualWakeUpTime;
	
	static ArrayList<SalesAssistant> assistants;
	boolean done = false;
	
	public SalesAssistant(OrderQueue queue, long timeModifier, Report report) {
		this.queue = queue;
		this.observers = new LinkedList<Observer>();
		this.report = report;
		this.updateDisplay();
		
		this.actualSleepTime = DEFAULTSLEEPTIME * timeModifier;
		this.actualWakeUpTime = DEFAULTWAKEUPTIME * timeModifier;
		if(assistants == null) {
			assistants = new ArrayList<SalesAssistant>();
		}
		assistants.add(this);
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
				processOrder();
			} catch (InterruptedException e) {
				//do nothing
			}
		}
		this.done = true;
		boolean makeReport = true;
		
		for(int i = 0; i< assistants.size(); i++) {
			try {
				if(!assistants.get(i).getDone()) {
					makeReport = false;
				}
			}catch(NullPointerException npe) {
				//Do nothing if assistant alread deleted
			}

		}
		
		if(makeReport) {
			System.out.println("report made");
			report.generateReport();
		}
	}
	
	public boolean getDone() {
		return this.done;
	}
	
	private void processOrder() throws InterruptedException{
		currentOrder = this.queue.get();
		this.updateDisplay();
		Thread.sleep(actualSleepTime * currentOrder.getItems().size());
		report.addOrder(currentOrder);
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
			/*for(Item item : currentOrder.getItems()) {
				this.displayString += item.getName();
			}*/
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
