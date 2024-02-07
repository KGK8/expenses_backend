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
import org.springframework.web.bind.annotation.RestController;

import com.example.expenses.models.expensesModels;
import com.example.expenses.repos.expensesRepository;

@RestController
public class AppController {

	@GetMapping("/")
	public ResponseEntity<Map<String, String>> Home(String args[]) {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(format);
		long createdTime = System.currentTimeMillis();
		System.out.println(createdTime);
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("message", formattedDate);
		return ResponseEntity.ok(responseMap);
	}
}
