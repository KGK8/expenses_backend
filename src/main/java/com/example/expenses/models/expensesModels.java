package com.example.expenses.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
@Document("expensesTracker")
public class expensesModels {
	@Id
	@GeneratedValue
	String id;
	Long amount;
	Long timeStamp;
	String date;
	String description;
	String category;
	@CreationTimestamp
	Long purchasedOn;

	public expensesModels(String id, String description, String category, Long amount, Long purchasedOn,
			Long timeStamp,String date) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.category = category;
		this.timeStamp = timeStamp;
		this.description = description;
		this.purchasedOn = purchasedOn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getPurchasedOn() {
		return purchasedOn;
	}

	public void setPurchasedOn(Long purchasedOn) {
		this.purchasedOn = purchasedOn;
	}

}
