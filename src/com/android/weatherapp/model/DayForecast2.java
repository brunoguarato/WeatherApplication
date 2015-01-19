// Developed By Naresh 

package com.survivingwithandroid.weatherapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayForecast2 {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public Weather weather = new Weather();
	public ForecastTemp forecastTemp = new ForecastTemp();
	public long timestamp;
	
	public class ForecastTemp {
		public float day;
		public float min;
		public float max;
		public float night;
		public float eve;
		public float morning;
	}
	
	public String getStringDate() {
		return sdf.format(new Date(timestamp));
	}
}
