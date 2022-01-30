package rrpss;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * The manager class for controlling the Staff and Customer data
 * 
 * @author Aryan Madan
 *
 */
public class StaffMgr {

	/**
	 * An enumeration type used to determine the sub-menu options
	 *
	 */
	private enum StaffSubMenu {
		ViewAllStaff,
		CreateStaff,
		UpdateStaff,
		RemoveStaff,
		ViewAllCustomer,
		CreateCustomer,
		UpdateCustomer,
		RemoveCustomer
	}

	/**
	 * A static list of all employees contained in the database
	 */
	private ArrayList<Staff> staff;
	
	/**
	 * A static list of all customers contained in the database
	 */
	private ArrayList<Customer> customer;
	
	/**
	 * Keeps track of the current staff ID
	 */
	private int cur_staff;
	
	/**
	 * Keeps track of the current customer ID
	 */
	private int cur_customer;
	
	/**
	 * Standard Java Scanner
	 */
	Scanner sc = new Scanner(System.in);

	/**
	 * A static instance of this staff manager
	 */
	private static StaffMgr mngr = null;
	
	/**
	 * The file path for the binary file used to store the HR information
	 */
	private static final String Staff_filePath = "hrdata.dat";

	/**
	 * Private constructor used to create a new arraylist of Staff and Customers
	 */
	private StaffMgr() {
		staff = new ArrayList<Staff>();
		customer = new ArrayList<Customer>();
		//throw new UnsupportedOperationException();
	}

	/**
	 * Public static function used to get the staff manager
	 * 
	 * @return The static instance of the staff manager
	 */
	public static StaffMgr getStaffMgr() {
		if(mngr == null)
		{
			mngr = new StaffMgr();
		}
		return mngr;
		//throw new UnsupportedOperationException();
	}

	/**
	 * Loads the Staff and Customer information from the file
	 */
	public void loadPeople() {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try
		{
			fis = new FileInputStream(Staff_filePath);
			in = new ObjectInputStream(fis);
			
			Object obj = in.readObject();

			if (obj instanceof ArrayList<?>) {
				ArrayList<?> al = (ArrayList<?>) obj;

				if (al.size() > 0) {
					for (int objIndex = 0; objIndex < al.size(); objIndex++) {
						Object childObj = al.get(objIndex);

						if (childObj instanceof Staff) {
							staff.add(((Staff) childObj));
						}
					}
				}
			}
			
			obj = in.readObject();

			if (obj instanceof ArrayList<?>) {
				ArrayList<?> al = (ArrayList<?>) obj;

				if (al.size() > 0) {
					for (int objIndex = 0; objIndex < al.size(); objIndex++) {
						Object childObj = al.get(objIndex);

						if (childObj instanceof Customer) {
							customer.add(((Customer) childObj));
						}
					}
				}
			}

			// To increment the IDs
			cur_staff = in.readInt();
			cur_customer = in.readInt();

			in.close();
			
			if(!staff.isEmpty())
				System.out.println("Staff data loaded!");
			
			if(!customer.isEmpty())
				System.out.println("Customer data loaded!");
			
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to load Staff and Customer data!");
		} catch (IOException ex) {
			System.out.println("Unable to load Staff and Customer data!");
		} catch (ClassNotFoundException ex) {
			System.out.println("Unable to load Staff and Customer data!");
		} catch (Exception ex) {
			System.out.println("Unable to load Staff and Customer data!");
		}
		//throw new UnsupportedOperationException();
	}

	/**
	 * Saves the Staff and Customer information to the file
	*/	
	public void storePeople() {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			fos = new FileOutputStream(Staff_filePath);
			out = new ObjectOutputStream(fos);
			
			out.writeObject(staff);
			out.writeObject(customer);
			
			out.writeInt(cur_staff);
			out.writeInt(cur_customer);
			
			out.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to save data!");
		} catch (IOException ex) {
			System.out.println("Unable to save data!");
		} catch (Exception ex) {
			System.out.println("Unable to save data!");
		}

		//System.out.println("Saved data successfully!");
		//throw new UnsupportedOperationException();
	}

	/**
	 * Displays the list of options for the Staff and Customer Management submenu
	 */
	private void displayStaffOptions() {
		/*
		System.out.print("\n"+new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("STAFF MANAGER ");
		System.out.print(new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("|0. Return to main menu                  ");
		System.out.println("|1. View all staff                       ");
		System.out.println("|2. Add a new staff                     ");
		System.out.println("|3. Update an existing staff             ");
		System.out.println("|4. Remove an existing staff             ");
		System.out.println("|5. View all customers                   ");
		System.out.println("|6. Add a new customer                   ");
		System.out.println("|7. Update an existing customer          ");
		System.out.println("|8. Remove an existing customer          ");
		System.out.print(new String(new char[87]).replace("\0", "="));
		*/
		System.out.print("\n"
				+ "Human Resource Department:\n"
				+ "0) Return to main menu\n"
				+ "\nSTAFF:\n"
                + "1) View all staff\n"
                + "2) Add a new staff\n"
                + "3) Update an existing staff\n"
                + "4) Remove an existing staff\n"
                + "\nCUSTOMERS:\n"
                + "5) View all customers\n"
                + "6) Add a new customer\n"
                + "7) Update an existing customer\n"
                + "8) Remove an existing customer\n");	
		
		//throw new UnsupportedOperationException();
	}

	/**
	 * Prompts the user to choose a menu function
	 * 
	 * @return The integer value representing the choice of the submenu to be expanded
	 */
	public int getStaffMgrChoice() {
		displayStaffOptions();
		int choice = -1;
		do
		{
			try
			{
				System.out.print("\nPlease enter your choice (0-8): ");
				choice = sc.nextInt();
				sc.nextLine();
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Invalid input! Please enter between 0-8");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			catch(Exception ex)
			{
				System.out.println("Invalid input! Please enter between 0-8");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			
			if(choice < 0 || choice > 8)
				System.out.println("Invalid input! Please enter between 0-8");
			
		} while (choice < 0 || choice > 8);
		
		if(choice == 0)
			return choice; // Go back to main menu
		else
		{
			switch(StaffSubMenu.values()[choice - 1])
			{
			case ViewAllStaff:
				displayStaff();
				break;
			
			case CreateStaff:
				addStaff();
				break;
				
			case UpdateStaff:
				updateStaff();
				break;
				
			case RemoveStaff:
				removeStaff();
				break;
				
			case ViewAllCustomer:
				displayCustomer();
				break;
				
			case CreateCustomer:
				addCustomer();
				break;
				
			case UpdateCustomer:
				updateCustomer();
				break;
				
			case RemoveCustomer:
				removeCustomer();
				break;
			}
		}
		
		return choice;
		//throw new UnsupportedOperationException();
	}

	/**
	 * Displays the complete Staff details, with their personal information
	 */
	private void displayStaff() {
		if(staff.isEmpty())
		{
			System.out.println("Sorry, no staff hired yet!");
			return;
		}

		System.out.println("STAFF");

		// Display staff information
		for(Staff stf : staff)
		{
			stf.showStaffSummary();
			System.out.println();
		}

		System.out.println();
		//throw new UnsupportedOperationException();
	}

	/**
	 * Adds a new employee to the Staff Database
	 *
	 */	
	private void addStaff() {
		Staff newStaff = null;
		
		try
		{
			System.out.print("Enter the name of the staff: ");
			String staffName = sc.nextLine();
			
			System.out.print("Enter the age of the staff: ");
			int staffAge = sc.nextInt();
			
			if(staffAge < 16 && staffAge >0) {
				System.out.println("Minimum employment age not satisfied! Pls try again");
				return;
			}
			
			if(staffAge<0) {
				System.out.println("Invalid input! Pls try again");
				return;
			}
			
			System.out.print("Enter the gender of the staff (M/F/O): ");
			char staffGenderValue = sc.next().charAt(0);
			staffGenderValue = Character.toUpperCase(staffGenderValue);
			
			person.Gender staffGender; 
			if(staffGenderValue == 'M')
			{
				staffGender = person.Gender.Male;
			}
			else if(staffGenderValue == 'F')
			{
				staffGender = person.Gender.Female;
			}
			else if(staffGenderValue == 'O')
			{
				staffGender = person.Gender.Other;
			}
			else
			{
				System.out.println("Invalid Input! Pls try again");
				return;
			}

			int numOfJobs = Staff.JobTitle.values().length;
			int jobIndex = 0;
			for(Staff.JobTitle jobTitle : Staff.JobTitle.values())
			{
				System.out.print(++jobIndex + "  " + jobTitle.toStrValue() + "  ");
			}
			
			System.out.println("\nChoose the job title (1-3): ");
			int jobTitleValue = sc.nextInt();
			sc.nextLine();
			
			// Valid jobTitleValue from 1 to numOfJobs
			if(jobTitleValue < 1 || jobTitleValue > numOfJobs)
			{
				System.out.println("Invalid input! Pls try again");
				return;
			}
			
			Staff.JobTitle staffJobTitle = Staff.JobTitle.values()[jobTitleValue - 1];
			
			System.out.print("Enter the salary of the staff: ");
			double staffSalary = sc.nextDouble();
			sc.nextLine();
			
			if(staffSalary<0) {
				System.out.println("Invalid input! Pls try again");
				return;
			}
			
			newStaff = new Staff(staffName, staffAge, staffGender,++cur_staff, staffJobTitle, staffSalary);
			
			staff.add(newStaff);
			
			if(newStaff != null)
			{
				System.out.println("Successfully hired " + newStaff.getName()  + " as a " + newStaff.getJobTitle().toStrValue());
				System.out.println();
			}
		}

		catch(InputMismatchException ex)
		{

			System.out.println("Failed to add new staff! Pls try again");
			
			sc.nextLine(); // Clear the garbage input
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to add new staff! Pls try again");

			sc.nextLine(); // Clear the garbage input
			return;
		}

		//throw new UnsupportedOperationException();
	}

	/**
	 * Updates an existing employee's information within the Database
	 * 
	 */	
	private void updateStaff() {
		if(staff.isEmpty())
		{
			System.out.println("There is no staff hired at the moment!");
			return;
		}
		
		try
		{
			int numOfStaff = 0;

			System.out.println();
			System.out.printf("%5s%-30s", "", "Staff Name");
			System.out.printf("/t/t%-30s", "Staff Job Title");
			System.out.printf("/t/t%-20s%n", "Staff Salary");

			// Display staff
			for(Staff stf : staff)
			{
				System.out.printf("%-5s", "(" + (++numOfStaff) + ")");
				stf.showStaffSummary();
			}

			System.out.println("Please select a staff to update or 0 to cancel: ");

			int staffIndex = sc.nextInt();
			sc.nextLine();

			// User chooses not to update any staff
			if(staffIndex == 0)
			{
				System.out.println("Nothing to be updated!");
				return;
			}

			// Valid staffIndex from 1 to numOfStaff
			if (staffIndex < 1 || staffIndex > numOfStaff) {
				System.out.println("Failed to update staff! Please try again");
				return;
			}

			Staff updatingStaff = staff.get(staffIndex - 1);
			updateStaffInfo(updatingStaff);
		}

		catch(InputMismatchException ex)
		{
			System.out.println("Failed to update staff! Please try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to update staff! Please try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		//throw new UnsupportedOperationException();
	}

	/**
	 * Removes an existing employee from the Database 
	 */
	private void removeStaff() {
		if(staff.isEmpty())
		{
			System.out.println(" There is no staff hired!");
			return;
		}

		try
		{
			int numOfStaff = 0;
			String firedStaffName = null;

			System.out.println();
			System.out.printf("%5s%-30s", "", "Staff Name");
			System.out.printf("%-30s", "Staff Job Title");
			System.out.printf("%-20s%n", "Staff Salary");

			// Display staff
			for(Staff staff : staff)
			{
				System.out.printf("%-5s", "(" + (++numOfStaff) + ")");
				staff.showStaffSummary();
			}

			System.out.printf("%nPlease select a staff to fire (0 to cancel): ");

			int staffIndex = sc.nextInt();
			sc.nextLine();

			if(staffIndex == 0)
			{
				System.out.println("No staff has been fired!");
				return;
			}

			if (staffIndex < 1 || staffIndex > numOfStaff) {
				System.out.println("Ivalid Input! Please try again");
				return;
			}

			firedStaffName = staff.get(staffIndex - 1).getName();
			staff.remove(staffIndex - 1);

			if(firedStaffName != null)
			{
				System.out.println("\nSuccessfully fired " + firedStaffName);
				System.out.println();
			}
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Failed to fire staff! Try again");

			sc.nextLine(); // Clear the garbage input
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Failed to fire staff! Try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		//throw new UnsupportedOperationException();
	}

	/**
	 * Displays the complete employee details, with their personal information and membership status
	 */
	private void displayCustomer() {
		if(customer.isEmpty())
		{
			System.out.println(" No Customers yet!");
			System.out.println();
			return;
		}

		System.out.println("CUSTOMER");
		for(Customer customer : customer)
		{
			System.out.println();
			customer.showCustomerSummary();
		}

		System.out.println();
		//throw new UnsupportedOperationException();
	}

	/**
	 * Adds a new customer to the Database
	 *
	 */	
	private void addCustomer() {
		Customer newCustomer = null;
		
		try
		{
			System.out.print("\nEnter the name of the customer: ");
			String customerName = sc.nextLine();
			
			System.out.print("Enter the age of the customer: ");
			int customerAge = sc.nextInt();
			
			System.out.print("Enter the gender of the customer (M/F/O): ");
			char customerGenderValue = sc.next().charAt(0);
			customerGenderValue = Character.toUpperCase(customerGenderValue);
			
			person.Gender customerGender; 
			if(customerGenderValue == 'M')
			{
				customerGender = person.Gender.Male;
			}
			else if(customerGenderValue == 'F')
			{
				customerGender = person.Gender.Female;
			}
			else if(customerGenderValue == 'O')
			{
				customerGender = person.Gender.Other;
			}
			else
			{
				System.out.println("Invalid Input! Pls try again");
				return;
			}
			
			System.out.print("Enter the contact number of the customer: ");
			long customerContact = sc.nextLong();
			sc.nextLine();
			
			System.out.print("Is the customer a member? (Y/N): ");
			char customerMembershipValue = sc.next().charAt(0);
			customerMembershipValue = Character.toUpperCase(customerMembershipValue);
			
			boolean isMember = false;
			if(customerMembershipValue == 'Y')
			{
				isMember = true;
			}
			else if(customerMembershipValue == 'N')
			{
				isMember = false;
			}
			else
			{
				System.out.println("Invalid input! Failed to add new customer");
				return;
			}
			
			newCustomer = new Customer(customerName, customerAge, customerGender,++cur_customer, customerContact, isMember);
			
			customer.add(newCustomer);
			
			if(newCustomer != null)
			{
				System.out.println("Successfully registered " + newCustomer.getName());
				System.out.println();
			}
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid Input! Pls try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Invalid Input! Pls try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		//throw new UnsupportedOperationException();
	}

	/**
	 * Updates an existing customer's information within the Database
	 * 
	 */	
	private void updateCustomer() {
		if(customer.isEmpty())
		{
			System.out.println("There are no customers!");
			return;
		}

		try
		{
			int numOfCustomer = 0;
			System.out.println();
			System.out.printf("%5s%-30s", "","Customer Name");
			System.out.printf("%-30s", "Contact Number");
			System.out.printf("%-20s%n", "Existing Member");

			for(Customer customer : customer)
			{
				System.out.printf("%-5s", "(" + (++numOfCustomer) + ")");
				customer.showCustomerSummary();
			}

			System.out.printf("%nPlease select a customer to update (0 to cancel): ");

			int customerIndex = sc.nextInt();
			sc.nextLine();

			if(customerIndex == 0)
			{
				System.out.println("\nNothing to be updated!");
				return;
			}

			if (customerIndex < 1 || customerIndex > numOfCustomer) {
				System.out.println("Invalid Input! Pls try again");
				return;
			}

			Customer updatingCustomer = customer.get(customerIndex - 1);
			updateCustomerInfo(updatingCustomer);
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid Input! Pls try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Invalid Input! Pls try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		//throw new UnsupportedOperationException();
	}

	/**
	 * Removes an existing customer from the Database 
	 */
	private void removeCustomer() {
		if(customer.isEmpty())
		{
			System.out.println(" There are no customers");
			return;
		}

		try
		{
			int numOfCustomer = 0;
			String removedCustName = null;

			System.out.println();
			System.out.printf("%5s%-30s", "", "Customer Name");
			System.out.printf("%-30s", "Customer Contact Number");
			System.out.printf("%-20s%n", "Member");

			for(Customer customer : customer)
			{
				System.out.printf("%-5s", "(" + (++numOfCustomer) + ")");
				customer.showCustomerSummary();
			}

			System.out.printf("%nPlease select a customer to remove (0 to cancel): ");

			int customerIndex = sc.nextInt();
			sc.nextLine();

			if(customerIndex == 0)
			{
				System.out.println("\nNo customer removed!");
				return;
			}

			if (customerIndex < 1 || customerIndex > numOfCustomer) {
				System.out.println("Invalid Input! Pls try again");
				return;
			}

			removedCustName = customer.get(customerIndex - 1).getName();

			customer.remove(customerIndex - 1);

			if(removedCustName != null)
			{
				System.out.println("\nSuccessfully removed " + removedCustName);
				System.out.println();
			}
		}
		catch(InputMismatchException ex)
		{
			System.out.println("Invalid Input! Pls try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		catch(Exception ex)
		{
			System.out.println("Invalid Input! Pls try again");
			sc.nextLine(); // Clear the garbage input
			return;
		}
		//throw new UnsupportedOperationException();
	}
	
	/**
	 * Updates the information of a particular employee
	 * 
	 * @param updatingStaff The employee to be updated
	 *
	 * @throws InputMismatchException Exception thrown for input mismatch
	 * @throws Exception General exceptions
	 */
	public void updateStaffInfo(Staff updatingStaff) 
	    throws InputMismatchException, Exception
	{
		int updateOption = -1;
		do
		{
			System.out.println();
			System.out.println("(1) Update staff name, age & gender");
			System.out.println("(2) Update job title & salary");
			
			System.out.printf("%nPlease select the action to be"
					+ " performed (0 to cancel): ");
		
			updateOption = sc.nextInt();
			sc.nextLine();
			
			// User chooses not to update any staff
			if(updateOption == 0)
			{
				System.out.println("\nNothing to be updated!");
				return;
			}
			
			// Valid values from 1 to 2
			if (updateOption < 1 || updateOption > 2) {
				System.out.println("Invalid Input! Pls try again");
			}
			
			if(updateOption == 1)
			{
				System.out.printf("%n%-40s", "Current Name: \"" + updatingStaff.getName() + "\"");
				System.out.print("\tEnter a new name: ");
				String newStaffName = sc.nextLine();
				
				System.out.printf("%-40s", "Current age: \"" + updatingStaff.getAge() + "\"");
				System.out.print("\tEnter a new age: ");
				int newStaffAge = sc.nextInt();
				
				System.out.printf("%-40s", "Current gender: \"" + updatingStaff.getGender().toStrValue() + "\"");

				System.out.print("\tEnter the gender of the staff" + " (M/F/O): ");
				char staffGenderValue = sc.next().charAt(0);
				staffGenderValue = Character.toUpperCase(staffGenderValue);
				
				person.Gender staffGender; 
				if(staffGenderValue == 'M')
				{
					staffGender = person.Gender.Male;
				}
				else if(staffGenderValue == 'F')
				{
					staffGender = person.Gender.Female;
				}
				else if(staffGenderValue == 'O')
				{
					staffGender = person.Gender.Other;
				}
				else
				{
					System.out.println("Invalid Input! Pls try again");
					continue;
				}
				
				updatingStaff.setName(newStaffName);
				updatingStaff.setAge(newStaffAge);
				updatingStaff.setGender(staffGender);
				
				System.out.printf("%nSuccessfully updated information for " + updatingStaff.getName());
			}
			else if(updateOption == 2)
			{
				System.out.printf("%n%-40s%n", "Current Job Title: \"" + updatingStaff.getJobTitle().toStrValue() + "\"");
				
				int numOfJobs = Staff.JobTitle.values().length;
				int jobIndex = 0;
				for(Staff.JobTitle jobTitle : Staff.JobTitle.values())
				{
					System.out.printf("%n%-5s", "(" + (++jobIndex) + ")");
					System.out.print(jobTitle.toStrValue());
				}
				
				System.out.printf("%n%nChoose the new job title (1-%d): ", numOfJobs);
				int jobTitleValue = sc.nextInt();
				sc.nextLine();
				
				if(jobTitleValue < 1 || jobTitleValue > numOfJobs)
				{
					System.out.println("Invalid Input! Pls try again");
					return;
				}
				
				Staff.JobTitle newStaffJobTitle = Staff.JobTitle.values()[jobTitleValue - 1];
				
				System.out.printf("%-40s", "Current salary: " + updatingStaff.getSalary());
				
				System.out.print("\tEnter the new salary of the staff: ");
				double newStaffSalary = sc.nextDouble();
				sc.nextLine();
				
				updatingStaff.setJobTitle(newStaffJobTitle);
				updatingStaff.setSalary(newStaffSalary);
				
				System.out.printf("%nSuccessfully updated job title & salary for" + updatingStaff.getName());
			}
			
		} while(updateOption != 0);
	}

	/**
	 * Updates the information of a particular customer
	 * 
	 * @param updatingCustomer The customer to be updated
	 *
	 * @throws InputMismatchException Exception thrown for input mismatch
	 * @throws Exception General exceptions
	 */
	public void updateCustomerInfo(Customer updatingCustomer)
	    throws InputMismatchException, Exception 
	{
		int updateOption = -1;
		do
		{
			System.out.println();
			System.out.println("(1) Update customer name, age & gender");
			System.out.println("(2) Update customer contact number & membership status");
			
			System.out.printf("%nPlease select the action to be performed (0 to cancel): ");
		
			updateOption = sc.nextInt();
			sc.nextLine();
			
			// User chooses not to update any customer
			if(updateOption == 0)
			{
				System.out.println("\nNothing to be updated!");
				return;
			}
			
			// Valid values from 1 to 2
			if (updateOption < 1 || updateOption > 2) {
				System.out.print("\nInvalid input! ");
				System.out.println("Failed to update customer please try again");
			}
			
			if(updateOption == 1)
			{
				System.out.printf("%n%-40s", "Current Name: \"" + 
						updatingCustomer.getName() + "\"");
				System.out.print("\tEnter a new name: ");
				String newCustomerName = sc.nextLine();
				
				System.out.printf("%-40s", "Current age: \"" + 
						updatingCustomer.getAge() + "\"");
				System.out.print("\tEnter a new age: ");
				int newCustomerAge = sc.nextInt();
				
				System.out.printf("%-40s", "Current gender: \"" + 
						updatingCustomer.getGender().toStrValue() + "\"");

				System.out.print("\tEnter the gender of the customer (M/F): ");
				char custGenderValue = sc.next().charAt(0);
				custGenderValue = Character.toUpperCase(custGenderValue);
				
				person.Gender custGender; 
				if(custGenderValue == 'M')
				{
					custGender = person.Gender.Male;
				}
				else if(custGenderValue == 'F')
				{
					custGender = person.Gender.Female;
				}
				else
				{
					System.out.println("Invalid Input! Pls try again");
					continue;
				}
				
				updatingCustomer.setName(newCustomerName);
				updatingCustomer.setAge(newCustomerAge);
				updatingCustomer.setGender(custGender);
				
				System.out.printf("%nSuccessfully updated information for " + updatingCustomer.getName());
			}
			else if(updateOption == 2)
			{
				System.out.printf("%-40s", "Current contact number: \"" + updatingCustomer.getPhone() + "\"");
				System.out.print("\tEnter a new contact number: ");
				int newCustomerContact = sc.nextInt();
				
				System.out.printf("%-40s", "Current membership status: \"" + (updatingCustomer.checkMember() ? "Existing Member" : "Not a member")  + "\"");
				
				System.out.print("\tIs this customer a member? (Y/N): ");
				char customerMembershipValue = sc.next().charAt(0);
				customerMembershipValue = Character.toUpperCase(customerMembershipValue);
				
				boolean isMember = false;
				if(customerMembershipValue == 'Y')
				{
					isMember = true;
				}
				else if(customerMembershipValue == 'N')
				{
					isMember = false;
				}
				else
				{
					System.out.println("Invalid Input! Pls try again");
					return;
				}
				
				updatingCustomer.setPhone(newCustomerContact);
				updatingCustomer.setMember(isMember);
				
				System.out.printf("%nSuccessfully updated contact number" + " & membership status" + " for \"%s\"!%n", updatingCustomer.getName());
			}
			
		} while(updateOption != 0);
	}

	/**
	 * Selects a particular customer from the list of all customers
	 * 
	 * @return The selected customer (if any)
	 */
	public Customer selectCustomer() {
		if(customer.isEmpty())
		{
			System.out.print("\nThere are no customers registered in the system!");
			return null;
		}

		int customerIndex = 0;
		int maxCustomerIndex = customer.size();

		do
		{
			try
			{
				int currIndex = 0;

				System.out.println();
				System.out.printf("%5s%-30s", "", "Customer Name");
				System.out.printf("%-30s", "Customer Contact Number");
				System.out.printf("%-20s%n", "Member");

				// Display customer
				for(Customer customer : customer)
				{
					System.out.printf("%-5s", "(" + (++currIndex) + ")");
					customer.showCustomerSummary();
				}
				//System.out.printf("%nTo add a new customer, please go to the Human Resource Department first!%n");
				System.out.printf("%nPlease select a customer(-1 to add a new customer) : ");

				customerIndex = sc.nextInt();
				sc.nextLine();

				// Valid customerIndex from 1 to maxCustomerIndex
				if (!(customerIndex >= 1) || !(customerIndex < maxCustomerIndex))
				{
					if(customerIndex!=-1) {
						System.out.println("Invalid Input! Pls try again");
						break;
					}
				}
				
				if(customerIndex==-1) {
					addCustomer();
					break;
				}
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Invalid Input! Pls try again");
				sc.nextLine(); // Clear the garbage input
			}
			catch(Exception ex)
			{
				System.out.println("Invalid Input! Pls try again");
				sc.nextLine(); // Clear the garbage input
			}

		} while( customerIndex > maxCustomerIndex);

		return customer.get(customerIndex - 1);
		//throw new UnsupportedOperationException();
	}

	/**
	 * Checks the membership status of the customer
	 * 
	 * @param customerID The customer ID of the customer 
	 * 
	 * @return True if the provided customer ID is a member
	 */
	public boolean isMember(int customerID) {
		if(customer.isEmpty())
			return false;
		
		for(Customer customer : customer)
		{
			if(customer.getCustomerID() == customerID)
				return customer.checkMember();
		}
		
		return false;
		//throw new UnsupportedOperationException();
	}

	/**
	 * Selects a waiter from the list of all waiters
	 * 
	 * @return The selected waiter (if any)
	 */
	public Staff selectWaiter() {
		if(staff.isEmpty())
			return null;
		
		List<Staff> waiters = new ArrayList<Staff>();
		
		for(Staff staff : staff)
		{
			if(staff.getJobTitle() == Staff.JobTitle.Waiter)
				waiters.add(staff);
		}
		
		if(waiters.isEmpty())
			return null;
		
		int waiterIndex = 0;
		int maxWaiterIndex = waiters.size();

		do
		{
			try
			{
				int currIndex = 0;

				System.out.println();
				System.out.printf("%5s%-15s", "", "Staff ID");
				System.out.printf("%-30s%n", "Waiter Name");

				// Display waiters
				for(Staff waiter : waiters)
				{
					System.out.printf("%-5s", "(" + (++currIndex) + ")");
					System.out.printf("%-15s", waiter.getStaffID());
					System.out.printf("%-30s%n", waiter.getName());
				}

				System.out.printf("%nPlease select a waiter to take the order: ");

				waiterIndex = sc.nextInt();
				sc.nextLine();

				// Valid waiterIndex from 1 to maxWaiterIndex
				if (waiterIndex < 1 || waiterIndex > maxWaiterIndex)
				{
					System.out.println("Invalid Input! Pls try again");
					continue;
				}
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Invalid Input! Pls try again");
				sc.nextLine(); // Clear the garbage input
			}
			catch(Exception ex)
			{
				System.out.println("Invalid Input! Pls try again");
				sc.nextLine(); // Clear the garbage input
			}

		} while(waiterIndex < 1 || waiterIndex > maxWaiterIndex);

		return waiters.get(waiterIndex - 1);
		//throw new UnsupportedOperationException();
	}
}