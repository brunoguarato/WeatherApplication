// Developed By Naresh 

package com.survivingwithandroid.weatherapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.survivingwithandroid.weatherapp.fragment.DayForecastFragment;
import com.survivingwithandroid.weatherapp.fragment.DayForecastFragment1;
import com.survivingwithandroid.weatherapp.model.DayForecast;
import com.survivingwithandroid.weatherapp.model.WeatherForecast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class DailyForecastPageAdapter1 extends FragmentPagerAdapter {

	private int numDays;
	String week_day;
	private FragmentManager fm;
	private WeatherForecast forecast;
	public final static SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
	
	public DailyForecastPageAdapter1(int numDays, FragmentManager fm, WeatherForecast forecast) {
		super(fm);
		this.numDays = numDays;
		this.fm = fm;
		this.forecast = forecast;
		
	}
	
	
	// Page title
	@Override
	public CharSequence getPageTitle(int position) {
		// We calculate the next days adding position to the current date
		Date d = new Date();
		Calendar gc =  new GregorianCalendar();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
		if(sdf.format(gc.getTime()).equals("Tuesday")){
			week_day="bye";
		}else{
		week_day= sdf.format(gc.getTime());
		}
		return week_day;
		
		
	}



	@Override
	public Fragment getItem(int num) {
		DayForecast dayForecast = (DayForecast) forecast.getForecast(1);
		DayForecastFragment1 f = new DayForecastFragment1();
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