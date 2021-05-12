package com.chinx.covjabnoti.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.chinx.covjabnoti.model.Subscription;
import com.chinx.covjabnoti.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	public Set<String> getDistrictIDSet() {
		Set<String> districtIDSet = new HashSet<String>();
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		for (Subscription s : subscriptions) {
			districtIDSet.add(s.getDistrict_id());
		}
		return districtIDSet;
	}

	public List<Subscription> findByDistrict_idAndAge_slot(String district_id, String age_slot) {
		List<Subscription> _subscriptions = new ArrayList<Subscription>();
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		for (Subscription s : subscriptions) {
			if (s.getDistrict_id().equals(district_id) && s.getAge_slot().equals(age_slot)) {
				_subscriptions.add(s);
			}
		}
		return _subscriptions;
	}

}
