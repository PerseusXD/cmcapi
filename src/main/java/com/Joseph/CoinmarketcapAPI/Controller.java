package com.Joseph.CoinmarketcapAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController
public class Controller {
	
	 private static String apiKey = "f08ddd72-4d03-436f-b829-a5fec48c07d7";
	 
	@GetMapping("/getFeed")
	@CrossOrigin(origins = {"http://localhost:3000", "https://zecmarketcap.vercel.app"})
	public JSONArray getFeed() throws ParseException {
		
		//String uri = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
	    List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
	    paratmers.add(new BasicNameValuePair("start","1"));
	    paratmers.add(new BasicNameValuePair("limit","50"));
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
	    return array;
	}
	
	public static String makeAPICall(String uri, List<NameValuePair> parameters)
		      throws URISyntaxException, IOException {
		    String response_content = "";

		    URIBuilder query = new URIBuilder(uri);
		    query.addParameters(parameters);

		    CloseableHttpClient client = HttpClients.createDefault();
		    HttpGet request = new HttpGet(query.build());

		    request.setHeader(HttpHeaders.ACCEPT, "application/json");
		    request.addHeader("X-CMC_PRO_API_KEY", apiKey);

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
