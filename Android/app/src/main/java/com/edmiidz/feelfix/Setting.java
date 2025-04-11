package com.edmiidz.feelfix;

import com.edmiidz.feelfix.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.AdapterView.*;

public class Setting extends AppCompatActivity {
    Spinner sp1,sp2,sp3,sp4;
    String field1,field2,field3,field4;


    Button save_setting;


    private ArrayList<String> fieldValueList;


    protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		 
		//String compareValue= preferences.getString("field1", "from1to10");
		sp1=(Spinner)findViewById(R.id.Spinner1);
		sp2=(Spinner)findViewById(R.id.Spinner2);
		sp3=(Spinner)findViewById(R.id.Spinner3);
		sp4=(Spinner)findViewById(R.id.Spinner4);

            ArrayAdapter<CharSequence> adapternames= ArrayAdapter.createFromResource(this, R.array.fieldname, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adaptervalues= ArrayAdapter.createFromResource(this, R.array.fieldvalues, android.R.layout.simple_spinner_item);
        adapternames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sp1.setAdapter(adapternames);
	    if (!preferences.getString("field1", "from1to10").equals(null)) {
	        int spinnerPosition = adaptervalues.getPosition(preferences.getString("field1", "from1to10"));
	        sp1.setSelection(spinnerPosition);
	        spinnerPosition = 0;
	    }
	    sp2.setAdapter(adapternames);
	    if (!preferences.getString("field2", "from1to10").equals(null)) {
	        int spinnerPosition = adaptervalues.getPosition(preferences.getString("field2", "Feeling"));
	        sp2.setSelection(spinnerPosition);
	        spinnerPosition = 0;
	    }
	    sp3.setAdapter(adapternames);
	    if (!preferences.getString("field3", "from1to10").equals(null)) {
	        int spinnerPosition = adaptervalues.getPosition(preferences.getString("field3", "feelingTime"));
	        sp3.setSelection(spinnerPosition);
	        spinnerPosition = 0;
	    }
	    sp4.setAdapter(adapternames);
	    if (!preferences.getString("field4", "from1to10").equals(null)) {
	        int spinnerPosition = adaptervalues.getPosition(preferences.getString("field4", "mainaffectedinstinct"));
	        sp4.setSelection(spinnerPosition);
	        spinnerPosition = 0;
	    }

 		 System.out.println(preferences.getString("field1", "from1to10"));


        fieldValueList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.fieldvalues)));

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();

        sp1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                field1 = fieldValueList.get(position);
            //    Toast.makeText(Setting.this, "field1:"+ "\n("+field1+")", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                field2 = fieldValueList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp3.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                field3 = fieldValueList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp4.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                field4 = fieldValueList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


	    save_setting=(Button)findViewById(R.id.button_save_setting);
		save_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				  SharedPreferences.Editor editor = preferences.edit();
                editor.putString("field1", field1);
                editor.putString("field2", field2);
                editor.putString("field3", field3);
                editor.putString("field4", field4);

//				  editor.putString("field2", sp2.getSelectedItem().toString());
//				  editor.putString("field3", sp3.getSelectedItem().toString());
//				  editor.putString("field4", sp4.getSelectedItem().toString());
				  editor.commit();
				
				Toast.makeText(getApplicationContext(), "Setting has been Saved", Toast.LENGTH_SHORT).show();
				finish();
			}
		});

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
