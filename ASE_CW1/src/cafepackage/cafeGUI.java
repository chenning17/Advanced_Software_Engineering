package cafepackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultListModel;

public class cafeGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private ItemCollection menu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cafeGUI frame = new cafeGUI(new ItemCollection());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public cafeGUI(ItemCollection menu) {
		this.menu = menu;

		DefaultListModel<Item> listModel = new DefaultListModel<Item>();
		for(Item item : this.menu) {
			listModel.addElement(item);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JList<Item> menuList = new JList<>(listModel);
		contentPane.add(menuList);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		JButton btnAdd = new JButton("Add");
		panel.add(btnAdd);
		
		JButton btnCreateOrder = new JButton("Create Order");
		panel.add(btnCreateOrder);
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);

	}

}
