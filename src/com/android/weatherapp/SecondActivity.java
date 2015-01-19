package com.survivingwithandroid.weatherapp;

// Developed By Naresh /Hari



import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 
public class SecondActivity extends Activity {
 
  private Spinner spinner1, spinner2;
  EditText editText1;
  TextView textView1,textView2;
  Button okay;
 
  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.secondactivity);
 
	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
	addListenerOnButton();
	addListenerOnSpinnerItemSelection();
	okay=(Button)findViewById(R.id.button1);
	editText1=(EditText)findViewById(R.id.editText1);
	spinner1=(Spinner)findViewById(R.id.spinner1);
	textView1=(TextView)findViewById(R.id.textView1);
	textView2=(TextView)findViewById(R.id.textView2);
	spinner2=(Spinner)findViewById(R.id.spinner2);
	
	textView1.setTypeface(font);
	textView2.setTypeface(font);
	okay.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String lang=spinner1.getSelectedItem().toString();
			String temp=spinner2.getSelectedItem().toString();
			String location= editText1.getText().toString();
			
			Intent i = new Intent(SecondActivity.this,MainActivity.class);
			i.putExtra("lang", lang);
			i.putExtra("temp", temp);
			i.putExtra("location", location);
			startActivity(i);
		}
	});
	
  }
 
  // add items into spinner dynamically
  public void addListenerOnSpinnerItemSelection1() {
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(new CustomOnSelected2());
	  }
	 
 
  public void addListenerOnSpinnerItemSelection() {
	spinner1 = (Spinner) findViewById(R.id.spinner1);
	spinner1.setOnItemSelectedListener(new CustomOnSelected());
  }
 
  // get the selected dropdown list value
  public void addListenerOnButton() {
 
	spinner1 = (Spinner) findViewById(R.id.spinner1);
	spinner2 = (Spinner) findViewById(R.id.spinner2);
	 {
 
	  
 
	};
  }
}