package com.Joseph.CoinmarketcapAPI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


	@GetMapping("/getFeed")
	@CrossOrigin(origins = {"http://localhost:3000/", "https://zecmarketcap.vercel.app/", "https://www.zeccap.com/", "https://www.zecmarketcap.com/"})
	public List<Data> getFeed() throws ParseException {
		long currTime = System.currentTimeMillis();
		//The max number of calls we can make in a day assuming free version of CMC API
		//Worst case scenario to avoid API full usage is 1 call every 4.32 minutes or 259200 miliseconds
		if (currTime - lastRefreshed > 259200) { 
			evictAllCaches();
			lastRefreshed = System.currentTimeMillis();
		}

		return feedService.getFeed();

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
