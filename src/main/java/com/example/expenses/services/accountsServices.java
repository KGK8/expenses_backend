package com.example.expenses.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.expenses.models.accountModel;
import com.example.expenses.repos.accountsRepository;
import com.example.expenses.utils.JwtUtils;
import com.example.expenses.utils.utils;

public class accountsServices {

	@Autowired
	private accountsRepository accountsRepository;

	@Autowired
	private utils utils;

	@Autowired
	private JwtUtils jwtUtils;

	public accountModel checkUserExist(String email) {
		System.out.println("ASD"+email);
		try {
			accountModel user = accountsRepository.findByEmail(email);
			System.out.println(user);
			return user;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	public Map<String, String> createAccount(accountModel accountBody) {
		Map<String, String> responseMap = new HashMap<String, String>();
		if (checkUserExist(accountBody.getEmail()) == null) {
			String passwordCheck = utils.checkPasswordStrength(accountBody.getPassword());
			if (passwordCheck == "OK") {
				accountBody.setCreatedOn(0);
				accountBody.setCreatedOn(utils.getTime());
				accountBody.setUpdatedOn(utils.getTime());
				String encyPassword = utils.encryptPassword(accountBody.getPassword());
				if (encyPassword != "" || !encyPassword.isEmpty()) {
					accountBody.setPassword(encyPassword);
				} else {
					responseMap.put("status", "ERROR");
					responseMap.put("message", "UNABLE TO ENCRYPT PASSWORD");
					return responseMap;
				}
				try {
					accountModel saveAccount = this.accountsRepository.save(accountBody);
					saveAccount.setPassword(null);
					responseMap.put("status", "SUCCESS");
					responseMap.put("message", "USER SAVED SUCCESSFULLY");
				} catch (Exception e) {
					System.err.println(e);
					responseMap.put("status", "ERROR");
					responseMap.put("message", "UNABLE TO SAVE USER IN DB...!");
				}
			} else {
				responseMap.put("status", "ERROR");
				responseMap.put("message", passwordCheck);
			}
		} else {
			responseMap.put("status", "ERROR");
			responseMap.put("message", "USER ALREADY EXIST...!");
		}
		return responseMap;
	}

	public accountModel makeUserLogin(accountModel loginBody) {
		accountModel user = checkUserExist(loginBody.getEmail());
		String password = utils.encryptPassword(loginBody.getPassword());
		System.out.println(password);
		if (user != null) {
			System.out.println(user.getPassword());
			if (user.getPassword().contentEquals(password)) {
				String jwtToken = "Bearer " + jwtUtils.generateJWTToken(user.getEmail(), user.getId());
				user.setPassword(jwtToken);
				return user;
			} else {
				user.setPassword("IN_VALID_PASSWORD");
				return user;
			}
		} else {
			return null;
		}
	}
}
