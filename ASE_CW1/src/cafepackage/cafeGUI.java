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

		refreshDiscount(true);

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
		// remove previousDiscount before refreshing discount to get newest discount

		refreshDiscount(false);
		updateTotalPrice();
	}

	/**
	 * Using the current list of items selected, updates the discount item held in
	 * the currently selected items list if applicable. Will replace an old discount
	 * item with a better discount if it is found. If the given parameter itemAdded
	 * is false the previous discount if found will always be removed and replaced
	 * with the current best discount available. This is useful for when an item is
	 * removed from the selected list of items. If a value of true is given, it will
	 * only replace the previous discount if the current discount value is greater
	 * 
	 * @param itemAdded
	 *            boolean value used to choose if previous discount value should be
	 *            instantly removed or compared with current best discount available
	 */
	private void refreshDiscount(boolean itemAdded) {
		ArrayList<Item> basket = new ArrayList<Item>();
		for (int i = 0; i < this.orderListModel.getSize(); i++) {
			basket.add(this.orderListModel.getElementAt(i));
		}

		Item prevDiscount = null;
		// check for any previous discounts
		for (Item item : basket) {
			if (item instanceof Discount) {
				prevDiscount = item;
			}
		}

		// remove old discount if item was removed
		if (itemAdded == false) {
			this.orderListModel.removeElement(prevDiscount);
			basket.remove(prevDiscount);
			prevDiscount = null;
		}

		// get current best discount
		Discount discount = DiscountCalculator.getBestDeal(basket);
		if (discount != null) {
			// check if new discount is better than previous
			if (prevDiscount != null && prevDiscount.getCost() > discount.getCost()) {
				discount = (Discount) prevDiscount;
				// remove old discount from current chosen items
				this.orderListModel.removeElement(prevDiscount);
				basket.remove(prevDiscount);
			} else if (prevDiscount != null) {
				// remove old discount from current chosen items
				this.orderListModel.removeElement(prevDiscount);
				basket.remove(prevDiscount);
			}
			// add new discount
			this.orderListModel.addElement(discount);
		}
	}

	/**
	 * Tells manager to generate a new report
	 */
	private void generateReport() {
		this.manager.generateReport();
	}

	private void updateTotalPrice() {
		// TODO: Calculate discounts and update cart
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
