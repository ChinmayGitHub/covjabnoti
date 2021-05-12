package com.chinx.covjabnoti.model;

public class Slot {
	
	  private String center_name;
	  private String date;
	  private String age_slot;
	  private String capacity;
	  private String vaccine;
	  
	public Slot(String center_name, String date, String age_slot, String capacity, String vaccine) {
		super();
		this.center_name = center_name;
		this.date = date;
		this.age_slot = age_slot;
		this.capacity = capacity;
		this.vaccine = vaccine;
	}
	
	public String getCenter_name() {
		return center_name;
	}
	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAge_slot() {
		return age_slot;
	}
	public void setAge_slot(String age_slot) {
		this.age_slot = age_slot;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getVaccine() {
		return vaccine;
	}
	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	@Override
	public String toString() {
		return "Slot [center_name=" + center_name + ", date=" + date + ", age_slot=" + age_slot + ", capacity="
				+ capacity + ", vaccine=" + vaccine + "]";
	}
	


}
