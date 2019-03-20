package cafepackage.model;

import java.util.LinkedList;

import cafepackage.interfaces.Observer;
import cafepackage.interfaces.Subject;

public class SimulationTime implements Subject{
	private int hours, minutes, seconds;
	private int year, month, day;
	static SimulationTime instance;
	
	private LinkedList<Observer> observers;
	
	private SimulationTime(int hours, int minutes, int seconds, int year, int month, int day) {
		this.hours = hours;
		this.minutes = minutes; 
		this.seconds = seconds;
		this.year = year;
		this.month = month;
		this.day = day;
		
		this.observers = new LinkedList<Observer>();
	}
	
	//Getter method to retrieve the singleton
	public static synchronized SimulationTime getInstance() {
		// to ensure only one instance is created
		if (instance == null) {
			synchronized (SimulationTime.class) {
				if (instance == null) {
					instance = new SimulationTime(9, 0, 0, 2019, 3, 10);
				}
			}
		}
		return instance;
	}
	
	public Date getCurrentDateTime() {
		return new Date(hours, minutes, seconds, year, month, day);
	}
	
	public void increment() {
		seconds++;
		
		if(seconds >= 60) {
			seconds = 0;
			minutes++;
		}
		if(minutes >= 60) {
			minutes = 0;
			hours++;
		}
		if(hours > 17) {
			hours = 9;
			day++;
		}
		//TODO: Know bug, number of days in each month is always 31. Should be changed to reflect actual number of days
		if(day > 31) {
			day = 1;
			month++;
		} if(month > 12) {
			month = 1;
			year++;
		}
		
		//Notify all observers when the time has changed
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
}
