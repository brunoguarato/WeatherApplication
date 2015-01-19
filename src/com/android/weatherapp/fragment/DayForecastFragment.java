// Developed By Naresh /Hari
package com.survivingwithandroid.weatherapp.fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.survivingwithandroid.weatherapp.R;
import com.survivingwithandroid.weatherapp.WeatherHttpClient;

import com.survivingwithandroid.weatherapp.adapter.DailyForecastPageAdapter;
import com.survivingwithandroid.weatherapp.model.DayForecast;



import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DayForecastFragment extends Fragment {
	
	public DayForecast dayForecast;
	private ImageView iconWeather;
	SQLiteDatabase db;
	public String mins;
	public String maxs;
	
	public DayForecastFragment() {}
	
	public void setForecast(DayForecast dayForecast) {
		
		this.dayForecast = dayForecast;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dayforecast_fragment, container, false);
		
		TextView tempView = (TextView) v.findViewById(R.id.tempForecast);
		TextView tempView1 = (TextView) v.findViewById(R.id.tempForecast1);
		//TextView descView = (TextView) v.findViewById(R.id.skydescForecast);
		
		tempView.setText( (int) (dayForecast.forecastTemp.min - 275.15) + "°");
		tempView1.setText( (int) (dayForecast.forecastTemp.max - 275.15)+"°" );
		 maxs=String.valueOf(dayForecast.forecastTemp.max - 275.15);
		 mins=String.valueOf(dayForecast.forecastTemp.min - 275.15);
		
		Log.d("value", mins);
		Log.d("vlue2",maxs);
		
		//descView.setText(dayForecast.weather.currentCondition.getDescr());
		//int min=(int) (dayForecast.forecastTemp.min - 275.15);
		//int max =(int)(dayForecast.forecastTemp.max - 275.15);
		Float min1=dayForecast.forecastTemp.min;
		Toast.makeText(getActivity(),min1.toString(),6000);
		String min=tempView.getText().toString();
		String max=tempView1.getText().toString();
		//log.d("min1",min1.toString());
//		String min=
//		Log.d("min",+(String)max);
	// db.execSQL("insert into table tempforcasting('min','max')");
	  
	//	db.execSQL("insert into tempforcasting values(1,2)");
		
		
		Date d = new Date();
		Calendar gc =  new GregorianCalendar();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_MONTH, 2);
		String day=gc.toString();
		Log.d("Value is", day);
		iconWeather = (ImageView) v.findViewById(R.id.forCondIcon);
		if((dayForecast.forecastTemp.min - 275.15)<=0){
		iconWeather.setImageResource(R.drawable.we_1);
		}else{
			if((dayForecast.forecastTemp.min - 275.15)<=10){
				iconWeather.setImageResource(R.drawable.we_2);
			}else{
				if((dayForecast.forecastTemp.min - 275.15)<=20){
					iconWeather.setImageResource(R.drawable.we_3);
				}else{
					if((dayForecast.forecastTemp.min - 275.15)<=30){
						iconWeather.setImageResource(R.drawable.we_4);
						
					}else{
						if((dayForecast.forecastTemp.min - 275.15)<=40){
							iconWeather.setImageResource(R.drawable.we_5);
						}else{
							if((dayForecast.forecastTemp.min - 275.15)>40){
								iconWeather.setImageResource(R.drawable.we_6);
							}
						}
					}
				}
			}
		}
		// Now we retrieve the weather icon
		/*JSONIconWeatherTask task = new JSONIconWeatherTask();
		task.execute(new String[]{dayForecast.weather.currentCondition.getIcon()});*/
		
		return v;
	}

	
	
	private class JSONIconWeatherTask extends AsyncTask<String, Void, byte[]> {
		
		@Override
		protected byte[] doInBackground(String... params) {
			
			byte[] data = null;
			
			try {
				
				// Let's retrieve the icon
				data = ( (new WeatherHttpClient()).getImage(params[0]));
				
			} catch (Exception e) {				
				e.printStackTrace();
			}
			
			return data;
	}
		
		
		
		
	@Override
		protected void onPostExecute(byte[] data) {			
			super.onPostExecute(data);
			
			if (data != null) {
				Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length); 
				iconWeather.setImageBitmap(img);
			}
		}



  }
}
