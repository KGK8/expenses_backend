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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.expenses.models.PushNotificationsModel;
import com.example.expenses.models.expensesModels;
import com.example.expenses.repos.PushNotificationUserKeysRepo;
import com.example.expenses.repos.expensesRepository;
import com.example.expenses.utils.utils;

@RestController
public class AppController {

	@Autowired
	private PushNotificationUserKeysRepo pushNotificationUserKeysRepo;
	
	@Autowired
	private utils utils;

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

	public PushNotificationsModel findDeviceKeysExistForUser(String userId) {
		try {
			PushNotificationsModel keys = this.pushNotificationUserKeysRepo.findByUserId(userId);
			return keys;
		} catch (Exception e) {
			return null;
		}
	}

	@PutMapping("/notifications/subscribe/{userId}")
	public ResponseEntity<?> saveUserDeviceKeys(@PathVariable String userId,
			@RequestBody PushNotificationsModel userKeys) {
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing user Id");
		}
		userKeys.setUserId(userId);

		PushNotificationsModel userDeviceKeys = findDeviceKeysExistForUser(userId);

		if (userDeviceKeys != null) {
			userDeviceKeys.setUpdatedOn(utils.getTime());
			userDeviceKeys.setDeviceKeys(userKeys.getDeviceKeys());
			try {
				PushNotificationsModel updatedKeys = pushNotificationUserKeysRepo.save(userDeviceKeys);
				return ResponseEntity.ok(updatedKeys);
			} catch (Exception e) {
				System.out.println("FAILED TO UPDATE THE KEYS");
				return null;
			}
		} else {
			System.out.println(userKeys.getDeviceKeys());
			try {
				long unixTime = utils.getTime();
				userKeys.setCreatedOn(unixTime);
				userKeys.setUpdatedOn(unixTime);
				PushNotificationsModel keys = this.pushNotificationUserKeysRepo.save(userKeys);
				return ResponseEntity.ok(keys);
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("FAILED TO SAVE THE KEYS");
				return null;
			}
		}
	}
}
