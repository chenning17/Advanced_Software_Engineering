package cafepackage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

public class CafeStateGUI extends JFrame{

	//queue details, at top of GUI
	JPanel queueInfoPanel = new JPanel();
	JLabel queueTitle = new JLabel("Queue Status Log");
	JScrollPane queueInfoScrollArea;
	JTextPane queueInfoText = new JTextPane();

	//server info panel, at bottom of GUI
	JPanel serverInfoPanel = new JPanel();
	
	ArrayList<Server> servers = new ArrayList<Server>();

	//create cafeStateGUI, takes input argument which is the number of servers to be added to the GUI display
	public CafeStateGUI(int numServers) {
			
		//set layout and size of GUI window
		this.setSize(new Dimension(1200, 800));
		this.setLayout(new GridLayout(2,1));
		
		/********************QUEUE INFO PANEL*************************/
		
		queueInfoPanel.setLayout(new BorderLayout());
		//queueInfoPanel.setSize(new Dimension(300, 400));
		queueInfoScrollArea = new JScrollPane(queueInfoText);

		queueInfoText.setEditable(false); 

		queueInfoPanel.add(queueTitle, BorderLayout.NORTH);
		queueInfoPanel.add(queueInfoScrollArea, BorderLayout.CENTER);
					
		/*___________________________________________________________*/
		
				
		/*********************SERVER INFO PANEL***********************/
	
		
		serverInfoPanel.setLayout(new GridLayout( 1, numServers )); 
		//serverInfoPanel.setSize(new Dimension(300, 400));
		
	
		for(int i=0; i<numServers; i++) {
			Server server = new Server(i);
			serverInfoPanel.add(server);
			servers.add(server);
		}

		/*____________________________________________________________*/
		
		
		// add all panels to the main CafeStateGUI window
		this.add(queueInfoPanel);
		this.add(serverInfoPanel);
		
		
		this.setVisible(true);
		
		
		/*******TESTING THE TEXT SETTER METHODS*********/
		//add some text to test all the set methods
		
		for(int i = 0; i<25; i++) {
			appendQueueLogText("hello");
			appendQueueLogText("here's a tea");
			appendQueueLogText("bye!!!");
		}
		
		setQueueLogTitle("there are 15 people in queue");
		setServerText("I have run out of muffins :(", 1);
		setServerText("I am server number 4", 4);
		setServerText("I don't work here :o", 6);
		
		/*_________________________________________________*/
		
	}

	
	//appends text to the end of the text held in the queue info log text box
	public void appendQueueLogText(String toBeAdded) {
		String prevText = this.queueInfoText.getText();
		String newText = prevText + "\n" + toBeAdded;
		this.queueInfoText.setText(newText);
	}
	
	
	//appends text to the end of the text held in the queue info log text box
	public void setQueueLogTitle(String queueStatusTitle) {
		this.queueTitle.setText(queueStatusTitle);
	}
	
	//update the server text for the given inputted server number
	public void setServerText(String newText, int serverNumber) {
		//TODO make sure input server number is in valid range
		servers.get(serverNumber).setServerText(newText);
	}
	
	//server class used only in this class in order to make adding and removing servers to cafe state gui simple
	private class Server extends JPanel{
		JLabel serverTitle;
		JTextPane serverInfoText = new JTextPane();
		
		private Server(Integer serverNumber){
			serverTitle = new JLabel("Server " + serverNumber.toString());
			
			this.setLayout(new BorderLayout());
			this.add(serverTitle, BorderLayout.NORTH);
			
			//serverInfoText.setSize(new Dimension(150, 400));
			serverInfoText.setText("I am currently on my break...;)");
			serverInfoText.setEditable(false); 
			this.add(serverInfoText, BorderLayout.CENTER);
		}
		
		//set the server's text box info
		private void setServerText(String newServerInfo) {
			this.serverInfoText.setText(newServerInfo);
		}
		
	}
	
	
}
