package cafepackage;

import java.sql.Date;

public class OrderLoader extends FileInput {

	//Basic line processor for orders
	@Override
	protected void processLine(String inputLine) {
		

		OrderCollection orderCollection = new OrderCollection(); 
		
		try {
			String parts [] = inputLine.split(",");

			Date timestamp = Date.valueOf(parts[0]);	
			int customerID = Integer.parseInt(parts[1]);
			String orderID = parts[2]; //error- should be Item not String
			

			Order order = new Order(timestamp, customerID, orderID);
			orderCollection.add(order);
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}		
}
