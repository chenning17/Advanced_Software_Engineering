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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	private ItemCollection menu;

	/**
	 * Create the frame.
	 */
	public cafeGUI(ItemCollection menu, Manager manager) {
		//keep class variable reference to menu in order to add new discount items to the collection
		this.menu = menu; 
		this.manager = manager;

		this.menuListModel = new DefaultListModel<Item>();
		for (Item item : menu) {
			this.menuListModel.addElement(item);
		}

		this.orderListModel = new DefaultListModel<Item>();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		panel.setLayout(new GridLayout(4, 0, 0, 20));
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

		this.totalPrice = new JTextField();
		panel.add(totalPrice);
		updateTotalPrice();

		this.currentOrder = new JList<Item>(this.orderListModel);
		contentPane.add(currentOrder);
		
		/**
		 * Override default close operation to include report generation
		 * https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				generateReport();
				System.exit(0);
			}
		});

	}

	// listener for buttons being pressed
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnAdd) {
			addToCart();
		} else if (e.getSource() == this.btnCreateOrder) {
			submitOrder();
		} else if (e.getSource() == this.btnRemove) {
			removeFromCart();
		}
	}

	/**
	 * Submits items in cart to manager as a new order
	 */
	private void submitOrder() {
		ArrayList<Order> newOrder = new ArrayList<Order>();
		// System.out.println("Create order button pressed");
		int customerID = Order.getCurrentCustomerID();
		Date date = new Date();
		for (int i = 0; i < orderListModel.size(); i++) {
			Item item = orderListModel.getElementAt(i);
			Order order = new Order(date, customerID, item);
			newOrder.add(order);
			
			//if item is a discount, add it to the menu item collection for report generation
			if(item instanceof Discount) {
				this.menu.add(item);
			}
		}
		this.manager.addOrder(newOrder);
		this.orderListModel.removeAllElements();
		updateTotalPrice();
	}

	/**
	 * Loops through selected items and adds them to the list on the right of the
	 * screen
	 */
	private void addToCart() {

		int[] indices = menuList.getSelectedIndices(); // get indices of selected items
		if (indices.length != 0) {
			for (int i : indices) {
				Item selected = this.menuListModel.getElementAt(i);
				this.orderListModel.addElement(selected);
			}
		}

		refreshDiscount();

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
		for (int i = this.orderListModel.size() - 1; i > -1; i--) {
			for (int index : indices) {
				if (i == index) {
					orderListModel.remove(i);
				}
			}
		}

		refreshDiscount();
		updateTotalPrice();
	}

	/**
	 * Calculates best discounts for current cart.
	 * Will remove old discounts before calculating new ones.
	 * If discounts are found they will be added to basket. Best discounts added first.
	 * Can add multiple discounts but any item can only be part of one discount.
	 */
	private void refreshDiscount() {
		ArrayList<Item> tempBasket = new ArrayList<Item>();
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		
		for (int i = 0; i < this.orderListModel.getSize(); i++) {
			Item tempItem = this.orderListModel.getElementAt(i); //Store item temporarily so we don't have to 
			
			if (tempItem instanceof Discount) {
				//Store discounts to be removed from GUI after finished looping through list
				discounts.add((Discount)tempItem);
			}else {
				//Collect all items in cart
				tempBasket.add(tempItem);
			}
		}
		
		//Remove all old discounts
		for(Discount discount : discounts) {
			this.orderListModel.removeElement(discount);
		}

		//Loop through basket finding best discount and removing used items. Breaks out of loop when no more discounts
		while(true) {
			Discount discount = DiscountCalculator.getBestDeal(tempBasket);
			
			if(discount != null) {
				this.orderListModel.addElement(discount);
			}else {
				break;
			}
		}

	}

	/**
	 * Tells manager to generate a new report
	 */
	private void generateReport() {
		this.manager.generateReport();
	}

	private void updateTotalPrice() {
		totalPrice.setText(String.format("Total price: %.2f", calculateTotalPrice()));
	}

	private double calculateTotalPrice() {
		double price = 0.0;
		for (int i = 0; i < orderListModel.size(); i++) {
			Item item = orderListModel.getElementAt(i);
			if (item instanceof Discount) {
				price -= item.getCost();
			} else {
				price += item.getCost();
			}
		}
		return price;
	}

}
