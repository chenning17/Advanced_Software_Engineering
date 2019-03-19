package cafepackage;

public class SimulationSettings {
	//menu file name
	private String menuFile;
	
	//order file name
	private String orderFile;
	
	//Simulation speed	
	private int timeModifier;
	
	//number of sales assistants	
	private int assistantsCount;
		
	public SimulationSettings(String menuFile, String orderFile, int assistantsCount) {
		this.setMenuFile(menuFile); 				
		this.setOrderFile(orderFile); 				 
		this.setTimeModifier(1); 		
		this.setAssistantsCount(assistantsCount); 	
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
