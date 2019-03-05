package cafepackage;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class OrderQueue {
	private LinkedList<Order> currentQueue;
	private Boolean done;
	private Boolean empty;
	
	public OrderQueue() {
		this.currentQueue = new LinkedList<Order>();
		this.empty = true;
		this.done = false;		
	}
	
	/**
	 * Removes and returns the first element of the queue. Marks queue as empty	
	 * @return Head of the queue
	 * @throws NoSuchElementException Thrown when the queue is empty
	 */
	public synchronized Order get() throws NoSuchElementException {
		while(this.empty) {
			try {
				wait();
			}
			catch (InterruptedException e) {
				//do nothing?
			}
		}
		
		notifyAll();
		
		if(this.currentQueue.size() > 1) {
			return this.currentQueue.remove();
		}
		else {
			this.empty = true;
			return this.currentQueue.remove();
		}
		
	}
	
	/**
	 * Adds an order to the end of the queue
	 * @param o Order to be added
	 */
	public synchronized void put(Order o) {
		if(this.empty == true) {
			this.empty = false;
		}
		this.currentQueue.add(o);
	}
	
	/**
	 * 
	 * @return number of elements currently in queue
	 */
	public synchronized int size() {
		return this.currentQueue.size();
	}
	
	/**
	 * 
	 * @return True if queue is empty, false if not
	 */
	public Boolean isEmpty() {
		return this.empty;
	}
	
	/**
	 * Check whether producer has marked production as finished
	 * @return Boolean representing whether producer is finished 
	 * adding to queue
	 */
	public Boolean isDone() {
		return this.done;
	}
	
	/**
	 * Used by producers to indicate when they are done adding to the queue
	 */
	public void markFinished() {
		this.done = true;
	}
	
	/**
	 * Prints contents of queue to console (mainly testing purposes)
	 */
	public void printQueue() {
		for(Order o: this.currentQueue) {
			System.out.println(o.getCustomerId());
		}
	}
}
