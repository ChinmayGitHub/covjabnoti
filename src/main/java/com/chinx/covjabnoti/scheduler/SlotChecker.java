package com.chinx.covjabnoti.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.chinx.covjabnoti.model.Slot;
import com.chinx.covjabnoti.model.Subscription;
import com.chinx.covjabnoti.service.SubscriptionService;

@Component
public class SlotChecker {
	private static final Logger log = LoggerFactory.getLogger(SlotChecker.class);
	private static final String cowin_api = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict";
	private static final String wp_api = "https://api.callmebot.com/whatsapp.php";

	@Autowired
	private SubscriptionService subscriptionService;

	@Scheduled(fixedRateString = "${frequency.in.milliseconds}", initialDelay = 5000)
	public void slotCheck() {
		log.info("******* Insid slotCheck *******");

		Set<String> districtIDSet = subscriptionService.getDistrictIDSet();
		log.info("districtIDSet : " + districtIDSet);

		for (String district_id : districtIDSet) {
			log.info("## Processing district_id : " + district_id);
			List<Subscription> subscriptions18 = subscriptionService.findByDistrict_idAndAge_slot(district_id, "18");
			log.info("subscriptions18 : " + subscriptions18.size());
			List<Subscription> subscriptions45 = subscriptionService.findByDistrict_idAndAge_slot(district_id, "45");
			log.info("subscriptions45 : " + subscriptions45.size());

			String date = getDateTimeString();
			String cowin_uri = cowin_api + "?district_id=" + district_id + "&date=" + date;
			log.info("cowin_uri : " + cowin_uri);
			String cowin_response = callCowinAPI(cowin_uri);
			// log.info("cowin_response : " + cowin_response);
			ArrayList<ArrayList<Slot>> availableSlots = getAvailableSlots(cowin_response);
			log.info("availableSlots : " + availableSlots.size());
			ArrayList<Slot> availableSlots18 = availableSlots.get(0);
			ArrayList<Slot> availableSlots45 = availableSlots.get(1);
			log.info("availableSlots18 : " + availableSlots18.size());
			log.info("availableSlots45 : " + availableSlots45.size());

			for (Slot slot : availableSlots18) {
				notifySubscribers(slot, subscriptions18);
				notifySubscribers(slot, subscriptions45);
			}

			for (Slot slot : availableSlots45) {
				notifySubscribers(slot, subscriptions45);
			}

		}

////		374 - sindh
		// 424 - megha
		// 151 - goa
		// 796 - andman

//		final String uri = wp_api + "?phone=+919923163638&text=this+msg+"+i+"+from+code&apikey=393740";
//	    RestTemplate restTemplate = new RestTemplate();
//	    String result = restTemplate.getForObject(uri, String.class);
//	    System.out.println(result);

	}

	private void notifySubscribers(Slot slot, List<Subscription> Subscriptions) {
		for (Subscription subscription : Subscriptions) {
			String phone = subscription.getPhone_no();
			String apikey = subscription.getApi_key();
			String msg = getFormatedMsg(slot);
			final String uri = wp_api + "?phone=" + phone + "&text=" + msg + "&apikey=" + apikey;
			log.info("Notification sent to : " + uri);
		}
	}

	private String getFormatedMsg(Slot slot) {
		String msg = slot.toString();
		return msg.replace(' ', '+');
	}

	private ArrayList<ArrayList<Slot>> getAvailableSlots(String cowin_response) {
		ArrayList<ArrayList<Slot>> availableSlots = new ArrayList<ArrayList<Slot>>();
		ArrayList<Slot> availableSlots18 = new ArrayList<Slot>();
		ArrayList<Slot> availableSlots45 = new ArrayList<Slot>();

		JSONObject response = new JSONObject(cowin_response);

		JSONArray centers = response.getJSONArray("centers");
		for (int i = 0; i < centers.length(); i++) {
			JSONObject center = centers.getJSONObject(i);
			if (center.has("sessions")) {
				JSONArray sessions = center.getJSONArray("sessions");
				for (int j = 0; j < sessions.length(); j++) {
					JSONObject session = sessions.getJSONObject(j);
					if (session.getInt("available_capacity") > 0) {
						Slot slot = new Slot(center.getString("name"), session.getString("date"),
								String.valueOf(session.getInt("min_age_limit")),
								String.valueOf(session.getInt("available_capacity")), session.getString("vaccine"));
						if (String.valueOf(session.getInt("min_age_limit")).equals("18")) {
							availableSlots18.add(slot);
						}
						if (String.valueOf(session.getInt("min_age_limit")).equals("45")) {
							availableSlots45.add(slot);
						}
					}
				}
			}
		}
		availableSlots.add(availableSlots18);
		availableSlots.add(availableSlots45);
		return availableSlots;
	}

	private String callCowinAPI(String cowin_uri) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> cowin_response = restTemplate.exchange(cowin_uri, HttpMethod.GET, entity, String.class);
		return cowin_response.getBody();
	}

	private String getDateTimeString() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		calendar.setTimeInMillis(new Date().getTime());
		return new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(calendar.getTime());
	}

}
