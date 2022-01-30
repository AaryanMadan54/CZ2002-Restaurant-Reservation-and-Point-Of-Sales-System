package rrpss;
import java.util.ArrayList;

/**
 * Class to represent a Set Package in the menu.
 * A set package is also a menuitem, hence it inherits its methods.
 * 
 * @author Siddhant Pathak
 *
 */
public class setPackage extends menuitem {
	
	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = -2775741260403140572L;

	/**
	 * Discount applied on total cost of the set package
	 */	
	private static final double ITEM_DISCOUNT = 0.8;
	
	/**
	 * A list of all menu items in this set package
	 */
	private ArrayList<menuitem> setPackageItems;

	/**
	 * Public Constructor to create a set package with the given information.
	 * 
	 * @param itemtype Type of the menu item to be added
	 * @param name Name of the set package
	 * @param description Description of the set package
	 * @param price Price of the set package
	 */
	public setPackage(type itemtype, String name, String description, double price) {
		// TODO - implement setPackage.setPackage
		super(itemtype,name,description,price);
		setPackageItems=new ArrayList<menuitem>();
	}
	
	/**
	 * Public Constructor to create a set package with the given information
	 * 
	 * @param itemtype Type of the menu item to be added
	 * @param name Name of the set package
	 * @param description Description of the set package
	 * @param price Price of the set package
	 * @param setPkgItems The list of set package items (if exists)
	 */
	public setPackage(type itemtype, String name, String description, double price, ArrayList<menuitem> setPkgItems) {
		// TODO - implement setPackage.setPackage
		super(itemtype,name,description,price);
		if(setPkgItems!=null)
		{
			setPackageItems = setPkgItems;
		}
		else
		{
			setPackageItems = new ArrayList <menuitem>();
		}
	}

	/**
	 * Adds a menu item to the set package
	 * 
	 * @param newItem Menu item to be added
	 */
	public void addItemToPackage(menuitem newItem) {
		// TODO - implement setPackage.addItemToPackage
		setPackageItems.add(newItem);
		setPrice(getPrice()+ (newItem.getPrice()*ITEM_DISCOUNT));
	}

	/**
	 * Removes menu item from the set package
	 * 
	 * @param item_to_be_removed The menu item to be removed
	 */
	public void removeItemFromPackage(menuitem item_to_be_removed) {
		// TODO - implement setPackage.removeItemFromPackage
		if(getPackageSize()==0)
		{
			return;
		}
		setPackageItems.remove(item_to_be_removed);
		setPrice(getPrice() - (item_to_be_removed.getPrice()*ITEM_DISCOUNT));
	}

	/**
	 * Gives the number of items in the set package 
	 * 
	 * @return The number of items in the set package
	 */
	public int getPackageSize() {
		// TODO - implement setPackage.getNoOfItems
		return setPackageItems.size();
	}

	/**
	 * Gives the menu items list in the set package
	 * 
	 * @return The list of menu items in set package
	 */
	public ArrayList<menuitem> getPackageItems() {
		// TODO - implement setPackage.getPackageItems
		return setPackageItems;
	}

	/**
	 * Checks if the set package is empty or not
	 * 
	 * @return True if the set package if empty
	 */
	public boolean isEmpty() {
		return setPackageItems.isEmpty();
	}

	/**
	 * Recalculates the price of a set package in case of any changes
	 */
	public void recalculatePackagePrice()
	{
		if(getPackageSize() == 0)
			return;
		
		double newPackagePrice = 0;
		for(menuitem setPackageItem : setPackageItems)
		{
			newPackagePrice += (setPackageItem.getPrice() * ITEM_DISCOUNT);
		}
		
		setPrice(newPackagePrice);
	}
	
	/**
	 * Checks if this set package contains the specified menu item
	 * 
	 * @param menuItem The menu item to look for in this set package
	 * @return True if this menu item can be found in the set package
	 */
	public boolean findInPackage(menuitem menuItem)
	{
		if(getPackageSize() == 0)
			return false;
		
		return setPackageItems.contains(menuItem);
	}
	
	/**
	 * Shows the necessary details of a set package.
	 * 
	 * Prints the name, description and price of the set package.
	 */
	public void showPackage() {
		// TODO - implement setPackage.showPackage
		System.out.printf("%-30s",  getName() );
		System.out.printf("%-20s", getPrice());
		System.out.printf("%-30s", getDescription());
		System.out.println();
	}
}