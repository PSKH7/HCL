package com.shoptracker.models;

import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Shops")
public class Shop {

	public Shop() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name = "Name")
	private String shopName;
	
	@Embedded
	private ShopAddress shopAddress;
	
	@Embedded
	private Location location;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public ShopAddress getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Shop(String shopName, ShopAddress shopAddress, Location location) {
		super();
		this.shopName = shopName;
		this.shopAddress = shopAddress;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Shop [shopName=" + shopName + ", shopAddress=" + shopAddress + ", location=" + location + "]";
	}
	
	

}
