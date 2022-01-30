package rrpss;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main application for the restaurant reservation and point of sales system
 * 
 * @author Siddhant Pathak
 *
 */
public class MainApplication
{
	/**
	 * An enumeration used to identify different application states
	 *
	 */
	public enum ApplicationState
	{
		MainMenu,
		Menu,
		Order,
		RestaurantManagement,
		HRManagement,
		SaleRevenueReport,
		ExitApplication
	}
	
	/**
	 * A private static application state instance indicating the current application state
	 */
	private static ApplicationState currAppState = ApplicationState.MainMenu;
	
	/**
	 * Standard Java Scanner object
	 */
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Main function for the application
	 * 
	 * @param args Additional arguments to be passed in
	 */
	public static void main(String[] args)
	{
		System.out.println("Initalizing system..");
		System.out.println("Loading necessary data files....\n");
		
		doSystemInitialization();
		
		System.out.println("\nSystem initialization completed!");
		
		System.out.println("\nWelcome to S.N.A.K.");
		System.out.println("S.N.A.K. is a Restaurant Reservation & Point of Sales Application");
		System.out.println("Created by Siddhant Pathak, Nipun Bhatia, Aryan Madan and Kanupriya Arora");
		do
		{
			switch(currAppState)
			{
			case MainMenu:
				displayMainMenu();	
				currAppState = ApplicationState.values()[getMainMenuChoice()];
				break;
				
			case Menu:
				if(menuMgr.getmenuMgr().getMenuChoice() == 0)
				{
					// Return to main menu
					currAppState = ApplicationState.values()[0];
				}
				break;
				
			case Order:
				if(OrderMgr.getOrderMgr().getOrderChoice() == 0)
				{
					// Return to main menu
					currAppState = ApplicationState.values()[0];
				}
				break;
						
			case RestaurantManagement:
				if(restaurantMgr.getrestaurantMgr().getRestMgrChoice() == 0)
				{
					// Return to main menu
					currAppState = ApplicationState.values()[0];
				}
				break;
				
			case HRManagement:
				if(StaffMgr.getStaffMgr().getStaffMgrChoice() == 0)
				{
					// Return to main menu
					currAppState = ApplicationState.values()[0];
				}
				break;
			
			case SaleRevenueReport:
				OrderMgr.getOrderMgr().displaySaleRevenue();
				
				// Return to main menu
				currAppState = ApplicationState.values()[0];
				break;
				
			case ExitApplication:
				currAppState = ApplicationState.ExitApplication;
				break;
			}
			
		} while (currAppState != ApplicationState.ExitApplication);
		
		saveSystemState();
		
		System.out.println("\nThank you for using the application.");
	}

	/**
	 * Displays the list of available submenus. List of submenus include "Menu", "Order", 
	 * "Restaurant Management", "Staff and Customer Management Management" and "Sales Revenue Report"
	 */
	public static void displayMainMenu()
	{
		/*System.out.print(new String(new char[50]).replace("\0", "="));
		System.out.println();
		System.out.println("MAIN MENU");
		System.out.print(new String(new char[50]).replace("\0", "="));
		System.out.println();
		*/
		System.out.print("\n"
				+ "MAIN MENU:\n"
				+ "1) Menu\n"
                + "2) Order\n"
                + "3) Reservations\n"
                + "4) Human Resource Department\n"
                + "5) Sales Revenue Report\n"
                + "6) Exit the Application\n");		
		//System.out.print(new String(new char[87]).replace("\0", "="));
	}
	
	/**
	 * Process the user's input to determine the application state
	 * 
	 * @return The integer value representing a valid application state
	 */
	public static int getMainMenuChoice()
	{
		int maxChoices = ApplicationState.values().length - 1;
		int mainMenuChoice = 0;
		
		do
		{
			try
			{
				System.out.printf("%nPlease enter your choice (1-%d): ", maxChoices);
				mainMenuChoice = sc.nextInt();
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
			
			// Valid mainMenuChoice from 1 to maxChoices
			if(mainMenuChoice < 1 || mainMenuChoice > maxChoices)
				System.out.println("Invalid choice! Please try again..");
			
		} while (mainMenuChoice < 1 || mainMenuChoice > maxChoices);
		
		return mainMenuChoice;
	}
	
	/**
	 * Loads all the required data files (if any)
	 */
	private static void doSystemInitialization()
	{
		menuMgr.getmenuMgr().loadMenu();
		StaffMgr.getStaffMgr().loadPeople();
		restaurantMgr.getrestaurantMgr().loadRestaurant();
		OrderMgr.getOrderMgr().loadOrders();
	}
	
	/**
	 * Save all the necessary data files. User is given the option to save/not save the current session
	 */
	private static void saveSystemState()
	{
		int saveDataChoice = 0;
		do
		{
			try
			{
				System.out.println("\nWould you like to save the changes made during the current session?");
				System.out.print("\nPlease enter your choice [(1) Yes (2) No ]: ");
				
				saveDataChoice = sc.nextInt();
				sc.nextLine();
			}
			catch(InputMismatchException ex1)
			{
				System.out.println("Invalid input! Please try again..");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			catch(Exception ex2)
			{
				System.out.println("Invalid input! Please try again..");
				sc.nextLine(); // Clear the garbage input
				continue;
			}
			
			if(saveDataChoice < 1 || saveDataChoice > 2)
				System.out.println("Invalid choice! Please try again..");
			
		} while (saveDataChoice < 1 || saveDataChoice > 2);
		
		if(saveDataChoice == 1)
		{
			System.out.println();
			
			menuMgr.getmenuMgr().saveMenu();
			StaffMgr.getStaffMgr().storePeople();
			restaurantMgr.getrestaurantMgr().saveRestaurant();
			OrderMgr.getOrderMgr().saveOrders();
			
			System.out.println("Data saved successfully!");
			System.out.println("You can exit the application now!");
		}
	}
}
