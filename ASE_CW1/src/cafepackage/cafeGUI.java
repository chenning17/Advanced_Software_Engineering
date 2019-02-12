package cafepackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.DefaultListModel;

public class cafeGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private ItemCollection menu;
	private JButton btnAdd;
	private DefaultListModel<Item> menuListModel;
	private JList<Item> menuList;
	private DefaultListModel<Item> orderListModel;
	private JList<Item> currentOrder;

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

		this.menuListModel = new DefaultListModel<Item>();
		for(Item item : this.menu) {
			this.menuListModel.addElement(item);
		}
		
		this.orderListModel = new DefaultListModel<Item>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		this.menuList = new JList<>(this.menuListModel);
		contentPane.add(menuList);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		this.btnAdd = new JButton("Add");
		this.btnAdd.addActionListener(this);
		panel.add(this.btnAdd);
		
		JButton btnCreateOrder = new JButton("Create Order");
		panel.add(btnCreateOrder);
		
		this.currentOrder = new JList<Item>(this.orderListModel);
		contentPane.add(currentOrder);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnAdd) {
			addButtonPressed();
		}
		
	}
	/**
	 * Loops through selected items and adds them to the list on the right of the screen
	 */
	private void addButtonPressed() {
		//System.out.println("You pressed the add button");
		
		int[] indices = menuList.getSelectedIndices();
		if (indices.length != 0) {
			for (int i : indices) {
				Item selected = this.menuListModel.getElementAt(i);
				this.orderListModel.addElement(selected);
			}
		}
	}

}
