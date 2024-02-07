package com.example.expenses.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expenses.models.expensesModels;
import com.example.expenses.repos.expensesRepository;

@RestController
@RequestMapping("/expenses")
public class expensesController {

	@Autowired
	private expensesRepository expensesRepository;

	@PostMapping("/add")
	public ResponseEntity<?> addExpenses(@RequestBody expensesModels expenses) {
		if (expenses != null) {
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = currentDate.format(format);
			long createdTime = System.currentTimeMillis();
			expenses.setDate(formattedDate);
			expenses.setPurchasedOn(createdTime);
			try {
				expensesModels save = this.expensesRepository.save(expenses);
				return ResponseEntity.ok(save);
			} catch (Exception e) {
				System.err.println(e);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
			}
		} else {
			Map<String, String> badMap = new HashMap<String, String>();
			badMap.put("error", "Missing Request Body...!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badMap);

		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getAllExpenses() {
//		Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("date").sum("amount").as("totalAmount"));
//		Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("date"));
//		System.out.println(aggregation);
//		AggregationResults<?> result = mongoTemplate.aggregate(aggregation, "expensesTracker",expensesModels.class);
//		System.out.println(result);
		try {
			List<expensesModels> expenses = this.expensesRepository.findAll();
			return ResponseEntity.ok(expenses);
		} catch (Exception e) {
			System.err.println(e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request...!");
		}
	}
}
