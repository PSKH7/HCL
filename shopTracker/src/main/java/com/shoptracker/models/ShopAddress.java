package com.shoptracker.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ShopAddress {

	public ShopAddress() {
		// TODO Auto-generated constructor stub
	}
	@Column(name = "ADDRESS_NUMBER")
	private String number;
	@Column(name = "ADDRESS_POSTCODE")
	private String postCode;
	@Override
	public String toString() {
		return "ShopAddress [number=" + number + ", postCode=" + postCode + "]";
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public ShopAddress(String number, String postCode) {
		super();
		this.number = number;
		this.postCode = postCode;
	}
}
