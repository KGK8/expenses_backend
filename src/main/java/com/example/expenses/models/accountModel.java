package com.example.expenses.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TimeSeries;

import enums.transactionTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

@Entity
@Document("accounts")
@TimeSeries(collection = "accounts", timeField = "updatedOn", metaField = "updatedOn")
public class accountModel {
	@Id
	@GeneratedValue
	String id;
	String name;
	String email;
	String phoneNumber;
	@Enumerated(EnumType.STRING)
	transactionTypeEnum transactionType = transactionTypeEnum.SAVINGS;
	long balance = 0;
	long creditBalance = 0;
	long createdOn;
	long updatedOn;
	String password;

	public accountModel(String id, String name, long balance, long createdOn, long updatedOn, String email,
			String phoneNumber, String password, transactionTypeEnum transactionType, long creditBalance) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.balance = balance;
		this.password = password;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.phoneNumber = phoneNumber;
		this.transactionType = transactionType;
		this.creditBalance = creditBalance;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public long getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(long updatedOn) {
		this.updatedOn = updatedOn;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(long createdOn) {
		this.createdOn = createdOn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
