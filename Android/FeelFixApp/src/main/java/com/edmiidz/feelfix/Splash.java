package com.edmiidz.feelfix;

import java.util.Timer;
import java.util.TimerTask;

import com.edmiidz.feelfix.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

public class Splash extends Activity {

    Timer t = new Timer();

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
	//	Timer t = new Timer();
		t.schedule(task,5000);


        ImageButton ib=(ImageButton)findViewById(R.id.splashscreen);
        ib.setOnClickListener(ibLis);
    }
    private OnClickListener ibLis=new OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            //START YOUR ACTIVITY HERE AS
            Intent in = new Intent(getApplicationContext(),Home.class);
            startActivity(in);
            t.cancel();
            finish();
        }
    };

		






}
