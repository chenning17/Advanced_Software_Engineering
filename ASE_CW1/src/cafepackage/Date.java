package cafepackage;

public class Date {

	private int hours, minutes, seconds;
	private int year, month, day;
	
	public Date(int hours, int minutes, int seconds, int year, int month, int day) {
		this.hours = hours;
		this.minutes = minutes; 
		this.seconds = seconds;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	/**
	 * Checks if this date object refers to the same date as another
	 * @param other The date to be compared to
	 * @return True if they refer to the same date, false otherwise
	 */
	public boolean equals(Date other) {
		if(this.hours == other.getHours() && this.minutes == other.getMinutes()
				&& this.seconds == other.getSeconds() && this.year == other.getYear()
				&& this.month == other.getMonth() && this.day == other.getDay()) {
			return true;
		}else {
			return false;
		}
	}
	
	//---------Getters---------
	public int getHours() {
		return this.hours;
	}
	
	public int getMinutes() {
		return this.minutes;
	}
	
	public int getSeconds() {
		return this.seconds;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getDay() {
		return this.day;
	}
}
