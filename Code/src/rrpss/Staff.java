package rrpss;

/**
 * This class refers to those who work in the restaurant - the staff/employees.
 * Staff is a person, hence it extends the person class.
 * 
 * @author Aryan Madan
 */
public class Staff extends person {

	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = 1833924628828676797L;

	/**
	 * An enumeration type to refer to the various job titles
	 *
	 */
	public static enum JobTitle
	{
		Waiter("Waiter"),
		Cashier("Cashier"),
		Chef("Chef");
		
		private final String value;
		  
		private JobTitle(String value) {
			this.value = value;
		}
		
		public String toStrValue() {
			return value;
		}
	}

	/**
	 * Unique identifier for each employee.
	 */
	private int staffID;
	
	/**
	 * Refers to the job title of the employee.
	 */
	private JobTitle job;
	
	/**
	 * Refers to the salary of the employee.
	 */
	private double salary;

	/**
	 * Public Constructor to create a new employee with the given information
	 * 
	 * @param name Name of the employee
	 * @param age Age of the employee
	 * @param gender Gender of the employee
	 * @param staffID Staff ID of the employee
	 * @param job Job Title of the employee
	 * @param salary Salary of the employee
	 */
	public Staff(String name, int age, Gender gender, int staffID, JobTitle job, double salary) {
		super(name,age,gender);
		this.staffID = staffID;
		this.job = job;
		this.salary = salary;
		//throw new UnsupportedOperationException();
	}

	/**
	 * Gets the StaffID of the employee
	 */
	public int getStaffID() {
		return this.staffID;
	}

	/**
	 * Sets the StaffID of the employee
	 * 
	 * @param staffID The new staff ID of the employee
	 */
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	/**
	 * Gets the job title of the employee
	 */
	public JobTitle getJobTitle() {
		return this.job;
	}

	/**
	 * Sets the job title of the employee
	 * @param job The new job title of the employee
	 */
	public void setJobTitle(JobTitle job) {
		this.job = job;
	}

	/**
	 * Gets the salary of the employee
	 */
	public double getSalary() {
		return this.salary;
	}

	/**
	 * Sets the salary of the employee
	 * @param newSalary The new salary of the employee
	 */
	public void setSalary(double newSalary) {
		this.salary = newSalary;
	}

	/**
	 * Provides a summary of a particular staff.
	 */
	public void showStaffSummary() {
		System.out.printf("%-30s", "Name: " + getName());
		System.out.printf("%-30s", "Staff ID: " + getStaffID());
		System.out.printf("%-30s", "Job Title: " + getJobTitle().toStrValue());
		System.out.printf("%-20s%n", "Salary: " + getSalary());
		//throw new UnsupportedOperationException();
	}

}