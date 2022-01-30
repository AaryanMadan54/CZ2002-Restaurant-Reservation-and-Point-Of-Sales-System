package rrpss;

/**
 * Class denoting a table in the restaurant.
 * @author Kanupriya Arora
 *
 */
public class table {
		
	/**
	 * Table number of the table
	 */
	private int tableNo;
		
	/**
	 * Capacity of the table
	 */
	private int capacity;
	
	/**
	 * Reservation status of the table	
	 */
	private boolean isReserved;
	
	/**	
	 * Denotes whether the table is currently occupied or not
	 */
	private boolean isOccupied;
	
	/**
	 * The customer ID of the customer who made the reservation
	 */
	private int customerID;

	/**
	 * Public Constructor to create a new table
	 * @param tableNo The table number
	 * @param capacity Capacity of the table
	 * @param isReserved Reservation Status of the table
	 * @param isOccupied Whether the table is currently occupied or not
	 * @param customerID Customer ID of the customer who made the reservation
	 */
	public table(int tableNo, int capacity, boolean isReserved, boolean isOccupied, int customerID) {
			// TODO - implement table.table
			//throw new UnsupportedOperationException();
			this.tableNo= tableNo;
			this.capacity=capacity;
			this.isReserved=isReserved;
			this.isOccupied=isOccupied;
			this.customerID=customerID;
		}

	/**
	 * Gets the table number of the table allocated
	 * @return The table number
	 */
	public int getTableNo() {
			return this.tableNo;
		}

	/**
	 * Sets the table number of the table to be allocated
	 * @param tableNo The table number
	 */
	public void setTableNo(int tableNo) {
			this.tableNo = tableNo;
		}

	/**
	 * Gets the capacity of the table
	 * @return Integer value denoting the capacity of the table
	 */
	public int getCapacity() {
			return this.capacity;
		}

	/**
	 * Sets the capacity of a table
	 * @param capacity The new capacity of a table
	 */
	public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

	/**
	 * Checks the reservation status of a table
	 * @return True if the table is reserved
	 */
	public boolean isReserved() {
			// TODO - implement table.isReserved
			//throw new UnsupportedOperationException();
			return this.isReserved;
		}

	/**
	 * Checks whether the table is currently occupied or not	
	 * @return True if the table is currently occupied
	 */
	public boolean isOccupied() {
			// TODO - implement table.isOccupied
			//throw new UnsupportedOperationException();
			return this.isOccupied;
		}

	/**
	 * Gets the Customer ID of the customer who made the reservation	
	 * @return Integer value denoting the reservation
	 */
	public int getCustomerID() {
			return this.customerID;
		}
		
	/**
	 * Sets the Customer ID of the customer who is making the reservation/walk-in
	 * @param customerID Customer ID of the customer
	 */
	public void setCustomerID(int customerID) {
			this.customerID = customerID;
		}

	/**
	 * Reserves a table for a customer
	 * @param customerID The customer making the reservation
	 */
	public void reserveTable(int customerID) {
			// TODO - implement table.reserveTable
			//throw new UnsupportedOperationException();
			
			this.isReserved= true;
			this.customerID= customerID;
		}

	/**
	 * Remove the reservation of a table for a customer
	 */
	public void unreserveTable() {
			// TODO - implement table.unreserveTable
			//throw new UnsupportedOperationException();
			this.isReserved= false;
			this.customerID= 0;
		}

	/**
	 * Assigns the table to a customer
	 * @param customerID
	 */
	public void assignTable(int customerID) {
			// TODO - implement table.assignTable
			//throw new UnsupportedOperationException();
			this.isReserved = false;
			this.isOccupied = true;
		    this.customerID = customerID;
		}

	/**
	 * Free a table once the payment has been made and invoice is printed 
	 */
	public void freeTable() {
			// TODO - implement table.freeTable
			//throw new UnsupportedOperationException();
		     this.isReserved = false;
		     this.isOccupied = false;
			 this.customerID = 0;
		}

	/**
	 * Shows the chart of all the tables in the restaurant
	 */
	public void showTableChart() {
			// TODO - implement table.showTableChart
			//throw new UnsupportedOperationException();
			System.out.printf("%-20s", getTableNo());
			System.out.printf("%-20s", getCapacity());
			
			System.out.printf("%-20s", isOccupied() ? "Occupied" : (isReserved() ? "Reserved" : "Available"));
			
			System.out.printf("%-20s%n", (getCustomerID() != 0) ? getCustomerID() : "N/A");	
		}	
	}


