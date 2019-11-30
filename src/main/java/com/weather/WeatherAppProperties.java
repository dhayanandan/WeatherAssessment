package com.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.weather.api")
public class WeatherAppProperties {
	
	
	private String key;
	private String campbellCa;
	private String omahaNe;
	private String austinTx;
	private String nisekoJapan;
	private String naraJapan;
	private String jakartaIndonesia;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCampbellCa() {
		return campbellCa;
	}
	public void setCampbellCa(String campbellCa) {
		this.campbellCa = campbellCa;
	}
	public String getOmahaNe() {
		return omahaNe;
	}
	public void setOmahaNe(String omahaNe) {
		this.omahaNe = omahaNe;
	}
	public String getAustinTx() {
		return austinTx;
	}
	public void setAustinTx(String austinTx) {
		this.austinTx = austinTx;
	}
	public String getNisekoJapan() {
		return nisekoJapan;
	}
	public void setNisekoJapan(String nisekoJapan) {
		this.nisekoJapan = nisekoJapan;
	}
	public String getNaraJapan() {
		return naraJapan;
	}
	public void setNaraJapan(String naraJapan) {
		this.naraJapan = naraJapan;
	}
	public String getJakartaIndonesia() {
		return jakartaIndonesia;
	}
	public void setJakartaIndonesia(String jakartaIndonesia) {
		this.jakartaIndonesia = jakartaIndonesia;
	}

}
