package com.example.expenses.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.expenses.models.accountModel;

public interface accountsRepository extends MongoRepository<accountModel, Integer> {

	public accountModel findByEmail(String email);

}
