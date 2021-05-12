package com.chinx.covjabnoti.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.chinx.covjabnoti.model.Subscription;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

}