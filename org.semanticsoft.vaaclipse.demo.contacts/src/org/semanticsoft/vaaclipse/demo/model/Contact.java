package org.semanticsoft.vaaclipse.demo.model;

public class Contact {

	private String firstName;
	private String lastName;
	private int age;
	private String company;

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public Contact(String firstName, String lastName, int age, String company) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.company = company;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
