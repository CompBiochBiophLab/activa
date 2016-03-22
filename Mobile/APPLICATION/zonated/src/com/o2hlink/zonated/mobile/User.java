package com.o2hlink.zonated.mobile;

import java.util.Date;

import com.o2hlink.activ8.common.entity.Country;
import com.o2hlink.activ8.common.entity.Sex;
import com.o2hlink.zonated.Zonated;
import com.o2hlink.zonated.exceptions.NotUpdatedException;

/**
 * @author Adrian Rejas<P>
 *
 * This class represents the users of the application. As there can be just one user
 * running the application, this class is a singleton.
 */
public class User {
	
	/**
	 * User name.
	 */
	private String name;
	
	/**
	 * User password for validating the user.
	 */
	private String password;
	
	/**
	 * User password for validating the user.
	 */
	private String mail;

	/**
	 * User ambient name.
	 */
	private String ambient;
	
	/**
	 * User ID at the application.
	 */
	private long id;
	
	/**
	 * User type (0 for patient, 1 for clinician, 2 for researcher)
	 */
	private int type;
	
	/**
	 * User date of birth.
	 */
	private Date birthdate;
	
	/**
	 * Date of last update of height and weight.
	 */
	private Date lastupdate;
	
	/**
	 * Date of last calibration for the pedometer at the exercise.
	 */
	private Date laststepcalibration;
	
	/**
	 * Integer value for step detection at the exercise.
	 */
	private int steplimit;
	
	/**
	 * True if the user is male, false if he's female.
	 */
	private Sex sex;
	
	/**
	 * True if the user has been created at the O2H middleware.
	 */
	private boolean created;
	
	/**
	 * Do you want animations (0 for no animations, the number indicating the speed)?
	 */
	public int animations;
	
	/**
	 * Time between updates
	 */
	public int timeforupdating;
	
	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @return the isMale
	 */
	public Sex getSex() {
		return sex;
	}

	/**
	 * @param isMale the isMale to set
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	/**
	 * User height at centimeters.
	 */
	private int height;
	
	/**
	 * User weight at kilograms.
	 */
	private float weight;
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	/**
	 * First name of the user
	 */
	public String firstname;
	
	/**
	 * Last name of the user
	 */
	public String lastname;
	
	/**
	 * Country of the user
	 */
	public Country country;
	
	public int pedometerCalValue;

	/**
	 * Instance of the user.
	 */
	private static User instance;
	
	/**
	 * The private constructor for the class
	 * @param name
	 * @param password
	 */
	public User(String name, String password) {
		if (name != null) this.name = name;
		else this.name = Zonated.myLanguageManager.USER_DEFAULT;
		this.password = password;
		this.birthdate = new Date();
		this.sex = Sex.NOT_SPECIFIED;
		this.animations = 0;
		this.height = 0;
		this.weight = 0;
		this.created = false;
		this.lastupdate = new Date(0);
		this.laststepcalibration = new Date(0);
		this.steplimit = 0;
		this.firstname = "";
		this.lastname = "";
		this.country = null;
		this.pedometerCalValue = 30;
	}
	
	/**
	 * The private constructor for the class
	 * @param name
	 * @param password
	 */
	public User(String name, String password, Date birthdate, Sex sex, int height, float weight) {
		if (name != null) this.name = name;
		else this.name = Zonated.myLanguageManager.USER_DEFAULT;
		this.password = password;
		this.birthdate = birthdate;
		this.sex = sex;
		this.height = height;
		this.weight = weight;
		this.created = false;
		this.animations = 0;
		this.lastupdate = new Date(0);
		this.laststepcalibration = new Date(0);
		this.steplimit = 0;
		this.firstname = "";
		this.lastname = "";
		this.country = null;
		this.pedometerCalValue = 30;
	}
	
	/**
	 * Method for getting the instance of the User.
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static User getInstance(String name, String password) {
		if (User.instance == null) {
			User.instance = new User(name, password);
		}
		else {
			if (name != null) User.instance.setName(name);
			else User.instance.setName(Zonated.myLanguageManager.USER_DEFAULT);
			User.instance.setName(name);
			User.instance.setPassword(password);
		}
		return User.instance;
	}

	/**
	 * Getter for name.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for password.
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Setter for ID.
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Getter for ID.
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the lastupdate
	 */
	public Date getLastupdate() {
		return lastupdate;
	}

	/**
	 * @param lastupdate the lastupdate to set
	 */
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	/**
	 * @return the created
	 */
	public boolean isCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(boolean created) {
		this.created = created;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the laststepcalibration
	 */
	public Date getLaststepcalibration() {
		return laststepcalibration;
	}

	/**
	 * @param laststepcalibration the laststepcalibration to set
	 */
	public void setLaststepcalibration(Date laststepcalibration) {
		this.laststepcalibration = laststepcalibration;
	}

	/**
	 * @return the steplimit
	 */
	public int getSteplimit() {
		return steplimit;
	}

	/**
	 * @param steplimit the steplimit to set
	 */
	public void setSteplimit(int steplimit) {
		this.steplimit = steplimit;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	public void setAmbient(String ambient) {
		this.ambient = ambient;
	}

	public String getAmbient() {
		return ambient;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		if (mail != null) return mail;
		else return "";
	}
	
}
