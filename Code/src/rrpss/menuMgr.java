package rrpss;

import java.io.FileInputStream;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * The manager class for controlling the menu data.
 * 
 * @author Siddhant Pathak
 *
 */
public class menuMgr {

	/**
	 * Standard Java scanner
	 */
	private static Scanner sc;
	
	/**
	 * A static list of all menu items contained in the menu
	 */
	private ArrayList<menuitem> menu;
	
	/**
	 * An enumeration type used to determine the sub-menu options
	 *
	 */
	private enum menuSubMenu {
		ViewMenu,
		Createmenuitem,
		Updatemenuitem,
		Removemenuitem,
		CreateSetPackage,
		UpdateSetPackage,
		RemoveSetPackage
	}
	
	/**
	 * A static instance of this menu manager
	 */	
	private static menuMgr MenuMgr = null;
		
	/**
	 * The file path for the binary file used to store the menu information
	 */
	private static final String MENU_FILE_PATH = "menudata.dat";
	
	/**
	 * Private constructor used to create a new Java Scanner object, and creates a new arraylist of menu items
	 */
	private menuMgr() {
		sc = new Scanner(System.in);
		menu = new ArrayList <menuitem>();
	}

	/**
	 * Public static function used to get the menu manager
	 * 
	 * @return The static instance of the menu manager
	 */
	public static menuMgr getmenuMgr() {
		if(MenuMgr==null) MenuMgr = new menuMgr();
		return MenuMgr;
	}

	/**
	 * Loads the menu information from the menu file
	 */
	public void loadMenu() {
		FileInputStream fin = null;
		ObjectInputStream objin = null;
		
		try
		{
			fin = new FileInputStream(MENU_FILE_PATH);
			objin = new ObjectInputStream(fin);
		
			Object ob = objin.readObject();
			
			if(ob instanceof ArrayList<?>)
			{
				ArrayList<?> arr = (ArrayList<?>) ob;
				if(arr.size()>0){
					for(int i=0;i<arr.size(); i++){
						Object child = arr.get(i);
						if(child instanceof menuitem){ menu.add(((menuitem) child)); }
					}
				}
			}
		objin.close();
		
		if(!menu.isEmpty()){ System.out.println("Menu data loaded!");}
		
		} catch (FileNotFoundException ex) { System.out.println("Unable to load Menu data!");
		} catch (IOException ex) { System.out.println("Unable to load Menu data!");
		} catch (ClassNotFoundException ex) { System.out.println("Unable to load Menu data!");
		} catch (Exception ex) { System.out.println("Unable to load Menu data!");
		}
	}

	/**
	 * Saves the menu information to the menu file
	*/	
	public void saveMenu() {
		FileOutputStream fout = null;
		ObjectOutputStream objout = null;
		
		try {
			fout = new FileOutputStream(MENU_FILE_PATH);
			objout = new ObjectOutputStream(fout);
			objout.writeObject(menu);
			objout.close();
		} catch (FileNotFoundException ex) { System.out.println("Unable to save Menu data\n");} 
		catch (IOException ex) { System.out.println("Unable to save Menu data\n");} 
		catch (Exception ex) { System.out.println("Unable to save Menu data \n");}
		//System.out.println("Saved Menu data successfully\n");
	}

	/**
	 * Displays the list of options for the menu submenu
	 */
	private void displayMenuOptions() {
		/*
		System.out.print("\n"+new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("MENU MANAGER ");
		System.out.print(new String(new char[87]).replace("\0", "="));
		System.out.println();
		System.out.println("|0. Return to main menu                  ");
		System.out.println("|1. View menu                            ");
		System.out.println("|2. Create a new menu item               ");
		System.out.println("|3. Update an existing menu item         ");
		System.out.println("|4. Remove an existing menu item         ");
		System.out.println("|5. Create a new set package             ");
		System.out.println("|6. Update an existing set package       ");
		System.out.println("|7. Remove an existing set package       ");
		System.out.print(new String(new char[87]).replace("\0", "="));
		*/

		
		System.out.print("\n"
				+ "Menu:\n"
				+ "0) Return to main menu\n"
                + "1) View menu\n"
                + "2) Create a new menu item\n"
                + "3) Update an existing menu item\n"
                + "4) Remove an existing menu item\n"
                + "5) Create a new set package\n"
                + "6) Update an existing set package\n"
                + "7) Remove an existing set package\n");	
	}

	/**
	 * Prompts the user to choose a menu function
	 * 
	 * @return The integer value representing the choice of the submenu to be expanded
	 */
	public int getMenuChoice() {
		displayMenuOptions();
		int choice=-1;
		while (choice<0||choice>7){
			try
			{
				System.out.print("\nPlease enter your choice (0-7):");
				choice = sc.nextInt();
				sc.nextLine();
			}
			catch (InputMismatchException ex1)
			{
				System.out.println("Invalid input! Please try again");
				sc.nextLine();
				continue;
			}
			catch (Exception ex2)
			{
				System.out.println("Invalid Input! Please try again\n");
				sc.nextLine();
				continue;
			}
			if (choice<0||choice>7)
			{
				System.out.println("Invalid Input! Please try again\n");
				sc.nextLine();
			}
	} 
		
	if(choice==0)
	{
		return choice;
	}
	
	else
	{
		switch(menuSubMenu.values()[choice-1])
		{
		case ViewMenu:
			displayMenu();
			break;
		
		case Createmenuitem:
			addmenuitem();
			break;
		
		case Updatemenuitem:
			updatemenuitem();
			break;
		
		case Removemenuitem:
			removemenuitem();
			break;
		
		case CreateSetPackage:
			addSetPackage();
			break;
		
		case UpdateSetPackage:
			updateSetPackage();
			break;
		
		case RemoveSetPackage:
			removeSetPackage();
			break;
		}
	}
	return choice;
}

	/**
	 * Displays the complete menu, with the information of each menu item
	 */
	private void displayMenu() {
		if(menu.isEmpty())
		{
			System.out.print("The menu is empty right now\n");
		}
		
		else
		{
			System.out.print("\n" + new String(new char[37]).replace("\0", "="));
			System.out.print(" MENU ");
			System.out.print(new String(new char[37]).replace("\0", "="));
			
			menuitem.type currItemType = null;
			int sz = menu.size();
			
			for(int currItem = 0; currItem < sz; currItem++)
			{
				if(menu.get(currItem).getItemType() != currItemType)
				{
					currItemType = menu.get(currItem).getItemType();
					displaymenuitemType(currItemType);
				}
				
				menu.get(currItem).showItem();
				System.out.println();
			}
			
			//System.out.println("-----------------------");
			//System.out.print(" MENU ");
			//System.out.println("-----------------------");
		}		
	}

	/**
	 * Displays the item type (Main Course, Drinks, Desserts) of a menu item
	 * 
	 * @param currItemType The item type to be displayed
	 */
	private void displaymenuitemType(menuitem.type currItemType) {
		String strCurrItemType = currItemType.toStrValue();
		System.out.println();
		System.out.printf("[" + strCurrItemType + "]");
		System.out.println();

	}

	/**
	 * Adds a new menu item to the menu
	 * 
	 */	
	private void addmenuitem() {
		menuitem newitem = null;
		int choicetype = -1;
		
		while (choicetype<1||choicetype>3)
		{
			try
			{
				System.out.println("Enter the item type");
				System.out.println("1. Main Course 2. Drinks 3. Dessert");
				choicetype = sc.nextInt();
				sc.nextLine();
				
				if(choicetype<1||choicetype>3)
				{
					System.out.println("Invalid input! Please try again");
					return;
				}
				
				menuitem.type type_of_item = menuitem.type.values()[choicetype-1];
				
				System.out.println("Enter the name of the item: ");
				String newname = sc.nextLine();
				
				if(isDuplicateItem(newname))
				{
					System.out.println("A menu item with the given name already exists. Please try again");
					return;
				}
				
				System.out.println("Enter the description of the item: ");
				String itemDescription = sc.nextLine();
				
				System.out.println("Enter a price for the item: ");
				double cost = sc.nextDouble();
				sc.nextLine();
				
				if(cost<0)
				{
					System.out.println("Item price must be non-negative. Please try again");
					return;
				}
				
				newitem = new menuitem(type_of_item, newname, itemDescription, cost);
				menu.add(newitem);
				
				Collections.sort(menu);
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Invalid Input! Please try again");
				sc.nextLine();
				continue;
			}
			catch(Exception ex)
			{
				System.out.println("Invalid Input! Please try again");
				continue;
			}	
		}
		
		if(newitem!=null)
		{
			System.out.println("Item added successfully!");
		}
	}

	/**
	 * Updates an existing menu item 
	 * The user can choose from a list of existing menu items, and update the name, description and price of the menu item
	 * 
	 * If the menu item is included in a set package, the set package will be updated automatically
	 */	
	private void updatemenuitem() {
		if(menu.isEmpty()) { System.out.print("The Menu is empty right now!"); }
		else
		{
			int itemIndex; 			// Index of item to be updated
			try
			{
				System.out.printf("%n%5s%-30s", "", "Item Name");
				System.out.printf("%15s", "Item Price");
				System.out.printf("%-17s%n", "Item Type");

				
				int menuSize = menu.size();
				int actualMenuSize = 0; // Actual menu size, excluding set packages
				
				for(int currItem = 0; currItem < menuSize; currItem++) // Display menu items
				{
					if(menu.get(currItem).getItemType() == menuitem.type.SetPackage) break;
					
					System.out.printf("%-5s", "(" + (currItem + 1) + ")");
					menu.get(currItem).showItem();		
					actualMenuSize = currItem + 1;
				}
				
				System.out.printf("%nPlease select an item to update (0 to cancel): ");
			
				itemIndex = sc.nextInt();
				sc.nextLine();
				
				if(itemIndex == 0) 				// User chooses not to update any menu item
				{
					System.out.println("\nNothing to be updated!");
					return;
				}
				
				if (itemIndex < 1 || itemIndex > actualMenuSize) { 				// Valid itemTypeValues from 1 to actualMenuSize
					System.out.println("Invalid input! Please try again");
					return;
				}
				
				menuitem updatingItem = menu.get(itemIndex - 1);
				updateItemInfo(updatingItem);
				
				System.out.printf("%nSuccessfully updated \"%s\"!%n", updatingItem.getName());
				
				// Update Set Packages (if any)
				for(int currItem = 0; currItem < menuSize; currItem++)
				{
					menuitem menuItem = menu.get(currItem);
					
					// If this item is a set package
					if(menuItem.getItemType() == menuitem.type.SetPackage)
					{
						// Makes the set package recalculate its price
						((setPackage) menuItem).recalculatePackagePrice();
					}
				}
				
			}
			catch(InputMismatchException ex)
			{
				System.out.println("Invalid input! Please try again");
				sc.nextLine();
				return;
			}
			catch(Exception ex)
			{
				System.out.println("Invalid input! Please try again");
				return;
			}
		}
	}

	/**
	 * Removes an existing menu item
	 * The user can choose to remove an menu item from the list of existing menu items. 
	 * If the menu item is included in any set package, the set package will be updated. 
	 * However, if the set package becomes empty after removing the menu item, the set package will be deleted.
	 */	
	private void removemenuitem(){
		if(menu.isEmpty()){
			System.out.print("\nThe Menu is empty right now!");
		}
		
		else{
			int itemIndex;
			String removedItemName = "";
			
			try
			{
				System.out.printf("%n%5s%-30s", "", "Item Name");
				System.out.printf("%-17s", "Item Type");
				System.out.printf("%15s%n", "Item Price");
				
				int menuSize = menu.size();
				
				int actualMenuSize = 0;
				
				for(int currItem = 0; currItem < menuSize; currItem++)
				{
					if(menu.get(currItem).getItemType() == menuitem.type.SetPackage) break;
					
					System.out.printf("%-5s", "(" + (currItem + 1) + ")");
					menu.get(currItem).showItem();
					actualMenuSize = currItem + 1;
				}
				
				System.out.println("\nPlease select an item to remove (0 to cancel): ");
				itemIndex = sc.nextInt();
				
				if(itemIndex == 0)
				{
					System.out.println("\nNothing to be removed!");
					return;
				}
				
				if (itemIndex < 1 || itemIndex > actualMenuSize) {
					System.out.println("\nInvalid input! Please try again\n");
					return;
				}
				
				menuitem removedItem = menu.get(itemIndex - 1);
				
				Iterator<menuitem> menuIter = menu.iterator();
				menuitem menuItem = null;
				
				// Update Set Packages (if any)
				while(menuIter.hasNext()) {
					
					menuItem = menuIter.next();
					
					// If this item is a set package
					if(menuItem.getItemType() == menuitem.type.SetPackage)
					{
						if(((setPackage) menuItem).findInPackage(removedItem)) // Explicit downcasting 
						{
							((setPackage) menuItem).removeItemFromPackage(removedItem);
							
							((setPackage) menuItem).recalculatePackagePrice();
							
							if(((setPackage) menuItem).getPackageSize() == 0)
							{
								// Cannot have a set package with no items Add to list of set packages pending removal
								menuIter.remove();
							}
						}
					}
				}
				
				removedItemName = removedItem.getName();
				menu.remove(removedItem);	
				
				System.out.printf("%nSuccessfully removed \"%s\" from the menu!%n", removedItemName);
			}
			catch(InputMismatchException ex1)
			{
				System.out.println("\nInvalid input! Please try again");
				return;
			}
			catch(Exception ex2)
			{
				System.out.println("\nInvalid input! Please try again");
				return;
			}
		}
	}
	
	/**
	 * Creates a new set package
	 * 
	 * Allows the user to choose from a list of menu items to add to the set package.	  
	 * The set package will not be created if there are no menu items in it
	 */
	private void addSetPackage()
	{
		if(menu.isEmpty())
		{
			System.out.print("\nThe Menu is empty right now!");
			System.out.println(" Create new menu items to add them to a set package!");
		}
		else
		{
			setPackage newSetPackage = null;
			
			try
			{
				System.out.print("Enter a name for the set package: ");
				String newPackageName = sc.nextLine();
				
				if(isDuplicateItem(newPackageName))
				{
					System.out.print("\nSorry, a set package with the given name exists in the system. No duplicate item name allowed!");
					System.out.println("\nInvalid input! Please try again");
					return;
				}
				
				System.out.print("Enter a description for the set package: ");
				String newPackageDesc = sc.nextLine();
				
				newSetPackage = new setPackage(menuitem.type.SetPackage, newPackageName, newPackageDesc, 0);
			}
			catch(InputMismatchException ex1)
			{
				System.out.println("\nInvalid input! Please try again");
				return;
			}
			catch(Exception ex2) {
				System.out.println("\nInvalid input! Please try again");
				return;}
			
			int itemIndex = 0;
			
			do
			{
				try
				{
					System.out.printf("%n%5s%-30s", "", "Item Name");
					System.out.printf("%-17s", "Item Type");
					System.out.printf("%15s%n", "Item Price");
					
					int menuSize = menu.size();
					
					int actualMenuSize = 0;
					
					for(int currItem = 0; currItem < menuSize; currItem++)
					{
						if(menu.get(currItem).getItemType() == menuitem.type.SetPackage) break;
						
						System.out.println("(" + (currItem + 1) + ")");
						menu.get(currItem).showItem();
						actualMenuSize = currItem + 1;
					}
					
					System.out.printf("%nSelect item to be added to the set package (0 to end): ");
				
					itemIndex = sc.nextInt();
					sc.nextLine();
					
					if(itemIndex == 0) continue;
					
					if (itemIndex < 1 || itemIndex > actualMenuSize) {
						System.out.println("\nInvalid input! Please try again");
						continue;
					}
					
					menuitem selectedItem = menu.get(itemIndex - 1);
					newSetPackage.addItemToPackage(selectedItem);
					System.out.printf("\"%s\" has been added to the set package!%n", selectedItem.getName());
				}
				
				catch(InputMismatchException ex1) { 
					System.out.println("\nInvalid input! Please try again"); 
					sc.nextLine();
					continue;
				}
				
				catch(Exception ex2) { 
					System.out.println("Invalid input! Please try again");
					sc.nextLine();
					continue;
				}
				
			} while(itemIndex != 0);
			
			if(newSetPackage.getPackageSize() == 0) { System.out.println("Empty Set Package! Please try again"); }
			else
			{
				System.out.println("\nSuccessfully created new set package!" + newSetPackage.getName());
				newSetPackage.showItem();
				menu.add(newSetPackage);
			
				Collections.sort(menu);
			}
		}
	}
	
	/**
	 * Updates the set package
	 * 
	 * The user can update the information for the set package, add more menu items to the set package or remove existing menu items from the set package.
	 */
	private void updateSetPackage(){
		if(menu.isEmpty())
		{
			System.out.print("\nThe Menu is empty right now!");
		}
		else
		{
			int startingSetIndex = -1;
			int currSet = 0, totalSets = 0;
			
			int menuSize = menu.size();
			
			// Display set packages (if any)
			for(int currItem = 0; currItem < menuSize; currItem++)
			{
				menuitem menuItem = menu.get(currItem);
				
				// Only include set packages as valid options
				if(menuItem.getItemType() == menuitem.type.SetPackage)
				{
					if(startingSetIndex == -1)
					{
						startingSetIndex = currItem;
						currSet = 0;
						totalSets = 1;
						
						System.out.printf("%n%5s%-30s", "","Set Package Name");
						System.out.printf("%10s", "Item Price");
						System.out.printf("%33s%n", "Set Package Description");

					}
					else
					{
						currSet++;
						totalSets++;
					}
					
					System.out.printf("%-5s", "(" + (currSet + 1) + ")");
					((setPackage) menuItem).showPackage();
				}
			}
			
			if(totalSets == 0)
			{
				System.out.println("\nThere are no set packages in the menu right now!");
			}
			else
			{
				// Index of set package to be updated
				int itemIndex;
				
				// Name of updated set package, if any
				String updatedPackageName = "";
				
				try
				{
					System.out.printf("%nPlease select a set package to update (0 to cancel): ");
				
					itemIndex = sc.nextInt();
					sc.nextLine();
					
					// User chooses not to update any set package
					if(itemIndex == 0)
					{
						System.out.println("\nNothing to be updated!");
						return;
					}
					
					// Valid values from 1 to totalSets
					if (itemIndex < 1 || itemIndex > totalSets) {
						System.out.print("\nInvalid input! ");
						System.out.println("Failed to update set package, please try again..");
						return;
					}
					
					// Calculate actual index
					itemIndex = (startingSetIndex + itemIndex - 1);
					
					menuitem updatingPackage = menu.get(itemIndex);
					updatedPackageName = updatingPackage.getName();
					
					int updateOption = -1;
					do
					{
						System.out.println();
						System.out.println("(1) Update item information");
						System.out.println("(2) Add menu item to set package");
						System.out.println("(3) Remove menu item from set package");
						
						System.out.printf("%nPlease select the action to be performed (0 to cancel): ");
					
						updateOption = sc.nextInt();
						sc.nextLine();
						
						// User chooses not to update any set package
						if(updateOption == 0)
						{
							System.out.println("\nNothing to be updated!");
							return;
						}
						
						// Valid values from 1 to 3
						if (updateOption < 1 || updateOption > 3) {
							System.out.print("\nInvalid input! ");
							System.out.println("Failed to update set package, please try again..");
							return;
						}
						
						if(updateOption == 1)
						{
							updateItemInfo(updatingPackage);
							System.out.printf("%nSuccessfully updated \"%s\"! %n", updatedPackageName);
						}
						else if(updateOption == 2)
						{
							addItemToPackage(updatingPackage);
							System.out.printf("%nSuccessfully updated \"%s\"! %n", updatedPackageName);
						}
						else
						{
							removeItemFromPackage(updatingPackage);
							
							if(((setPackage) updatingPackage).isEmpty())
							{
								System.out.print("\nEmpty set package!");
								System.out.printf("\"%s\" has been " + "deleted..%n", updatedPackageName);
								menu.remove(updatingPackage);
								
								return;
							}
							else
							{
								System.out.printf("%nSuccessfully updated " + "\"%s\"!%n", updatedPackageName);
							}
						}
						
					} while(updateOption != 0);
				}
				catch(InputMismatchException ex1)
				{
					System.out.print("\nInvalid input! ");
					System.out.println("Failed to update set package, please try again..");
					sc.nextLine(); // Clear the garbage input
					return;
				}
				catch(Exception ex2)
				{
					System.out.println("\nFailed to update set package, please try again..");
					return;
				}
			}
		}
	}
	
	/**
	 * Remove the set package. Asks the user to find and remove any set package.
	 */
	private void removeSetPackage()
	{
		if(menu.isEmpty())
		{
			System.out.print("\nWell, the Menu is empty right now!");
			System.out.println(" No set package to remove..");
		}
		else
		{
			int startingSetIndex = -1;
			int currSet = 0, totalSets = 0;
			
			int menuSize = menu.size();
			
			// Display set packages (if any)
			for(int currItem = 0; currItem < menuSize; currItem++)
			{
				menuitem menuItem = menu.get(currItem);
				
				// Only include set packages as valid options
				if(menuItem.getItemType() == menuitem.type.SetPackage)
				{
					if(startingSetIndex == -1)
					{
						startingSetIndex = currItem;
						currSet = 0;
						totalSets = 1;
						
						System.out.printf("%n%5s%-30s", "","Set Package Name");
						System.out.printf("%10s", "Item Price");
						System.out.printf("%33s", "Set Package Description");

						System.out.println();
					}
					else
					{
						currSet++;
						totalSets++;
					}
					
					System.out.printf("%-5s", "(" + (currSet + 1) + ")");
					((setPackage) menuItem).showPackage();
				}
			}
			
			if(totalSets == 0)
			{
				System.out.println("\nWell, there are no set packages in the"
						+ " menu right now! Try creating one? :-)");
			}
			else
			{
				// Index of set package to be removed
				int itemIndex;
				
				// Name of removed set package, if any
				String removedItemName = "";
				
				try
				{	
					System.out.printf("%nPlease select a set package to remove (0 to cancel): ");
				
					itemIndex = sc.nextInt();
					sc.nextLine();
					
					if(itemIndex == 0)
					{
						System.out.println("\nNothing to be removed!");
						return;
					}
					
					// Valid values from 1 to totalSets
					if (itemIndex < 1 || itemIndex > totalSets) {
						System.out.print("\nInvalid input! Failed to remove set package, please try again..");
						return;
					}
					
					// Calculate actual index
					itemIndex = (startingSetIndex + itemIndex - 1);
					
					// Remove it from the menu
					removedItemName = menu.get(itemIndex).getName();
					menu.remove(itemIndex);
					
					System.out.printf("%nSuccessfully removed \"%s\" from"
							+ " the menu!%n", removedItemName);
				}
				catch(InputMismatchException ex1)
				{
					System.out.print("\nInvalid input! ");
					System.out.println("Failed to remove set package, please try again..");
					
					sc.nextLine(); // Clear the garbage input
					return;
				}
				catch(Exception ex2)
				{
					System.out.println("\nFailed to remove set package, please try again..");
					return;
				}
			}
		}
	}

	/**
	 * Updates the info of a menu item
	 * 
	 * @param updatingItem The menu item to be updated
	 *  
	 * @throws InputMismatchException Exception thrown for input mismatch
	 * @throws Exception General exception thrown
	 */	
	private void updateItemInfo(menuitem updatingItem)
			throws InputMismatchException, Exception
	{
		System.out.println("Current Name: " + updatingItem.getName());
		System.out.print("\tEnter a new name: ");
		String newItemName = sc.nextLine();
		
		System.out.println("Current description: " + updatingItem.getDescription());
		System.out.print("\tEnter a new description: ");
		String newItemDesc = sc.nextLine();
		
		double newItemPrice = 0;
		if(! (updatingItem instanceof setPackage))
		{
			System.out.printf("Current price: " + updatingItem.getPrice()); 
			System.out.print("\tEnter a new price: ");
			newItemPrice = sc.nextDouble();
			sc.nextLine();
		}
		
		updatingItem.setName(newItemName);
		updatingItem.setDescription(newItemDesc);
		
		if(! (updatingItem instanceof setPackage))
		{
			updatingItem.setPrice(newItemPrice);
		}
	}
	
	/**
	 * Adds a menu item to a particular set package
	 * 
	 * @param updatingSetPackage The set package that the menu item is added to
	 * 
	 * @throws InputMismatchException Exception thrown for input mismatch
	 * @throws Exception General exception thrown
	 */	
	private void addItemToPackage(menuitem updatingSetPackage)
			throws InputMismatchException, Exception
	{
		System.out.printf("%n%5s%-30s", "", "Item Name");
		System.out.printf("%-17s", "Item Type");
		System.out.printf("%15s%n", "Item Price");
		
		int menuSize = menu.size();
		
		int actualMenuSize = 0;
		
		for(int currItem = 0; currItem < menuSize; currItem++)
		{
			if(menu.get(currItem).getItemType() == menuitem.type.SetPackage)
				break;
			
			System.out.printf("%-5s", "(" + (currItem + 1) + ")");
			menu.get(currItem).showItem();
			actualMenuSize = currItem + 1;
		}
		
		System.out.printf("%nSelect item to be added to the set package (0 to cancel): ");
	
		int itemIndex = sc.nextInt();
		sc.nextLine();
		
		if(itemIndex == 0)
			return;
		
		if (itemIndex < 1 || itemIndex > actualMenuSize) {
			System.out.println("\nInvalid input! Please try again");
			return;
		}
		
		menuitem selectedItem = menu.get(itemIndex - 1);
		((setPackage) updatingSetPackage).addItemToPackage(selectedItem);
		
		System.out.printf(selectedItem.getName() + " has been added to the set package!\n");
	}

	/**
	 * Removes a menu item from a particular set package
	 * 
	 * @param updatingSetPackage The set package to remove the menu item from
	 * 
	 * @throws InputMismatchException Exception thrown for input mismatch
	 * @throws Exception General exception thrown
	 */
	private void removeItemFromPackage(menuitem updatingSetPackage)
			throws InputMismatchException, Exception
	{
		System.out.printf("%n%5s%-30s", "", "Item Name");
		System.out.printf("%-17s", "Item Type");
		System.out.printf("%15s%n", "Item Price");
		
		List<menuitem> packageItems = ((setPackage) updatingSetPackage).getPackageItems();
		
		int packageSize = packageItems.size();
		
		for(int currItem = 0; currItem < packageSize; currItem++)
		{
			System.out.println("(" + (currItem + 1) + ")");
			packageItems.get(currItem).showItem();
		}
		
		System.out.printf("%nSelect item to be removed from the set package (0 to cancel): ");
	
		int itemIndex = sc.nextInt();
		sc.nextLine();
		
		if(itemIndex == 0)
			return;
		
		if (itemIndex < 1 || itemIndex > packageSize) {
			System.out.println("\nInvalid input! Please try again");
			return;
		}
		
		menuitem selectedItem = packageItems.get(itemIndex - 1);
		((setPackage) updatingSetPackage).removeItemFromPackage(selectedItem);
		
		System.out.printf("\"%s\" has been removed from the set package!%n", selectedItem.getName());
	}
	
	/**
	 * Checks for duplicate menu item names in the menu
	 * 
	 * @param menuItemName The name of the new menu item to be created
	 * 
	 * @return True if it is a duplicate menu item name
	 */
	private boolean isDuplicateItem(String menuitemName)
	{
		if(menu.isEmpty())
			return false;
		
		for(menuitem menuitem : menu){
			if(menuitem.getName().equalsIgnoreCase(menuitemName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets a selected menu item based from the menu.
	 * 
	 * @return The selected menu item
	 */
	public menuitem selectmenuitem(){
		if(menu.isEmpty())
		{
			System.out.print("\nThe Menu is empty right now!");
			
			return null;
		}
		else
		{
			int menuChoice = 0;
			int menuSize = menu.size();
			
			do
			{
				int currItemIndex = 0;
				
				try
				{
					System.out.println();
					
					for(menuitem menuitem : menu)
					{
						if(menuitem.getItemType().toStrValue() == "SetPackage") System.out.println("\n[Set Package]");
						System.out.printf("%-4s ", "(" + (++currItemIndex) + ")");
						menuitem.showItem();
					}

					System.out.printf("%nPlease select a menu item (0 to cancel): ");

					menuChoice = sc.nextInt();
					sc.nextLine();
					
					if(menuChoice == 0) return null;

					if (menuChoice < 1 || menuChoice > menuSize)
					{
						System.out.println("\nInvalid input! Please try again");
						continue;
					}
				}
				catch(InputMismatchException ex)
				{
					System.out.println("\nInvalid input! Please try again");
					sc.nextLine(); 
					continue;
				}
				catch(Exception ex)
				{
					System.out.println("\nInvalid input! Please try again");
					sc.nextLine();
					continue;
				}

			} while(menuChoice < 1 || menuChoice > menuSize);

			return menu.get(menuChoice - 1);
		}
	}
}