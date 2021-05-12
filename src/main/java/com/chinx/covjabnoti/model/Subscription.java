package com.chinx.covjabnoti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subscription")
public class Subscription {
	
	  @Id
	  private String id;

	  private String district_id;
	  private String name;
	  private String phone_no;
	  private String api_key;
	  private String age_slot;
	  
	public Subscription(String id, String district_id, String name, String phone_no, String api_key, String age_slot) {
		super();
		this.id = id;
		this.district_id = district_id;
		this.name = name;
		this.phone_no = phone_no;
		this.api_key = api_key;
		this.age_slot = age_slot;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	
	public String getAge_slot() {
		return age_slot;
	}

	public void setAge_slot(String age_slot) {
		this.age_slot = age_slot;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", district_id=" + district_id + ", name=" + name + ", phone_no=" + phone_no
				+ ", api_key=" + api_key + ", age_slot=" + age_slot + "]";
	}


}
