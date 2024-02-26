package com.example.expenses.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.example.expenses.models.accountModel;
import com.example.expenses.models.transModel;
import com.example.expenses.models.transactionsModel;
import com.example.expenses.repos.transactionsRepository;
import com.example.expenses.utils.utils;

import enums.transactionEnum;
import enums.transactionTypeEnum;

@Component
public class transactionServices {

	@Autowired
	private accountsServices accountsServices;

	@Autowired
	private transactionsRepository transactionsRepository;

	@Autowired
	private utils utils;

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<transactionsModel> getTransactionsByUserId(String userId) {
		try {
			List<transactionsModel> transactions = transactionsRepository.findAllByUserId(userId);
			return transactions;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public transactionsModel makeTransactions(transactionsModel transactions) {
		accountModel account = accountsServices.getAccountById(transactions.getUserId());
		if (account == null) {
			return null;
		}
		try {
			long timeStamp = utils.getTime();
			long balance = account.getBalance();
			long creditBalance = account.getCreditBalance();
			if (transactions.getTransactionType() == transactionTypeEnum.CREDIT) {
				if (transactions.getType() == transactionEnum.DEBITED) {
					creditBalance = creditBalance - transactions.getAmount();
				} else {
					creditBalance = creditBalance + transactions.getAmount();
				}
				System.out.println("LOG"+creditBalance);
				try {
					accountModel acc = accountsServices.updateSavingsBalance(transactions.getUserId(), "CREDIT",
							creditBalance);
					System.out.println("ACCOUNT BALANCE UPDATED SUCCESSFULLY:" + acc.toString());
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
			} else {
				if (transactions.getType() == transactionEnum.DEBITED) {
					balance = balance - transactions.getAmount();
				} else {
					balance = balance + transactions.getAmount();
				}
				try {
					accountModel acc = accountsServices.updateSavingsBalance(transactions.getUserId(), "SAVINGS",
							balance);
					System.out.println("ACCOUNT BALANCE UPDATED SUCCESSFULLY:" + acc.toString());
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
			}
			transactions.setDate(utils.getDate());
			transactions.setSavingsBalance(balance);
			transactions.setCreditBalance(creditBalance);
			transactions.setCreatedOn(timeStamp);
			transactions.setUpdatedOn(timeStamp);
			transactionsModel trans = this.transactionsRepository.save(transactions);
			return trans;
		} catch (Exception e) {
			return null;
		}
	}

	public List<transModel> getTransactionsByDate(long fromDate, long toDate) {

		System.out.println("DATE" + utils.UnixTimeStampToDate(fromDate));

		MatchOperation match = Aggregation.match(Criteria.where("createdOn").gte(fromDate).lte(toDate));

		GroupOperation group = Aggregation.group("date").count().as("count")
				.sum(ConditionalOperators
						.when(ComparisonOperators.valueOf("$type").equalToValue(transactionEnum.DEBITED))
						.then("$amount").otherwise(0))
				.as("spent")
				.sum(ConditionalOperators
						.when(ComparisonOperators.valueOf("$type").equalToValue(transactionEnum.CREDITED))
						.then("$amount").otherwise(0))
				.as("received").push("$$ROOT").as("documents");
		
		SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdOn"));

		Aggregation aggregation = Aggregation.newAggregation(sort, match, group);

		System.out.println(aggregation);

		AggregationResults<transModel> results = mongoTemplate.aggregate(aggregation, "transactions", transModel.class);
		return results.getMappedResults();
	}
}
