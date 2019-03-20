package cafepackage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import cafepackage.interfaces.Observer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * CafeStateGUI class used to display the current state of the cafe simulation
 * Contains info about the current queue, the status log and the current actions
 * of each server.
 */
public class CafeStateGUI extends JFrame implements Observer {
	private OrderQueue queue;
	private OnlineOrderQueue onlineQueue;
	private ArrayList<SalesAssistant> salesAssistant;



	// queue details, at top left of GUI
	JPanel queueInfoPanel = new JPanel();
	JLabel queueInfoTitle = new JLabel("Queue Status Log");
	JScrollPane queueInfoScrollArea;
	JTextPane queueInfoText = new JTextPane();

	// status log details, at top right of GUI
	JPanel statusLogPanel = new JPanel();
	JLabel statusLogTitle = new JLabel("Online Orders");
	JScrollPane statusLogScrollArea;
	JTextPane statusLogText = new JTextPane();

	// Panel at top to contain both the queue info panel and the status log panel
	JPanel combinedQueueLogPanel = new JPanel();

	// server info panel, at bottom of GUI
	JPanel serverInfoPanel = new JPanel();

	ArrayList<Server> servers = new ArrayList<Server>();

	/**
	 * CafeStateGUI constructor, takes one argument - an integer used to set the
	 * number of servers displayed in the GUI.
	 *
	 * @param numServers
	 *            integer used to set number of servers to display in the GUI
	 */
	public CafeStateGUI(ArrayList<SalesAssistant> salesAssistants, OrderQueue queue, OnlineOrderQueue onlineQueue) {

		this.queue = queue;
		this.queue.registerObserver(this);

		this.onlineQueue = onlineQueue;
		this.onlineQueue.registerObserver(this);

		this.salesAssistant = salesAssistants;
		for(SalesAssistant s: this.salesAssistant) {
			s.registerObserver(this);
		}

		// set layout and size of GUI window
		this.setSize(new Dimension(1200, 800));
		this.setLayout(new GridLayout(2, 1));

		/******************** QUEUE INFO PANEL *************************/

		queueInfoPanel.setLayout(new BorderLayout());
		queueInfoScrollArea = new JScrollPane(queueInfoText);

		queueInfoText.setEditable(false);

		queueInfoPanel.add(queueInfoTitle, BorderLayout.NORTH);
		queueInfoPanel.add(queueInfoScrollArea, BorderLayout.CENTER);

		/* ___________________________________________________________ */

		/******************** STATUS LOG PANEL *************************/

		statusLogPanel.setLayout(new BorderLayout());
		statusLogScrollArea = new JScrollPane(statusLogText);

		statusLogText.setEditable(false);

		statusLogPanel.add(statusLogTitle, BorderLayout.NORTH);
		statusLogPanel.add(statusLogScrollArea, BorderLayout.CENTER);

		/* ___________________________________________________________ */

		// add queue panel and status panel to the combined panel
		combinedQueueLogPanel.setLayout(new GridLayout(1, 2));
		combinedQueueLogPanel.add(queueInfoPanel);
		combinedQueueLogPanel.add(statusLogPanel);

		/********************* SERVER INFO PANEL ***********************/

		serverInfoPanel.setLayout(new GridLayout(1, salesAssistants.size()));

		for (int i = 0; i < salesAssistants.size(); i++) {
			Server server = new Server(i, salesAssistants.get(i));
			serverInfoPanel.add(server);
			servers.add(server);
		}

		/* ____________________________________________________________ */

		// add all panels to the main CafeStateGUI window
		this.add(combinedQueueLogPanel);
		this.add(serverInfoPanel);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Update the queue info text with the given string
	 *
	 * @param newText
	 *            string to set the QueueInfoText to
	 */
	public void setQueueInfoText(String newText) {
		this.queueInfoText.setText(newText);
	}

	/**
	 * Update the queue info title to be the input string
	 *
	 * @param queueInfoTitle
	 *            string to be used as the new title
	 */
	public void setQueueInfoTitle(String queueInfoTitle) {
		this.queueInfoTitle.setText(queueInfoTitle);
	}

	/**
	 * Appends text to the end of the text held in the status Log info text box
	 *
	 * @param toBeAdded
	 *            string to be appended to end of the log info text
	 */
	public void appendStatusLogText(String toBeAdded) {
		String prevText = this.statusLogText.getText();
		String newText = prevText + "\n" + toBeAdded;
		this.statusLogText.setText(newText);
	}

	/**
	 * Update the status log title to be the given string
	 *
	 * @param statusLogTitle
	 *            string to be used as the new status log title
	 */
	public void setStatusLogTitle(String statusLogTitle) {
		this.statusLogTitle.setText(statusLogTitle);
	}

	/**
	 * Update the server text for the given inputted server number
	 *
	 * @param newText
	 *            string used to update the text for the server
	 * @param serverNumber
	 *            number of the server to update
	 */
	public void setServerText(String newText, int serverNumber) {
		// TODO make sure input server number is in valid range
		servers.get(serverNumber).setServerText(newText);
	}
			
	@Override
	public void Update() {
		LinkedList<Order> orders = this.queue.getQueueCopy();
		String output = "";
		for(Order order: orders) {
			output = order.getCustomerId() + ": " + order.getItems().size() + " items\n" + output;
		}
		this.queueInfoText.setText(output);
		this.queueInfoTitle.setText("Queue size: " + orders.size());


		//------------------ Update RHS panel----------------
		LinkedList<Order> onlineOrds = this.onlineQueue.getQueueCopy();
		String output1 = "";
		//this.statusLogTitle.setText("Collecting orders: " + onlineOrds.size());
		output1 += "Unprocessed Online Orders: " + this.onlineQueue.pendingSize() + "\n";
		output1 += "Online orders ready for collection: " + this.onlineQueue.processedSize() + "\n";
		output1 += "\n Customers ready to collect:";
		for(Order o : onlineOrds) {
			output1 += "\n"+ o.getCustomerId();
		}
		this.statusLogText.setText(output1);

	}

	/**
	 * Server class used only in this class in order to make adding and removing
	 * servers to cafe state gui simple
	 *
	 */
	private class Server extends JPanel implements Observer {
		JLabel serverTitle;
		JTextPane serverInfoText = new JTextPane();
		private SalesAssistant assistant;

		/**
		 * Server constructor, takes one argument the current server number for use in
		 * the server title
		 *
		 * @param serverNumber
		 *            the index of the current server, used to set the title (title
		 *            number will be serverNumber +1)
		 * @param s Sales assistant the server observes
		 */
		private Server(Integer serverNumber, SalesAssistant s) {
			serverTitle = new JLabel("Server " + (++serverNumber).toString());

			this.setLayout(new BorderLayout());
			this.add(serverTitle, BorderLayout.NORTH);

			serverInfoText.setText("Not currently serving");
			serverInfoText.setEditable(false);
			this.add(serverInfoText, BorderLayout.CENTER);

			//store sales assistant, register server as observer
			this.assistant = s;
			this.assistant.registerObserver(this);
		}

		// set the server's text box info
		private void setServerText(String newServerInfo) {
			this.serverInfoText.setText(newServerInfo);
		}
				
		//update server box when order changes
		@Override
		public void Update() {
			this.serverInfoText.setText(this.assistant.getCurrentOrder());
		}

	}

}
