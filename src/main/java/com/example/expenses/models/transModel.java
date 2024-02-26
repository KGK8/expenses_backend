package com.example.expenses.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class transModel {
	private String id;
	private long count;
	private long spent;
	private long received;
	private List<transactionsModel> documents;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long getSpent() {
		return spent;
	}
	public void setSpent(long spent) {
		this.spent = spent;
	}
	public long getReceived() {
		return received;
	}
	public void setReceived(long received) {
		this.received = received;
	}
	public List<transactionsModel> getDocuments() {
		return documents;
	}
	public void setDocuments(List<transactionsModel> documents) {
		this.documents = documents;
	}

}
