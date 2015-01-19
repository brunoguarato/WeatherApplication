// Developed By Naresh /Hari

package com.survivingwithandroid.weatherapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import com.survivingwithandroid.weatherapp.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.survivingwithandroid.weatherapp.adapter.DailyForecastPageAdapter;
import com.survivingwithandroid.weatherapp.adapter.DailyForecastPageAdapter1;
import com.survivingwithandroid.weatherapp.adapter.DailyForecastPageAdapter2;
import com.survivingwithandroid.weatherapp.fragment.DayForecastFragment;
import com.survivingwithandroid.weatherapp.model.DayForecast;
import com.survivingwithandroid.weatherapp.model.Location;
import com.survivingwithandroid.weatherapp.model.Weather;
import com.survivingwithandroid.weatherapp.model.WeatherForecast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	ImageView imageView1;
	// flag for Internet connection status
		Boolean isInternetPresent = false;
		 int count,count2;
		// Connection detector class
		ConnectionDetector cd;
	Button button1;
	private TextView cityText,time;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView windSpeed;
	private TextView windDeg,minmax;
	ListView list;
	String city,status,location_db;
	PagerTitleStrip pager_title_strip1,pager_title_strip,pager_title_strip2;
	DayForecast dayForecast;
	private TextView unitTemp,date_mon;
	String date,temperature,l,date1,date2;
	private TextView hum,week_day1,week_day2,week_day3;
	public String language,location,lll;
	private ImageView imgView;
	SQLiteDatabase db;
	private static String forecastDaysNum = "3",pagerdays,pagerdays1,pagerdays2;
	private ViewPager pager,pager1,pager2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        // creating table
		unitTemp = (TextView) findViewById(R.id.unittemp);
		week_day1 = (TextView) findViewById(R.id.week_day1);
		week_day2 = (TextView) findViewById(R.id.week_day2);
		week_day3 = (TextView) findViewById(R.id.week_day3);
		//button1 = (Button) findViewById(R.id.button1);
		 //language="English";
		// temperature="Celsius";
		//unitTemp.setText("O");
		
		//new DayForecastFragment(); 
		//int min= (int) (dayForecast.forecastTemp.min - 275.15);
		// max=  (int) (dayForecast.forecastTemp.max - 275.15);
		//unitTemp.setText( (int) (dayForecast.forecastTemp.min - 275.15));
		db= openOrCreateDatabase("weather1.db", MODE_PRIVATE, null);
		db.execSQL("create table if not exists tempforcasting(location varchar,temperature varchar,language varchar)");
		db.execSQL("create table if not exists tempforcasting2(temp varchar,minmax varchar,location varchar)"); 
		//DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);
		//db.execSQL("insert into tempforcasting values('"+min+"','"+max+"')");
		db.execSQL("delete from tempforcasting where location='null'");
		//db.execSQL("insert into tempforcasting values('"+value+"','"+vlue2+"')");
		imageView1 = (ImageView) findViewById(R.id.imageView1);
        String lang = l;
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
		Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-BoldItalic.ttf");
		Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		Typeface font3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		cityText = (TextView) findViewById(R.id.cityText);
		date_mon = (TextView) findViewById(R.id.date_mon);
		temp = (TextView) findViewById(R.id.temp);
		minmax = (TextView) findViewById(R.id.minmax);
		time = (TextView) findViewById(R.id.time);
		unitTemp = (TextView) findViewById(R.id.unittemp);
		//button1 = (Button) findViewById(R.id.button1);
		//pager_title_strip=(PagerTitleStrip) findViewById(R.id.pager_title_strip);
		/*pager_title_strip1=(PagerTitleStrip) findViewById(R.id.pager_title_strip1);
		pager_title_strip2=(PagerTitleStrip) findViewById(R.id.pager_title_strip2);*/
		unitTemp.setText("O");
		//condDescr = (TextView) findViewById(R.id.skydesc);
		
		pager = (ViewPager) findViewById(R.id.pager);
		pager1 = (ViewPager) findViewById(R.id.pager1);
		pager2 = (ViewPager) findViewById(R.id.pager2);
		
		/*pager_title_strip.setTextSpacing(80);
		pager_title_strip.setNonPrimaryAlpha(0);*/
		/*pager_title_strip1.setNonPrimaryAlpha(0);
		pager_title_strip2.setNonPrimaryAlpha(0);*/
		 pagerdays= pager.toString();
		 pagerdays1= pager1.toString();
		 pagerdays2= pager2.toString();
		 temp.setTypeface(font);
		 unitTemp.setTypeface(font);
		 date_mon.setTypeface(font3);
		 cityText.setTypeface(font3);
		 time.setTypeface(font2);
		 
		 SimpleDateFormat de,dem,ti;
		 de= new SimpleDateFormat("d");
		 dem= new SimpleDateFormat("'day' MMMM");
		 ti= new SimpleDateFormat("HH:mm");
		 date = de.format(Calendar.getInstance().getTime());
		 date1 = dem.format(Calendar.getInstance().getTime());
		 date2 = ti.format(Calendar.getInstance().getTime());
		 time.setText(date2);
		 
		 cd = new ConnectionDetector(getApplicationContext());
		 isInternetPresent = cd.isConnectingToInternet();
		 Intent intent = getIntent();
			language = intent.getStringExtra("lang");
			temperature = intent.getStringExtra("temp");
			location = intent.getStringExtra("location");
			location_db=intent.getStringExtra("location");
			//Toast.makeText(getBaseContext(),"location is"+location, Toast.LENGTH_SHORT).show();
			
			// check for Internet status
			if (isInternetPresent) {
				// Internet Connection is Present
				// make HTTP requests
				Log.d("internet status present", "");
				
				//db.execSQL("delete from tempforcasting where location='null'");
			//	ORDER BY Id DSC
				Cursor c2=db.rawQuery("SELECT * from tempforcasting",null);
				 count2 = c2.getCount();
				// c2.moveToLast();
				 if(location_db==0){
					 
					 location="uberlandia";
					 language="English";
					 temperature="Celsius";
				 }
				 else{
				 if(count2==0)
					 //(location,temperature,language)
				{
					 db.execSQL("insert into tempforcasting(location,temperature,language) values('"+location_db+"','"+temperature+"','"+language+"')");
				}else
				{
					location="uberlandia";
					 language="English";
					 temperature="Celsius";
				 }}
				status="ok";
				//Toast.makeText(getBaseContext()," status OK ,..", Toast.LENGTH_SHORT).show();
				
			
		 
		
		// Toast.makeText(MainActivity.this, date1, Toast.LENGTH_SHORT).show();

		 Cursor c=db.rawQuery("SELECT * from tempforcasting",null);
		 c.moveToLast();
		 count = c.getCount();
		 count=count-1;
		 if(count>0)
		 {
			
		 location =c.getString(c.getColumnIndex("location"));
		 }
		// language=c.getString(c.getColumnIndex("language"));
		 //b1.setText(c.getString(c.getColumnIndex("min")));
		//Log.d(, msg)
		// Toast.makeText(getBaseContext(),"hi===="+location,Toast.LENGTH_SHORT).show();
		if(count<=0){
			
			 location="uberlandia";
			 language="English";
			 temperature="Celsius";
		}else{
			count=count-1;
			c.moveToLast();
			location =c.getString(c.getColumnIndex("location"));
			
			//Toast.makeText(getBaseContext(),"hi"+location,Toast.LENGTH_SHORT).show();
			language=c.getString(c.getColumnIndex("language"));
			
			temperature=c.getString(c.getColumnIndex("temperature"));
		//	Toast.makeText(getBaseContext(),"hi"+location+language+temperature,Toast.LENGTH_SHORT).show();
			 
		}
		Log.i("location is","" +location);
		city=location;
		if(language.equals("Portuguese")){
			l="pt";
			if(date1.equals("day November")){
				date_mon.setText(date+" de Novembro");
			}else{
				if(date1.equals("day December")){
					date_mon.setText(date+" de Dezembro");
				}else{
					if(date1.equals("day January")){
						date_mon.setText(date+" de Janeiro");
					}else{
						if(date1.equals("day February")){
							date_mon.setText(date+" de Fevereiro");
						}else{
							if(date1.equals("day March")){
								date_mon.setText(date+" de Março");
							}else{
								if(date1.equals("day April")){
									date_mon.setText(date+" de Abril");
								}else{
									if(date1.equals("day May")){
										date_mon.setText(date+" de Maio");
									}else{
										if(date1.equals("day June")){
											date_mon.setText(date+" de Junho");
										}else{
											if(date1.equals("day July")){
												date_mon.setText(date+" de Julho");
											}else{
												if(date1.equals("day August")){
													date_mon.setText(date+" de Agosto");
												}else{
													if(date1.equals("day September")){
														date_mon.setText(date+" de Setembro");
													}else{
														if(date1.equals("day October")){
															date_mon.setText(date+" de Outubro");
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
		}else{
			if(language.equals("Spanish")){
				l="es";
				if(date1.equals("day November")){
					date_mon.setText(date+" de Noviembre");
				}else{
					if(date1.equals("day December")){
						date_mon.setText(date+" de Deciembre");
					}else{
						if(date1.equals("day January")){
							date_mon.setText(date+" de Enero");
						}else{
							if(date1.equals("day February")){
								date_mon.setText(date+" de Febrero");
							}else{
								if(date1.equals("day March")){
									date_mon.setText(date+" de Marzo");
								}else{
									if(date1.equals("day April")){
										date_mon.setText(date+" de Abril");
									}else{
										if(date1.equals("day May")){
											date_mon.setText(date+" de Mayo");
										}else{
											if(date1.equals("day June")){
												date_mon.setText(date+" de Junio");
											}else{
												if(date1.equals("day July")){
													date_mon.setText(date+" de Julio");
												}else{
													if(date1.equals("day August")){
														date_mon.setText(date+" de Agosto");
													}else{
														if(date1.equals("day September")){
															date_mon.setText(date+" de Septiembre");
														}else{
															if(date1.equals("day October")){
																date_mon.setText(date+" de Octubre");
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}else{
				if(language.equals("English")){
					l="en";
					date_mon.setText(date+" "+date1);
				}
			}
		}
		
		lll=language;
		
		
		
		 //Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
		 
		 //date_mon.setText(date);
		 
		
		///////*************************** for days 
	
		/*
		condDescr = (TextView) findViewById(R.id.condDescr);
		
		hum = (TextView) findViewById(R.id.hum);
		press = (TextView) findViewById(R.id.press);
		windSpeed = (TextView) findViewById(R.id.windSpeed);
		windDeg = (TextView) findViewById(R.id.windDeg);
		
		*/
		
		
		JSONWeatherTask task = new JSONWeatherTask();
		task.execute(new String[]{city,lang});
		
	
		
		JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
		task1.execute(new String[]{city,lang, forecastDaysNum});
		
		cityText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(MainActivity.this,SecondActivity.class);	
				startActivity(i);
			}
		
		});
			}else{
				Toast.makeText(MainActivity.this, "Internet Connection is not avaliable", Toast.LENGTH_LONG).show();
				String loc,tem,mnx;
				Cursor c=db.rawQuery("SELECT * from tempforcasting2;",null);
				 count = c.getCount();
				 c.moveToFirst();
				 tem =c.getString(c.getColumnIndex("temp"));
				 mnx =c.getString(c.getColumnIndex("minmax"));
				 loc =c.getString(c.getColumnIndex("location"));
				 
				 temp.setText(tem);
				 minmax.setText(mnx);
				 cityText.setText(loc);
			}
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
		
		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0], params[1]));

			try {
				weather = JSONWeatherParser.getWeather(data);
				System.out.println("Weather ["+weather+"]");
				// Let's retrieve the icon
				weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
				
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			return weather;
		
	}
		
		
	@Override
	protected void onPostExecute(Weather weather) {			
			super.onPostExecute(weather);
			
			/*if (weather.iconData != null && weather.iconData.length > 0) {
				Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length); 
				imgView.setImageBitmap(img);
			}*/
			
			minmax.setText(Html.fromHtml("<b>"+Math.round((weather.temperature.getMinTemp() - 275.15))+"</b>"+"° Min "+Math.round((weather.temperature.getMaxTemp() - 275.15))+"° Max"));
			cityText.setText(weather.location.getCity() /*+ "," + weather.location.getCountry()*/);
			if(temperature.equals("Celsius")){
			temp.setText("" + Math.round((weather.temperature.getTemp() - 275.15)));
			}else{
				if(temperature.equals("FarenHeit")){
					temp.setText("" + Math.round(((weather.temperature.getTemp() - 275.15)*2)+30));
				}
			}
			//condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
			/*
			
			temp.setText("" + Math.round((weather.temperature.getTemp() - 275.15)) + "°C");
			hum.setText("" + weather.currentCondition.getHumidity() + "%");
			press.setText("" + weather.currentCondition.getPressure() + " hPa");
			windSpeed.setText("" + weather.wind.getSpeed() + " mps");
			windDeg.setText("" + weather.wind.getDeg() + "°");
			*/	
			if(Math.round((weather.temperature.getTemp() - 275.15)) <=0){
				 imageView1.setImageResource(R.drawable.we_1);
			 }else{
				 if(Math.round((weather.temperature.getTemp() - 275.15)) <=10){
					 imageView1.setImageResource(R.drawable.we_2);
				 }else{
					 if(Math.round((weather.temperature.getTemp() - 275.15)) <=20){
						 imageView1.setImageResource(R.drawable.we_3);
					 }
					 else{
						 if(Math.round((weather.temperature.getTemp() - 275.15)) <=30){
							 imageView1.setImageResource(R.drawable.we_4);
						 }else{
							 if(Math.round((weather.temperature.getTemp() - 275.15)) <= 40){
								 imageView1.setImageResource(R.drawable.we_5);
							 }else{
								 if(Math.round((weather.temperature.getTemp() - 275.15)) > 40){
									 imageView1.setImageResource(R.drawable.we_6);
								 }
							 }
						 }
					 }
					 
				 }
			 }
		}



  }
	
	
	private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {
		
		@Override
		protected WeatherForecast doInBackground(String... params) {
			
			String data = ( (new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
			WeatherForecast forecast = new WeatherForecast();
			try {
				forecast = JSONWeatherParser.getForecastWeather(data);
				System.out.println("Weather ["+forecast+"]");
				// Let's retrieve the icon
				//weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
				//Toast.makeText(MainActivity.this, forecast.toString(), Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			return forecast;
		
	}
		
		
		
		
	@Override
		protected void onPostExecute(WeatherForecast forecastWeather) {			
			super.onPostExecute(forecastWeather);
			Calendar gc =  new GregorianCalendar();
			//gc.add(GregorianCalendar.DAY_OF_MONTH, 2);
			DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);
			
			DailyForecastPageAdapter1 adapter1 = new DailyForecastPageAdapter1(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);
			DailyForecastPageAdapter2 adapter2 = new DailyForecastPageAdapter2(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);
			
			
			if(language.equals("Portuguese")){
				l="pt";
				if(adapter1.sdf.format(gc.getTime()).equals("Monday")){
			          week_day1.setText("Segunda");
				}else{
					if(adapter1.sdf.format(gc.getTime()).equals("Tuesday")){
				          week_day1.setText("Terça");
					}else{
						if(adapter1.sdf.format(gc.getTime()).equals("Wednesday")){
					          week_day1.setText("Quarta");
						}else{
							if(adapter1.sdf.format(gc.getTime()).equals("Thursday")){
						          week_day1.setText("Quinta");
							}else{
								if(adapter1.sdf.format(gc.getTime()).equals("Friday")){
							          week_day1.setText("Sexta");
								}else{
									if(adapter1.sdf.format(gc.getTime()).equals("Saturday")){
								          week_day1.setText("Sábado");
									}else{
										if(adapter1.sdf.format(gc.getTime()).equals("Sunday")){
									          week_day1.setText("Domingo");
										}
									}
								}
							}
						}
					}
				}
			}else{
				if(language.equals("Spanish")){
					l="es";
					if(adapter1.sdf.format(gc.getTime()).equals("Monday")){
				          week_day1.setText("Lunes");
					}else{
						if(adapter1.sdf.format(gc.getTime()).equals("Tuesday")){
					          week_day1.setText("Martes");
						}else{
							if(adapter1.sdf.format(gc.getTime()).equals("Wednesday")){
						          week_day1.setText("Miércoles");
							}else{
								if(adapter1.sdf.format(gc.getTime()).equals("Thursday")){
							          week_day1.setText("Jueves");
								}else{
									if(adapter1.sdf.format(gc.getTime()).equals("Friday")){
								          week_day1.setText("Viernes");
									}else{
										if(adapter1.sdf.format(gc.getTime()).equals("Saturday")){
									          week_day1.setText("Sábado");
										}else{
											if(adapter1.sdf.format(gc.getTime()).equals("Sunday")){
										          week_day1.setText("Domingo");
											}
										}
									}
								}
							}
						}
					}
				}else{
					if(language.equals("English")){
						l="en";
						week_day1.setText(adapter1.sdf.format(gc.getTime()));
					}
				}
			}
			
			
			
			
			gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
			
			if(language.equals("Portuguese")){
				l="pt";
				if(adapter1.sdf.format(gc.getTime()).equals("Monday")){
			          week_day2.setText("Segunda");
				}else{
					if(adapter1.sdf.format(gc.getTime()).equals("Tuesday")){
				          week_day2.setText("Terça");
					}else{
						if(adapter1.sdf.format(gc.getTime()).equals("Wednesday")){
					          week_day2.setText("Quarta");
						}else{
							if(adapter1.sdf.format(gc.getTime()).equals("Thursday")){
						          week_day2.setText("Quinta");
							}else{
								if(adapter1.sdf.format(gc.getTime()).equals("Friday")){
							          week_day2.setText("Sexta");
								}else{
									if(adapter1.sdf.format(gc.getTime()).equals("Saturday")){
								          week_day2.setText("Sábado");
									}else{
										if(adapter1.sdf.format(gc.getTime()).equals("Sunday")){
									          week_day2.setText("Domingo");
										}
									}
								}
							}
						}
					}
				}
			}else{
				if(language.equals("Spanish")){
					l="es";
					if(adapter1.sdf.format(gc.getTime()).equals("Monday")){
				          week_day2.setText("Lunes");
					}else{
						if(adapter1.sdf.format(gc.getTime()).equals("Tuesday")){
					          week_day2.setText("Martes");
						}else{
							if(adapter1.sdf.format(gc.getTime()).equals("Wednesday")){
						          week_day2.setText("Miércoles");
							}else{
								if(adapter1.sdf.format(gc.getTime()).equals("Thursday")){
							          week_day2.setText("Jueves");
								}else{
									if(adapter1.sdf.format(gc.getTime()).equals("Friday")){
								          week_day2.setText("Viernes");
									}else{
										if(adapter1.sdf.format(gc.getTime()).equals("Saturday")){
									          week_day2.setText("Sábado");
										}else{
											if(adapter1.sdf.format(gc.getTime()).equals("Sunday")){
										          week_day2.setText("Domingo");
											}
										}
									}
								}
							}
						}
					}
				}else{
					if(language.equals("English")){
						l="en";
						week_day2.setText(adapter1.sdf.format(gc.getTime()));
					}
				}
			}
			
			
			gc.add(GregorianCalendar.DAY_OF_MONTH, 1);
			
			if(language.equals("Portuguese")){
				l="pt";
				if(adapter1.sdf.format(gc.getTime()).equals("Monday")){
			          week_day3.setText("Segunda");
				}else{
					if(adapter1.sdf.format(gc.getTime()).equals("Tuesday")){
				          week_day3.setText("Terça");
					}else{
						if(adapter1.sdf.format(gc.getTime()).equals("Wednesday")){
					          week_day3.setText("Quarta");
						}else{
							if(adapter1.sdf.format(gc.getTime()).equals("Thursday")){
						          week_day3.setText("Quinta");
							}else{
								if(adapter1.sdf.format(gc.getTime()).equals("Friday")){
							          week_day3.setText("Sexta");
								}else{
									if(adapter1.sdf.format(gc.getTime()).equals("Saturday")){
								          week_day3.setText("Sábado");
									}else{
										if(adapter1.sdf.format(gc.getTime()).equals("Sunday")){
									          week_day3.setText("Domingo");
										}
									}
								}
							}
						}
					}
				}
			}else{
				if(language.equals("Spanish")){
					l="es";
					if(adapter1.sdf.format(gc.getTime()).equals("Monday")){
				          week_day3.setText("Lunes");
					}else{
						if(adapter1.sdf.format(gc.getTime()).equals("Tuesday")){
					          week_day3.setText("Martes");
						}else{
							if(adapter1.sdf.format(gc.getTime()).equals("Wednesday")){
						          week_day3.setText("Miércoles");
							}else{
								if(adapter1.sdf.format(gc.getTime()).equals("Thursday")){
							          week_day3.setText("Jueves");
								}else{
									if(adapter1.sdf.format(gc.getTime()).equals("Friday")){
								          week_day3.setText("Viernes");
									}else{
										if(adapter1.sdf.format(gc.getTime()).equals("Saturday")){
									          week_day3.setText("Sábado");
										}else{
											if(adapter1.sdf.format(gc.getTime()).equals("Sunday")){
										          week_day3.setText("Domingo");
											}
										}
									}
								}
							}
						}
					}
				}else{
					if(language.equals("English")){
						l="en";
						week_day3.setText(adapter1.sdf.format(gc.getTime()));
					}
				}
			}
			pager.setAdapter(adapter);
			if(status=="ok"){
				String loc_db;
				Cursor c=db.rawQuery("SELECT location from tempforcasting;",null);
				 count = c.getCount();
				 c.moveToLast();
				 if(count>0){
				 loc_db =c.getString(c.getColumnIndex("location"));}
				//Toast.makeText(getBaseContext()," status OK ,.."+location, Toast.LENGTH_SHORT).show();
			//db.execSQL("delete from tempforcasting2");
		//	db.execSQL("delete from tempforcasting");
			// (location,temperature,language)
			db.execSQL("insert into tempforcasting2 values('"+temp.getText().toString()+"','"+minmax.getText().toString()+"','"+location+"')");
			}
			pager1.setAdapter(adapter1);
			pager2.setAdapter(adapter2);
			//db.execSQL("delete from tempforcasting");
			//week_day2.setText(adapter.sdf.format(gc.getTime()));
			
		}
	}
	

}
