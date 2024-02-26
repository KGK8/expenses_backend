package com.example.expenses.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import enums.transactionEnum;
import enums.transactionTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

@Entity
@Document("transactions")
public class transactionsModel {

	@Autowired
	private transactionEnum transactionEnum;

	@Id
	@GeneratedValue
	String id;
	@Enumerated(EnumType.STRING)
	transactionEnum type = transactionEnum.DEBITED;
	@Enumerated(EnumType.STRING)
	transactionTypeEnum transactionType = transactionTypeEnum.SAVINGS;
	long amount;
	String userId;
	@CreationTimestamp
	long createdOn;
	@UpdateTimestamp
	long updatedOn;
	String date;
	long savingsBalance;
	long creditBalance;
	String description;

	public transactionsModel(String id, transactionEnum type, long amount, String userId, long createdOn,
			long updatedOn, long savingsBalance, String description, String date, transactionTypeEnum transactionType,
			long creditBalance) {
		super();
		this.id = id;
		this.type = type;
		this.date = date;
		this.amount = amount;
		this.userId = userId;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.description = description;
		this.savingsBalance = savingsBalance;
		this.transactionType = transactionType;
		this.creditBalance = creditBalance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public transactionEnum getType() {
		return type;
	}

	public void setType(transactionEnum type) {
		this.type = type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public long getSavingsBalance() {
		return savingsBalance;
	}

	public void setSavingsBalance(long SavingsBalance) {
		this.savingsBalance = savingsBalance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public transactionTypeEnum getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(transactionTypeEnum transactionType) {
		this.transactionType = transactionType;
	}

	public long getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(long creditBalance) {
		this.creditBalance = creditBalance;
	}
}
