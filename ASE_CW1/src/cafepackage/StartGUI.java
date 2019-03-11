package cafepackage;

import java.awt.CardLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JFileChooser;

import javax.swing.*;

public class StartGUI extends JFrame implements ActionListener {

	
/** 
 * The StartGUI class is used to display the first state of the cafe simulation
 * Contains info about the speed of order processing, a selection for the servers and the ability to
 * select both previous Menu.csv and Order.csv's to be viewed from documents saved.
 **/
	
	JPanel pnl = new JPanel();

	//Changes the cafe simulation speed, at the top of GUI
	JPanel pnlOne = new JPanel();
	JLabel chooseSimSpeed = new JLabel("Choose Simulation Speed:");
	JSlider simSpeed = new JSlider(0, 10);

	//Inserts a selection list to choose number of servers, second section of GUI
	JPanel pnlTwo = new JPanel();
	JLabel chooseServerNum = new JLabel("Choose the number of servers:");
	String[] numServers = new String[] { "1", "2", "3", "4", "5" };
	JComboBox serverNum = new JComboBox<String>(numServers);

	//Button to select and browse files for Menu.csv, third part of GUI
	JPanel pnlThree = new JPanel();
	JTextArea menuChoice = new JTextArea(1, 30);
	JLabel chooseMenu = new JLabel("Choose Menu:");
	JButton menuButton = new JButton("Select File");

	//Button to select and browse files for Order.csv, bottom of GUI
	JPanel pnlFour = new JPanel();
	JTextArea orderChoice = new JTextArea(1, 30);
	JLabel chooseOrder = new JLabel("Choose Order:");
	JButton orderButton = new JButton("Select File");

	// creates overall frame for GUI
	public StartGUI() {
		super("WELCOME!"); // window name
		setSize(500, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(450, 400);
		setVisible(true);
		add(pnl);
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(pnlOne);
		pnl.add(pnlTwo);
		pnl.add(pnlThree);
		pnl.add(pnlFour);
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
		pnlThree.add(menuButton);

		pnlFour.add(chooseOrder);
		pnlFour.add(orderChoice);
		pnlFour.add(orderButton);
	
	//Implements the buttons use
	menuButton.addActionListener(this);
	orderButton.addActionListener(this);
	
	}
	
	/**
	 * StartGUI constructor, takes one argument - a String used to set the
	 * action performed by the GUI.
	 * 
	 * @param fileSelect
	 *       
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileSelect = new JFileChooser();
		
		fileSelect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		fileSelect.setAcceptAllFileFilterUsed(true);
		
		int rVal = fileSelect.showOpenDialog(null);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			menuChoice.setText(fileSelect.getSelectedFile().toString());
			orderChoice.setText(fileSelect.getSelectedFile().toString());
		}
	} 
	public static void main(String[] args) {
		StartGUI startgui = new StartGUI();

	}

}