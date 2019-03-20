package cafepackage.model;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import cafepackage.interfaces.Observer;
import cafepackage.interfaces.Subject;

public class OrderQueue implements Subject{
	protected LinkedList<Observer> observers;
	protected LinkedList<Order> currentQueue;
	private Boolean done;
	protected Boolean empty;
	
	public OrderQueue() {
		this.observers = new LinkedList<Observer>();
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
		if(this.empty) {
			throw new NoSuchElementException("No orders to get");
		}

		Order returnedOrder = this.currentQueue.remove();
		if(this.currentQueue.size() == 0) {
			this.empty = true;
		}
		
		this.notifyObservers();
				
		return returnedOrder;
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
		this.notifyObservers();
		notifyAll();
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
	public boolean isDone() {
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
	
	/**
	 * Returns a clone of queue so that data can be accessed by an external class
	 * Clone ensures external class cannot actually edit queue.
	 * @return Clone of queue containing orders
	 */
	public LinkedList<Order> getQueueCopy(){
		return (LinkedList<Order>) this.currentQueue.clone();
		//TODO - think of better solution
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
