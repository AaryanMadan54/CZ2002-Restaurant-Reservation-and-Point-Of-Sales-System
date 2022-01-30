package rrpss;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 * The manager class for controlling the order data.
 * @author Nipun Bhatia
 *
 */
public class OrderMgr
{
	/**
	 * An enumeration type used to determine the sub-menu options
	 *
	 */
	private enum OrderSubmenuState
	{
		ViewCurrentOrders,
		CreateOrder,
		AddItemToOrder,
		RemoveItemFromOrder,
		MakePaymentForOrder
	}
	
	/**
	* The file path for the binary file used to store the menu information
	*/
	private static final String ORDER_FILE_PATH = "ordermngr.dat";
	
	/**
	 * A static instance of this order manager
	 */
	private static OrderMgr _orderMgr = null;
	
	/**
	 * A static list of ongoing orders (Not yet paid for)
	 */
	private static List<Order> _currentOrders;
	
	/**
	 * A static list of completed transactions (Orders that have been paid for)
	 */
	private static List<Order> _completedOrders;
	
	/**
	 * Standard Java Scanner used for processing user inputs
	 */
	private static Scanner sc;
	
	/**
	 * Private constructor used to support the Singleton design pattern
	 * 
	 */
	private OrderMgr()
	{
		sc = new Scanner(System.in);
		
		_currentOrders = new ArrayList<Order>();
		_completedOrders = new ArrayList<Order>();
	}
	
	/**
	 * Public static function used to get hold of the Order manager
	 * 
	 * @return The static instance of the Order manager
	 */
	public static OrderMgr getOrderMgr()
	{
		if(_orderMgr == null)
		{
			_orderMgr = new OrderMgr();
		}
		
		return _orderMgr;
	}
	
	/**
	 * Loads the order-related information from the order file, if it exists
	 */
	public void loadOrders()
	{
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try
		{
			fis = new FileInputStream(ORDER_FILE_PATH);
			in = new ObjectInputStream(fis);
			
			Object obj = in.readObject();

			if (obj instanceof ArrayList<?>) {
				ArrayList<?> al = (ArrayList<?>) obj;

				if (al.size() > 0) {
					for (int objIndex = 0; objIndex < al.size(); objIndex++)
					{
						Object childObj = al.get(objIndex);

						if (childObj instanceof Order) {
							_completedOrders.add(((Order) childObj));
						}
					}
				}
			}

			in.close();
			
			if(!_completedOrders.isEmpty())
				System.out.println("Revenue data loaded!");
			
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to load Revenue data!");
		} catch (IOException ex) {
			System.out.println("Unable to load Revenue data!");
		} catch (ClassNotFoundException ex) {
			System.out.println("Unable to load Revenue data!");
		} catch (Exception ex) {
			System.out.println("Unable to load Revenue data!");
		}
	}
	
	/**
	 * Saves the all order-related information to the order file<br>
	 */
	public void saveOrders()
	{
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			fos = new FileOutputStream(ORDER_FILE_PATH);
			out = new ObjectOutputStream(fos);
			
			out.writeObject(_completedOrders);
			
			out.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to save 'Transactions' data!");
		} catch (IOException ex) {
			System.out.println("Unable to save 'Transactions' data!");
		} catch (Exception ex) {
			System.out.println("Unable to save 'Transactions' data!");
		}
		
		//System.out.println("Saved 'Transactions' data successfully!");
	}
	
	/**
	 * Displays the list of options for this Order submenu
	 */
	private void displayOrderSubmenuOptions()
	{
		/*
		System.out.print("\n"+new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("ORDER MANAGER ");
		System.out.print(new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("|0. Return to main menu                  ");
		System.out.println("|1. View current orders                 ");
		System.out.println("|2. Create a new order                  ");
		System.out.println("|3. Add item to existing order          ");
		System.out.println("|4. Remove item from existing order      ");
		System.out.println("|5. Make payment for existing order     ");
		System.out.print(new String(new char[87]).replace("\0", "="));
		*/

		System.out.print("\n"
				+ "Order:\n"
				+ "0) Return to main menu\n"
                + "1) View current order(s)\n"
                + "2) Create a new order\n"
                + "3) Add item(s) to existing order(s)\n"
                + "4) Remove item(s) from existing order(s)\n"
                + "5) Make payment\n");	
	}
	
	/**
	 * Prompts the user to choose a order function
	 * 
	 * @return The integer representing the order function that the user
	 * 		   has chosen
	 */
	public int getOrderChoice()
	{
		displayOrderSubmenuOptions();
		
		int maxOrderChoices = OrderSubmenuState.values().length;
		int orderChoice = -1;
		do
		{
			try
			{
				System.out.printf("%nPlease enter your choice (0-%d): ", maxOrderChoices);
				orderChoice = sc.nextInt();
				sc.nextLine();
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Invalid input! Please try again..");
				sc.nextLine(); 
				continue;
			}
			catch(Exception ex)
			{
				System.out.println("Invalid input! Please try again..");
				sc.nextLine(); 
				continue;
			}
			
			if(orderChoice < 0 || orderChoice > maxOrderChoices)
				System.out.println("Invalid choice! Please try again..");
			
		} while (orderChoice < 0 || orderChoice > maxOrderChoices);
		
		if(orderChoice == 0)
			return orderChoice;
		else
		{
			switch(OrderSubmenuState.values()[orderChoice - 1])
			{
				case ViewCurrentOrders:
					viewCurrentOrders();
					break;
			
				case CreateOrder:
					createOrder();
					break;
				
				case AddItemToOrder:
					addItemToOrder();
					break;
				
				case RemoveItemFromOrder:
					removeItemFromOrder();
					break;
				
				case MakePaymentForOrder:
					makePaymentForOrder();
					break;
			}
		}
		return orderChoice;
	}
	
	/**
	 * Displays the currently ongoing/unpaid orders
	 */
	private void viewCurrentOrders()
	{
		if(_currentOrders.isEmpty())
		{
			System.out.print("\nThere are no orders taken at the moment!\n");			
			return;
		}
		
		System.out.printf("\nCURRENT ORDERS\n");
		
		int currOrderNo = 1;
		
		for(Order order : _currentOrders)
		{
			System.out.printf("%n%-5s", "(" + (currOrderNo++) + ")");
			order.displayOrderDetails();
		}
	}
	
	/**
	 * Creates a new order<p>
	 * 
	 */
	private void createOrder()
	{	
		try
		{
			Staff selectedWaiter = StaffMgr.getStaffMgr().selectWaiter();
			if(selectedWaiter == null)
			{
				System.out.println("\nSorry, the restaurant does not have any waiter.");
				return;
			}
			
			Customer selectedCustomer = StaffMgr.getStaffMgr().selectCustomer();
			if(selectedCustomer == null)
			{
				System.out.println("\nSorry, the restaurant does not have any registered customer.");
				return;
			}
			
			table table;
			
			System.out.print("\nChecking for reservations..");
			table = restaurantMgr.getrestaurantMgr().getReservedTable(selectedCustomer.getCustomerID());
			
			int numOfPeople = 0;
			if(table == null) //if the customer has no reservation
			{
				System.out.println(" No reserved table found for this customer..");
				
				System.out.print("\nWe'll try to look for an empty table for you!");
				System.out.print(" Enter number of people (1-10): ");
				
				numOfPeople = sc.nextInt();
				sc.nextLine();
				
				if(numOfPeople < 1 || numOfPeople > 10)
				{
					System.out.println("\nInvalid input, only 1-10 people allowed!");
					System.out.println(" Failed to create order, please try again..");
					return;
				}
				
				table = restaurantMgr.getrestaurantMgr().getAvailableTable(selectedCustomer.getCustomerID(), numOfPeople);
				
				if(table == null)
				{
					System.out.println("\nUnable to find an empty table right now! Failed to create order, please try again later..");
					return;
				}
				else
				{
					System.out.printf("\nFound an empty table.. Please go to table '%d'!%n", table.getTableNo());
				}
			}
			else //if the customer has a reservation
			{
				System.out.printf(" Found an reserved table.. Please go to table '%d'!%n", table.getTableNo());
				
				numOfPeople = restaurantMgr.getrestaurantMgr().getPaxFromReservation(selectedCustomer.getCustomerID());
			}
			
			Calendar currentInstant = GregorianCalendar.getInstance();
        	Date currentDateTime = currentInstant.getTime();
        	currentInstant.setTime(currentDateTime);
        	
        	Timestamp timestamp = new Timestamp(currentDateTime.getTime());
        	String timestampStr = timestamp.toString();
        	timestampStr = timestampStr.replaceAll("[^0-9]", "");
        	
        	//each order id is unique which consists of timestamp and table number
        	String orderID = timestampStr + table.getTableNo();
        	
        	Order newOrder = new Order(selectedWaiter.getStaffID(),
        							   selectedCustomer.getCustomerID(), table.getTableNo(),
        							   numOfPeople, 0, 0, currentInstant, orderID);
        	
        	System.out.println("\nWhat would you like to have?");
        	
        	menuitem selectedItem = null;
        	do
        	{
        		selectedItem = menuMgr.getmenuMgr().selectmenuitem();
        		if(selectedItem != null)
        		{
        			System.out.print("Enter the quantity for this item: ");
        			int itemQuantity = sc.nextInt();
        			
        			if(itemQuantity < 1)
        			{
        				System.out.println("\nInvalid input! Minimum quantity is 1..");
        				continue;
        			}
        			
        			newOrder.addItemToOrder(selectedItem, itemQuantity);
        		}
        		
        	} while(selectedItem != null);
        	
        	if(newOrder.getNumberOfOrderItems() == 0)
        	{
        		System.out.print("\nCannot create an order with 0 items!");
    			System.out.println(" Failed to create order, please try again..");
    			return;
        	}
        	else
        	{
        		_currentOrders.add(newOrder);    		
        		System.out.println("\nSucessfully created new order!!");
        	}
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid input! Please try again");
			System.out.println("Failed to create order, please try again..");

			sc.nextLine(); 
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to create order, please try again..");
			sc.nextLine(); 
			return;
		}
	}

	/**
	 * Adds an menu item to the selected order
	 */
	private void addItemToOrder() 
	{
		if(_currentOrders.isEmpty())
		{
			System.out.print("\nWell, there are no orders taken at the moment!");			
			return;
		}
		
		try
		{
        	int orderIndex = 0;
        	int maxOrders = _currentOrders.size();
        	
        	System.out.println();
        	System.out.printf("%5s%-25s", "", "Order ID");
    		System.out.printf("%-15s", "Staff ID");
    		System.out.printf("%-15s", "Customer ID");
    		System.out.printf("%-15s%n", "table Number");
    		
        	for(Order order : _currentOrders)
        	{
        		System.out.printf("%-5s", "(" + (++orderIndex) + ")");
        		order.displayOrderSummary();
        	}
        	
        	System.out.print("\nPlease select the order to add to (0 to cancel): ");
        	int selectedOrderIndex = sc.nextInt();
        	
        	if(selectedOrderIndex == 0)
        	{
        		System.out.println("\nNothing to be updated!");
				return;
        	}
        	
			if (selectedOrderIndex < 1 || selectedOrderIndex > maxOrders)
			{
				System.out.println("Invalid input! Please try again");
				System.out.println("Failed to update order, please try again..");
				return;
			}
			
			Order updatingOrder = _currentOrders.get(selectedOrderIndex - 1);			
			System.out.println("\nWhat would you like to add to the order?");
			
        	menuitem selectedItem = null;
        	selectedItem = menuMgr.getmenuMgr().selectmenuitem();
        	if(selectedItem != null)
        	{
        		System.out.print("Enter the quantity for this item: ");
        		int itemQuantity = sc.nextInt();

        		if(itemQuantity < 1)
        		{
        			System.out.println("\nInvalid input! Minimum quantity is 1..");
        		}
        		else
        		{
        			updatingOrder.addItemToOrder(selectedItem, itemQuantity);
        			System.out.printf("%nSuccessfully added \"%dx" + " %s\" to the order!%n", itemQuantity, selectedItem.getName());
        		}
        	}
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid input! Please try again");
			System.out.println("Failed to add item to order, please try again..");

			sc.nextLine(); 
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to add item to order, please try again..");

			sc.nextLine(); 
			return;
		}
	}

	/**
	 * Removes an item from the order
	 * 
	 */
	private void removeItemFromOrder()
	{
		if(_currentOrders.isEmpty())
		{
			System.out.print("\nWell, there are no orders at the moment!");			
			return;
		}
		
		try
		{
        	int orderIndex = 0;
        	int maxOrders = _currentOrders.size();
        	
        	System.out.println();
        	System.out.printf("%5s%-25s", "", "Order ID");
    		System.out.printf("%-15s", "Staff ID");
    		System.out.printf("%-15s", "Customer ID");
    		System.out.printf("%-15s%n", "table Number");
    		
        	for(Order order : _currentOrders)
        	{
        		System.out.printf("%-5s", "(" + (++orderIndex) + ")");
        		order.displayOrderSummary();
        	}
        	
        	System.out.print("\nPlease select the order to remove from (0 to cancel): ");
        	int selectedOrderIndex = sc.nextInt();
        	
        	if(selectedOrderIndex == 0)
        	{
        		System.out.println("\nNothing to be updated!");
				return;
        	}
        	
        	// Valid values from 1 to maxOrders
			if (selectedOrderIndex < 1 || selectedOrderIndex > maxOrders)
			{
				System.out.println("Invalid input! Please try again");
				return;
			}
			
			Order updatingOrder = _currentOrders.get(selectedOrderIndex - 1);			
			System.out.println("\nWhat would you like to remove from the order?");
			
			List<orderitem> orderItems = updatingOrder.getOrderItems();
			
			int orderItemIndex = 0;
        	int maxOrderItems = orderItems.size();
    		
        	System.out.println();
        	for(orderitem orderitem : orderItems)
        	{
        		System.out.printf("%-5s", "(" + (++orderItemIndex) + ")");
        		orderitem.displayOrderItemSummary();
        	}
        	
        	System.out.print("\nPlease select the order item to remove from (0 to cancel): ");
        	int selectedOrderItemIndex = sc.nextInt();
        	
        	if(selectedOrderItemIndex == 0)
        	{
        		System.out.println("\nNothing to be updated!");
				return;
        	}
        	
			if (selectedOrderItemIndex < 1 || selectedOrderItemIndex > maxOrderItems)
			{
				System.out.println("Invalid input! Failed to update order. Please try again");
				return;
			}
			
			orderitem removedOrderItem = orderItems.get(selectedOrderItemIndex - 1);
			
        	if(removedOrderItem != null)
        	{
        		int maxItemQuantity = updatingOrder.getOrderItemQty(removedOrderItem.getName());
        		
        		System.out.print("Enter the quantity to be removed: ");
        		int itemQuantity = sc.nextInt();

        		if(itemQuantity < 1)
        		{
        			System.out.println("\nInvalid input! Minimum quantity is 1..");
        			System.out.println("Failed to remove item from order, please try again..");
        		}
        		else if(itemQuantity > maxItemQuantity)
        		{
        			System.out.printf("%nInvalid input! Maximum quantity is %d..%n", maxItemQuantity);
        			System.out.println("Failed to remove item from order, please try again..");
        		}
        		else
        		{
        			updatingOrder.removeItemFromOrder(removedOrderItem.getName(), itemQuantity);
        			System.out.printf("%nSuccessfully removed \"%dx"+" %s\" from the order!%n", itemQuantity,removedOrderItem.getName());
        			
        			if(updatingOrder.getNumberOfOrderItems() == 0)
                	{
                		System.out.print("\nThe order is now empty! Thus, removing order..");
            			
            			_currentOrders.remove(updatingOrder);
            			return;
                	}
        		}
        	}
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid input! Please try again");
			System.out.println("Failed to remove item from order, please try again..");

			sc.nextLine(); 
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to remove item from order, please try again..");

			sc.nextLine(); 
			return;
		}
	}
	
	/**
	 * Allows the user to make a payment for a order
	 * 
	 */
	private void makePaymentForOrder()
	{
		if(_currentOrders.isEmpty())
		{
			System.out.print("\nWell, there are no orders taken at the moment!");			
			return;
		}
		
		try
		{
        	int orderIndex = 0;
        	int maxOrders = _currentOrders.size();
        	
        	System.out.println();
        	System.out.printf("%5s%-25s", "", "Order ID");
    		System.out.printf("%-15s", "Staff ID");
    		System.out.printf("%-15s", "Customer ID");
    		System.out.printf("%-15s%n", "table Number");
    		
        	for(Order order : _currentOrders)
        	{
        		System.out.printf("%-5s", "(" + (++orderIndex) + ")");
        		order.displayOrderSummary();
        	}
        	
        	System.out.print("\nPlease select the order to make payment for (0 to cancel): ");
        	int selectedOrderIndex = sc.nextInt();
        	
        	if(selectedOrderIndex == 0)
        	{
        		System.out.println("\nNo payment made!");
				return;
        	}
        	
			if (selectedOrderIndex < 1 || selectedOrderIndex > maxOrders)
			{
				System.out.println("Invalid input! Please try again");
				System.out.println("Failed to make payment, please try again..");
				return;
			}
			
			Order payingOrder = _currentOrders.get(selectedOrderIndex - 1);
			payingOrder.displayOrderInvoice();
			
			table occupiedTable = restaurantMgr.getrestaurantMgr().getTableByNumber(payingOrder.getTableNo());
			if(occupiedTable != null)
			{
				occupiedTable.freeTable();
				System.out.printf("%nTable \'%d\' is now available!%n", occupiedTable.getTableNo());
			}
			
			// Remove it from the list of current orders
			// Add it to the list of completed transactions
			_currentOrders.remove(payingOrder);
			_completedOrders.add(payingOrder);
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid input! Please try again");
			System.out.println("Failed to make payment, please try again..");

			sc.nextLine(); 
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to make payment, please try again..");

			sc.nextLine(); 
			return;
		}
	}
	
	/**
	 * Allows the user to view the sale revenue report by day/month
	 */
	public void displaySaleRevenue()
	{
		if(_completedOrders.isEmpty())
		{
			System.out.println("\nNo completed orders!");
			return;
		}
		
		try
		{
			System.out.println("\n1) Display sale revenue report by day");
			System.out.println("2) Display sale revenue report by month");
			
			System.out.print("\nPlease select the mode of display (0 to cancel): ");
			
			int displayMode = sc.nextInt();
			
			if(displayMode == 0)
			{
				System.out.println("\nNothing to be displayed!");
				return;
			}
			
			if(displayMode < 1 || displayMode > 2)
			{
				System.out.println("\nInvalid choice, valid options are (1) & (2)!");
				System.out.println("Failed to display sale revenue report, please try again..");
				return;
			}
			
			if(displayMode == 1)
				displaySaleRevenueByDay();
			else
				displaySaleRevenueByMonth();
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid input! Please try again");
			System.out.println("Failed to display sale revenue report, please try again..");

			sc.nextLine(); 
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to display sale revenue report, please try again..");

			sc.nextLine(); 
			return;
		}
	}
	
	/**
	 * Allows the user to view the sale revenue by day
	 * 
	 */
	private void displaySaleRevenueByDay()
	{
		try
		{	
			SimpleDateFormat sdf = new SimpleDateFormat();
        	sdf.applyPattern("dd/MM/yyyy");
        	sdf.setLenient(false);
        	
        	System.out.println("\nDisplaying sale revenue report by day..");
        	System.out.print("Enter date (dd/mm/yyyy): ");
        	String saleRevenueDateStr = sc.next();

        	Date saleRevenueDate = sdf.parse(saleRevenueDateStr);
        	Calendar saleRevenueCal = GregorianCalendar.getInstance();
        	saleRevenueCal.setTime(saleRevenueDate);
        	
			Calendar currentInstant = GregorianCalendar.getInstance();
        	Date currentDateTime = currentInstant.getTime();
        	currentInstant.setTime(currentDateTime);
        	currentInstant.set(Calendar.HOUR_OF_DAY, 23);
        	currentInstant.set(Calendar.MINUTE, 59);
        	
        	if(saleRevenueCal.after(currentInstant))
        	{
        		System.out.print("\nInvalid date! ");
    			System.out.println("Failed to check sale revenue report, please try again..");
    			System.out.println("NOTE: You can only check sale revenue reports for previous days!");		
    			return;
        	}
        	
        	double overallRevenue = 0.0;
        	
        	for(Order order : _completedOrders)
        	{
        		Calendar orderDateTime = order.getDateTime();
        		
        		if( (orderDateTime.get(Calendar.YEAR) == saleRevenueCal.get(Calendar.YEAR)) &&
        			(orderDateTime.get(Calendar.MONTH) == saleRevenueCal.get(Calendar.MONTH)) &&
        			(orderDateTime.get(Calendar.DAY_OF_MONTH) == saleRevenueCal.get(Calendar.DAY_OF_MONTH)) )
        		{
        			overallRevenue += order.getNettBill();
        			order.displayOrderInvoice();
        		}
        	}
        	
        	SimpleDateFormat saleRevenueDateFormat;
        	saleRevenueDateFormat = new SimpleDateFormat("E, dd/MM/yyyy");
        	
        	if(overallRevenue == 0.0)
        	{
        		System.out.println("\nThere are no sales made on the selected"+ " day, \"" + saleRevenueDateFormat.format(saleRevenueDate) + "\"");
        	}
        	else
        	{
        		System.out.println("\nTotal sales for"+ " \"" + saleRevenueDateFormat.format(saleRevenueDate) + "\": " +new DecimalFormat("$###,##0.00").format(overallRevenue) );
        	}
		}
		catch(ParseException ex)
        {
        	System.out.print("\nInvalid date input! ");
			System.out.println("Failed to display sale revenue report, please try again..");
			System.out.println("NOTE: Date entered should be in dd/mm/yyyy, e.g. 25/10/2014!");	
			return;
        }
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid input! Please try again");
			System.out.println("Failed to display sale revenue report, please try again..");

			sc.nextLine(); 
			return;
		}
		catch(Exception ex)
		{
			System.out.println("\nFailed to display sale revenue report, please try again..");

			sc.nextLine(); 
			return;
		}
	}
	
	/**
	 * Allows the user to view the sale revenue by month
	 * 
	 */
	private void displaySaleRevenueByMonth()
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat();
        	sdf.applyPattern("MM/yyyy");
        	sdf.setLenient(false);
        	
        	System.out.println("\nDisplaying sale revenue report by month..");
        	System.out.print("Enter month (mm/yyyy): ");
        	String saleRevenueDateStr = sc.next();

        	Date saleRevenueMonth = sdf.parse(saleRevenueDateStr);
        	Calendar saleRevenueCal = GregorianCalendar.getInstance();
        	saleRevenueCal.setTime(saleRevenueMonth);
        	
			Calendar currentInstant = GregorianCalendar.getInstance();
        	Date currentDateTime = currentInstant.getTime();
        	currentInstant.setTime(currentDateTime);
        	currentInstant.set(Calendar.DAY_OF_MONTH, 0);
        	
			if (saleRevenueCal.get(Calendar.YEAR) > currentInstant
					.get(Calendar.YEAR))
			{
				System.out.print("\nInvalid month! ");
				System.out.println("Failed to check sale revenue report, please try again..");
				System.out.println("NOTE: You can only check sale revenue reports for previous/current month!");
				return;
			}
			if (saleRevenueCal.get(Calendar.YEAR) == currentInstant
					.get(Calendar.YEAR))
			{
				if ((saleRevenueCal.get(Calendar.MONTH) - 1) > 
					currentInstant.get(Calendar.MONTH))
				{
					System.out.print("\nInvalid month! ");
					System.out.println("Failed to check sale revenue report, please try again..");
					System.out.println("NOTE: You can only check sale revenue  reports for previous/current month!");
					return;
				}
			}
        	
        	double[] overallRevenue = new double[31];
        	double totalRevenue = 0.0;
        	
        	for(Order order : _completedOrders)
        	{
        		Calendar orderDateTime = order.getDateTime();
        		
        		if( (orderDateTime.get(Calendar.YEAR) == 
        				saleRevenueCal.get(Calendar.YEAR)) &&
        				(orderDateTime.get(Calendar.MONTH) ==
        				saleRevenueCal.get(Calendar.MONTH)) )
        		{
        			overallRevenue[orderDateTime.get(Calendar.DAY_OF_MONTH) - 1] += order.getNettBill();
        			totalRevenue += order.getNettBill();
        		}
        	}
        	
        	SimpleDateFormat saleRevenueDateFormat;
        	saleRevenueDateFormat = new SimpleDateFormat("MMMM yyyy");
        	
        	if(totalRevenue == 0.0)
        	{
        		System.out.println("\nThere are no sales made on the selected month, \"" + saleRevenueDateFormat.format(saleRevenueCal.getTime()) + "\"!");
        		return;
        	}
        	
        	int minDay = 0, maxDay = 0;
        	double minDayRevenue = 1000, maxDayRevenue = 0;
        	
        	for(int currDay = 0; currDay < overallRevenue.length; currDay++)
        	{
        		if(overallRevenue[currDay] == 0)
        			continue;
        		
        		if(overallRevenue[currDay] >= maxDayRevenue)
        		{
        			maxDayRevenue = overallRevenue[currDay];
        			maxDay = currDay;
        		}
        		
        		if(overallRevenue[currDay] <= minDayRevenue)
        		{
        			minDayRevenue = overallRevenue[currDay];
        			minDay = currDay;
        		}
        	}
        	
        	saleRevenueDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        	saleRevenueCal.set(Calendar.DAY_OF_MONTH, maxDay + 1);
        	System.out.printf("%nHighest sales for the month: \"%s\"\t", saleRevenueDateFormat.format(saleRevenueCal.getTime()));
        	System.out.printf("Revenue for the day: %s%n", new DecimalFormat("$###,##0.00").format(maxDayRevenue));
        	
        	saleRevenueCal.set(Calendar.DAY_OF_MONTH, minDay + 1);
        	System.out.printf("Lowest sales for the month: \"%s\"\t", saleRevenueDateFormat.format(saleRevenueCal.getTime()));
        	System.out.printf("Revenue for the day: %s%n", new DecimalFormat("$###,##0.00").format(minDayRevenue));
        	
        	saleRevenueDateFormat = new SimpleDateFormat("MMM yyyy");
        	System.out.printf("%nTotal sales for the month \"%s\": %s%n", saleRevenueDateFormat.format(saleRevenueCal.getTime()), new DecimalFormat("$###,##0.00").format(totalRevenue));
		}
		catch(ParseException ex)
        {
        	System.out.print("\nInvalid month input! ");
			System.out.println("Failed to display sale revenue report, please try again..");
			System.out.println("NOTE: Month entered should be in mm/yyyy, e.g. 10/2014!");
			return;
        }
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid input! Please try again");
			sc.nextLine(); 
			return;
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
			System.out.println("\nFailed to display sale revenue report, please try again..");
			sc.nextLine(); 
			return;
		}
	}
}