package cafepackage;

import java.awt.CardLayout;
import java.awt.TextField;
import java.util.Hashtable;

import javax.swing.*;

public class StartGUI extends JFrame {
	
	JPanel pnl = new JPanel();
	
	JPanel pnlOne = new JPanel();
	JLabel chooseSimSpeed = new JLabel("Choose Simulation Speed:");
	JSlider simSpeed = new JSlider(0,10);
	
	JPanel pnlTwo = new JPanel();
	JLabel chooseServerNum = new JLabel("Choose the number of servers:");
	String[] numServers = new String[] {"1","2","3","4","5"};
	JComboBox serverNum = new JComboBox<String>(numServers);
	
	JPanel pnlThree = new JPanel();
	JTextArea menuChoice = new JTextArea(1,30);
	JLabel chooseMenu = new JLabel("Choose Menu:");

	JPanel pnlFour = new JPanel();
	JTextArea orderChoice = new JTextArea(1,30);
	JLabel chooseOrder = new JLabel("Choose Order:");

	//creates overall frame for GUI
	public StartGUI() {
		super( "WELCOME!" ); //window name
		setSize( 500 , 300 );
		setDefaultCloseOperation (DISPOSE_ON_CLOSE );
		setLocation(450,400);
		setVisible (true );
		add (pnl);
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add( pnlOne );
		pnl.add( pnlTwo );
		pnl.add( pnlThree );
		pnl.add( pnlFour );
		menuChoice.setLineWrap(true);
		menuChoice.setWrapStyleWord(true);
		orderChoice.setLineWrap(true);
		orderChoice.setWrapStyleWord(true);
		
		pnlOne.add(chooseSimSpeed);
		pnlOne.add(simSpeed);
		simSpeed.setMajorTickSpacing(5);
		simSpeed.setPaintTicks(true);
		simSpeed.setPaintLabels(true);
		Hashtable position = new Hashtable();
		position.put(0, new JLabel("0"));
		position.put(5, new JLabel("5"));
		position.put(10, new JLabel("10"));
		simSpeed.setLabelTable(position);
		
		pnlTwo.add(chooseServerNum);
		pnlTwo.add(serverNum);
		
		pnlThree.add(chooseMenu);
		pnlThree.add(menuChoice);
		
		pnlFour.add(chooseOrder);
		pnlFour.add(orderChoice);
		
		
	}
	




public static void main(String[] args) {
	StartGUI startgui = new StartGUI();

	
}

}