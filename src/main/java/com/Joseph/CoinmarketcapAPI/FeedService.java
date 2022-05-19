package com.Joseph.CoinmarketcapAPI;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Service
public class FeedService {

	//private static String apiKey = "f08ddd72-4d03-436f-b829-a5fec48c07d7";
	private static String apiKeyCMC = "f113f572-0d49-45d6-879a-5b2c0b964a5b";
	private final int LIST_CAP = 80;


	@Cacheable("data")
	public List<Data> getFeed() throws ParseException {		
		//String uri = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
		List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
		paratmers.add(new BasicNameValuePair("start","1"));
		paratmers.add(new BasicNameValuePair("limit","80"));
		paratmers.add(new BasicNameValuePair("convert","USD"));

		String result = "";
		try {
			result = makeAPICall(uri, paratmers);
			//  System.out.println(result);
		} catch (IOException e) {
			System.out.println("Error: cannont access content - " + e.toString());
		} catch (URISyntaxException e) {
			System.out.println("Error: Invalid URL " + e.toString());
		}

		JSONParser parser = new JSONParser();  
		JSONObject json = (JSONObject) parser.parse(result);  
		JSONArray array = (JSONArray) json.get("data");

		List<Data> toReturn = new ArrayList<Data>();

		//get zec price info

		double zcashPrice = 1;
		double zcashPercentChange1h = 1;
		double zcashPercentChange24h = 1;

		DecimalFormat fourDecimals = new DecimalFormat("#.####");
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		
		for(int i=0; i < LIST_CAP; i++) {
			JSONObject current = (JSONObject) array.get(i);
			Data curr = new Data();
			curr.setID(i+1);

			String name = current.get("name").toString();
			curr.setName(name);



			JSONObject quote = (JSONObject) current.get("quote");
			JSONObject usd = (JSONObject) quote.get("USD");
			double price = (double)usd.get("price");
			double percentChange1hr = (double)usd.get("percent_change_1h");
			double percentChange24hr = (double)usd.get("percent_change_24h");
			double marketCap = (double)usd.get("market_cap");
			System.out.println("marketcap for " + name + " is " + marketCap);

			curr.setPriceUSD(Double.parseDouble(twoDecimals.format(price)));
			curr.setOneHrZEC(percentChange1hr);
			curr.setTwentyFourHrZEC(percentChange24hr);
			curr.setMarketcapZEC(marketCap);

			if (name.equals("Zcash")) {
				zcashPrice = price;
				zcashPercentChange1h = percentChange1hr;
				zcashPercentChange24h = percentChange24hr;		
			}
			toReturn.add(curr);
		}

		

		for(Data d: toReturn) {
			d.setPriceZEC(Double.parseDouble(fourDecimals.format(d.getPriceUSD()/zcashPrice)));
			d.setOneHrZEC(Double.parseDouble(twoDecimals.format(d.getOneHrZEC()/zcashPercentChange1h)));
			d.setTwentyFourHrZEC(Double.parseDouble(twoDecimals.format(d.getTwentyFourHrZEC()/zcashPercentChange24h)));
			d.setMarketcapZEC(Double.parseDouble(twoDecimals.format(d.getMarketcapZEC()/zcashPrice)));
		}
		return toReturn;
	}




	public static String makeAPICall(String uri, List<NameValuePair> parameters)
			throws URISyntaxException, IOException {
		String response_content = "";

		URIBuilder query = new URIBuilder(uri);
		query.addParameters(parameters);

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(query.build());

		request.setHeader(HttpHeaders.ACCEPT, "application/json");
		request.addHeader("X-CMC_PRO_API_KEY", apiKeyCMC);

		CloseableHttpResponse response = client.execute(request);

		try {
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			response_content = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}

		return response_content;
	}
}
