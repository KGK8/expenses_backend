package com.example.expenses.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.example.expenses.models.transactionsModel;

@Repository
@EnableMongoRepositories
public interface transactionsRepository extends MongoRepository<transactionsModel, Integer> {

	transactionsModel findById(String id);
	
	List<transactionsModel> findAllByUserId(String userId);
}
