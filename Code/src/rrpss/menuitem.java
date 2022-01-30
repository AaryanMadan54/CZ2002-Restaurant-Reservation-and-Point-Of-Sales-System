package rrpss;
import java.io.Serializable;

/**
 * Class to represent a item in the menu.
 * 
 * Implements the Serializable interface from java.io.Serializable to save the menu.
 * 
 * @author Siddhant Pathak 
 *
 */
public class menuitem implements Serializable, Comparable <menuitem> {
	
	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = -3514192202034850964L;

	/**
	 * An enumeration type to refer to the various types of menu items
	 *
	 */
	public static enum type
	{
		MainCourse("Main Course"),
		Drink("Drink"),
		Dessert("Dessert"),
		SetPackage("Set Package");
		
		private final String value;
		
		private type(String value)
		{
			this.value=value;
		}
		
		public String toStrValue()
		{
			return value;
		}
	}
	
	/**
	 * The item type of the menu item
	 */
	private type itemType;
	
	/**
	 * The name of the menu item
	 */
	private String name;
	
	/**
	 * The description of the menu item
	 */
	private String description;
	
	/**
	 * The price of the menu item
	 */
	private double price;
	
	/**
	 * Public Constructor to create a menu item on the basis of given information
	 * 
	 * @param item The type of the menu item
	 * @param Name The name of the menu item
	 * @param desc The description of the menu item
	 * @param cost The price of the menu item
	 */
	public menuitem(type item, String Name, String desc, double cost) {
		// TODO - implement menuitem.menuitem
		itemType = item;
		name = Name;
		description = desc;
		price = cost;
	}
	
	/**
	 * Gets the Item type of the menu item
	 * @return The item type of the menu item
	 */
	public type getItemType() {
		return itemType;
	}

	/**
	 * Sets the Item type of a menu item
	 * @param newType The new item type to be set
	 */
	public void setItemType(type newType) {
		this.itemType = newType;
	}

	/**
	 * Gets the name of the menu item
	 * @return The name of the menu item
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of a menu item
	 * @param newname The new name to be set
	 */
	public void setName(String newname) {
		this.name = newname;
	}

	/**
	 * Gets the description of the menu item
	 * @return The description of the menu item
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of the menu item
	 * @param newdescription The new description to be set
	 */
	public void setDescription(String newdescription) {
		this.description = newdescription;
	}

	/**
	 * Gets the price of the menu item
	 * @return The price of the menu item
	 */
	public double getPrice() {
		// TODO - implement menuitem.getPrice
		return this.price;
	}

	/**
	 * Sets the price of a menu item
	 * @param newprice The new price to be set
	 */
	public void setPrice(double newprice) {
		// TODO - implement menuitem.setPrice
		this.price=newprice;
	}

	/**
	 * Shows the necessary details of the menu item.
	 * Prints the name and price of the menu item.
	 */
	public void showItem() {
		// TODO - implement menuitem.showItem
		System.out.printf("%-30s", getName());
		//System.out.printf("%-30s", "[" + getItemType().toStrValue() + "]");
		System.out.printf("%50s%n", getPrice());
		System.out.println(getDescription());
	}

	@Override
	public int compareTo(menuitem o) {
		// TODO Auto-generated method stub
		// Compare based on ItemType
				return this.getItemType().compareTo(o.getItemType());
	}
}