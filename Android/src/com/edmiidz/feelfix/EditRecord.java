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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditRecord extends Activity {
	SeekBar seekBar;
	EditText editTextfrom1to10, editText_why, editTextfeeling, editText_person,editText_other;
	// CheckBox ispositive;
	RadioGroup rg;
	RadioButton sex, security, social;
	TextView textView_feelingtime;
	Spinner spinner_status;
	Button save, date;
	Dialog dialog;
	DatePicker datePicker;
	TimePicker timePicker;
	ImageView img;
	String id;
	DBHelper h;
	SQLiteDatabase db;
	LinearLayout other_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		id = getIntent().getStringExtra("ID");
		h = new DBHelper(this);
		db = h.getWritableDatabase();
		dialog = new Dialog(EditRecord.this);
		dialog.setTitle("Date Time DialogBox");
		dialog.setContentView(R.layout.dataandtimepicker);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setMax(210);
		img = (ImageView) findViewById(R.id.ImageView_smily);
		editText_person = (EditText) findViewById(R.id.EditText_person);
		other_layout = (LinearLayout) findViewById(R.id.other_layout);
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
		date = (Button) findViewById(R.id.button_date);
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});

		editTextfrom1to10 = (EditText) findViewById(R.id.EditText_from1to10);
		editTextfrom1to10.setEnabled(false);
		editTextfrom1to10.setText(0 + "");
		// ispositive=(CheckBox)findViewById(R.id.checkBox_ispositive);
		editText_why = (EditText) findViewById(R.id.EditText_why);

		rg = (RadioGroup) findViewById(R.id.radioGroup1);
		sex = (RadioButton) findViewById(R.id.radio_sex);
		security = (RadioButton) findViewById(R.id.radio_secuirty);
		social = (RadioButton) findViewById(R.id.radio_sodial);
		textView_feelingtime = (TextView) findViewById(R.id.textView_feelingtime);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		textView_feelingtime.setText(formattedDate);
		editTextfeeling = (EditText) findViewById(R.id.EditText_feeling);
		spinner_status = (Spinner) findViewById(R.id.spinner_status);
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
		editText_other=(EditText)findViewById(R.id.EditText_other);
		// editText_main_instict=(EditText)findViewById(R.id.EditText_main_instict);
		// havebeembetter=(EditText)findViewById(R.id.EditText_havebeenbetter);
		// havebeenworse=(EditText)findViewById(R.id.EditText_have_been_worse);
		save = (Button) findViewById(R.id.Button_save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if(editText_why.getText().toString().trim().length()<=0){
				// Toast.makeText(getApplicationContext(),"Please Enter Reason for Feeling",
				// Toast.LENGTH_SHORT).show();
				// }
				// else
				// if(editTextfeeling.getText().toString().trim().length()<=0){
				// Toast.makeText(getApplicationContext(),"Please Enter Feeling Name",
				// Toast.LENGTH_SHORT).show();
				// }
				// else
				// if(editText_main_instict.getText().toString().trim().length()<=0){
				// Toast.makeText(getApplicationContext(),"Please Enter Main Instinct ",
				// Toast.LENGTH_SHORT).show();
				// }
				// else
				// if(havebeembetter.getText().toString().trim().length()<=0){
				// Toast.makeText(getApplicationContext(),"Please Enter Feeling Would have been Better to",
				// Toast.LENGTH_SHORT).show();
				// }
				// else
				// if(havebeenworse.getText().toString().trim().length()<=0){
				// Toast.makeText(getApplicationContext(),"Please Enter Would have been Worse to",
				// Toast.LENGTH_SHORT).show();
				// }
				// else
				// if(editText_person.getText().toString().trim().length()<=0){
				// Toast.makeText(getApplicationContext(),"Please Enter Person Name",
				// Toast.LENGTH_SHORT).show();
				// }
				// else{
				insertData();
				Toast.makeText(getApplicationContext(),
						"Feeling has been sucessfully inserted",
						Toast.LENGTH_LONG).show();
				finish();
				// }
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

		Button ok_dailog = (Button) dialog.findViewById(R.id.button_d_ok);
		ok_dailog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Random r = new Random();
				int i1=r.nextInt(60-10) + 10;
				String datatm = datePicker.getDayOfMonth() + "-"
						+ (datePicker.getMonth() + 1) + "-"
						+ datePicker.getYear();
				datatm = datatm + " " + timePicker.getCurrentHour() + ":"
						+ timePicker.getCurrentMinute()+":"+i1;
				textView_feelingtime.setText(datatm);
				dialog.dismiss();
			}
		});
		setRecord(id);

		// ispositive.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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
		Button more = (Button) findViewById(R.id.Button_more);
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insertData();
				Intent in = new Intent(getApplicationContext(), More.class);
				in.putExtra("id", id);
				startActivity(in);
				finish();

			}
		});

	}

	private void insertData() {
		// TODO Auto-generated method stub
		// String positivefeel;
		// if(ispositive.isChecked()){
		// positivefeel="yes";
		// }
		// else{
		// positivefeel="no";
		//
		// }
		String effectedinstinct = null;
		if (sex.isChecked()) {
			effectedinstinct = "sex";
		} else if (security.isChecked()) {
			effectedinstinct = "security";
		} else {
			effectedinstinct = "social";
		}
		// String
		// curtime=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String formattedDate = df.format(c.getTime());
		ContentValues cv = new ContentValues();
		// cv.put(DBHelper.positivefeeling, positivefeel);
		cv.put(DBHelper.from1to10, editTextfrom1to10.getText().toString());
		cv.put(DBHelper.feelingTime, textView_feelingtime.getText().toString());
		cv.put(DBHelper.feelingCauseDesc, editText_why.getText().toString());
		// cv.put(DBHelper.mainaffectedinstinct,
		// editText_main_instict.getText().toString());
		cv.put(DBHelper.effectedinstinct, effectedinstinct);
		cv.put(DBHelper.Feeling, editTextfeeling.getText().toString());
		cv.put(DBHelper.involvedpeople, editText_person.getText().toString());
		if (spinner_status.getSelectedItem().toString().equals("Other")) {

			cv.put(DBHelper.status, editText_other.getText().toString());
		} else {
			cv.put(DBHelper.status, spinner_status.getSelectedItem().toString());
		}
		// cv.put(DBHelper.wouldabeenbetter,
		// havebeembetter.getText().toString());
		// cv.put(DBHelper.wouldabeenworse, havebeenworse.getText().toString());
		cv.put(DBHelper.updatedLast, formattedDate);

		db.update(DBHelper.feelings_table, cv, DBHelper.Id + "= '" + id + "'",
				null);
		// db.close();
		// h.close();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
		h.close();
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
					// editTextfrom1to10.setText(1+"");
					seekBar.setProgress(10);
				} else if (c == -9) {
					img.setImageResource(R.drawable.sm5);
					// editTextfrom1to10.setText(2+"");
					seekBar.setProgress(20);
				} else if (c == -8) {
					img.setImageResource(R.drawable.sm5);
					// editTextfrom1to10.setText(3+"");
					seekBar.setProgress(30);
				} else if (c == -7) {
					img.setImageResource(R.drawable.sm5);
					// editTextfrom1to10.setText(4+"");
					seekBar.setProgress(40);

				} else if (c == -6) {
					img.setImageResource(R.drawable.sm5);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(50);
				} else if (c == -5) {
					img.setImageResource(R.drawable.sm4);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(60);
				} else if (c == -4) {
					img.setImageResource(R.drawable.sm4);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(70);
				} else if (c == -3) {
					img.setImageResource(R.drawable.sm4);
					// editTextfrom1to10.setText(5+"");
					seekBar.setProgress(80);
				} else if (c == -2) {
					img.setImageResource(R.drawable.sm4);
					// editTextfrom1to10.setText(5+"");
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
				// ispositive.setChecked(true);
				// String
				// ispos=cursor.getString(cursor.getColumnIndex("positivefeeling"));
				// if(ispos.equals("yes")){
				// ispositive.setChecked(true);
				// }

				// System.out.println("run");
				editText_why.setText(cursor.getString(cursor
						.getColumnIndex("feelingCauseDesc")));
				String effected_instict = cursor.getString(cursor
						.getColumnIndex("effectedinstinct"));
				System.out.println(effected_instict);
				if (effected_instict.trim().equals("sex")) {
					sex.setChecked(true);
				} else if (effected_instict.trim().equals("security")) {
					security.setChecked(true);
				} else if (effected_instict.trim().equals("social")) {
					social.setChecked(true);
				}
				textView_feelingtime.setText(cursor.getString(cursor
						.getColumnIndex("feelingTime")));
				editTextfeeling.setText(cursor.getString(cursor
						.getColumnIndex("Feeling")));
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
				// havebeembetter.setText(cursor.getString(cursor.getColumnIndex("wouldabeenbetter")));
				// havebeenworse.setText(cursor.getString(cursor.getColumnIndex("wouldabeenworse")));
			}
		}
		cursor.close();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setRecord(id);
	}
}
