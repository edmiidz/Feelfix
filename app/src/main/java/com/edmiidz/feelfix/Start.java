package com.edmiidz.feelfix;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class Start extends Activity {
	SeekBar seekBar;
    SimpleLocation location;
    String coordinates[];
    Double lattitude;
    Double longtitude;


    EditText editTextfrom1to10,editText_why,editTextfeeling,editText_person,editText_other;
//	CheckBox ispositive;
	RadioGroup rg;
	CheckBox sex,security,social;
	TextView textView_feelingtime;
	Spinner spinner_status;
	Button save,date,more;
	Dialog dialog;
	DatePicker datePicker;
	TimePicker timePicker;
	ImageView img;
	DBHelper h;
	SQLiteDatabase db;
	LinearLayout other_layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		h= new DBHelper(this);
		db= h.getWritableDatabase();
		dialog= new Dialog(Start.this);
		dialog.setTitle(getString(R.string.datetimedialogbox));
		dialog.setContentView(R.layout.dataandtimepicker);
		seekBar=(SeekBar)findViewById(R.id.seekBar1);
		seekBar.setMax(210);
		seekBar.setProgress(110);

        lattitude = location.getLatitude();
        longtitude = location.getLongitude();

        System.out.println("I am Here");
        System.out.println(lattitude.toString());

		
		img=(ImageView)findViewById(R.id.ImageView_smily);
		img.setImageResource(R.drawable.sm3);
		editText_person=(EditText)findViewById(R.id.EditText_person);
		other_layout=(LinearLayout)findViewById(R.id.other_layout);
		other_layout.setVisibility(View.GONE);
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() <= 50) {
					img.setImageResource(R.drawable.sm5);
				} else if (seekBar.getProgress() > 50
						&& seekBar.getProgress() <= 100) {
					img.setImageResource(R.drawable.sm4);
				} else if (seekBar.getProgress() > 100
						&& seekBar.getProgress() <= 110) {
					img.setImageResource(R.drawable.sm3);
				} else if (seekBar.getProgress() > 110
						&& seekBar.getProgress() <= 160) {
					img.setImageResource(R.drawable.sm2);
				} else if (seekBar.getProgress() > 160
						&& seekBar.getProgress() <= 210) {
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
				// editTextfrom1to10.setText(progress/20+1+"");
				if (seekBar.getProgress() <= 10) {
					// img.setImageResource(R.drawable.sm3);
					editTextfrom1to10.setText(-10 + "");
				} else if (seekBar.getProgress() > 10
						&& seekBar.getProgress() <= 20) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-9 + "");
				} else if (seekBar.getProgress() > 20
						&& seekBar.getProgress() <= 30) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-8 + "");
				} else if (seekBar.getProgress() > 30
						&& seekBar.getProgress() <= 40) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-7 + "");
				} else if (seekBar.getProgress() > 40
						&& seekBar.getProgress() <= 50) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-6 + "");
				} else if (seekBar.getProgress() > 50
						&& seekBar.getProgress() <= 60) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-5 + "");
				} else if (seekBar.getProgress() > 60
						&& seekBar.getProgress() <= 70) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-4 + "");
				} else if (seekBar.getProgress() > 70
						&& seekBar.getProgress() <= 80) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-3 + "");
				} else if (seekBar.getProgress() > 80
						&& seekBar.getProgress() <= 90) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-2 + "");
				} else if (seekBar.getProgress() > 90
						&& seekBar.getProgress() <= 100) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(-1 + "");
				} else if (seekBar.getProgress() > 100
						&& seekBar.getProgress() <= 110) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(0 + "");
				} else if (seekBar.getProgress() > 110
						&& seekBar.getProgress() <= 120) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(1 + "");
				} else if (seekBar.getProgress() > 120
						&& seekBar.getProgress() <= 130) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(2 + "");
				} else if (seekBar.getProgress() > 130
						&& seekBar.getProgress() <= 140) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(3 + "");
				} else if (seekBar.getProgress() > 140
						&& seekBar.getProgress() <= 150) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(4 + "");
				} else if (seekBar.getProgress() > 150
						&& seekBar.getProgress() <= 160) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(5 + "");
				} else if (seekBar.getProgress() > 160
						&& seekBar.getProgress() <= 170) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(6 + "");
				} else if (seekBar.getProgress() > 170
						&& seekBar.getProgress() <= 180) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(7 + "");
				} else if (seekBar.getProgress() > 180
						&& seekBar.getProgress() <= 190) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(8 + "");
				} else if (seekBar.getProgress() > 190
						&& seekBar.getProgress() <= 200) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(9 + "");
				} else if (seekBar.getProgress() > 200
						&& seekBar.getProgress() <= 210) {
					// img.setImageResource(R.drawable.sm4);
					editTextfrom1to10.setText(10 + "");
				}
				
				
			}
		});
		date=(Button)findViewById(R.id.button_date);
		Log.d("SilentModeApp", "Nik Start.java");
date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});
		
		editTextfrom1to10=(EditText)findViewById(R.id.EditText_from1to10);
		editTextfrom1to10.setEnabled(false);
		editTextfrom1to10.setText(0+"");
//		ispositive=(CheckBox)findViewById(R.id.checkBox_ispositive);
//		ispositive.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				// TODO Auto-generated method stub
//				if(isChecked){
//					seekBar.setProgress(210);
//					img.setImageResource(R.drawable.sm1);
//				}
////				else{
////					seekBar.setProgress(100);
////					img.setImageResource(R.drawable.sm5);
////				}
//				
//			}
//		});
		editText_why=(EditText)findViewById(R.id.EditText_why);
		
		//rg=(RadioGroup)findViewById(R.id.radioGroup1);
		sex=(CheckBox)findViewById(R.id.check_sex);
		security=(CheckBox)findViewById(R.id.check_security);
		social=(CheckBox)findViewById(R.id.check_social);
		textView_feelingtime=(TextView)findViewById(R.id.textView_feelingtime);
//		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
		textView_feelingtime.setText(formattedDate);
		editTextfeeling=(EditText)findViewById(R.id.EditText_feeling_new);
		spinner_status=(Spinner)findViewById(R.id.spinner_status);
		spinner_status.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(spinner_status.getSelectedItem().toString().equals("Other")){
					other_layout.setVisibility(View.VISIBLE);
				}
				else{
					other_layout.setVisibility(View.GONE);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		editText_other=(EditText)findViewById(R.id.EditText_other);
//		editText_main_instict=(EditText)findViewById(R.id.EditText_main_instict);
//		havebeembetter=(EditText)findViewById(R.id.EditText_havebeenbetter);
//		havebeenworse=(EditText)findViewById(R.id.EditText_have_been_worse);
		save=(Button)findViewById(R.id.Button_save);
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if(editText_why.getText().toString().trim().length()<=0){
//					Toast.makeText(getApplicationContext(),"Please Enter Reason for Feeling", Toast.LENGTH_SHORT).show();
//				}
//				else if(editTextfeeling.getText().toString().trim().length()<=0){
//					Toast.makeText(getApplicationContext(),"Please Enter Feeling Name", Toast.LENGTH_SHORT).show();
//				}
//				else if(editText_main_instict.getText().toString().trim().length()<=0){
//					Toast.makeText(getApplicationContext(),"Please Enter Main Instinct ", Toast.LENGTH_SHORT).show();
//				}
//				else if(havebeembetter.getText().toString().trim().length()<=0){
//					Toast.makeText(getApplicationContext(),"Please Enter Feeling Would have been Better to", Toast.LENGTH_SHORT).show();
//				}
//				else if(havebeenworse.getText().toString().trim().length()<=0){
//					Toast.makeText(getApplicationContext(),"Please Enter Would have been Worse to", Toast.LENGTH_SHORT).show();
//				}
//				else if(editText_person.getText().toString().trim().length()<=0){
//					Toast.makeText(getApplicationContext(),"Please Enter Person Name", Toast.LENGTH_SHORT).show();
//				}
//				else{
				insertData();
				Toast.makeText(getApplicationContext(),
						"Feeling has been sucessfully inserted",
						Toast.LENGTH_LONG).show();
				finish();
//				}
			}

		});
		timePicker = (TimePicker) dialog.findViewById(R.id.timePicker1);
		datePicker = (DatePicker) dialog.findViewById(R.id.datePicker1);

		Button cancel_dailog = (Button) dialog
				.findViewById(R.id.button_d_cancel);
		cancel_dailog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		Button ok_dailog=(Button)dialog.findViewById(R.id.button_d_ok);
		ok_dailog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Random r = new Random();
				int i1=r.nextInt(60-10) + 10;
				
				//todo this is the NOT real deal:
				
				    String paddedMonth = String.valueOf(datePicker.getMonth() + 1);
    				paddedMonth = "00".substring(paddedMonth.length()) + paddedMonth;
    				String paddedDayOfMonth = String.valueOf(datePicker.getDayOfMonth());
    				//String paddedDayOfMonth = "/11";
    				paddedDayOfMonth = "00".substring(paddedDayOfMonth.length()) + paddedDayOfMonth;
    				
					String datatm = datePicker.getYear() + "/" + paddedMonth + "/" + paddedDayOfMonth;
					
					Log.d("SilentModeApp", "Start.java DDDDDDDDDDDDDDDDDDDDD");
					
					String paddedMinute = String.valueOf(timePicker.getCurrentMinute());
    				paddedMinute = "00".substring(paddedMinute.length()) + paddedMinute;
    				
					Log.d("SilentModeApp", paddedMinute);

				datatm = datatm + " " + timePicker.getCurrentHour() + ":"
						+ paddedMinute + ":" + i1;
				textView_feelingtime.setText(datatm);
				dialog.dismiss();
			}
		});
		more=(Button)findViewById(R.id.Button_more);
		more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insertData();
				String id="";
				String query="SELECT Id FROM feelings_table WHERE ID = (SELECT MAX(ID) FROM feelings_table)";
				Cursor cursor=db.rawQuery(query, null);
				if(cursor.getCount()>0){
					while(cursor.moveToNext()){
						id=cursor.getString(0);
					}
				}
				cursor.close();
				Intent in = new Intent(getApplicationContext(),More.class);
				in.putExtra("id",id);
				startActivity(in);
				finish();
			}
		});
		
		
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
		String effectedinstinct=null;
		if(sex.isChecked()){
			effectedinstinct="sex";
		} else if (security.isChecked()) {
			effectedinstinct = "security";
		} else {
			effectedinstinct = "social";
		}
//		String curtime=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
		ContentValues cv= new ContentValues();
//		cv.put(DBHelper.positivefeeling, positivefeel);
		cv.put(DBHelper.from1to10, editTextfrom1to10.getText().toString());
		cv.put(DBHelper.feelingTime, textView_feelingtime.getText().toString());
		cv.put(DBHelper.feelingCauseDesc, editText_why.getText().toString());
//		cv.put(DBHelper.mainaffectedinstinct, editText_main_instict.getText().toString());
		cv.put(DBHelper.mainaffectedinstinct, effectedinstinct);
		cv.put(DBHelper.Feeling,editTextfeeling.getText().toString());
		cv.put(DBHelper.involvedpeople, editText_person.getText().toString());
		if(spinner_status.getSelectedItem().toString().equals("Other")){
			
			cv.put(DBHelper.status, editText_other.getText().toString());
		}else{
			cv.put(DBHelper.status, spinner_status.getSelectedItem().toString());
		}
		
//		cv.put(DBHelper.wouldabeenbetter, havebeembetter.getText().toString());
//		cv.put(DBHelper.wouldabeenworse, havebeenworse.getText().toString());
		cv.put(DBHelper.created, formattedDate);
		cv.put(DBHelper.updatedLast, formattedDate);
		
		db.insert(DBHelper.feelings_table, null, cv);
		
		
		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
}
