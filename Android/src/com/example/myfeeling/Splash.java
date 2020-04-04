package com.example.myfeeling;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		TimerTask task= new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),Home.class);
				startActivity(in);
				finish();
			}
		};
		Timer t = new Timer();
		t.schedule(task,3000);
		
	}



}
