package com.edmiidz.feelfix;



import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RecordListView_cie extends Activity {
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
	TableLayout table;
//	ArrayList<HashMap<String, String>> data= new ArrayList<HashMap<String,String>>();
//	RealViewSwitcher realViewSwitcher;
// This is the list
	String field1,field2,field3;
	int i;
	mytask t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		t=new mytask();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		field1 = preferences.getString("field1", "from1to10");
		field2 = preferences.getString("field2", "feelingTime");
		field3 = preferences.getString("field3", "status");
//		realViewSwitcher = new RealViewSwitcher(getApplicationContext());
//		realViewSwitcher.setOnScreenSwitchListener(onScreenSwitchListener);
		table = (TableLayout) findViewById(R.id.mytable);

//		setContentView(realViewSwitcher);

	}

	private ArrayList<HashMap<String, String>> getDatafromDatabase() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String query = "select from1to10,Feeling,feelingTime,feelingCauseDesc,status,Id from feelings_table order by Id desc";
		DBHelper h = new DBHelper(this);
		SQLiteDatabase db = h.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		Log.d("SilentModeApp", query);
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("from1to10", cursor.getString(0));
				//hm.put("Feeling", cursor.getString(1));
				if(cursor.getString(1).length()>14){
				hm.put("Feeling", cursor.getString(1).substring(0, 11) + "...");
				} else {
					hm.put("Feeling", cursor.getString(1));
					
				}
				//SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				//String formattedDate = df.format(cursor.getString(1));
				//hm.put("Feeling", formattedDate);
				
				hm.put("feelingTime", cursor.getString(2));
				hm.put("feelingCauseDesc", cursor.getString(3));
				hm.put("status", cursor.getString(4));
				hm.put("Id", cursor.getString(5));
				list.add(hm);
			}
		}
		cursor.close();
		db.close();
		h.close();

		return list;
	}


	private void setDataOnTable(ArrayList<HashMap<String, String>> data2) {
		// TODO Auto-generated method stub
		if(data2.size()>0){
			TableRow rr= new TableRow(this);
			rr.setBackgroundResource(R.drawable.bg_button);
			TextView t11= new TextView(this);
			t11.setTextColor(Color.WHITE);
			t11.setGravity(Gravity.CENTER);		
			TextView t22= new TextView(this);
			t22.setTextColor(Color.WHITE);
			t22.setGravity(Gravity.CENTER);
			TextView t33= new TextView(this);
			t33.setTextColor(Color.WHITE);
			t33.setGravity(Gravity.CENTER);
//			TextView t44= new TextView(this);
//			t44.setGravity(Gravity.CENTER);
			TextView t55= new TextView(this);
			t55.setTextColor(Color.WHITE);
			t55.setGravity(Gravity.CENTER);
			t11.setText("Rating");
			t22.setText("Feelings");
			t33.setText("Time");
//			t44.setText("Desc");
			t55.setText("status");
			rr.addView(t11);
			rr.addView(t22);
			rr.addView(t33);
//			rr.addView(t44);
			rr.addView(t55);
			rr.setGravity(Gravity.CENTER);
			table.addView(rr);
			
			for(i=0;i<data2.size();i++){
				
				HashMap<String,String> hm=data2.get(i);
				TableRow r= new TableRow(this);
				r.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,100));
				r.setBackgroundResource(R.drawable.bg_titlebar);
				TextView t1= new TextView(this);
				
				t1.setGravity(Gravity.CENTER);
				TextView t2= new TextView(this);
				t2.setGravity(Gravity.CENTER);
				TextView t3= new TextView(this);
				t3.setGravity(Gravity.CENTER);
//				TextView t4= new TextView(this);
//				t4.setGravity(Gravity.CENTER);
				TextView t5= new TextView(this);
				t5.setGravity(Gravity.CENTER);
				t1.setText(hm.get("from1to10"));
				t2.setText(hm.get("Feeling"));
				
				t3.setText(hm.get("feelingTime"));
//				t4.setText(hm.get("feelingCauseDesc"));
				t5.setText(hm.get("status"));
				r.addView(t1);
				r.addView(t2);
				r.addView(t3);
//				r.addView(t4);
				r.addView(t5);
				table.addView(r);
				final TextView coutv= new TextView(this);
				coutv.setText(i+"");
				
				r.setGravity(Gravity.CENTER);
				r.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent in = new Intent(getApplicationContext(),RecordList.class);
						in.putExtra("position",coutv.getText().toString());
				//		Toast.makeText(getApplicationContext(), coutv.getText().toString(), Toast.LENGTH_SHORT).show();
						startActivity(in);
						
					}
				});
				 
			}
		}
		else{
			Toast.makeText(getApplicationContext(),"There is No Data For Display in Record..........!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
		@Override
			protected void onResume() {
				// TODO Auto-generated method stub
				super.onResume();

				table.removeAllViews();
				ArrayList<HashMap<String, String>> data=getDatafromDatabase();

				setDataOnTable(data);
				data.clear();
			}
		class mytask extends AsyncTask{
			ProgressDialog progressDialog2;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
//				progressDialog.setIcon(R.drawable.icon);
				progressDialog2 = ProgressDialog.show(RecordListView_cie.this, "", "Please wait for Loading...");
			}
			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub
//				table.removeAllViews();
//				data = getDatafromDatabase();
//				setDataOnTable(data);
				return null;
			}
			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				progressDialog2.dismiss();
			}
			
		}
}
