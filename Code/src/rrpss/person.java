package rrpss;
import java.io.Serializable;

/**
 * Class to represent everybody who is/will be at the restaurant. For example: employees of the restaurants, and customers.
 * 
 * @author Aryan Madan
 */
public class person implements Serializable {

	/**
	 * Generated serial version ID for serializable classes
	 */
	private static final long serialVersionUID = -3077226518979820168L;

	/**
	 * An enumeration type to refer to the gender (Male, Female, Other/Non-binary)
	 *
	 */
	public static enum Gender
	{

		Male("Male"),
		Female("Female"),
		Other("Other");
		
		private final String value;
		  
		private Gender(String value) {
			this.value = value;
		}
		
		public String toStrValue() {
			return value;
		}
	}

	/**
	 * Name of the person
	 */
	private String name;
	
	/**
	 * Age of the person
	 */
	private int age;
	
	/**
	 * Gender of the person
	 */
	private Gender gender;

	/**
	 * Public Constructor to create a new person with the given information
	 * @param name Name of the person
	 * @param age Age of the person
	 * @param gender Gender of the person
	 */
	public person(String name, int age, Gender gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		//throw new UnsupportedOperationException();
	}

	/**
	 * Gets the name of the person
	 * 
	 * @return The name of the person
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the person
	 * @param newName The new name of the person
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Gets the age of the person
	 * 
	 * @return The age of the person
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * Sets the age of the person
	 * 
	 * @param newAge The new age of the person
	 */
	public void setAge(int newAge) {
		this.age = newAge;
	}

	/**
	 * Gets the gender of the person
	 * 
	 * @return The gender of the person
	 */
	public Gender getGender() {
		return this.gender;
	}

	/**
	 * Sets the gender of the person
	 * @param newGender The new gender of the person
	 */
	public void setGender(Gender newGender) {
		this.gender = newGender;
	}

}