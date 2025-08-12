package be.iannel.iannelloecoleski.models;

public abstract class Person {

	//Attributes
	protected int id;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String street;
	protected int streetNumber;
	protected String city;
	protected String phoneNumber;
	protected int age;
	
	// Constructor
	
	public Person() {}
	
	public Person(int id, String firstName, String lastName, String email,
			String street, int streetNumber, String city, String phoneNumber, int age) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.street = street;
		this.streetNumber = streetNumber;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.age = age;
	}
	
	//Get-Set
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public int getStreetNumber() {
		return streetNumber;
	}
	
	public void setStreetNumber(int streetNumber) {
		if(streetNumber <= 0) {
			throw new IllegalArgumentException("Le numéro de rue doit être positif");
		}
		this.streetNumber = streetNumber;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void SetPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		if(age < 0) {
			throw new IllegalArgumentException("L'âge doit être positif");
		}
		this.age = age;
	}
}
