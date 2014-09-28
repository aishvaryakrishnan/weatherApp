package com.app.weatherapp;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private EditText location;
	private Button lookup;
	private TextView err;
	private LinearLayout data;
	private TextView reprt;
	private TextView temp;
	private TextView press;
	private TextView wind;
	private TextView humidity;
	private TextView forecstNA;
	private LinearLayout fdata;
	private TextView freprt;
	private TextView ftemp;
	private TextView fpress;
	private TextView fwind;
	private TextView fhumidity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		location = (EditText) findViewById(R.id.city);

		lookup = (Button) findViewById(R.id.lookup);
		lookup.setText("Look up!");
		lookup.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(location.getWindowToken(), 0);
				JSONWeatherTask task = new JSONWeatherTask();
				task.execute(new String[] { location.getText().toString() });
				return true;
			}
		});

		err = (TextView) findViewById(R.id.err);
		err.setText("Location not found");
		err.setVisibility(View.GONE);

		forecstNA = (TextView) findViewById(R.id.forecstna);
		forecstNA.setText("Not available at present");
		forecstNA.setVisibility(View.GONE);

		reprt = (TextView) findViewById(R.id.reprt);
		temp = (TextView) findViewById(R.id.temp);
		humidity = (TextView) findViewById(R.id.humidity);
		press = (TextView) findViewById(R.id.press);
		wind = (TextView) findViewById(R.id.wind);

		data = (LinearLayout) findViewById(R.id.data);
		data.setVisibility(View.GONE);
		freprt = (TextView) findViewById(R.id.freprt);
		ftemp = (TextView) findViewById(R.id.ftemp);
		fhumidity = (TextView) findViewById(R.id.fhumidity);
		fpress = (TextView) findViewById(R.id.fpress);
		fwind = (TextView) findViewById(R.id.fwind);

		fdata = (LinearLayout) findViewById(R.id.fdata);
		fdata.setVisibility(View.GONE);
	}

	private class JSONWeatherTask extends AsyncTask<String, Void, Weather[]> {

		@Override
		protected Weather[] doInBackground(String... params) {
			try {
				return JSONUtil.getWeather(params[0]);
			} catch (JSONException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Weather[] weather) {
			if (weather != null) {
				err.setVisibility(View.GONE);
				data.setVisibility(View.VISIBLE);
				fdata.setVisibility(View.VISIBLE);
				String city = "";
				if (weather[0].getCity() != null
						&& !weather[0].getCity().equals("")) {
					city = weather[0].getCity() + ", ";
				}

				location.setText(city + weather[0].getCountry());
				reprt.setText(weather[0].getReprt());
				temp.setText("" + Math.round((weather[0].getTemp())) + " "
						+ (char) 0x00B0 + "F");
				humidity.setText("" + weather[0].getHumidity() + "%");
				press.setText("" + (weather[0].getPressure()) + " hPa");
				wind.setText("" + Math.round(weather[0].getWind() * 2.2369)
						+ " mph");

				if (weather[1] != null && !weather[1].equals("")) {
					fdata.setVisibility(View.VISIBLE);
					forecstNA.setVisibility(View.GONE);
					freprt.setText(weather[1].getReprt());
					ftemp.setText("" + Math.round((weather[1].getTemp())) + " "
							+ (char) 0x00B0 + "F");
					fhumidity.setText("" + weather[1].getHumidity() + "%");
					fpress.setText("" + (weather[1].getPressure() * 0.014503)
							+ " hPa");
					fwind.setText(""
							+ Math.round(weather[1].getWind() * 2.2369)
							+ " mph");
				} else {
					forecstNA.setVisibility(View.VISIBLE);
					fdata.setVisibility(View.GONE);
				}
			} else {
				err.setVisibility(View.VISIBLE);
				data.setVisibility(View.GONE);
				fdata.setVisibility(View.GONE);
			}

		}
	}

}