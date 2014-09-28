package com.app.weatherapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

	private static Weather[] weather = new Weather[2];

	public static Weather[] getWeather(String data) throws JSONException {

		String CURR_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
		String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
		weather[0] = getCurrent(new JSONObject(getWeatherData(data, CURR_URL)));
		weather[1] = getForecast(new JSONObject(getWeatherData(data,
				FORECAST_URL)));

		return weather;
	}

	private static Weather getCurrent(JSONObject jObj) throws JSONException {
		JSONObject sysObj;
		Weather weather = new Weather();
		sysObj = getObject("sys", jObj);
		weather.setCountry(getString("country", sysObj));
		weather.setCity(getString("name", jObj));

		JSONArray jArr = jObj.getJSONArray("weather");

		JSONObject JSONWeather = jArr.getJSONObject(0);
		weather.setReprt(getString("main", JSONWeather) + " ("
				+ getString("description", JSONWeather) + ")");

		JSONObject mainObj = getObject("main", jObj);
		weather.setHumidity(getInt("humidity", mainObj));
		weather.setPressure(getInt("pressure", mainObj));
		weather.setTemp(getFloat("temp", mainObj));
		weather.setWind(getFloat("speed", getObject("wind", jObj)));

		return weather;
	}

	private static Weather getForecast(JSONObject jObj) throws JSONException {
		JSONObject sysObj;
		Weather weather = new Weather();

		sysObj = jObj.getJSONArray("list").getJSONObject(5);

		JSONArray jArr = sysObj.getJSONArray("weather");

		JSONObject JSONWeather = jArr.getJSONObject(0);
		weather.setReprt(getString("main", JSONWeather) + " ("
				+ getString("description", JSONWeather) + ")");

		JSONObject mainObj = getObject("main", sysObj);
		weather.setHumidity(getInt("humidity", mainObj));
		weather.setPressure(getInt("pressure", mainObj));
		weather.setTemp(getFloat("temp", mainObj));
		weather.setWind(getFloat("speed", getObject("wind", sysObj)));

		return weather;
	}

	public static String getWeatherData(String location, String url) {
		HttpURLConnection conn = null;
		InputStream is = null;

		try {
			conn = (HttpURLConnection) (new URL(url + location
					+ "&units=imperial")).openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();

			StringBuffer buffer = new StringBuffer();
			is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null)
				buffer.append(line + "\r\n");

			is.close();
			conn.disconnect();
			return buffer.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
			try {
				conn.disconnect();
			} catch (Throwable t) {
			}
		}

		return null;
	}

	private static JSONObject getObject(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getJSONObject(tagName);
	}

	private static String getString(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getString(tagName);
	}

	private static float getFloat(String tagName, JSONObject jObj)
			throws JSONException {
		return (float) jObj.getDouble(tagName);
	}

	private static int getInt(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getInt(tagName);
	}

}
