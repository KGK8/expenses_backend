package com.example.expenses.controllers;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.expenses.models.accountModel;
import com.example.expenses.repos.accountsRepository;
import com.example.expenses.services.accountsServices;
import com.example.expenses.utils.JwtUtils;
import com.example.expenses.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/accounts")
public class accountController {

	private static final Logger logger = LoggerFactory.getLogger(accountController.class);

	@Autowired
	private utils utils;

	@Autowired
	private accountsServices accountsServices;

	@Autowired
	private accountsRepository accountsRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/add")
	public ResponseEntity<?> accountGetMapping(@RequestBody accountModel accountBody) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (accountBody.getEmail() != null && accountBody.getName() != null && accountBody.getPhoneNumber() != null
				&& accountBody.getPassword() != null) {
			resMap = accountsServices.createAccount(accountBody);
			String message = resMap.get("message");
			String status = resMap.get("status");
			HttpStatus codeStatus;
			if (status == "SUCCESS") {
				codeStatus = HttpStatus.OK;
			} else {
				if (message == "USER ALREADY EXIST...!") {
					codeStatus = HttpStatus.FORBIDDEN;
				} else {
					codeStatus = HttpStatus.CONFLICT;
				}
			}
			return ResponseEntity.status(codeStatus).body(resMap);
		} else {
			resMap.put("status", "ERROR");
			resMap.put("message", "INVALID_BODY");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resMap);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> accountLogin(@RequestHeader(HttpHeaders.USER_AGENT) String USERAGENT,
			@RequestBody accountModel loginBody) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (loginBody.getEmail() != null && !loginBody.getEmail().isEmpty() && loginBody.getPassword() != null
				&& !loginBody.getPassword().isEmpty()) {
			accountModel user = accountsServices.makeUserLogin(loginBody);
			if (user == null) {
				resMap.put("status", "ERROR");
				resMap.put("message", "User Not Exist...!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resMap);
			} else if (user.getPassword() != "IN_VALID_PASSWORD") {
				String token = user.getPassword();
				user.setPassword(null);
				return ResponseEntity.ok().header("Authorization", token).body(user);
			} else {
				resMap.put("status", "ERROR");
				resMap.put("message", "In Valid Credentials...!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resMap);
			}
		} else {
			resMap.put("status", "ERROR");
			resMap.put("message", "Email or Password Missing in the body...!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resMap);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String Authorization) {
		logger.info("FIRST LOG");
		String token = Authorization.split(" ")[1];
		accountModel acc = accountsServices.checkUserExist(jwtUtils.validateToken(token));
		System.out.println(acc);
		return ResponseEntity.ok(acc);
	}
}
