package cafepackage;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import cafepackage.interfaces.Observer;
import logFilePackage.LogFile;

public class OnlineOrderQueue extends OrderQueue {
	private LinkedList<Order> pendingOrders; //Orders that have been requested but not processed
	private LinkedList<Order> processedOrders; //orders that have been processed by not collected
	
	private boolean pending = false;
	private boolean processed = false;

	public OnlineOrderQueue() {
		this.observers = new LinkedList<Observer>();
		this.currentQueue = new LinkedList<Order>();
		this.pendingOrders = new LinkedList<Order>();
		this.processedOrders = new LinkedList<Order>();
	}
	
	/**
	 * 
	 * @return true if there are still orders needing processed
	 */
	public synchronized boolean needsProcessed() {
		return (this.pending || !this.empty);
	}

	@Override
	public synchronized boolean isDone() {
		return (this.pending || this.processed || !this.isEmpty());
	}
	
	/**
	 * Add a new order to pending orders
	 * @param o order to be added
	 */
	public synchronized void addPending(Order o) {
		this.pendingOrders.add(o);
			if(this.pendingOrders.size() > 0) {
				this.pending = true;
		}
		LogFile.getInstance().writeToLogFile("Online order for : " + o.getCustomerId());
		this.notifyObservers();
	}
	
	/**
	 * Remove an order from Processed orders at specified index
	 * @param i index to remove item at
	 */
	public synchronized void removePreparedAt(int i) {
		this.processedOrders.remove(i);
		notifyObservers();
	}
	
	/**
	 * Get the first order from the pending order list
	 * @return first order needing processed
	 */
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
	
	/**
	 * 
	 * @return Number of orders in pending list
	 */
	public synchronized int pendingSize() {
		return this.pendingOrders.size();
	}
	
	/**
	 * Add order to processed list, ready for collection
	 * @param o order to be added
	 */
	public synchronized void addProcessed(Order o) {
		this.processedOrders.add(o);
		if(this.processedOrders.size() > 0) {
			this.processed = true;
		}
		this.notifyObservers();
	}
	
	/**
	 * 
	 * @return LinkedList of processed orders, awaiting collection
	 */
	public synchronized LinkedList<Order> getProcessed() {
		return this.processedOrders;
	}
	
	/**
	 * Remove first element from the processed list
	 */
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
	
	/**
	 * 
	 * @return number of orders that have been processed and are awaiting collection
	 */
	public synchronized int processedSize() {
		return this.processedOrders.size();
	}
	
	/**
	 * Getter for pending boolean
	 * @return true if there are orders in the pending list
	 */
	public synchronized boolean isPending() {
		return this.pending;
	}
}
