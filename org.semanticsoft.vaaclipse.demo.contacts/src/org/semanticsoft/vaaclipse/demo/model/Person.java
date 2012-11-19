package org.semanticsoft.vaaclipse.demo.model;

import java.util.List;

import javax.inject.Inject;

public class Person {

	private String firstName;
	private String lastName;
	private int age;
	private Address address;
	
	@Inject
	public Person() {
		this ("Sopot","Cela",33, new Address("eclipse e4 stree", "Boston", "USA"));
	}
	
	public Person(String firstName, String lastName, int age,
			Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.address = address;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
