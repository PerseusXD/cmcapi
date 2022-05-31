package com.Joseph.CoinmarketcapAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class DBClient {

	private final String url = "jdbc:postgresql://localhost/mydb";
	private final String user = "postgres";
	private final String password = "zcashusergroup";
	
	private static Connection connection = null;
	
	public void init() {
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			if (connection != null) {
				System.out.println("connected!");
			} else {
				System.out.println("failed to connect");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public Post getPinnedAd() {
		
		String query = "SELECT * FROM ads";
		
		try {
		Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        while(rs.next()) {
        	System.out.println(rs.getString(1));
        }
        
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
