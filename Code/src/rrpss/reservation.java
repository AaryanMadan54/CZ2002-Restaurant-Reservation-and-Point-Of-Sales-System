package rrpss;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.Serializable;

/**
 * Class to represent a reservation in the reservation.
 * 
 * Implements the Serializable interface from java.io.Serializable to save the data.
 * 
 * @author Kanupriya Arora 
 *
 */
public class reservation implements Serializable, Comparable<reservation> {

	/**
	 * Integer denoting the Table number reserved for the customer	
	 */
	private int reservedTableNo;
	
	/**
	 * Integer denoting the Customer ID of the customer who made the reservation
	 */
	private int customerID;
	
	/**
	 * String denoting the name of the Customer who made the reservation
	 */
	private String customerName;
	
	/**
	 * Contact details of the customer who made the reservation
	 */
	private long customerPhone;
	
	/**
	 * Number of people for whom the reservation was made
	 */
	private int pax;
	
	/**
	 * Calendar instance to take note of the starting date and time
	 */
	private Calendar startDateTime;
	
	/**
	 * Duration of the reservation
	 */
	private int duration;
		
	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = -1362303644760942385L; 
		
	/**
	 * Static instance of a date formatter to parse the date and time correctly	
	 */
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("E, dd/MM/yyyy, HH:mm");

	/**
	 * Public Constructor to create a new reservation
	 * @param reservedTableNo The table number alloted to the customer
	 * @param customerID The customer ID of the customer who made the reservation
	 * @param customerName Name of the customer who made the reservation
	 * @param customerPhone Contact details of the customer who made the reservation
	 * @param pax The number of people for whom the reservation was made
	 * @param startDateTime The starting time of the reservation
	 * @param duration Duration for which the reservation was made
	 */
	public reservation(int reservedTableNo, int customerID, String customerName, long customerPhone, int pax, Calendar startDateTime,int duration) {
			// TODO - implement reservation.reservation
			//throw new UnsupportedOperationException();
			this.reservedTableNo= reservedTableNo;
			this.customerID=customerID;
			this.customerName= customerName;
			this.customerPhone= customerPhone;		
			this.pax=pax;
			this.startDateTime=startDateTime;
			this.duration = duration;
		}

	/**
	 * Gets the Table number of the table allocated to the reservation
	 * @return
	 */
	public int getreservedTableNo() {
			return this.reservedTableNo;
		}
	
	/**
	 * Sets the Table number of the table to be allocated to the reservation
	 * @param newTableNo The new Table number of the table
	 */
	public void setTableNo(int newTableNo) {
			this.reservedTableNo = newTableNo;
		}

	/**
	 * Gets the Customer ID of the customer who made the reservation
	 * @return
	 */
	public int getCustomerID() {
			return this.customerID;
		}

	/**
	 * Sets the Customer ID of the customer who is making the reservation
	 * @param newCustomerID
	 */
	public void setCustomerID(int newCustomerID) {
			this.customerID = newCustomerID;
		}

	/**
	 * Gets the name of the customer who made the reservation.
	 * @return String denoting the name of the customer
	 */
	public String getCustomerName() {
			return this.customerName;
		}

	/**
	 * Gets the contact details of the customer who made the reservation
	 * @return The phone number of the customer
	 */
	public long getCustomerPhone() {
			return this.customerPhone;
		}

	/**
	 * Gets the number of people for whom the reservation is made
	 * @return Integer value denoting the number of people
	 */
	public int getPax() {
			return this.pax;
		}

	/**
	 * Sets the number of people for whom the reservation is being made
	 * @param new_pax The number of people
	 */
	public void setPax(int new_pax) {
			this.pax = new_pax;
		}

	/**
	* Gets the start date and time of the reservation
	* @return Calendar instance denoting the date and time of the reservation
	*/
	public Calendar getstartDateTime() {
			return startDateTime;
		}
		
	/**
	* Gets the duration of the reservation
	* @return Integer value denoting the reservation duration
	*/
	public int getDuration() {
			return duration;
			}
		
	/**
	* Sets the duration of the reservation
	* @param newDuration New duration of the reservation
	*/
	public void setDuration(int newDuration) {
			duration = newDuration;
		}

	/**
	* Shows the necessary details of the reservation made
	*/
	public void showReservationSummary() {
			// TODO - implement reservation.showReservationSummary
			//throw new UnsupportedOperationException();
			System.out.printf("%-20s%n", "Table Number: " + getreservedTableNo());
			
			System.out.printf("%5s%-25s", "","Customer ID: " + getCustomerID());
			System.out.printf("%-50s", "Name: " + getCustomerName());
			System.out.printf("%-20s%n", "Contact: " + getCustomerPhone());
			
			System.out.printf("%5s%-25s", "","Number of People: " + getPax());
			System.out.printf("%-50s", "Reservation Date/Time: " + dateFormatter.format(startDateTime.getTime()));
			System.out.printf("%-20s%n", "Duration: " + getDuration() + (getDuration() > 1 ? " Hours" : " Hour"));
		}
		
	public int compareTo(reservation res)
		{
			return this.getstartDateTime().compareTo(res.getstartDateTime());
		}
}
