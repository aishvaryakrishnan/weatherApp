package com.app.weatherapp;

public class Weather {

	private String country;
	private String city;
	private float temp;
	public float wind;
	private String reprt;
	private float pressure;
	private float humidity;

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

	public String getReprt() {
		return reprt;
	}

	public void setReprt(String reprt) {
		this.reprt = reprt;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public float getWind() {
		return wind;
	}

	public void setWind(float wind) {
		this.wind = wind;
	}

}
