package com.ceit.vic.device.models;

import java.util.List;

public class Module {
	String id;
	String name;
	String loaded;
	String url;
	List<Project> projects;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoaded() {
		return loaded;
	}
	public void setLoaded(String loaded) {
		this.loaded = loaded;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
}
