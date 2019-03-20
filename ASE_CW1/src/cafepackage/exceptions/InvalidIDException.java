package cafepackage.exceptions;

public class InvalidIDException extends Exception {

	public InvalidIDException(String id){
		super("ID \'" + id + "\' is invalid.");  
		} 
}
