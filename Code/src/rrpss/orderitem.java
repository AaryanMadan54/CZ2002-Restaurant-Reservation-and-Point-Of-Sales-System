package rrpss;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Class to represent an order item
 * @author Nipun Bhatia
 *
 */
public class orderitem implements Serializable {
	
	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = -8968713658956205498L;
	
	/**
	 * Name of the item ordered
	 */
	private String name;
	
	/**
	 * Price of the item ordered
	 */
	private double price;
	
	/**
	 * Quantity of the item ordered
	 */
	private int quantity;
	
	/**
	 * Public Constructor to create a new order item
	 * @param name Name of the item ordered
	 * @param price Price of the item ordered
	 * @param quantity Quantity of the item ordered
	 */
	public orderitem(String name, double price, int quantity) {
		this.name=name;
		this.price=price;
		this.quantity=quantity;
	}

	/**
	 * Gets the name of the order item
	 * @return String name of the order item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the order item
	 * @param name New name of the order item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price of the order item
	 * @return The price of the order item
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price of the order item
	 * @param price The price of the order item
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets the quantity of the order item
	 * @return The quantity of the order item
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity of the order item
	 * @param quantity The quantity of the item ordered
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Increases the quantity of an order item 
	 * @param add_value By how much to much to increase the quantity
	 */
	public void addQty(int add_value) {
		quantity+= add_value;
		displayOrderItemSummary();
	}
	
	/**
	 * Decreases the quantity of an order item 
	 * @param dec_value By how much to much to decrease the quantity
	 */
	public void decQty(int dec_value) {
		if(dec_value>quantity) {
			System.out.println("Invalid Input");
			return;
		}
		quantity-= dec_value;
		displayOrderItemSummary();
	}
	
	/**
	 * Shows the summary of the items in the order
	 */
	public void displayOrderItemSummary()
	{
		System.out.printf("%-5s", getQuantity() + "x");
		System.out.printf("%-30s%n", getName());
	}
	
	/**
	 * Shows the detailed information about items in the order
	 */
	public void displayOrderItemDetails()
	{
		System.out.printf("%-5s", getQuantity() + "x");
		System.out.printf("%-30s", getName());
		System.out.printf("%40s%n", new DecimalFormat("$###,##0.00").format(getPrice() * getQuantity()));
	}
}
