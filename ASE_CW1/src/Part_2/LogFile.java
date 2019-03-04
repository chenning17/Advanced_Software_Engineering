package Part_2;

public class LogFile {

	public static void main(String args[]) {

		// instantiating Singleton class with variable x
		Singleton x = Singleton.getInstance();

		// instantiating Singleton class with variable y
		Singleton y = Singleton.getInstance();

		// instantiating Singleton class with variable z
		Singleton z = Singleton.getInstance();

		// changing variable of instance x
		x.s = (x.s).toUpperCase();

		System.out.println("String from x is " + x.s);
		System.out.println("String from y is " + y.s);
		System.out.println("String from z is " + z.s);

		// changing variable of instance z
		z.s = (z.s).toLowerCase();

		System.out.println("String from x is " + x.s);
		System.out.println("String from y is " + y.s);
		System.out.println("String from z is " + z.s);

	}

}
