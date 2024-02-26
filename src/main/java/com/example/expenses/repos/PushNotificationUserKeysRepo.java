package com.example.expenses.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.expenses.models.PushNotificationsModel;

public interface PushNotificationUserKeysRepo extends MongoRepository<PushNotificationsModel, Integer> {

	PushNotificationsModel findByUserId(String userId);

}
