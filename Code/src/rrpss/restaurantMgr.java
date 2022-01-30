package rrpss;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The manager class for controlling the Restaurant data such as tables, reservations etc.
 * 
 * @author Kanupriya Arora
 *
 */
public class restaurantMgr {
	
	/**
	 * An enumeration type used to determine the sub-menu options
	 *
	 */
	private enum RestaurantSubmenuState
	{
		ViewTableAvailability,
		ViewReservations,
		AddReservation,
		RemoveReservation
	}
	
	/**
	 * A static integer array containing the various table sizes offered by the restaurant.
	 */
	private static final int[] TABLE_SIZES = {2,2,4,4,6,6,8,8,10};
	
	/**
	 * Static integer denoting the maximum number of hours for which the reservation shall be made
	 */
	private static final int RESERVATION_DURATION = 2;
	
	/**
	 * Static integer denoting the opening time of the restaurant (in 24 hours)
	 */
	private static final int OPENING_HOUR = 9;
	
	/**
	 * Static integer denoting the closing time of the restaurant (in 24 hours).
	 */
	private static final int CLOSING_HOUR = 23;
	
	/**
	 * The file path for the binary file used to store the Restaurant information
	 */
	private static final String RESTAURANT_FILE_PATH = "restaurantdata.dat"; 
	
	/**
	 * Instance of an array containing the various tables.
	 */
	private ArrayList<table> tables;
	
	/**
	 * Instance of an array containing the various customers.
	 */
	private ArrayList<Customer> customers;

	/**
	 * Instance of an array containing the various reservations.
	 */
	private ArrayList<reservation> reservations;
	
	/**
	 * Static instance of the Restaurant Manager.
	 */
	private static restaurantMgr RestaurantMgr = null;

	/**
	 * Standard Java Scanner
	 */
	Scanner sc  = new Scanner(System.in); 
	private static SimpleDateFormat dateFormatter = null;

	/**
	 * Private Constructor used to create new arrays of tables and reservations, formats dates and sets the tables up.
	 */
	private restaurantMgr() {
		// TODO - implement RestaurantMgr.RestaurantMgr
		//throw new UnsupportedOperationException();
        
		tables = new ArrayList<table>();
		reservations = new ArrayList<reservation>();
		dateFormatter = new SimpleDateFormat("E, dd/MM/yyyy, HH:mm");
		
		setUpTables();
	}

	/**
	 * Public static function to get the Restaurant Manager.
	 * @return Instance of the Restaurant manager
	 */
	public static restaurantMgr getrestaurantMgr() {
		// TODO - implement RestaurantMgr.getRestaurantMgr
		//throw new UnsupportedOperationException();
		if(RestaurantMgr == null)
		{
			RestaurantMgr = new restaurantMgr();
		}
		
		return RestaurantMgr;
	}

	/**
	 * Loads the Restaurant information from the file
	 */
	public void loadRestaurant() {
		// TODO - implement RestaurantMgr.loadRestaurant
		//throw new UnsupportedOperationException();
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try
		{
			fis = new FileInputStream(RESTAURANT_FILE_PATH);
			in = new ObjectInputStream(fis);
			
			Object obj = in.readObject();

			if (obj instanceof ArrayList<?>) {
				ArrayList<?> al = (ArrayList<?>) obj;

				if (al.size() > 0) {
					for (int objIndex = 0; objIndex < al.size(); objIndex++) {
						Object childObj = al.get(objIndex);

						if (childObj instanceof reservation) {
							reservations.add(((reservation) childObj));
						}
					}
				}
			}

			in.close();
			
			if(!reservations.isEmpty())
				System.out.println("Reservations data loaded!");
			
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to load Reservations data!");
		} catch (IOException ex) {
			System.out.println("Unable to load Reservations data!");
		} catch (ClassNotFoundException ex) {
			System.out.println("Unable to load Reservations data!");
		} catch (Exception ex) {
			System.out.println("Unable to load Reservations data!");
		}
	}

	/**
	 * Saves the Restaurant information to the file
	*/	
	public void saveRestaurant() {
		// TODO - implement RestaurantMgr.saveRestaurant
		//throw new UnsupportedOperationException();
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			fos = new FileOutputStream(RESTAURANT_FILE_PATH);
			out = new ObjectOutputStream(fos);
			
			out.writeObject(reservations);
			
			out.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to save Reservations data!");
		} catch (IOException ex) {
			System.out.println("Unable to save Reservations data!");
		} catch (Exception ex) {
			System.out.println("Unable to save Reservations data!");
		}

		//System.out.println("Saved 'Reservations' data successfully!");
	}

	/**
	 * Adds tables and initializes them for use.
	 */
	private void setUpTables() {
		// TODO - implement RestaurantMgr.setUpTables
		//throw new UnsupportedOperationException();
		int tableNumber = 1;
		for(int tableSize : TABLE_SIZES)
		{		
			table newTable;
			newTable = new table(tableNumber++, tableSize, false,
					false, 0);
			
			tables.add(newTable);
		}
	}

	/**
	 * Displays the list of options for the Restaurant Management submenu
	 */
	private void displayRestMgrOptions() {
		// TODO - implement RestaurantMgr.displayRestMgrOptions
		//throw new UnsupportedOperationException();
		
		/*
		System.out.print("\n"+new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("RESTAURANT MANAGER ");
		System.out.print(new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("|0. Return to main menu                  ");
		System.out.println("|1. View table availability              ");
		System.out.println("|2. View Reservations                    ");
		System.out.println("|3. Add a new reservation                ");
		System.out.println("|4. Remove an existing reservation       ");
		System.out.println(new String(new char[87]).replace("\0", "="));
		*/

		System.out.print("\n"
				+ "Reservations:\n"
				+ "0) Return to main menu\n"
                + "1) View table availability\n"
                + "2) View Reservations\n"
                + "3) Add a new reservation\n"
                + "4) Remove an existing reservation\n");		
	}

	/**
	 * Prompts the user to choose a menu function
	 * 
	 * @return The integer value representing the choice of the submenu to be expanded
	 */
	public int getRestMgrChoice() {
		// TODO - implement RestaurantMgr.getRestMgrChoice
		//throw new UnsupportedOperationException();
		displayRestMgrOptions();
		
		int maxRestaurantChoices = RestaurantSubmenuState.values().length;
		int restaurantChoice = -1;
		do
		{
			try
			{
				System.out.printf("%nPlease enter your choice (0-%d): ",
						maxRestaurantChoices);
				restaurantChoice = sc.nextInt();
				sc.nextLine();
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Invalid input! Please try again..");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			catch(Exception ex)
			{
				System.out.println("Invalid input! Please try again..");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			
			if(restaurantChoice < 0 || restaurantChoice > maxRestaurantChoices)
				System.out.println("Invalid choice! Please try again..");
			
		} while (restaurantChoice < 0 || restaurantChoice > maxRestaurantChoices);
		
		if(restaurantChoice == 0)
			return restaurantChoice; // Go back to main menu
		else
		{
			switch(RestaurantSubmenuState.values()[restaurantChoice - 1])
			{
			case ViewTableAvailability:
				viewTableAvailability();
				break;
			
			case ViewReservations:
				viewReservations();
				break;
				
			case AddReservation:
				addReservation();
				break;
				
			case RemoveReservation:
				removeReservation();
				break;
			}
		}
		
		return restaurantChoice;
	}

	/**
	 * Shows the table availability status
	 */
	private void viewTableAvailability() {
		// TODO - implement RestaurantMgr.viewTableAvailability
		//throw new UnsupportedOperationException();
		checkReservations();
		
		System.out.printf("%n%-20s", "Table Number");
		System.out.printf("%-20s", "Number of Seats");
		System.out.printf("%-20s", "Table Status");
		System.out.printf("%-20s%n", "Customer ID");
		
		for(table table : tables)
		{
			table.showTableChart();
			
		}
	}

	/**
	 * Adds a new reservation
	 */
	private void addReservation() {
		// TODO - implement RestaurantMgr.addReservation
		//throw new UnsupportedOperationException();
		checkReservations();
		
		System.out.println("\nRestaurant's opening hours: 0900 - 2300, Reservation Duration: 2 Hours");
		System.out.println("NOTE: Reservations will be cancelled 5 minutes after reservation date/time if you do not show up!");
		
        try
        {
        	SimpleDateFormat sdf = new SimpleDateFormat();
        	sdf.applyPattern("dd/MM/yyyy HH:mm");
        	sdf.setLenient(false);
        	
        	System.out.print("\nEnter reservation date (dd/mm/yyyy): ");
        	String reservationDateStr = sc.next();

        	System.out.print("Enter reservation time, in 24-hour format (hh:mm): ");
        	String reservationTimeStr = sc.next();

        	Date reservationDateTime = sdf.parse(reservationDateStr + " " + reservationTimeStr);
        	
        	Calendar startDateTime = GregorianCalendar.getInstance();
        	startDateTime.setTime(reservationDateTime);
        	
        	Calendar endDateTime = (Calendar) startDateTime.clone();
        	endDateTime.add(Calendar.HOUR_OF_DAY, RESERVATION_DURATION);
        	
        	Calendar restOpeningTime = (Calendar) startDateTime.clone();
        	restOpeningTime.set(Calendar.HOUR_OF_DAY,(OPENING_HOUR - 1) );
        	restOpeningTime.set(Calendar.MINUTE, 59);
        	
        	Calendar restClosingTime = (Calendar) restOpeningTime.clone();
        	restClosingTime.set(Calendar.HOUR_OF_DAY, CLOSING_HOUR);
        	restClosingTime.set(Calendar.MINUTE, 1);
        	
        	if( !(startDateTime.after(restOpeningTime)) || !(endDateTime.before(restClosingTime)) )
        	{
        		System.out.print("\nInvalid reservation date/time! ");
    			System.out.println("Failed to add new reservation, please try again..");
    			System.out.println("NOTE: The restaurant is only open from 0900 - 2300!");
    			
    			return;
        	}
        	
        	Calendar currentInstant = GregorianCalendar.getInstance();
        	Date currentDateTime = currentInstant.getTime();
        	currentInstant.setTime(currentDateTime);
        	
        	if(startDateTime.before(currentInstant))
        	{
        		System.out.print("\nInvalid reservation date/time! ");
    			System.out.println("Failed to add new reservation please try again..");
    			System.out.println("NOTE: Reservation can only be made in advance!");
    			return;
        	}
        
         Customer customer = StaffMgr.getStaffMgr().selectCustomer();
           //Customer customer= customer.getCustomerID();

            if(customer == null)
            {
            	System.out.println("Unable to add new reservation as there are no registered customers!");
            	return;
            }
            	
        	System.out.print("Enter number of people (1-10): ");
        	int pax = sc.nextInt();
        	
        	if(pax < 1 || pax > 10)
            {
            	System.out.println("Invalid number of people, failed to add new reservation!");
            	return;
            }
        	
        	int availableTableNumber = 0;
        	int tableNumber = 1;
        	
    		for(int tableSize : TABLE_SIZES)
    		{
    			if(tableSize >= pax)
    			{
    				boolean isReserved = false;
    				
    				for(reservation reservation : reservations)
    	    		{
    					Calendar resStartDateTime = reservation.getstartDateTime();
    					
    					Calendar resEndDateTime = (Calendar) resStartDateTime.clone();
    					resEndDateTime.add(Calendar.HOUR_OF_DAY, reservation.getDuration());
    					
    	    			if(reservation.getreservedTableNo() == tableNumber)
    	    			{
    	    				if(startDateTime.before(resEndDateTime) && endDateTime.after(resStartDateTime))
	    	    			{
	    	    				isReserved = true;
	    	    				break;
	    	    			}
    	    			}
    	    		} 
    				
    				if(!isReserved)
    				{
    					availableTableNumber = tableNumber;
    					break;
    				}
    			}
    			
    			tableNumber++;
    		} 
    		
    		if(availableTableNumber == 0)
    		{
    			System.out.printf("\nSorry, there are no tables available at the selected date/time that can accommodate %d people!%n", pax);
    		}
    		else
    		{	
    			reservation newReservation;
    			newReservation = new reservation(availableTableNumber,
    											 customer.getCustomerID(), customer.getName(),
    											 customer.getPhone(), pax,
    											 startDateTime, RESERVATION_DURATION);
    			
    			reservations.add(newReservation);
    			
    			Collections.sort(reservations);
    			
    			System.out.printf("\nSuccessfully allocated Table '%d' to '%s'!%n", availableTableNumber, customer.getName());
    			System.out.printf("Reservation Date/Time: %s, Reservation Duration: %d Hours%n", dateFormatter.format(startDateTime.getTime()),RESERVATION_DURATION);
    		}
        }
        catch(ParseException ex)
        {
        	System.out.print("\nInvalid reservation date/time! ");
			System.out.println("Failed to add new reservation, please try again..");
			System.out.println("NOTE: Reservation date should be in dd/mm/yyyy, e.g. 25/12/2014 and reservation time should be in hh:mm (24-hour format), e.g. 19:30!");
			
			return;
        }
        catch(InputMismatchException ex)
        {
        	System.out.print("Invalid input! ");
			System.out.println("Failed to add new reservation, please try again..");
			return;
        }
        catch(Exception ex)
        {
        	System.out.print("Invalid input!");
			System.out.println("Failed to add new reservation, please try again..");
			return;
        }
	}

	/**
	 * Views all the reservations made, at that instant.
	 */
	private void viewReservations() {
		// TODO - implement RestaurantMgr.viewReservations
		//throw new UnsupportedOperationException();
		checkReservations();
		
		if(reservations.isEmpty())
		{
			System.out.print("\nThere are no reservations made at the moment!");
			return;
		}
		
		System.out.print("\n" + new String(new char[43]).replace("\0", "*"));
		System.out.print(" Reservations ");
		System.out.println(new String(new char[43]).replace("\0", "*"));
		
		int currReservationNo = 1;
		
		for(reservation reservation : reservations)
		{
			System.out.printf("%n%-5s", "(" + (currReservationNo++) + ")");
			reservation.showReservationSummary();
		}
		
		System.out.print("\n" + new String(new char[43]).replace("\0", "*"));
		System.out.print(" Reservations ");
		System.out.println(new String(new char[43]).replace("\0", "*"));
	}


	/**
	 * Removes a reservation
	 */
	private void removeReservation() {
		// TODO - implement RestaurantMgr.removeReservation
		//throw new UnsupportedOperationException();
        checkReservations();
		
		if(reservations.isEmpty())
		{
			System.out.print("\nThere are no reservations made at the moment!");			
			return;
		}

		try
		{
			int numOfReservations = 0;

			System.out.println();
			System.out.printf("%5s%-15s", "", "Table Number");
			System.out.printf("%-30s", "Customer Name");
			System.out.printf("%-25s", "Reservation Date/Time");
			System.out.printf("%-20s%n", "Reservation Duration");

			for(reservation reservation : reservations)
			{
				System.out.printf("%-5s", "(" + (++numOfReservations) + ")");
				reservation.showReservationSummary();
			}

			System.out.printf("%nPlease select a reservation to remove (0 to cancel): ");

			int reservationIndex = sc.nextInt();
			sc.nextLine();

			if(reservationIndex == 0)
			{
				System.out.println("\nNo reservation removed!");
				return;
			}

			if (reservationIndex < 1 || reservationIndex > numOfReservations)
			{
				System.out.print("\nInvalid input! ");
				System.out.println("Failed to remove reservation, please try again..");
				return;
			}

			reservation removedReservation = reservations.get(reservationIndex - 1);

			if(removedReservation != null)
			{
				table table = getTableByNumber(removedReservation.getreservedTableNo());
				
				if(table.isReserved() && (table.getCustomerID() == removedReservation.getCustomerID()) )
				{
					table.unreserveTable();
				}
				
				System.out.printf("%nSuccessfully removed reservation made by \"%s\"!%n", removedReservation.getCustomerName());
			}
			reservations.remove(removedReservation);
		}
		catch(InputMismatchException ex)
		{
			System.out.print("\nInvalid input! ");
			System.out.println("Failed to remove reservation, please try again..");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to remove reservation, please try again..");
			sc.nextLine(); // Clear the garbage input
			return;
		}
	}


	/**
	 * Checks for the expiry of any reservations past the time limit
	 */
	private void checkReservations() {
		// TODO - implement RestaurantMgr.checkReservations
		//throw new UnsupportedOperationException();
		if(reservations.isEmpty())
			return;
		
		Calendar currentInstant = GregorianCalendar.getInstance();
		Date currentDateTime = currentInstant.getTime();
		currentInstant.setTime(currentDateTime);

		Iterator<reservation> resIter = reservations.iterator();
		reservation reservation = null;
		
		while(resIter.hasNext()) {
			
			reservation = resIter.next();
			
			Calendar restStartDateTime = reservation.getstartDateTime();
			Calendar restClone = (Calendar) restStartDateTime.clone();
			restClone.add(Calendar.MINUTE, 5);

			// If currentInstant is after restStartDateTime + 5 mins
			if(restClone.before(currentInstant))
			{
				table table = getTableByNumber(reservation.getreservedTableNo());
				
				if( (!table.isOccupied()) && table.isReserved() && (table.getCustomerID() == reservation.getCustomerID()) )
				{
					table.unreserveTable();
				}
				
				resIter.remove();
			}
			else
			{
				restStartDateTime = reservation.getstartDateTime();
				if(restStartDateTime.before(currentInstant))
				{
					table table = getTableByNumber(reservation.getreservedTableNo());
					
					if(!table.isReserved())
						table.reserveTable(reservation.getCustomerID());
					
					if(table.isOccupied()) {
						resIter.remove();
					}
				}
			}
		}
	}

	
	/**
	 * Gets hold of a particular table by its Table Number 
	 * @param tableNo The table number
	 * @return The table
	 */
	public table getTableByNumber(int tableNo) {
		// TODO - implement RestaurantMgr.getTableByNumber
		//throw new UnsupportedOperationException();
		for(table table :tables)
		{
			if(table.getTableNo() == tableNo)
				return table;
		}
		
		return null;
	}

	
	/**
	 * Gets hold of a table reserved by a particular customer
	 * @param customerID The customer who made the reservation
	 * @return The table
	 */
	public table getReservedTable(int customerID) {
		// TODO - implement RestaurantMgr.getReservedTable
		//throw new UnsupportedOperationException();
checkReservations();
		
		for(table table : tables)
		{
			if(table.isReserved())
			{
				if(table.getCustomerID() == customerID)
				{
					table.assignTable(customerID);
					return table;
				}
			}
		}
		
		return null;
	
	}

	
	/**
	 * Gets the number of people for whom the reservation was made
	 * @param customerID The customer ID who made the reservation
	 * @return Integer value denoting number of people
	 */
	public int getPaxFromReservation(int customerID) {
		// TODO - implement RestaurantMgr.getPaxFromReservation
		//throw new UnsupportedOperationException();
		if(reservations.isEmpty())
			return 0;
		
		for(reservation reservation : reservations)
		{
			if(reservation.getCustomerID() == customerID)
			{
				return reservation.getPax();
			}
		}
		
		return 0;
	}

	
	/**
	 * Gets an available table, for walk-ins
	 * @param customerID Customer ID of the customer
	 * @param pax The number of people for whom table needs to be arranged.
	 * @return Table (if any).
	 */
	public table getAvailableTable(int customerID, int pax) {
		// TODO - implement RestaurantMgr.getAvailableTable
		//throw new UnsupportedOperationException();
		checkReservations();
		
		for(table table : tables)
		{
			if(!table.isOccupied())
			{
				if(!table.isReserved())
				{
					if(table.getCapacity() >= pax)
					{
						table.assignTable(customerID);
						return table;
					}
				}
			}
		}
		
		System.out.println("\nNo available tables!");
		return null;
	}
}
