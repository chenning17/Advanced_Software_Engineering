package cafepackage;

import java.util.ArrayList;
import java.util.LinkedList;

import Part_2.LogFile;

public class SalesAssistant implements Runnable, Subject{
	private String displayString;
	private int id;

	private OrderQueue queue;
	private LinkedList<Observer> observers;
	private Report report;
	private Order currentOrder;
	
	private static final long DEFAULTSLEEPTIME = 250; //Default time taken between adding orders
	private static final long DEFAULTWAKEUPTIME = 1100; //Default time to wait before thread becomes active
	
	//Actual wait times are the default multiplied by the simulation speed
	private long actualSleepTime;
	private long actualWakeUpTime;
	

	static ArrayList<SalesAssistant> assistants;
	boolean done = false;
	
	public SalesAssistant(OrderQueue queue, long timeModifier, Report report, int id) {
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
				//Do nothing if assistant already deleted
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
		LogFile.getInstance().writeToLogFile("Server " +this.id+" : " + currentOrder.getCustomerId());
		
		//Gives a wait time for taking the order, relative to the size of the order
		this.takeOrderDisplay();
		Thread.sleep(actualSleepTime*(currentOrder.getItems().size()));
		
		
		this.updateDisplay();
		//changes the processing times based on the menu item
		long totalTime = 0;
		for (int i = 0; i<currentOrder.getItems().size(); i++) {
			long time = currentOrder.getItems().get(i).getProcessTime();
			totalTime = totalTime + time;
		}
		long processSleepTime = actualSleepTime * totalTime;
		
		Thread.sleep(processSleepTime);
		report.addOrder(currentOrder);
		orderCompleted();
	}
	
	private void orderCompleted() throws InterruptedException{
		this.currentOrder = null;
		updateDisplay();
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
