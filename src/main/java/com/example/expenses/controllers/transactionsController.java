package com.example.expenses.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expenses.models.transModel;
import com.example.expenses.models.transactionsModel;
import com.example.expenses.services.transactionServices;
import com.example.expenses.utils.JwtUtils;

@RestController
@RequestMapping("/transaction")
public class transactionsController {

	@Autowired
	public JwtUtils jwtUtils;

	@Autowired
	private transactionServices transactionServices;

	@PostMapping("/add")
	public ResponseEntity<?> addTransactions(@RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization,
			@RequestBody transactionsModel transactions) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (Authorization.isEmpty() || Authorization == null) {
			resMap.put("status", "ERROR");
			resMap.put("message", "Missing Authorization");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resMap);
		} else {
			String userId = jwtUtils.validateToken(Authorization);
			transactions.setUserId(userId);
			transactionsModel trans = transactionServices.makeTransactions(transactions);
			if (trans != null) {
				return ResponseEntity.ok(trans);
			} else {
				resMap.put("status", "ERROR");
				resMap.put("message", "Failed To Make Transactions");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resMap);
			}
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getAllTransactions(@RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (Authorization.isEmpty() || Authorization == null) {
			resMap.put("status", "ERROR");
			resMap.put("message", "Missing Authorization");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resMap);
		} else {
			String userId = jwtUtils.validateToken(Authorization);
			try {
				List<transactionsModel> transactions = transactionServices.getTransactionsByUserId(userId);
				if (transactions == null || transactions.isEmpty()) {
					resMap.put("status", "ERROR");
					resMap.put("message", "Unable to get teansactions");
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resMap);
				}
				return ResponseEntity.ok(transactions);
			} catch (Exception e) {
				System.err.println(e);
				resMap.put("status", "ERROR");
				resMap.put("message", "Unable to get transactions");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resMap);
			}
		}
	}

	@GetMapping("/calendar/get")
	public ResponseEntity<?> getTransactionsByDates(@RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization,
			@RequestParam(required = true) long fromDate, @RequestParam(required = true) long toDate) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (Authorization.isEmpty() || Authorization == null) {
			resMap.put("status", "ERROR");
			resMap.put("message", "Missing Authorization");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resMap);
		} else {
			String userId = jwtUtils.validateToken(Authorization);
			return ResponseEntity.ok(transactionServices.getTransactionsByDate(fromDate, toDate));
//			return null;
		}
	}
}
