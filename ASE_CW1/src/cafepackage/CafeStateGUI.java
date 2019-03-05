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

/**
 * CafeStateGUI class used to display the current state of the cafe simulation
 * Contains info about the current queue, the status log and the current actions
 * of each server.
 */
public class CafeStateGUI extends JFrame {

	// queue details, at top left of GUI
	JPanel queueInfoPanel = new JPanel();
	JLabel queueInfoTitle = new JLabel("Queue Status Log");
	JScrollPane queueInfoScrollArea;
	JTextPane queueInfoText = new JTextPane();

	// status log details, at top right of GUI
	JPanel statusLogPanel = new JPanel();
	JLabel statusLogTitle = new JLabel("Queue Status Log");
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
	public CafeStateGUI(int numServers) {

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

		serverInfoPanel.setLayout(new GridLayout(1, numServers));

		for (int i = 0; i < numServers; i++) {
			Server server = new Server(i);
			serverInfoPanel.add(server);
			servers.add(server);
		}

		/* ____________________________________________________________ */

		// add all panels to the main CafeStateGUI window
		this.add(combinedQueueLogPanel);
		this.add(serverInfoPanel);

		this.setVisible(true);

		/******* TESTING THE TEXT SETTER METHODS *********/
		// add some text to test all the set methods

		for (int i = 0; i < 25; i++) {
			appendStatusLogText("10:04 hello");
			appendStatusLogText("10:09 here's a tea");
			appendStatusLogText("10 :24 bye!!!");
		}

		setQueueInfoTitle("there are 15 people in queue");
		setQueueInfoText("jim");
		setServerText("I have run out of muffins :(", 1);
		setServerText("I am server number 4", 4);
		setServerText("I don't work here :o", 6);

		/* _________________________________________________ */

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

	/**
	 * Server class used only in this class in order to make adding and removing
	 * servers to cafe state gui simple
	 *
	 */
	private class Server extends JPanel {
		JLabel serverTitle;
		JTextPane serverInfoText = new JTextPane();

		/**
		 * Server constructor, takes one argument the current server number for use in
		 * the server title
		 * 
		 * @param serverNumber
		 *            the index of the current server, used to set the title (title
		 *            number will be serverNumber +1)
		 */
		private Server(Integer serverNumber) {
			serverTitle = new JLabel("Server " + (++serverNumber).toString());

			this.setLayout(new BorderLayout());
			this.add(serverTitle, BorderLayout.NORTH);

			serverInfoText.setText("I am currently on my break...");
			serverInfoText.setEditable(false);
			this.add(serverInfoText, BorderLayout.CENTER);
		}

		// set the server's text box info
		private void setServerText(String newServerInfo) {
			this.serverInfoText.setText(newServerInfo);
		}

	}

}
