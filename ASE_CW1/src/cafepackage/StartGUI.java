package cafepackage;

import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.event.*;
import javax.swing.*;

public class StartGUI extends JFrame implements ActionListener {

	/**
	 * The StartGUI class is used to display the first state of the cafe simulation
	 * Contains info about the speed of order processing, a selection for the
	 * servers and the ability to select both previous Menu.csv and Order.csv's to
	 * be viewed from documents saved.
	 **/

	JPanel pnl = new JPanel();

	// Changes the cafe simulation speed, at the top of GUI
	JPanel pnlOne = new JPanel();
	JLabel chooseSimSpeed = new JLabel("Choose Simulation Speed:");
	JSlider simSpeed = new JSlider(0, 10);
	JLabel speed = new JLabel(); // Shows which speed is selected

	// Inserts a selection list to choose number of servers, second section of GUI
	JPanel pnlTwo = new JPanel();
	JLabel chooseServerNum = new JLabel("Choose the number of servers:");
	Integer[] numServers = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	JComboBox<Integer> serverNum = new JComboBox<Integer>(numServers);

	// Button to select and browse files for Menu.csv, third part of GUI
	JPanel pnlThree = new JPanel();
	JTextArea menuChoice = new JTextArea(1, 30);
	JLabel chooseMenu = new JLabel("Choose Menu:");
	JButton menuButton = new JButton("Select File");

	// Button to select and browse files for Order.csv, bottom of GUI
	JPanel pnlFour = new JPanel();
	JTextArea orderChoice = new JTextArea(1, 30);
	JLabel chooseOrder = new JLabel("Choose Order:");
	JButton orderButton = new JButton("Select File");

	// Panel containing buttons to submit preferences or to cancel the operation
	JPanel confirmationPanel = new JPanel();
	JButton okButton = new JButton("OK");
	JButton cancelButton = new JButton("Cancel");

	SimulationSettings settings;
	
	// creates overall frame for GUI
	public StartGUI(SimulationSettings settings) {
		super("Settings Input"); // window name
		
		this.settings = settings;
		this.setSize(500, 450);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
				
		this.setLocation(450, 400);
		
		this.add(pnl);
		
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(pnlOne);
		pnl.add(pnlTwo);
		pnl.add(pnlThree);
		pnl.add(pnlFour);
		pnl.add(confirmationPanel);

		menuChoice.setLineWrap(true);
		menuChoice.setWrapStyleWord(true);
		
		//////////
		menuChoice.setText(settings.getMenuFile());
		//////////
		
		orderChoice.setLineWrap(true);
		orderChoice.setWrapStyleWord(true);
		
		//////////
		orderChoice.setText(settings.getOrderFile());
		//////////

		// adding panel 1 - choosing simulation speed
		pnlOne.add(chooseSimSpeed);
		pnlOne.add(simSpeed);
		pnlOne.add(speed);
		// setting up the slider
		simSpeed.setMajorTickSpacing(5);
		simSpeed.setPaintTicks(true);
		simSpeed.setPaintLabels(true);
		Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
		position.put(0, new JLabel("0"));
		position.put(5, new JLabel("5"));
		position.put(10, new JLabel("10"));
		simSpeed.setLabelTable(position);
		speed.setText("5");
		// display speed beside the component
		simSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				speed.setText(Integer.toString(((JSlider) e.getSource()).getValue()));
			}
		});

		// adding panel 2 - choose number of servers
		pnlTwo.add(chooseServerNum);
		pnlTwo.add(serverNum);

		// adding panel 3 - choose menu
		pnlThree.add(chooseMenu);
		pnlThree.add(menuChoice);
		pnlThree.add(menuButton);
		menuButton.addActionListener(this);

		// adding panel 4 - choose order list
		pnlFour.add(chooseOrder);
		pnlFour.add(orderChoice);
		pnlFour.add(orderButton);
		orderButton.addActionListener(this);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		confirmationPanel.add(okButton);
		confirmationPanel.add(cancelButton);

		
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuButton) {
			JFileChooser fileSelect = new JFileChooser();
			fileSelect.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileSelect.setAcceptAllFileFilterUsed(true);
			int file = fileSelect.showOpenDialog(null);
			if (file == JFileChooser.APPROVE_OPTION) {
				menuChoice.setText(fileSelect.getSelectedFile().getName());
			}
		}
		if (e.getSource() == orderButton) {
			JFileChooser fileSelect = new JFileChooser();
			fileSelect.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileSelect.setAcceptAllFileFilterUsed(true);
			int file = fileSelect.showOpenDialog(null);
			if (file == JFileChooser.APPROVE_OPTION) {
				orderChoice.setText(fileSelect.getSelectedFile().getName());
			}
		}
		
		if (e.getSource() == okButton) {
			//update chosen setting for the simulation
			settings.setMenuFile(this.menuChoice.getText());
			settings.setOrderFile(this.orderChoice.getText());
			settings.setAssistantsCount((int)this.serverNum.getSelectedItem());
			settings.setTimeModifier(this.simSpeed.getValue());
			
			//hide this window to allow simulation to start
			this.setVisible(false);
		}
		
		if(e.getSource() == cancelButton) {
			//if cancel is chosen, close window and end program
			this.dispose();
			System.exit(0);
		}
		
		
	}

	// public static void main(String[] args) {
	// new StartGUI();
	// }
}