package com.example.expenses;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.client.MongoClient;

public class dbConnector {
	private MongoClient mongoClient;

	@Autowired
	public void MonngoConnectService(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	public boolean isMongoConnected() {
		try {
			mongoClient.listDatabaseNames();
			System.out.println(mongoClient.listDatabaseNames());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
