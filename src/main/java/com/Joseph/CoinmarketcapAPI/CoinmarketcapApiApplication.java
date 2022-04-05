package com.Joseph.CoinmarketcapAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CoinmarketcapApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinmarketcapApiApplication.class, args);
	}

}
