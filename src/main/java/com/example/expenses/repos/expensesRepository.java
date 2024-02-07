package com.example.expenses.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.expenses.models.expensesModels;

@Repository
public interface expensesRepository extends MongoRepository<expensesModels, Integer>{
	
}
