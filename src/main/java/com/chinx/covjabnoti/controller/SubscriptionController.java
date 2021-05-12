package com.chinx.covjabnoti.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinx.covjabnoti.model.Subscription;
import com.chinx.covjabnoti.repository.SubscriptionRepository;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	
	private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@GetMapping
	public ResponseEntity<List<Subscription>> getAllSubscription() {
		log.info("Inside getAllSubscription");
		try {
			List<Subscription> subscriptions = subscriptionRepository.findAll();
			if (subscriptions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(subscriptions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Subscription> getSubscriptionById(@PathVariable("id") String id) {
		log.info("Inside getSubscriptionById");
		try {
			Optional<Subscription> subscription = subscriptionRepository.findById(id);
			if (subscription.isPresent()) {
				return new ResponseEntity<>(subscription.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> createSubscription(@RequestBody Subscription subscription) {
		log.info("Inside createSubscription");
		try {
			log.info("subscription : " + subscription);
			if(subscription.getAge_slot().equals("18") || subscription.getAge_slot().equals("45")) {
				Subscription _subscription = subscriptionRepository.save(subscription);
				return new ResponseEntity<>(_subscription, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<String>("Invalid age_slot - Valid values:(18|45)", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteSubscription(@PathVariable("id") String id) {
		log.info("Inside deleteSubscription");
		try {
			subscriptionRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
