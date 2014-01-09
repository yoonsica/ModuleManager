package com.ceit.vic.device.models;

public class Project {
	String name;
	String type;
	public String getName() {
		return name;
	}
	public Project(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
