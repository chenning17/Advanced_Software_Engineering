package cafepackage;

public class SimulationSettings {
	//menu file name, defaults to given project menu file
	private String menuFile = "Menu (version 2).csv";
	
	//order file name, default to given project order file
	private String orderFile = "OrderList.csv";
	
	//Simulation speed	
	private int timeModifier;
	
	//number of sales assistants	
	private int assistantsCount;
		
	//default constructor
	public SimulationSettings() {
			
	}


	public String getMenuFile() {
		return menuFile;
	}


	public void setMenuFile(String menuFile) {
		this.menuFile = menuFile;
	}


	public String getOrderFile() {
		return orderFile;
	}


	public void setOrderFile(String orderFile) {
		this.orderFile = orderFile;
	}


	public int getTimeModifier() {
		return timeModifier;
	}


	public void setTimeModifier(int timeModifier) {
		this.timeModifier = timeModifier;
	}


	public int getAssistantsCount() {
		return assistantsCount;
	}


	public void setAssistantsCount(int assistantsCount) {
		this.assistantsCount = assistantsCount;
	}
	
}
