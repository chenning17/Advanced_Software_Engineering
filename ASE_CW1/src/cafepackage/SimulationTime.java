package cafepackage;

public class SimulationTime {
	private int hours, minutes, seconds;
	private int year, month, day;
	static SimulationTime instance;
	
	private SimulationTime(int hours, int minutes, int seconds, int year, int month, int day) {
		this.hours = hours;
		this.minutes = minutes; 
		this.seconds = seconds;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
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
	}
}
