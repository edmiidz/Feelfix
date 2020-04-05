package com.edmiidz.feelfix;





import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecordList extends Activity {
//	TableLayout table;
	ArrayList<HashMap<String, String>> data;
	RealViewSwitcher realViewSwitcher;
//	SharedPreferences prefs;
	String field1,field2,field3;
	DBHelper h ;
	SQLiteDatabase db;
	int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.recordlist);
	 h = new DBHelper(this);
		 db = h.getWritableDatabase();
		 pos=Integer.parseInt(getIntent().getStringExtra("position"));
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		field1 = preferences.getString("field1", "from1to10");
		field2 = preferences.getString("field2", "feelingTime");
		field3 = preferences.getString("field3", "status");
		realViewSwitcher = new RealViewSwitcher(getApplicationContext(),pos);
		realViewSwitcher.setOnScreenSwitchListener(onScreenSwitchListener);
//		table = (TableLayout) findViewById(R.id.mytable);
		/*data = getDatafromDatabase();
		setDataOnTable(data);*/
		setContentView(realViewSwitcher);

	}

	private ArrayList<HashMap<String, String>> getDatafromDatabase() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String query = "select Id,"+field1+","+field2+","+field3+ " from feelings_table";
		
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("Id", cursor.getString(0));
				hm.put(field1, cursor.getString(1));
				hm.put(field2, cursor.getString(2));
				hm.put(field3, cursor.getString(3));
//				hm.put("feelingCauseDesc", cursor.getString(3));
//				hm.put("status", cursor.getString(4));
				
				list.add(hm);
			}
		}
		cursor.close();
//		db.close();
//		h.close();

		return list;
	}

	@SuppressWarnings("unused")
	private void setDataOnTable(ArrayList<HashMap<String, String>> data2) {
		// TODO Auto-generated method stub
		if (data2.size() > 0) {
//			TableRow rr = new TableRow(this);
//			rr.setBackgroundResource(R.drawable.bg_button);
//			TextView t11 = new TextView(this);
//			t11.setGravity(Gravity.CENTER);
//			TextView t22 = new TextView(this);
//			t22.setGravity(Gravity.CENTER);
//			TextView t33 = new TextView(this);
//			t33.setGravity(Gravity.CENTER);
//			TextView t44 = new TextView(this);
//			t44.setGravity(Gravity.CENTER);
//			TextView t55 = new TextView(this);
//			t55.setGravity(Gravity.CENTER);
//			t11.setText("From1to10");
//			t22.setText("Feeling");
//			t33.setText("Time");
//			t44.setText("Desc");
//			t55.setText("status");
//			rr.addView(t11);
//			rr.addView(t22);
//			rr.addView(t33);
//			rr.addView(t44);
//			rr.addView(t55);
//			table.addView(rr);
//			realViewSwitcher.addView(rr);
			for (int i = 0; i < data2.size(); i++) {
				final HashMap<String, String> hm = data2.get(i);
//				TableRow rr = new TableRow(this);
				LinearLayout outl= new LinearLayout(this);
				outl.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, 1.0f));
				outl.setOrientation(LinearLayout.VERTICAL);
				outl.setGravity(Gravity.CENTER);
				LinearLayout l= new LinearLayout(this);
				l.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT, 8.0f));
				l.setOrientation(LinearLayout.HORIZONTAL);
				LinearLayout tab1= new LinearLayout(this);
				tab1.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT, 1.0f));
				tab1.setOrientation(LinearLayout.VERTICAL);
				tab1.setGravity(Gravity.CENTER|Gravity.RIGHT);
				LinearLayout tab2= new LinearLayout(this);
				tab2.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT, 1.0f));
				tab2.setOrientation(LinearLayout.VERTICAL);
				tab2.setGravity(Gravity.CENTER|Gravity.LEFT);
				
				
				LinearLayout rr= new LinearLayout(this);
				rr.setBackgroundResource(R.drawable.bg_button);
				TextView t11 = new TextView(this);
				t11.setGravity(Gravity.CENTER);
				t11.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT, 1.0f));
				
				TextView t22 = new TextView(this);
				t22.setGravity(Gravity.CENTER);
				t22.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT, 1.0f));
				TextView t33 = new TextView(this);
				t33.setGravity(Gravity.CENTER);
				t33.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT, 1.0f));
//				TextView t44 = new TextView(this);
//				t44.setGravity(Gravity.CENTER);
//				t44.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				TextView t55 = new TextView(this);
//				t55.setGravity(Gravity.CENTER);
//				t55.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				TextView t66 = new TextView(this);
//				t66.setGravity(Gravity.CENTER);
//				t66.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
				t11.setText(field1+"  :");
				t11.setGravity(Gravity.CENTER | Gravity.RIGHT);
				t22.setText(field2+"  :");
				t22.setGravity(Gravity.CENTER | Gravity.RIGHT);
				t33.setText(field3+"  :");
				t33.setGravity(Gravity.CENTER | Gravity.RIGHT);
//				t44.setText("Desc         :  ");
//				t44.setGravity(Gravity.CENTER | Gravity.RIGHT);
//				t55.setText("status       :  ");
//				t55.setGravity(Gravity.CENTER | Gravity.RIGHT);
//				t66.setText("Edit");
//				rr.addView(t11);
//				rr.addView(t22);
//				rr.addView(t33);
//				rr.addView(t44);
//				rr.addView(t55);
//				rr.addView(t66);
//				l.addView(rr);
//				TableRow r = new TableRow(this);
				LinearLayout tb= new LinearLayout(this);
				tb.setGravity(Gravity.CENTER);
//				tb.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT, 1.0f));
				tb.setBackgroundResource(R.drawable.bg_button_grey);
				TextView ttb = new TextView(this);
				ttb.setText("Record List");
				ttb.setGravity(Gravity.CENTER);
				ttb.setTextSize(22);
				ttb.setTextColor(Color.WHITE);
				tb.addView(ttb);
				LinearLayout r= new LinearLayout(this);
//				r.setBackgroundResource(R.drawable.bg_titlebar);
				TextView t1 = new TextView(this);
				t1.setGravity(Gravity.CENTER | Gravity.LEFT);
				t1.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT, 1.0f));
				tab1.addView(t11);
				tab2.addView(t1);
				LinearLayout r1= new LinearLayout(this);
//				r1.setBackgroundResource(R.drawable.bg_titlebar);
				TextView t2 = new TextView(this);
				t2.setGravity(Gravity.CENTER | Gravity.LEFT);
				t2.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT, 1.0f));
				tab1.addView(t22);
				tab2.addView(t2);
				LinearLayout r2= new LinearLayout(this);
//				r2.setBackgroundResource(R.drawable.bg_titlebar);
				TextView t3 = new TextView(this);
				t3.setGravity(Gravity.CENTER | Gravity.LEFT);
				t3.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.MATCH_PARENT, 1.0f));
				tab1.addView(t33);
				tab2.addView(t3);
				LinearLayout r3= new LinearLayout(this);
				r3.setLayoutParams(new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT, 1.0f));
				r3.setOrientation(LinearLayout.HORIZONTAL);
				r3.setGravity(Gravity.CENTER);
//				r3.setBackgroundResource(R.drawable.bg_titlebar);
//				TextView t4 = new TextView(this);
//				t4.setGravity(Gravity.CENTER | Gravity.LEFT);
//				t4.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
////				tab1.addView(t44);
//				tab2.addView(t4);
//				LinearLayout r4= new LinearLayout(this);
////				r4.setBackgroundResource(R.drawable.bg_titlebar);
//				TextView t5 = new TextView(this);
//				t5.setGravity(Gravity.CENTER | Gravity.LEFT);
//				t5.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
////				tab1.addView(t55);
//				tab2.addView(t5);
				
				t1.setText(hm.get(field1));
				t2.setText(hm.get(field2));
				
				t3.setText(hm.get(field3));
//				t4.setText(hm.get("feelingCauseDesc"));
//				t5.setText(hm.get("status"));
//				Button b= new Button(this);
//				b.setText("Edit");
//				b.setTextSize(10);
//				b.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT));
				
//				r.addView(t3);
//				r.addView(t4);
//				r.addView(t5);
//				r.addView(b);
//				table.addView(r);
//				r.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent in= new Intent(getApplicationContext(),EditRecord.class);
//						in.putExtra("ID", hm.get("Id"));
//						startActivity(in);
//					}
//				});
				Button b= new Button(this);
				android.widget.LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
						 LayoutParams.WRAP_CONTENT,
	                        60);
				params.setMargins(0, 0, 5,0);
				b.setLayoutParams(params);
				
				b.setText("Update Feeling");
				b.setTextColor(Color.WHITE);
				b.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent in= new Intent(getApplicationContext(),UpdateCollectionDemoActivity.class);
						in.putExtra("ID",hm.get("Id"));
						String s=hm.get("Id");
						System.out.println(hm.get("Id"));
						startActivity(in);  
					}
				});
				b.setBackgroundResource(R.xml.btn_bg);
				Button delete= new Button(this);
				delete.setLayoutParams(new LinearLayout.LayoutParams(
						 LayoutParams.WRAP_CONTENT,
                        60));
				delete.setText("Delete Feeling");
				delete.setTextColor(Color.WHITE);
				delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						db.execSQL("delete from feelings_table where Id='"+hm.get("Id")+"'");
						Toast.makeText(getApplicationContext(), "Feeling has been deleted", Toast.LENGTH_SHORT).show();
//						realViewSwitcher.removeAllViews();
//						data = getDatafromDatabase();
//						setDataOnTable(data);
						finish();
					}
				});
				delete.setBackgroundResource(R.xml.btn_bg);
//				b.setPadding(5,5,5,5);
				
				l.setGravity(Gravity.CENTER);
				LinearLayout tsw= new LinearLayout(this);
				tsw.setGravity(Gravity.CENTER);
//				tb.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT, 1.0f));
				tsw.setBackgroundResource(R.drawable.bg_button_grey);
				TextView ttbsw = new TextView(this);
				ttbsw.setText("<< Swipe For Next >>");
				
				ttbsw.setGravity(Gravity.CENTER);
				ttbsw.setTextSize(22);
				ttbsw.setTextColor(Color.WHITE);
				tsw.addView(ttbsw);
				
				outl.setBackgroundResource(R.drawable.bg_app);
				
				outl.addView(tb);
//				l.addView(r);
//				l.addView(r1);
//				l.addView(r2);
//				l.addView(r3);
//				l.addView(r4);
				l.addView(tab1);
				l.addView(tab2);
				outl.addView(l);
				r3.addView(b);
				r3.addView(delete);
				outl.addView(r3);
				outl.addView(tsw);
				realViewSwitcher.addView(outl);
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"There is No Data For Display in Record..........!",
					Toast.LENGTH_SHORT).show();
		}

	}
	 private final RealViewSwitcher.OnScreenSwitchListener onScreenSwitchListener = new RealViewSwitcher.OnScreenSwitchListener() {
			
			public void onScreenSwitched(int screen) {
				// this method is executed if a screen has been activated, i.e. the screen is completely visible
				//  and the animation has stopped (might be useful for removing / adding new views)
				Log.d("RealViewSwitcher", "switched to screen: " + screen);
			}
			
		};
		@Override
			protected void onResume() {
				// TODO Auto-generated method stub
				super.onResume();
				realViewSwitcher.removeAllViews();
				data = getDatafromDatabase();
				setDataOnTable(data);
			}
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			db.close();
			h.close();
		}
}
