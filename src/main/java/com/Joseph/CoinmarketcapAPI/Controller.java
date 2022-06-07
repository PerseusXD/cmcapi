package com.Joseph.CoinmarketcapAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import org.json.simple.parser.ParseException;

@RestController
public class Controller {

	private long lastRefreshed = System.currentTimeMillis();
	
	@Autowired
	CacheManager cacheManager;

	@Autowired
	private FeedService feedService;
	
	@Autowired
	private MessageRepository messageRepo;

	@GetMapping("/status")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/","https://zec.vercel.app/"})
	public String status() {
		return "Status is up";
	}

	@GetMapping("/getFeed")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/","https://zec.vercel.app/"})
	public List<ZECCapListing> getFeed() throws ParseException {
		long currTime = System.currentTimeMillis();
		//The max number of calls we can make in a day assuming free version of CMC API
		//Worst case scenario to avoid API full usage is 1 call every 4.32 minutes or 259200 miliseconds
		if (currTime - lastRefreshed > 259200) { 
			evictAllCaches();
			lastRefreshed = System.currentTimeMillis();
		}

		return feedService.getFeed();

	}
	
	@GetMapping("/getLastRefreshed")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/", "https://zec.vercel.app/"})
	public long getLastRefreshed() {
		return lastRefreshed;
	}
	
	@GetMapping("/getMessages")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/", "https://cmc-api-backend.herokuapp.com/", "https://zec.vercel.app/"})
	public List<Post> getMessages() {
		List<Post> messages = messageRepo.findAll();
		return messages;
	}
	
	@GetMapping("/getMostRecentMessage")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/", "https://cmc-api-backend.herokuapp.com/", "https://zec.vercel.app/"})
	public String getMostRecentMessage() {
		List<Post> messages = messageRepo.findAll();
				
		Integer latest = 0;
		int latestPost = 0;
		for(int i=0;i<messages.size();i++) {
			Post curr = messages.get(i);
			if (Integer.parseInt(curr.getpost_time()) > latest) {
				latestPost = i;
				latest = Integer.parseInt(curr.getpost_time());
			}
		}
		
		return messages.get(latestPost).getMessage();
		
	}
	
	@GetMapping("/getAds")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/", "https://cmc-api-backend.herokuapp.com/", "https://zec.vercel.app/"})
	public List<Post> getAds() {
		List<Post> messages = messageRepo.findAll();
		List<Post> toReturn = new ArrayList<Post>();
		for(int i=0;i<messages.size();i++) {
			Post curr = messages.get(i);
			
			if (curr.getMessage().startsWith("ZMC::")){
				toReturn.add(curr);
				
			}
		}
		
		return toReturn;
		
	}
	
	@PostMapping("/addMessage")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/", "https://cmc-api-backend.herokuapp.com/", "https://zec.vercel.app/"})
	public Post addAd(@RequestBody Post toAdd) {
		return messageRepo.save(toAdd);
	}
	
	public void evictAllCaches() {
		cacheManager.getCacheNames().stream()
		.forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

	@Scheduled(fixedRate = 6000)
	public void evictAllcachesAtIntervals() {
		evictAllCaches();
	}
	









}
