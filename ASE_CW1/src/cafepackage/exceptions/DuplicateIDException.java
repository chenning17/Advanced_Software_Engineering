package cafepackage.exceptions;

public class DuplicateIDException extends Exception{
	
	public DuplicateIDException(String id){
		super("ID \'" + id + "\' already exists.");  
		} 
}
