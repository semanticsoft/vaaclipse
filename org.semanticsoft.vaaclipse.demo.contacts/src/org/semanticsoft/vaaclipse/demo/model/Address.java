package org.semanticsoft.vaaclipse.demo.model;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;

public class Address {

	private String street;
	private String city;
	private String country;

	@Inject
	public Address() {
		this("e4 street","Boston","USA");
	}
	
	public Address(String street, String city, String country) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
