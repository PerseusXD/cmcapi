package com.Joseph.CoinmarketcapAPI;

public class Data {

	private int ID;
	private String name;
	private double priceUSD;
	private double priceZEC;
	private double oneHrZEC;
	private double twentyFourHrZEC;
	private double marketcapZEC;

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPriceUSD() {
		return priceUSD;
	}
	public void setPriceUSD(double priceUSD) {
		this.priceUSD = priceUSD;
	}
	public double getPriceZEC() {
		return priceZEC;
	}
	public void setPriceZEC(double priceZEC) {
		this.priceZEC = priceZEC;
	}
	public double getOneHrZEC() {
		return oneHrZEC;
	}
	public void setOneHrZEC(double oneHrZEC) {
		this.oneHrZEC = oneHrZEC;
	}
	public double getTwentyFourHrZEC() {
		return twentyFourHrZEC;
	}
	public void setTwentyFourHrZEC(double twentyFourHrZEC) {
		this.twentyFourHrZEC = twentyFourHrZEC;
	}
	public double getMarketcapZEC() {
		return marketcapZEC;
	}
	public void setMarketcapZEC(double marketcapZEC) {
		this.marketcapZEC = marketcapZEC;
	}
		
}
