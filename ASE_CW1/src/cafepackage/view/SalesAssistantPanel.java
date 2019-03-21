package cafepackage.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import cafepackage.interfaces.Observer;
import cafepackage.model.SalesAssistant;

/**
 * Server class used only in this class in order to make adding and removing
 * servers to cafe state gui simple
 *
 */
public class SalesAssistantPanel extends JPanel implements Observer {
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
	public SalesAssistantPanel(Integer serverNumber, SalesAssistant s) {
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
	public void setServerText(String newServerInfo) {
		this.serverInfoText.setText(newServerInfo);
	}
			
	//update server box when order changes
	@Override
	public void Update() {
		this.serverInfoText.setText(this.assistant.getCurrentOrder());
	}

}