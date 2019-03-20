package cafepackage.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import cafepackage.interfaces.Observer;
import cafepackage.model.SimulationTime;

public class ClockPanel extends JPanel implements Observer{
	
	JLabel title;
	JTextPane text = new JTextPane();
	
	public ClockPanel() {

		this.setLayout(new BorderLayout());

		title = new JLabel("Simulation Time");
		this.add(title, BorderLayout.NORTH);
		
		text.setText(SimulationTime.getInstance().getCurrentDateTime().toString());
		text.setEditable(false);
		this.add(text, BorderLayout.CENTER);
		text.setFont(text.getFont().deriveFont(18.0f));
		
		SimulationTime.getInstance().registerObserver(this);
	}
	
	//update server box when order changes
	@Override
	public void Update() {
		text.setText("Current time\n" + SimulationTime.getInstance().getCurrentDateTime().toString());
	}

}
