package cafepackage.model;

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
	
	public Date() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
		this.year = 2000;
		this.month = 1;
		this.day = 1;
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
	
	/**
	 * Returns a copy of this object
	 */
	public Date clone() {
		return new Date(this.hours, this.minutes, this.seconds, this.year, this.month, this.day);
	}
	
	/**
	 * Returns string representation of date in form "HH:MM:SS DD/MM/YYYY"
	 */
	public String toString() {
		return String.format("%02d:%02d:%02d %02d/%02d/%04d", hours, minutes, seconds, day, month, year);
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
