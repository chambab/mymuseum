package com.mm.core.resource;

import java.util.ArrayList;

public class MyMuseum {
	
	private ArrayList users = null;
	

	public ArrayList getUsers() {
		return users;
	}
	public void setUser(User user) {
		if(users == null) {
			users = new ArrayList();
		}
		this.users.add(user);
	}
	private String name = null;
	private String owner = null;
	private String location = null;
	private String mobilePhone = null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
}
