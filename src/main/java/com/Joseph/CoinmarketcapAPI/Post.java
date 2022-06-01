package com.Joseph.CoinmarketcapAPI;

import javax.persistence.*;

@Entity
@Table(name = "ads", schema = "public")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "post_time")
	private String postTime;  //epoch time
	
	public Post() {
		super();
		this.message = "";
		this.postTime = "";
	}
	public Post(String message, String postTime) {
		super();
		this.message = message;
		this.postTime = postTime;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

}
