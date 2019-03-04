package Part_2;

public class Singleton {
	// static variable single_Instance of Singleton
	private static Singleton single_Instance = null;

	public String s;

	// Private constructor restricted only to this class
	private Singleton() {
		s = " has Read Log File and timestamp.";
	}

	// Static method instance of Singleton Class
	public static Singleton getInstance() {
		// to ensure only one instance is created
		if (single_Instance == null)
			single_Instance = new Singleton();

		return single_Instance;

	}
}
