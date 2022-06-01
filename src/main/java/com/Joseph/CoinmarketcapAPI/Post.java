package com.Joseph.CoinmarketcapAPI;

import javax.persistence.*;

@Entity
@Table(name = "ads", schema = "public")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "post_time")
	private String post_time;  //epoch time
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "amount")
	private Integer amount;
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Post() {
		super();
		this.message = "";
		this.post_time = "";
	}
	public Post(String message, String post_time, Integer amount) {
		super();
		this.message = message;
		this.post_time = post_time;
		this.amount = amount;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getpost_time() {
		return post_time;
	}
	public void setPostTime(String post_time) {
		this.post_time = post_time;
	}

}
