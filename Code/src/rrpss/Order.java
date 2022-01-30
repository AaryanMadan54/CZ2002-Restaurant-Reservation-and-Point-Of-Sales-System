package rrpss;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Class to represent an order made by a table in the restaurant.
 * @author Nipun Bhatia
 *
 */
public class Order implements Serializable{

	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = -407341987017621087L;
	
	/**
	 * The service charge tax to be added to the total bill
	 */
	final double SERVICECHARGE = 0.10;
	
	/**
	 * The total Goods and Services Tax (GST) to be added to the total bill
	 */
	final double GST = 0.07;
	
	/**
	 * Discount to be offered to customers holding membership
	 */
	final double DISCOUNT_MEMBERSHIP = 0.9;
	
	/**
	 * Staff ID of the staff entertaining the table
	 */
	private int staffID;
	
	/**
	 * Customer ID of the customer at the table
	 */
	private int customerID;
	
	/**
	 * Table Number of the table
	 */
	private int TableNo;
	
	/**
	 * Number of people at the table
	 */
	private int pax;
	
	/**
	 * Original total bill amount, exclusive of taxes and discounts (if any).
	 */
	private double TotalBill_original;
	
	/**
	 * Finalised bill amount to be paid by the customer
	 */
	private double TotalBill_nett;
		
	/**
	 * A static instance of an array containing the various items ordered
	 */
	private List<orderitem> orderitems;
	
	/**
	 * Calendar instance to take note of the date and time of order placing
	 */
	private Calendar orderDateTime;
	
	/**
	 * Order ID of the order
	 */
	private String orderID;
	
	/**
	 * Static instance of a date formatter to parse the date and time correctly	
	 */
	private static final SimpleDateFormat _dateFormatter = new SimpleDateFormat("E, dd/MM/yyyy, HH:mm");
	
	/**
	 * Public Constructor to create a new order with the given information
	 * @param staffID Staff ID of the staff entertaining the table
	 * @param custID Customer ID of the customer at the table
	 * @param TableNo Table Number of the table
	 * @param pax Number of people at the table
	 * @param TotalBill_original Original total bill amount, exclusive of taxes and discounts (if any)
	 * @param TotalBill_nett Finalised bill amount to be paid by the customer
	 * @param orderDateTime Date and time of order placing
	 * @param orderID Order ID of the order
	 */
	public Order(int staffID, int custID, int TableNo, int pax, double TotalBill_original, double TotalBill_nett, Calendar orderDateTime,String orderID) {
		this.staffID=staffID;
		this.customerID=custID; 
		this.TableNo=TableNo; 
		this.pax=pax;
		this.TotalBill_original=TotalBill_original;
		this.TotalBill_nett=TotalBill_nett;
		this.orderDateTime= orderDateTime;
		this.orderID= orderID;
		orderitems = new ArrayList<orderitem>();
	}
	
	/**
	 * Public Constructor for existing order with the given information
	 * @param staffID Staff ID of the staff entertaining the table
	 * @param custID Customer ID of the customer at the table
	 * @param TableNo Table Number of the table
	 * @param pax Number of people at the table
	 * @param TotalBill_original Original total bill amount, exclusive of taxes and discounts (if any)
	 * @param TotalBill_nett Finalised bill amount to be paid by the customer
	 * @param orderDateTime Date and time of order placing
	 * @param orderID Order ID of the order
	 * @param orderItems Existing order
	 */
	public Order(int staffID, int custID, int TableNo, int pax, double TotalBill_original, double TotalBill_nett, Calendar orderDateTime,String orderID, List<orderitem> orderItems) {
		this.staffID=staffID;
		this.customerID=custID; 
		this.TableNo=TableNo; 
		this.pax=pax;
		this.TotalBill_original=TotalBill_original;
		this.TotalBill_nett=TotalBill_nett;
		this.orderDateTime= orderDateTime;
		this.orderID= orderID;
		//this.orderitems=orderitems;
		if(orderItems != null)
			this.orderitems = orderItems;
		else
			this.orderitems = new ArrayList<orderitem>();
	}
	
	/**
	 * Gets the staff ID of the employee entertaining the table
	 * @return Integer value denoting the employee
	 */
	public int getStaffID() {
		return staffID;
	}

	/**
	 * Gets the customer ID of the customer at the table
	 * @return Integer value of the customer ID
	 */
	public int getCustomerID() {
		return customerID;
	}
	
	/**
	 * Gets the Table Number of the table
	 * @return Table number
	 */
	public int getTableNo() {
		return TableNo;
	}
	
	/**
	 * Gets the Number of people at that table
	 * @return The number of people
	 */
	public int getNumberofPeople() {
		return pax;
	}
	
	/**
	 * Gets the original bill, exclusive of taxes and discounts (if any)
	 * @return Original bill amount
	 */
	public double getOriginalBill() {
		return TotalBill_original;
	}
	
	/**
	 * Sets the original bill amount
	 * @param newBillPrice New bill price
	 */
	public void setOriginalBill(double newBillPrice) {
		this.TotalBill_original=newBillPrice;
	}
	
	/**
	 * Gets the nett bill total amount
	 * @return Total nett bill amount to be paid
	 */
	public double getNettBill() {
		return TotalBill_nett;
	}
	
	/**
	 * Sets the nett bill amount
	 * @param newNettPrice Total nett bill amount to be paid
	 */
	public void setNettBill(double newNettPrice) {
		this.TotalBill_nett=newNettPrice;
	}
	
	/**
	 * Gets the Date and Time of order placing
	 * @return Calendar instance of date and time
	 */
	public Calendar getDateTime() {
		return orderDateTime;
	}
	
	/**
	 * Gets the Order ID of the table
	 * @return String denoting the order ID
	 */
	public String getOrderID() {
		return orderID;
	}
	
	/**
	 * Gets the items ordered in an order
	 * @return An arraylist containing of orderitems
	 */
	public List<orderitem> getOrderItems()	{
		return orderitems;
	}
	
	/**
	 * Adds items to order
	 * @param menuItem Menu item to be added
	 * @param quantity Quantity to be added
	 */
	public void addItemToOrder(menuitem menuItem, int quantity)
	{
		if(!orderitems.isEmpty())
		{
			for(orderitem orderItem : orderitems)
			{
				if(orderItem.getName() == menuItem.getName())
				{
					orderItem.addQty(quantity);
					recalculateOrderPrice();
					
					return;
				}
			}
		}
		
		// Empty order list or added menuItem not part of order list
		orderitem newOrderItem;
		newOrderItem = new orderitem(menuItem.getName(),menuItem.getPrice(), quantity);
		
		orderitems.add(newOrderItem);
		recalculateOrderPrice();
	}
	
	/**
	 * Removes item from order
	 * @param itemName Menu item to be removed
	 * @param quantity Quantity to be removed
	 */
	public void removeItemFromOrder(String itemName, int quantity)
	{
		if(getNumberOfOrderItems() == 0)
			return;
		
		Iterator<orderitem> ordersIter = orderitems.iterator();
		orderitem orderItem = null;
		
		while(ordersIter.hasNext()) {
			
			orderItem = ordersIter.next();
			
			if(orderItem.getName().equals(itemName)) {
				
				orderItem.decQty(quantity);

				if(orderItem.getQuantity() == 0)
					ordersIter.remove();

				break;
			}
		}
		
		recalculateOrderPrice();
	}
	
	/**
	 * Gets the number of items in the order
	 * @return Integer denoting the number of ordered items
	 */
	public int getNumberOfOrderItems() {
		return orderitems.size();
	}
	
	/**
	 * Checks whether the order is empty or not
	 * @return True if empty
	 */
	public boolean isEmpty() {
		return orderitems.isEmpty();
	}
	
	/**
	 * Gets the quantity of a particular orderitem in an order
	 * @param itemName Name of the orderitem
	 * @return The integer value representing the quantity of an orderitem ordered
	 */
	public int getOrderItemQty(String itemName)
	{
		for(orderitem orderItem : orderitems)
		{
			if(orderItem.getName().equals(itemName))
				return orderItem.getQuantity();
		}
		
		return 0;
	}
	
	/**
	 * Recalculates the amount of the order after modifications like add or remove items
	 */
	private void recalculateOrderPrice()
	{
		if(getNumberOfOrderItems() == 0) return;

		double newOriginalOrderPrice = 0;
		for(orderitem orderItem : orderitems)
		{
			newOriginalOrderPrice +=(orderItem.getPrice() * orderItem.getQuantity());
		}

		setOriginalBill(newOriginalOrderPrice);
		
		double newNettTotalPrice = 0;
		double taxes = 1 + SERVICECHARGE + GST;
		newNettTotalPrice = (newOriginalOrderPrice * taxes);
		
		if(StaffMgr.getStaffMgr().isMember(getCustomerID()))
		{
			newNettTotalPrice *= DISCOUNT_MEMBERSHIP;
		}
		
		setNettBill(newNettTotalPrice);
	}
	
	/**
	 * Shows the detailed information about the order
	 */
	public void displayOrderDetails()
	{
		System.out.printf("%-35s", "Order ID: " + getOrderID());
		System.out.printf("%-17s",
				"Staff ID: " + getStaffID());
		System.out.printf("%-19s",
				"Customer ID: " + getCustomerID());
		System.out.printf("%-12s%n",
				"Table No: " + getTableNo());

		int orderItemNo = 1;
		for(orderitem orderItem : orderitems)
		{
			System.out.printf("%5s%-5s: ", "", ("(" + (orderItemNo++) + ")") );
			orderItem.displayOrderItemDetails();
		}
	}
	
	/**
	 * Shows the summary of the order
	 */
	public void displayOrderSummary()
	{
		System.out.printf("%-25s", getOrderID());
		System.out.printf("%-15d", getStaffID());
		System.out.printf("%-15d", getCustomerID());
		System.out.printf("%-15d%n", getTableNo());
	}
	
	/**
	 * Shows the order invoice
	 */
	public void displayOrderInvoice()
	{
		System.out.println(new String(new char[87]).replace("\0", "-") + "\n");

		//87 is the total spacing, subtract from that for .replace
		System.out.print(new String(new char[38]).replace("\0", " "));
		System.out.print(" S.N.A.K. ");
		System.out.println(new String(new char[39]).replace("\0", " "));
		
		System.out.print("\n" + new String(new char[24]).replace("\0", " "));
		System.out.print(" 76 Nanyang Drive, Block N2.1, #01-08 ");
		System.out.println(new String(new char[25]).replace("\0", " "));
		
		System.out.print("\n" + new String(new char[36]).replace("\0", "="));
		System.out.print(" Order Invoice ");
		System.out.println(new String(new char[36]).replace("\0", "="));
		
		System.out.printf("%n%-47s", "Order ID: " + getOrderID());
		System.out.printf("%-20s", "Staff ID: " + getStaffID());
		System.out.printf("%20s%n", "Customer ID: " + getCustomerID());
		
		System.out.printf("%-47s", "Order Date/Time: " + _dateFormatter.format(orderDateTime.getTime()));
		System.out.printf("%-20s", "Table No: " + getTableNo());
		System.out.printf("%20s%n%n", "Pax: " + getNumberofPeople());

		int orderItemNo = 1;
		for(orderitem orderItem : orderitems)
		{
			System.out.printf("%5s%-5s: ", "", ("(" + (orderItemNo++) + ")") );
			orderItem.displayOrderItemDetails();
		}
		
		System.out.println("\n" + new String(new char[87]).replace("\0", "-"));

		System.out.printf("%87s%n", "Subtotal: " + new DecimalFormat("$###,##0.00").format(getOriginalBill()));

		System.out.printf("%n%87s%n", "+10% Service Charge");
		System.out.printf("%87s%n", "+7% Goods & Service Tax");
		if(StaffMgr.getStaffMgr().isMember(getCustomerID())) { System.out.printf("%87s%n", "-10% membership discount");}

		System.out.printf("%n%87s%n", "Total Payable(incl GST): " + new DecimalFormat("$###,##0.00").format(getNettBill()));
		
		System.out.println(new String(new char[87]).replace("\0", "-") + "\n");
		
		System.out.print(new String(new char[29]).replace("\0", " "));
		System.out.print("Thank you for dining with us!");
		System.out.println(new String(new char[29]).replace("\0", " "));
		
		System.out.print("\n" + new String(new char[36]).replace("\0", "="));
		System.out.print(" Order Invoice ");
		System.out.println(new String(new char[36]).replace("\0", "="));
	}
}