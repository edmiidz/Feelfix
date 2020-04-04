package com.example.myfeeling;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Setting extends Activity {
	Spinner sp1,sp2,sp3;
	
	Button save_setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		sp1=(Spinner)findViewById(R.id.Spinner1);
		sp1.setSelection(2);
		
		sp2=(Spinner)findViewById(R.id.Spinner2);
		sp2.setSelection(3);
		sp3=(Spinner)findViewById(R.id.Spinner3);
		sp3.setSelection(6);
		save_setting=(Button)findViewById(R.id.button_save_setting);
		save_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				  SharedPreferences.Editor editor = preferences.edit();
				  editor.putString("field1", sp1.getSelectedItem().toString());
				  editor.putString("field2", sp2.getSelectedItem().toString());
				editor.putString("field3", sp3.getSelectedItem().toString());
				  editor.commit();
				
				
				Toast.makeText(getApplicationContext(), "Setting has been Saved", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		
	}
}
