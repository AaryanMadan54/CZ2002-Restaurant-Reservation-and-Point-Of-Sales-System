package rrpss;

/**
 * This class refers to those who come to the restaurant - the customers.
 * Customer is a person, hence it extends the person class.
 * 
 * @author Aryan Madan
 */
public class Customer extends person {

	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = 6284504246953295864L;
	
	/**
	 * Unique identifier for each customer.
	 */
	private int customerID;
	
	/**
	 * The phone number of each customer
	 */
	private long phone;
	
	/**
	 * Membership status of each customer (Yes/No)
	 */
	private boolean isMember;

	/**
	 * Public Constructor to create a new customer with the given information
	 * @param name Name of the customer
	 * @param age Age of the customer
	 * @param gender Gender of the customer
	 * @param custID Customer ID of the customer
	 * @param phoneno Phone Number of the customer
	 * @param isMember Membership status of the customer
	 */
	public Customer(String name, int age, Gender gender, int custID, long phoneno, boolean isMember) {
		super(name,age,gender);
		customerID = custID;
		phone = phoneno;
		this.isMember = isMember;
		//throw new UnsupportedOperationException();
	}

	/**
	 * Gets the Customer ID of the customer
	 * @return The customer ID of the customer
	 */
	public int getCustomerID() {
		return this.customerID;
	}

	/**
	 * Sets the Customer ID of the customer
	 * @param newCustID The new Customer ID of the employee
	 */
	public void setCustomerID(int newCustID) {
		this.customerID = newCustID;
	}

	/**
	 * Gets the contact details of the customer
	 * @return The phone number of the customer
	 */
	public long getPhone() {
		return this.phone;
	}

	/**
	 * Sets the contact details of the customer
	 * @param newPhone THe new phone number of the customer
	 */
	public void setPhone(long newPhone) {
		this.phone = newPhone;
	}

	/**
	 * Checks for the membership of the customer
	 * @return True if the customer is a member
	 */
	public boolean checkMember() {
		return isMember;
		//throw new UnsupportedOperationException();
	}

	/**
	 * Sets the membership status of the customer
	 * @param newStatus New Membership status (1: Yes/ 2: No)
	 */
	public void setMember(boolean newStatus) {
		isMember = newStatus;
		throw new UnsupportedOperationException();
	}

	/**
	 * Provides a summary of a particular staff.
	 */
	public void showCustomerSummary() {

		System.out.printf("%-30s", "Name: " + getName());
		System.out.printf("%-30s", "Customer ID: " + getCustomerID());
		System.out.printf("%-30s", "Contact/Phone Details: " + getPhone());
		if(checkMember() == true)
			System.out.printf("%-20s", "Membership Status : " + "Member");
		else
			System.out.printf("%-20s", "Membership Status : " + "Not Member");
		//throw new UnsupportedOperationException();		
		System.out.println();
	}
}
