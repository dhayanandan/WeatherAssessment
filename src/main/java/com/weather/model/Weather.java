package com.weather.model;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="weatherdtls")
public class Weather {
	
	@Id
	private String id;
	private String country;
	private String city;
	private double temperature;
	private double humidity;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date createdDate;


	
	
	public Weather() {
	}
	
	public Weather(String country, String city, double temperature,
			double humidity,Date createdDate) {
		this.country = country;
		this.city = city;
		this.temperature = temperature;
		this.humidity = humidity;
		this.createdDate=createdDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	@JsonProperty("currently")
	public void setMain(Map<String, Object> main) {
		setTemperature(Double.parseDouble(main.get("temperature").toString()));
		setHumidity(Double.parseDouble(main.get("humidity").toString()));
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	


}
