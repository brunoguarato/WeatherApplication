
// Developed By Naresh/hari 
package com.survivingwithandroid.weatherapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import com.survivingwithandroid.weatherapp.MainActivity;
import com.survivingwithandroid.weatherapp.fragment.DayForecastFragment;
import com.survivingwithandroid.weatherapp.fragment.DayForecastFragment1;
import com.survivingwithandroid.weatherapp.model.DayForecast;
import com.survivingwithandroid.weatherapp.model.WeatherForecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class DailyForecastPageAdapter extends FragmentPagerAdapter {
	Calendar gc =  new GregorianCalendar();
	String weekday;
	private int numDays;
	private FragmentManager fm;
	private WeatherForecast forecast;
	public final static SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
	
	public DailyForecastPageAdapter(int numDays, FragmentManager fm, WeatherForecast forecast) {
		super(fm);
		this.numDays = numDays;
		this.fm = fm;
		this.forecast = forecast;
		
	}
	
	
	// Page title
	@Override
	public CharSequence getPageTitle(int position) {
		// We calculate the next days adding position to the current date
		MainActivity main = new MainActivity();
		Date d = new Date();
		String lang=main.location;
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 0);
		Log.i("Language is","" +lang);
		if(sdf.format(gc.getTime()).equals("Monday")){
	    weekday="hello";
		}
		return weekday;
		
	}



	@Override
	public Fragment getItem(int num) {
		DayForecast dayForecast = (DayForecast) forecast.getForecast(0);
		DayForecastFragment f = new DayForecastFragment();
		f.setForecast(dayForecast);
		return f;
	}

	/* 
	 * Number of the days we have the forecast
	 */
	@Override
	public int getCount() {
		
		return numDays;
	}

}
