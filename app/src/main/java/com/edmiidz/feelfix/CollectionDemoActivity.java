/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.edmiidz.feelfix;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class CollectionDemoActivity extends AppCompatActivity {
    private Button save;
    private Dialog dialog;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private ImageView img;
    private EditText editTextfrom1to10,editText_why,editText_person,editText_other;
    private EditText EditText_havebeenbetter,EditText_have_been_worse;
    private MultiAutoCompleteTextView autoEditTextfeeling;
    private CheckBox sex;
    private CheckBox security;
    private CheckBox social;
    private TextView textView_feelingtime;
    private Spinner spinner_status;
    private DBHelper h;
    private SQLiteDatabase db;
    private SeekBar seekBar;
    private SimpleLocation simpleLocation;
    private Double latitude;
    private Double longitude;
    private LinearLayout other_layout;



	


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_demo);


        h = new DBHelper(this);
        db= h.getWritableDatabase();

        simpleLocation= new SimpleLocation(this);

        if (!simpleLocation.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        dialog= new Dialog(this);
        dialog.setTitle(getString(R.string.datetimedialogbox));
        dialog.setContentView(R.layout.dataandtimepicker);
        seekBar = findViewById(R.id.seekBar1);
        seekBar.setMax(210);
        seekBar.setProgress(110);

        latitude = simpleLocation.getLatitude();
        longitude = simpleLocation.getLongitude();

        System.out.println("I am Here CollectionDemoActivity.java");
        System.out.println(latitude.toString());

        img = findViewById(R.id.ImageView_smily);
        img.setImageResource(R.drawable.sm3);
        editText_person = findViewById(R.id.EditText_person);
        other_layout = findViewById(R.id.other_layout);
        other_layout.setVisibility(View.GONE);
        editText_why = findViewById(R.id.EditText_why);
        EditText_havebeenbetter = findViewById(R.id.EditText_havebeenbetter);
        EditText_have_been_worse = findViewById(R.id.EditText_have_been_worse);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

                Log.d("SilentModeApp", String.valueOf(seekBar.getProgress()));


                if(seekBar.getProgress()<=50){
                    img.setImageResource(R.drawable.sm5);
                }
                else if(seekBar.getProgress()>50&& seekBar.getProgress()<=100){
                    img.setImageResource(R.drawable.sm4);
                }
                else if(seekBar.getProgress()>100&& seekBar.getProgress()<=110){
                    img.setImageResource(R.drawable.sm3);
                }
                else if(seekBar.getProgress()>110&& seekBar.getProgress()<=160){
                    img.setImageResource(R.drawable.sm2);
                }
                else if(seekBar.getProgress()>160&& seekBar.getProgress()<=210){
                    img.setImageResource(R.drawable.sm1);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
//    				editTextfrom1to10.setText(progress/20+1+"");
                if(seekBar.getProgress()<=10){
//    					img.setImageResource(R.drawable.sm3);
                    editTextfrom1to10.setText(-10+"");
                }
                else if(seekBar.getProgress()>10&& seekBar.getProgress()<=20){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-9+"");
                }
                else if(seekBar.getProgress()>20&& seekBar.getProgress()<=30){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-8+"");
                }
                else if(seekBar.getProgress()>30&& seekBar.getProgress()<=40){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-7+"");
                }
                else if(seekBar.getProgress()>40&& seekBar.getProgress()<=50){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-6+"");
                }
                else if(seekBar.getProgress()>50&& seekBar.getProgress()<=60){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-5+"");
                }
                else if(seekBar.getProgress()>60&& seekBar.getProgress()<=70){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-4+"");
                }
                else if(seekBar.getProgress()>70&& seekBar.getProgress()<=80){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-3+"");
                }
                else if(seekBar.getProgress()>80&& seekBar.getProgress()<=90){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-2+"");
                }
                else if(seekBar.getProgress()>90&& seekBar.getProgress()<=100){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(-1+"");
                }
                else if(seekBar.getProgress()>100&& seekBar.getProgress()<=110){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(0+"");
                }
                else if(seekBar.getProgress()>110&& seekBar.getProgress()<=120){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(1+"");
                }
                else if(seekBar.getProgress()>120&& seekBar.getProgress()<=130){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(2+"");
                }
                else if(seekBar.getProgress()>130&& seekBar.getProgress()<=140){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(3+"");
                }
                else if(seekBar.getProgress()>140&& seekBar.getProgress()<=150){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(4+"");
                }
                else if(seekBar.getProgress()>150&& seekBar.getProgress()<=160){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(5+"");
                }
                else if(seekBar.getProgress()>160&& seekBar.getProgress()<=170){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(6+"");
                }
                else if(seekBar.getProgress()>170&& seekBar.getProgress()<=180){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(7+"");
                }
                else if(seekBar.getProgress()>180&& seekBar.getProgress()<=190){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(8+"");
                }
                else if(seekBar.getProgress()>190&& seekBar.getProgress()<=200){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(9+"");
                }
                else if(seekBar.getProgress()>200&& seekBar.getProgress()<=210){
//    					img.setImageResource(R.drawable.sm4);
                    editTextfrom1to10.setText(10+"");
                }


            }
        });
//    		date=(Button)rootView.findViewById(R.id.button_date);
//    		Log.d("SilentModeApp", "Nik002 CollectionDemoActivity.java");
//date.setOnClickListener(new OnClickListener() {
//
//    			@Override
//    			public void onClick(View v) {
//    				// TODO Auto-generated method stub
//    				dialog.show();
//    			}
//    		});

        editTextfrom1to10 = findViewById(R.id.EditText_from1to10);
        editTextfrom1to10.setEnabled(false);
        editTextfrom1to10.setText(0+"");
//    		ispositive=(CheckBox)rootView.findViewById(R.id.checkBox_ispositive);
//    		ispositive.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//    			@Override
//    			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//    				// TODO Auto-generated method stub
//    				if(isChecked){
//    					seekBar.setProgress(210);
//    					img.setImageResource(R.drawable.sm1);
//    				}
////    				else{
////    					seekBar.setProgress(100);
////    					img.setImageResource(R.drawable.sm5);
////    				}
//
//    			}
//    		});
        //editText_why=(EditText)rootView.findViewById(R.id.EditText_why);

        //rg=(RadioGroup)rootView.findViewById(R.id.radioGroup1);
        sex = findViewById(R.id.check_sex);
        security = findViewById(R.id.check_security);
        social = findViewById(R.id.check_social);
        textView_feelingtime = findViewById(R.id.textView_feelingtime);
//    		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String formattedDate = df.format(c.getTime());
        textView_feelingtime.setText(formattedDate);

        String[] languages2={"fear","anger","jealousy","happy","connected","rejected"};
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,languages2);


        textView_feelingtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.show();
            }
        });


        autoEditTextfeeling = findViewById(R.id.EditText_feeling_new);
        // editTextfeeling=(MultiAutoCompleteTextView)rootView.findViewById(R.id.EditText_feeling_new);
        autoEditTextfeeling.setAdapter(adapter);
        autoEditTextfeeling.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        spinner_status = findViewById(R.id.spinner_status);
        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (spinner_status.getSelectedItem().toString().equals("Other")) {
                    other_layout.setVisibility(View.VISIBLE);
                } else {
                    other_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        editText_other = findViewById(R.id.EditText_other);
//    		editText_main_instict=(EditText)rootView.findViewById(R.id.EditText_main_instict);
//    		havebeembetter=(EditText)rootView.findViewById(R.id.EditText_havebeenbetter);
//    		havebeenworse=(EditText)rootView.findViewById(R.id.EditText_have_been_worse);
        save = findViewById(R.id.Button_more_save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//    				if(editText_why.getText().toString().trim().length()<=0){
//    					Toast.makeText(getApplicationContext(),"Please Enter Reason for Feeling", Toast.LENGTH_SHORT).show();
//    				}
//    				else if(editTextfeeling.getText().toString().trim().length()<=0){
//    					Toast.makeText(getApplicationContext(),"Please Enter Feeling Name", Toast.LENGTH_SHORT).show();
//    				}
//    				else if(editText_main_instict.getText().toString().trim().length()<=0){
//    					Toast.makeText(getApplicationContext(),"Please Enter Main Instinct ", Toast.LENGTH_SHORT).show();
//    				}
//    				else if(havebeembetter.getText().toString().trim().length()<=0){
//    					Toast.makeText(getApplicationContext(),"Please Enter Feeling Would have been Better to", Toast.LENGTH_SHORT).show();
//    				}
//    				else if(havebeenworse.getText().toString().trim().length()<=0){
//    					Toast.makeText(getApplicationContext(),"Please Enter Would have been Worse to", Toast.LENGTH_SHORT).show();
//    				}
//    				else if(editText_person.getText().toString().trim().length()<=0){
//    					Toast.makeText(getApplicationContext(),"Please Enter Person Name", Toast.LENGTH_SHORT).show();
//    				}
//    				else{
                insertData();
                Toast.makeText(CollectionDemoActivity.this, "Feeling has been sucessfully inserted", Toast.LENGTH_LONG).show();
                finish();
//    				}
            }

        });
        timePicker= dialog.findViewById(R.id.timePicker1);
        datePicker= dialog.findViewById(R.id.datePicker1);


        Button cancel_dailog = dialog.findViewById(R.id.button_d_cancel);
        cancel_dailog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        Button ok_dailog = dialog.findViewById(R.id.button_d_ok);
        ok_dailog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Random r = new Random();
                int i1=r.nextInt(60-10) + 10;
                // todo: add zero-padding
                // todo: defaul to user preference


                //todo this is one real deal, for inserting maybe:

                String paddedMonth = String.valueOf(datePicker.getMonth() + 1);
                paddedMonth = "00".substring(paddedMonth.length()) + paddedMonth;
                String paddedDayOfMonth = String.valueOf(datePicker.getDayOfMonth());
                paddedDayOfMonth = "00".substring(paddedDayOfMonth.length()) + paddedDayOfMonth;

                String datatm = datePicker.getYear() + "/" + paddedMonth + "/" + paddedDayOfMonth;


                String paddedHour = String.valueOf(timePicker.getCurrentHour());
                paddedHour = "00".substring(paddedHour.length()) + paddedHour;
                String paddedMinute = String.valueOf(timePicker.getCurrentMinute());
                paddedMinute = "00".substring(paddedMinute.length()) + paddedMinute;


                datatm=datatm+" "+paddedHour+":"+paddedMinute+":"+i1;
                textView_feelingtime.setText(datatm);
                dialog.dismiss();
            }
        });




        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }





	private void insertData() {
		// TODO Auto-generated method stub
//		String positivefeel;
//		if(ispositive.isChecked()){
//			positivefeel="yes";
//		}
//		else{
//			positivefeel="no";
//		}
		String instincts="";
		if(sex.isChecked()){
            instincts = instincts + ",sex";
		}
        if(security.isChecked()){
            instincts = instincts + ",security";
        }
        if(social.isChecked()){
            instincts = instincts + ",social";
        }

        instincts = instincts.substring(1,instincts.length());



		String curtime=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
		ContentValues cv= new ContentValues();
//		cv.put(DBHelper.positivefeeling, positivefeel);
		cv.put(DBHelper.from1to10, editTextfrom1to10.getText().toString());
		cv.put(DBHelper.feelingTime, textView_feelingtime.getText().toString());
		cv.put(DBHelper.feelingCauseDesc, editText_why.getText().toString());
//		cv.put(DBHelper.mainaffectedinstinct, editText_main_instict.getText().toString());
		cv.put(DBHelper.mainaffectedinstinct, instincts);
		cv.put(DBHelper.Feeling,autoEditTextfeeling.getText().toString());
		cv.put(DBHelper.involvedpeople, editText_person.getText().toString());
		cv.put(DBHelper.wouldabeenbetter, EditText_havebeenbetter.getText().toString());
		cv.put(DBHelper.wouldabeenworse, EditText_have_been_worse.getText().toString());
        cv.put(DBHelper.latitude, latitude.toString());
        cv.put(DBHelper.longitude, longitude.toString());
        cv.put(DBHelper.updatedLast, formattedDate);
        cv.put(DBHelper.created, formattedDate);
		if(spinner_status.getSelectedItem().toString().equals("Other")){

			cv.put(DBHelper.status, editText_other.getText().toString());
		}else{
			cv.put(DBHelper.status, spinner_status.getSelectedItem().toString());
		}

//		cv.put(DBHelper.wouldabeenbetter, havebeembetter.getText().toString());
//		cv.put(DBHelper.wouldabeenworse, havebeenworse.getText().toString());
//		cv.put(DBHelper.created, EditText_created.getText().toString());

		db.insert(DBHelper.feelings_table, null, cv);




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
