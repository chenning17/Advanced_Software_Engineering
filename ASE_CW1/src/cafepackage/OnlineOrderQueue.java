package cafepackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
	
	//adds an order to pending, updates boolean
	public synchronized void addPending(Order o) {
		this.pendingOrders.add(o);
			if(this.pendingOrders.size() > 0) {
				this.pending = true;
		}
		this.notifyObservers();
	}
	
	//removes and returns the next order from pending
	public synchronized Order getPending() {
		Order nextOrder = this.pendingOrders.removeFirst();
		if(this.pendingOrders.size() == 0) {
			this.pending = false;
		}
		this.notifyObservers();
		return nextOrder;
	}
	
	public int pendingSize() {
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
	
	//remove processed order upon collection
	public synchronized void removedProcessed() {
		this.processedOrders.removeFirst();
		if(this.processedOrders.size() == 0) {
			this.processed = false;
		}
		this.notifyObservers();
	}
	
	public int processedSize() {
		return this.processedOrders.size();
	}

}
