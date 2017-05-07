package com.shoptracker.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Location {
	
	public Location() {
		// TODO Auto-generated constructor stub
	}
	@Column(name = "latitude")
	private String latitude;
	@Column(name = "longitude")
	private String longitude;
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "Location [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	public Location(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
}
