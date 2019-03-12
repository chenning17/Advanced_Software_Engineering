package cafepackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import Part_2.LogFile;

public class OnlineOrderQueue extends OrderQueue {
	private LinkedList<Order> pendingOrders;
	private LinkedList<Order> processedOrders;
	
	private boolean pending = false;
	private boolean processed = false;

	
	
	public OnlineOrderQueue() {
		this.observers = new LinkedList<Observer>();
		this.currentQueue = new LinkedList<Order>();
		this.pendingOrders = new LinkedList<Order>();
		this.processedOrders = new LinkedList<Order>();
	}
	
	public synchronized boolean needsWork() {
		return (this.pending || !this.empty);
	}
	
	//adds an order to pending, updates boolean
	public synchronized void addPending(Order o) {
		this.pendingOrders.add(o);
			if(this.pendingOrders.size() > 0) {
				this.pending = true;
		}
			LogFile.getInstance().writeToLogFile("Online order for : " + o.getCustomerId());
		this.notifyObservers();
	}
	
	public synchronized void removePreparedAt(int i) {
		Order temp = this.processedOrders.remove(i);
		
	}
	
	//removes and returns the next order from pending
	public synchronized Order getPending() {
		if(!this.pending) {
			throw new NoSuchElementException();
		} else {
			Order nextOrder = this.pendingOrders.removeFirst();
			if(this.pendingOrders.size() == 0) {
				this.pending = false;
			}
			this.notifyObservers();
			return nextOrder;
		}
	}
	
	public synchronized int pendingSize() {
		return this.pendingOrders.size();
	}
	
	//add processed order ready for collection
	public synchronized void addProcessed(Order o) {
		this.processedOrders.add(o);
		if(this.processedOrders.size() > 0) {
			this.processed = true;
		}
		this.notifyObservers();
	}
	
	public synchronized LinkedList<Order> getProcessed() {
		return this.processedOrders;
	}
	
	//remove processed order upon collection
	public synchronized void removeProcessed() {
		if(!this.processed) {
			throw new NoSuchElementException();
		} else {
			this.processedOrders.removeFirst();
			if(this.processedOrders.size() == 0) {
				this.processed = false;
			}
			this.notifyObservers();
		}
		
	}
	
	public synchronized int processedSize() {
		return this.processedOrders.size();
	}
	
	public synchronized boolean arePending() {
		return this.pending;
	}
	
	public synchronized void printPending() {
		for(Order o: this.pendingOrders) {
			System.out.println(o.getCustomerId());
			for(Item i: o.getItems()) {
				System.out.println(i.getName());
			}
		}
	}
	
	public synchronized void printProcessed() {
		for(Order o: this.processedOrders) {
			System.out.println(o.getCustomerId());
			for(Item i: o.getItems()) {
				System.out.println(i.getName());
			}
		}
	}

}
