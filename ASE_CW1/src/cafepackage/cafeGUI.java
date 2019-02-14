package cafepackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.DefaultListModel;

public class cafeGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnCreateOrder;
	private JButton btnGenReport;
	private JTextField totalPrice;
	private DefaultListModel<Item> menuListModel;
	private JList<Item> menuList;
	private DefaultListModel<Item> orderListModel;
	private JList<Item> currentOrder;
	private Manager manager;


	/**
	 * Create the frame.
	 */
	public cafeGUI(ItemCollection menu, Manager manager) {
		this.manager = manager;

		this.menuListModel = new DefaultListModel<Item>();
		for(Item item : menu) {
			this.menuListModel.addElement(item);
		}
		
		this.orderListModel = new DefaultListModel<Item>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		this.menuList = new JList<>(this.menuListModel);
		scrollPane.setViewportView(menuList);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 0, 0, 20));
		contentPane.add(panel);
		
		this.btnAdd = new JButton("Add");
		this.btnAdd.addActionListener(this);
		panel.add(this.btnAdd);
		
		this.btnRemove = new JButton("Remove");
		this.btnRemove.addActionListener(this);
		panel.add(this.btnRemove);
		
		this.btnCreateOrder = new JButton("Create Order");
		this.btnCreateOrder.addActionListener(this);
		panel.add(btnCreateOrder);
		
		this.btnGenReport = new JButton("Generate Report");
		this.btnGenReport.addActionListener(this);
		panel.add(btnGenReport);
		
		this.totalPrice = new JTextField();
		panel.add(totalPrice);
		updateTotalPrice();
		
		this.currentOrder = new JList<Item>(this.orderListModel);
		contentPane.add(currentOrder);

	}

	//listener for buttons being pressed
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnAdd) {
			addToCart();
		}
		else if (e.getSource() == this.btnCreateOrder) {
			submitOrder();
		}
		else if(e.getSource() == this.btnGenReport) {
			generateReport();
		}
		else if(e.getSource() == this.btnRemove) {
			removeFromCart();
		}
	}
	
	/**
	 * Submits items in cart to manager as a new order
	 */
	private void submitOrder() {
		ArrayList<Order> newOrder = new ArrayList<Order>();
		//System.out.println("Create order button pressed");
		int customerID = Order.getCurrentCustomerID();
		Date date = new Date();
		for (int i = 0 ; i < orderListModel.size(); i++) {
			Item item = orderListModel.getElementAt(i);
			Order order = new Order(date, customerID, item);
			newOrder.add(order);
		}
		this.manager.addOrder(newOrder);
		this.orderListModel.removeAllElements();
		updateTotalPrice();
	}

	/**
	 * Loops through selected items and adds them to the list on the right of the screen
	 */
	private void addToCart() {
		
		int[] indices = menuList.getSelectedIndices(); //get indices of selected items
		if (indices.length != 0) {
			for (int i : indices) {
				Item selected = this.menuListModel.getElementAt(i);
				this.orderListModel.addElement(selected);
			}
		}
		updateTotalPrice();
		this.menuList.clearSelection();
	}
	
	/**
	 * Removes selected items from list on right of screen
	 */
	private void removeFromCart() {
		System.out.println("This would remove:");
		int[] indices = currentOrder.getSelectedIndices();
		for (int i : indices) {
			System.out.println(i);
		}
		for (int i = this.orderListModel.size()-1; i > -1; i--) {
			for(int index : indices) {
				if (i == index) {
					orderListModel.remove(i);
				}
			}
		}
		updateTotalPrice();
	}
	
	/**
	 * Tells manager to generate a new report
	 */
	private void generateReport() {
		this.manager.generateReport();
	}
	
	private void updateTotalPrice() {
		//TODO: Calculate discounts and update cart
		totalPrice.setText(String.format("Total price: %.2f", calculateTotalPrice()));
	}
	
	private double calculateTotalPrice() {
		double price = 0.0;
		for (int i = 0 ; i < orderListModel.size(); i++) {
			Item item = orderListModel.getElementAt(i);
			price += item.getCost();
		}
		return price;
	}

}
