package com.example.expenses.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
@Document("userDeviceKeys")
public class PushNotificationsModel {

	@Id
	@GeneratedValue
	String id;
	String userId;
//	List<Map<String, Object>> deviceKeys;
	private Map<String, Object> deviceKeys = new LinkedHashMap<String, Object>();
	long createdOn;
	long updatedOn;

	public PushNotificationsModel(String id, String userId, Map<String, Object> deviceKeys, long createdOn,
			long updatedOn) {
		super();
		this.id = id;
		this.userId = userId;
		this.deviceKeys = deviceKeys;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, Object> getDeviceKeys() {
		return deviceKeys;
	}

	public void setDeviceKeys(Map<String, Object> deviceKeys) {
		this.deviceKeys = deviceKeys;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public long getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(long updatedOn) {
		this.updatedOn = updatedOn;
	}

}
