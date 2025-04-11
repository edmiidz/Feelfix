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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateCollectionDemoActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments representing each object in a collection. We use a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter} derivative,
	 * which will destroy and re-create fragments as needed, saving and
	 * restoring their state in the process. This is important to conserve
	 * memory and is a best practice when allowing navigation between objects in
	 * a potentially large collection.
	 */
	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

	/**
	 * The {@link android.support.v4.view.ViewPager} that will display the
	 * object collection.
	 */
	ViewPager mViewPager;
	DBHelper h;
	SQLiteDatabase db;
	SeekBar seekBar;
	EditText editTextfrom1to10,editText_why,editTextfeeling,editText_person,editText_other;
	EditText EditText_havebeenbetter,EditText_have_been_worse,EditText_created,EditText_updatedlast;
	MultiAutoCompleteTextView autoEditTextfeeling;
	String[] languages={"Android ","java","IOS","SQL","JDBC","Web services"};

	// CheckBox ispositive;; 
	RadioGroup rg;
	CheckBox sex;
    CheckBox security;
    CheckBox social;
	TextView textView_feelingtime,UpdatedLastTextView,CreatedTextView;
	Spinner spinner_status;
	Button save, date, more;
	Dialog dialog;
	DatePicker datePicker;
	TimePicker timePicker;
	ImageView img;
	String id;
	//Spinner tempspinner;

	LinearLayout other_layout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_demo);
		id = getIntent().getStringExtra("ID");
		h = new DBHelper(this);
		db = h.getWritableDatabase();


		// Create an adapter that when requested, will return a fragment
		// representing an object in
		// the collection.
		//
		// ViewPager and its adapters use support library fragments, so we must
		// use
        // getSupportFragmentManager.
        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());

        // Set up action bar.
        final ActionBar actionBar = getActionBar();

		// Specify that the Home button should show an "Up" caret, indicating
		// that touching the
		// button will take the user one step up in the application's hierarchy.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the ViewPager, attaching the adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		// setRecord(id);
	}

	/**
	 * A {@link android.support.v4.app.FragmentStatePagerAdapter} that returns a
	 * fragment representing an object in the collection.
	 */
	public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
		ArrayList<Fragment> data = new ArrayList<Fragment>();

		public DemoCollectionPagerAdapter(FragmentManager fm) {
			super(fm);
			Fragment fragment = new DemoObjectFragment();
			Fragment fragment2 = new DemoObjectFragment2();
			data.add(fragment);
			data.add(fragment2);

		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = data.get(i);
//			Bundle args = new Bundle();
//			args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1); // Our object is
//																// just an
//																// integer :-P
//			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// For this contrived example, we have a 100-object collection.
			return 2;
		}

//		@Override
//		public CharSequence getPageTitle(int position) {
//			return "OBJECT " + (position + 1);
//		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public class DemoObjectFragment extends Fragment {

		public static final String ARG_OBJECT = "object";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.start, container, false);

			dialog = new Dialog(UpdateCollectionDemoActivity.this);
			dialog.setTitle("Feeling Date & Time");
			dialog.setContentView(R.layout.dataandtimepicker);
			seekBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
			seekBar.setMax(210);
			seekBar.setProgress(110);

			img = (ImageView) rootView.findViewById(R.id.ImageView_smily);
			img.setImageResource(R.drawable.sm3);
			editText_person = (EditText) rootView
					.findViewById(R.id.EditText_person);
			other_layout = (LinearLayout) rootView
					.findViewById(R.id.other_layout);
			other_layout.setVisibility(View.GONE);

			seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub

					
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
					// editTextfrom1to10.setText(progress/20+1+"");
					if (seekBar.getProgress() <= 10) {
						// img.setImageResource(R.drawable.sm3);
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
//			date = (Button) rootView.findViewById(R.id.button_date);
//			Log.d("SilentModeApp", "Nik UpdateCollectionDemoActivity.java");
//            date.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    dialog.show();
//                }
//            });
    		
    		editTextfrom1to10=(EditText)rootView.findViewById(R.id.EditText_from1to10);
    		editTextfrom1to10.setEnabled(false);
			editTextfrom1to10.setText(0 + "");
			// ispositive=(CheckBox)rootView.findViewById(R.id.checkBox_ispositive);
			// ispositive.setOnCheckedChangeListener(new
			// OnCheckedChangeListener() {
			//
			// @Override
			// public void onCheckedChanged(CompoundButton buttonView, boolean
			// isChecked) {
			// // TODO Auto-generated method stub
			// if(isChecked){
			// seekBar.setProgress(210);
			// img.setImageResource(R.drawable.sm1);
			// }
			// // else{
			// // seekBar.setProgress(100);
			// // img.setImageResource(R.drawable.sm5);
			// // }
			//
			// }
			// });
			//editText_why = (EditText) rootView.findViewById(R.id.EditText_why);

			//rg = (RadioGroup) rootView.findViewById(R.id.radioGroup1);
			sex = (CheckBox) rootView.findViewById(R.id.check_sex);
			security = (CheckBox) rootView.findViewById(R.id.check_security);
    		social=(CheckBox)rootView.findViewById(R.id.check_social);
    		textView_feelingtime=(TextView)rootView.findViewById(R.id.textView_feelingtime);
//    		String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    		Calendar c = Calendar.getInstance();
			System.out.println("Current time => " + c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

//			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String formattedDate = df.format(c.getTime());


			textView_feelingtime.setText(formattedDate);

			String[] languages2={"fear","anger","jealousy","happy","connected","rejected"};
			ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,languages2);


            textView_feelingtime.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.show();
                }
            });


			autoEditTextfeeling=(MultiAutoCompleteTextView)rootView.findViewById(R.id.EditText_feeling_new);
			// editTextfeeling=(MultiAutoCompleteTextView)rootView.findViewById(R.id.EditText_feeling_new);
			autoEditTextfeeling.setAdapter(adapter);
			autoEditTextfeeling.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    		spinner_status=(Spinner)rootView.findViewById(R.id.spinner_status);
    		spinner_status.setOnItemSelectedListener(new OnItemSelectedListener() {

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
    		editText_other=(EditText)rootView.findViewById(R.id.EditText_other);

			save = (Button) rootView.findViewById(R.id.Button_save);
			save.setText("Update");
			save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					insertData();
					Toast.makeText(getApplicationContext(),
							"Feeling has been successfully updated",
							Toast.LENGTH_LONG).show();
					finish();
					// }
				}

			});
			timePicker = (TimePicker) dialog.findViewById(R.id.timePicker1);
			datePicker = (DatePicker) dialog.findViewById(R.id.datePicker1);

			Log.d("SilentModeApp", "UpdateC.java 451 GGGGGGGGGGGGGGGGGG");
            Log.d("SilentModeApp", "UpdateC.java 451 formattedDate:" + formattedDate);

			Button cancel_dailog = (Button) dialog
					.findViewById(R.id.button_d_cancel);
			cancel_dailog.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});

			Button ok_dailog = (Button) dialog.findViewById(R.id.button_d_ok);
			ok_dailog.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Random r = new Random();
					int i1 = r.nextInt(60 - 10) + 10;

				    String paddedMonth = String.valueOf(datePicker.getMonth() + 1);
    				paddedMonth = "00".substring(paddedMonth.length()) + paddedMonth;
    				String paddedDayOfMonth = String.valueOf(datePicker.getDayOfMonth());
    				paddedDayOfMonth = "00".substring(paddedDayOfMonth.length()) + paddedDayOfMonth;
    				String paddedMinute = String.valueOf(timePicker.getCurrentMinute());
    				paddedMinute = "00".substring(paddedMinute.length()) + paddedMinute;


					String datatm = datePicker.getYear() + "/" + paddedMonth + "/" + paddedDayOfMonth;

                    Log.d("SilentModeApp", "UpdateC.java 485 DDDDDDDDDDDDDDDDDDDDD");

					Log.d("SilentModeApp", paddedMinute);

					datatm = datatm + " " + timePicker.getCurrentHour() + ":"
							+ paddedMinute + ":" + i1;
					textView_feelingtime.setText(datatm);
					dialog.dismiss();
				}
			});


			return rootView;
		}

	}

	public class DemoObjectFragment2 extends Fragment {

		public static final String ARG_OBJECT = "object";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.more, container, false);

            editText_why = (EditText) rootView.findViewById(R.id.EditText_why);
            EditText_havebeenbetter = (EditText) rootView
					.findViewById(R.id.EditText_havebeenbetter);
			EditText_have_been_worse = (EditText) rootView
					.findViewById(R.id.EditText_have_been_worse);
//			EditText_created = (EditText) rootView
//					.findViewById(R.id.EditText_created);
//			EditText_created.setEnabled(false);
//			EditText_updatedlast = (EditText) rootView
//					.findViewById(R.id.EditText_updatedlast);
//			EditText_updatedlast.setEnabled(false);
			Button Button_more_save=(Button)rootView.findViewById(R.id.Button_more_save);
            TextView UpdatedLastTextView = (TextView)rootView.findViewById(R.id.UpdatedLastTextView);
			Button_more_save.setText("Update");
    		Button_more_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					insertData();
					Toast.makeText(getApplicationContext(), "Feeling has been sucessfully Updated", Toast.LENGTH_LONG).show();
					Log.d("SilentModeApp", "UpdateCollectionDemoActivity.java  Feeling has been successfully Updated");

					finish();
				}
			});
			return rootView;
		}
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			setRecord(id);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
		h.close();
	}

	private void insertData() {

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
      if(instincts.length()>1) instincts = instincts.substring(1,instincts.length());

        //Log.d("SilentModeApp", "UpdateC.java 601 DDDDDDDDDDDDDDDDDDDDD");

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		ContentValues cv = new ContentValues();
		cv.put(DBHelper.from1to10, editTextfrom1to10.getText().toString());
		cv.put(DBHelper.feelingTime, textView_feelingtime.getText().toString());
		cv.put(DBHelper.feelingCauseDesc, editText_why.getText().toString());
		cv.put(DBHelper.mainaffectedinstinct, instincts);
		cv.put(DBHelper.Feeling, autoEditTextfeeling.getText().toString());
		cv.put(DBHelper.involvedpeople, editText_person.getText().toString());
		cv.put(DBHelper.wouldabeenbetter, EditText_havebeenbetter.getText().toString());
		cv.put(DBHelper.wouldabeenworse, EditText_have_been_worse.getText().toString());

		cv.put(DBHelper.updatedLast, formattedDate);
		if (spinner_status.getSelectedItem().toString().equals("Other")) {

			cv.put(DBHelper.status, editText_other.getText().toString());
		} else {
			cv.put(DBHelper.status, spinner_status.getSelectedItem().toString());
		}


		db.update(DBHelper.feelings_table, cv, DBHelper.Id + "= '" + id + "'",
				null);

	}

	private void setRecord(String id2) {
		// TODO Auto-generated method stub
		String Query = "select * from feelings_table where Id='" + id2 + "'";
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String frm1t010 = cursor.getString(cursor
						.getColumnIndex("from1to10"));
				editTextfrom1to10.setText(frm1t010);
				int c = Integer.parseInt(frm1t010.trim());

				if (c == -10) {
					img.setImageResource(R.drawable.sm5);
					seekBar.setProgress(10);
				} else if (c == -9) {
					img.setImageResource(R.drawable.sm5);
					seekBar.setProgress(20);
				} else if (c == -8) {
					img.setImageResource(R.drawable.sm5);
					seekBar.setProgress(30);
				} else if (c == -7) {
					img.setImageResource(R.drawable.sm5);
					seekBar.setProgress(40);

				} else if (c == -6) {
					img.setImageResource(R.drawable.sm5);
					seekBar.setProgress(50);
				} else if (c == -5) {
					img.setImageResource(R.drawable.sm4);
					seekBar.setProgress(60);
				} else if (c == -4) {
					img.setImageResource(R.drawable.sm4);
					seekBar.setProgress(70);
				} else if (c == -3) {
					img.setImageResource(R.drawable.sm4);
					seekBar.setProgress(80);
				} else if (c == -2) {
					img.setImageResource(R.drawable.sm4);
					seekBar.setProgress(90);
				} else if (c == -1) {
					img.setImageResource(R.drawable.sm4);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(100);
				} else if (c == 0) {
					img.setImageResource(R.drawable.sm3);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(110);
				} else if (c == 1) {
					img.setImageResource(R.drawable.sm2);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(120);
				} else if (c == 2) {
					img.setImageResource(R.drawable.sm2);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(130);
				} else if (c == 3) {
					img.setImageResource(R.drawable.sm2);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(140);
				} else if (c == 4) {
					img.setImageResource(R.drawable.sm2);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(150);
				} else if (c == 5) {
					img.setImageResource(R.drawable.sm2);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(160);
				} else if (c == 6) {
					img.setImageResource(R.drawable.sm1);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(170);
				} else if (c == 7) {
					img.setImageResource(R.drawable.sm1);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(180);
				} else if (c == 8) {
					img.setImageResource(R.drawable.sm1);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(190);
				} else if (c == 9) {
					img.setImageResource(R.drawable.sm1);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(200);
				} else if (c == 10) {
					img.setImageResource(R.drawable.sm1);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(210);
				}


				String instincts = cursor.getString(cursor
						.getColumnIndex("mainaffectedinstinct"));
                System.out.println("instincts");

                System.out.println(instincts);
				if (instincts.trim().contains("sex")) {
                    sex.setChecked(true);
                    System.out.println("sex");
                }
                if (instincts.trim().contains("security")) {
                    security.setChecked(true);
                    System.out.println("security");
                } else {
                    security.setChecked(false);
                }

                if (instincts.trim().contains("social")) {
                    social.setChecked(true);
                    System.out.println("social");

                }


				textView_feelingtime.setText(cursor.getString(cursor
                        .getColumnIndex("feelingTime")));

                try {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    cal.setTime(df.parse(cursor.getString(cursor.getColumnIndex("feelingTime"))));

                    String feelingYear = new SimpleDateFormat("yyyy").format(cal.getTime());
                    String feelingMonth = new SimpleDateFormat("MM").format(cal.getTime());
                    String feelingDay = new SimpleDateFormat("dd").format(cal.getTime());
                    String feelingHour = new SimpleDateFormat("HH").format(cal.getTime());
                    String feelingMinute = new SimpleDateFormat("mm").format(cal.getTime());

                    datePicker.init(Integer.parseInt(feelingYear),Integer.parseInt(feelingMonth),Integer.parseInt(feelingDay),null);
                    timePicker.setCurrentMinute(Integer.parseInt(feelingDay));
                    timePicker.setCurrentHour(Integer.parseInt(feelingHour));
                    timePicker.setCurrentMinute(Integer.parseInt(feelingMinute));


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.d("SilentModeApp", "UpdateC.java 739 FFFFFFFFFFFFFF");
                Log.d("SilentModeApp", "UpdateC.java 739 feelingTime:" + cursor.getString(cursor
                        .getColumnIndex("feelingTime")));
//                Log.d("SilentModeApp", "UpdateC.java 739 feelingMinute:" + feelingMinute);
//                Log.d("SilentModeApp", "UpdateC.java 739 formattedDate:" + formattedDate);

				autoEditTextfeeling.setText(cursor.getString(cursor
						.getColumnIndex("Feeling")));
			//	autoEditTextfeeling.setText("WTF?");
				String index = cursor
						.getString(cursor.getColumnIndex("status"));
				if (index.equals("New")) {
					spinner_status.setSelection(0);
				} else if (index.equals("Pending")) {
					spinner_status.setSelection(1);
				} else if (index.equals("Shared")) {
					spinner_status.setSelection(2);
				} else if (index.equals("Resolved")) {
					spinner_status.setSelection(3);
				} else {
					spinner_status.setSelection(4);
					editText_other.setText(index);
					other_layout.setVisibility(View.VISIBLE);
				}
				// System.out.println("........"+cursor.getInt(cursor.getColumnIndex("status")));
				editText_person.setText(cursor.getString(cursor
						.getColumnIndex("involvedpeople")));
				// editText_main_instict.setText(cursor.getString(cursor.getColumnIndex("mainaffectedinstinct")));
				EditText_havebeenbetter.setText(cursor.getString(cursor
						.getColumnIndex("wouldabeenbetter")));
				EditText_have_been_worse.setText(cursor.getString(cursor
						.getColumnIndex("wouldabeenworse")));

                editText_why.setText(cursor.getString(cursor
                        .getColumnIndex("feelingCauseDesc")));
			}
		}
		cursor.close();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
