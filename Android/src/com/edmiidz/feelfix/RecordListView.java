package com.edmiidz.feelfix;





import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RecordListView extends Activity {
	TableLayout table;
//	ArrayList<HashMap<String, String>> data= new ArrayList<HashMap<String,String>>();
//	RealViewSwitcher realViewSwitcher;
	int i;
	mytask t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recordlistview);
		t=new mytask();
//		realViewSwitcher = new RealViewSwitcher(getApplicationContext());
//		realViewSwitcher.setOnScreenSwitchListener(onScreenSwitchListener);
		table = (TableLayout) findViewById(R.id.mytable);
		/*data = getDatafromDatabase();
		setDataOnTable(data);*/
//		setContentView(realViewSwitcher);

	}

	private ArrayList<HashMap<String, String>> getDatafromDatabase() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String query = "select from1to10,Feeling,feelingTime,feelingCauseDesc,status,Id from feelings_table";
		DBHelper h = new DBHelper(this);
		SQLiteDatabase db = h.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("from1to10", cursor.getString(0));
				hm.put("Feeling", cursor.getString(1));
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

//	private void setDataOnTable(ArrayList<HashMap<String, String>> data2) {
//		// TODO Auto-generated method stub
//		if (data2.size() > 0) {
////			TableRow rr = new TableRow(this);
////			rr.setBackgroundResource(R.drawable.bg_button);
////			TextView t11 = new TextView(this);
////			t11.setGravity(Gravity.CENTER);
////			TextView t22 = new TextView(this);
////			t22.setGravity(Gravity.CENTER);
////			TextView t33 = new TextView(this);
////			t33.setGravity(Gravity.CENTER);
////			TextView t44 = new TextView(this);
////			t44.setGravity(Gravity.CENTER);
////			TextView t55 = new TextView(this);
////			t55.setGravity(Gravity.CENTER);
////			t11.setText("From1to10");
////			t22.setText("Feeling");
////			t33.setText("Time");
////			t44.setText("Desc");
////			t55.setText("status");
////			rr.addView(t11);
////			rr.addView(t22);
////			rr.addView(t33);
////			rr.addView(t44);
////			rr.addView(t55);
////			table.addView(rr);
////			realViewSwitcher.addView(rr);
//			for (int i = 0; i < data2.size(); i++) {
//				final HashMap<String, String> hm = data2.get(i);
////				TableRow rr = new TableRow(this);
//				LinearLayout outl= new LinearLayout(this);
//				outl.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				outl.setOrientation(LinearLayout.VERTICAL);
//				outl.setGravity(Gravity.CENTER);
//				LinearLayout l= new LinearLayout(this);
//				l.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT, 8.0f));
//				l.setOrientation(LinearLayout.HORIZONTAL);
//				LinearLayout tab1= new LinearLayout(this);
//				tab1.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT, 1.0f));
//				tab1.setOrientation(LinearLayout.VERTICAL);
//				tab1.setGravity(Gravity.CENTER|Gravity.RIGHT);
//				LinearLayout tab2= new LinearLayout(this);
//				tab2.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT, 1.0f));
//				tab2.setOrientation(LinearLayout.VERTICAL);
//				tab2.setGravity(Gravity.CENTER|Gravity.LEFT);
//				
//				
//				LinearLayout rr= new LinearLayout(this);
//				rr.setBackgroundResource(R.drawable.bg_button);
//				TextView t11 = new TextView(this);
//				t11.setGravity(Gravity.CENTER);
//				t11.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				
//				TextView t22 = new TextView(this);
//				t22.setGravity(Gravity.CENTER);
//				t22.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				TextView t33 = new TextView(this);
//				t33.setGravity(Gravity.CENTER);
//				t33.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
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
////				TextView t66 = new TextView(this);
////				t66.setGravity(Gravity.CENTER);
////				t66.setLayoutParams(new LinearLayout.LayoutParams(
////                        LayoutParams.WRAP_CONTENT,
////                        LayoutParams.MATCH_PARENT, 1.0f));
//				t11.setText("From1to10    :  ");
//				t11.setGravity(Gravity.CENTER | Gravity.RIGHT);
//				t22.setText("Feeling      :  ");
//				t22.setGravity(Gravity.CENTER | Gravity.RIGHT);
//				t33.setText("Time         :  ");
//				t33.setGravity(Gravity.CENTER | Gravity.RIGHT);
//				t44.setText("Desc         :  ");
//				t44.setGravity(Gravity.CENTER | Gravity.RIGHT);
//				t55.setText("status       :  ");
//				t55.setGravity(Gravity.CENTER | Gravity.RIGHT);
////				t66.setText("Edit");
////				rr.addView(t11);
////				rr.addView(t22);
////				rr.addView(t33);
////				rr.addView(t44);
////				rr.addView(t55);
////				rr.addView(t66);
////				l.addView(rr);
////				TableRow r = new TableRow(this);
//				LinearLayout tb= new LinearLayout(this);
//				tb.setGravity(Gravity.CENTER);
////				tb.setLayoutParams(new LinearLayout.LayoutParams(
////                        LayoutParams.MATCH_PARENT,
////                        LayoutParams.WRAP_CONTENT, 1.0f));
//				tb.setBackgroundResource(R.drawable.bg_button_grey);
//				TextView ttb = new TextView(this);
//				ttb.setText("Record List");
//				ttb.setGravity(Gravity.CENTER);
//				ttb.setTextSize(22);
//				ttb.setTextColor(Color.WHITE);
//				tb.addView(ttb);
//				LinearLayout r= new LinearLayout(this);
////				r.setBackgroundResource(R.drawable.bg_titlebar);
//				TextView t1 = new TextView(this);
//				t1.setGravity(Gravity.CENTER | Gravity.LEFT);
//				t1.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				tab1.addView(t11);
//				tab2.addView(t1);
//				LinearLayout r1= new LinearLayout(this);
////				r1.setBackgroundResource(R.drawable.bg_titlebar);
//				TextView t2 = new TextView(this);
//				t2.setGravity(Gravity.CENTER | Gravity.LEFT);
//				t2.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				tab1.addView(t22);
//				tab2.addView(t2);
//				LinearLayout r2= new LinearLayout(this);
////				r2.setBackgroundResource(R.drawable.bg_titlebar);
//				TextView t3 = new TextView(this);
//				t3.setGravity(Gravity.CENTER | Gravity.LEFT);
//				t3.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				tab1.addView(t33);
//				tab2.addView(t3);
//				LinearLayout r3= new LinearLayout(this);
////				r3.setBackgroundResource(R.drawable.bg_titlebar);
//				TextView t4 = new TextView(this);
//				t4.setGravity(Gravity.CENTER | Gravity.LEFT);
//				t4.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				tab1.addView(t44);
//				tab2.addView(t4);
//				LinearLayout r4= new LinearLayout(this);
////				r4.setBackgroundResource(R.drawable.bg_titlebar);
//				TextView t5 = new TextView(this);
//				t5.setGravity(Gravity.CENTER | Gravity.LEFT);
//				t5.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.MATCH_PARENT, 1.0f));
//				tab1.addView(t55);
//				tab2.addView(t5);
//				
//				t1.setText(hm.get("from1to10"));
//				t2.setText(hm.get("Feeling"));
//				
//				t3.setText(hm.get("feelingTime"));
//				t4.setText(hm.get("feelingCauseDesc"));
//				t5.setText(hm.get("status"));
////				Button b= new Button(this);
////				b.setText("Edit");
////				b.setTextSize(10);
////				b.setLayoutParams(new LinearLayout.LayoutParams(
////                        LayoutParams.WRAP_CONTENT,
////                        LayoutParams.MATCH_PARENT));
//				
////				r.addView(t3);
////				r.addView(t4);
////				r.addView(t5);
////				r.addView(b);
////				table.addView(r);
////				r.setOnClickListener(new OnClickListener() {
////					
////					@Override
////					public void onClick(View v) {
////						// TODO Auto-generated method stub
////						Intent in= new Intent(getApplicationContext(),EditRecord.class);
////						in.putExtra("ID", hm.get("Id"));
////						startActivity(in);
////					}
////				});
//				Button b= new Button(this);
//				b.setLayoutParams(new LinearLayout.LayoutParams(
//						 LayoutParams.WRAP_CONTENT,
//                        60));
//				b.setText("Update Feeling");
//				b.setTextColor(Color.WHITE);
//				b.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Intent in= new Intent(getApplicationContext(),EditRecord.class);
//						in.putExtra("ID",hm.get("Id"));
//						String s=hm.get("Id");
//						System.out.println(hm.get("Id"));
//						startActivity(in);  
//					}
//				});
//				b.setBackgroundResource(R.xml.btn_bg);
////				b.setPadding(5,5,5,5);
//				
//				l.setGravity(Gravity.CENTER);
//				LinearLayout tsw= new LinearLayout(this);
//				tsw.setGravity(Gravity.CENTER);
////				tb.setLayoutParams(new LinearLayout.LayoutParams(
////                        LayoutParams.MATCH_PARENT,
////                        LayoutParams.WRAP_CONTENT, 1.0f));
//				tsw.setBackgroundResource(R.drawable.bg_button_grey);
//				TextView ttbsw = new TextView(this);
//				ttbsw.setText("<< Swipe For Next >>");
//				
//				ttbsw.setGravity(Gravity.CENTER);
//				ttbsw.setTextSize(22);
//				ttbsw.setTextColor(Color.WHITE);
//				tsw.addView(ttbsw);
//				
//				outl.setBackgroundResource(R.drawable.bg_app);
//				
//				outl.addView(tb);
////				l.addView(r);
////				l.addView(r1);
////				l.addView(r2);
////				l.addView(r3);
////				l.addView(r4);
//				l.addView(tab1);
//				l.addView(tab2);
//				outl.addView(l);
//				outl.addView(b);
//				outl.addView(tsw);
//				realViewSwitcher.addView(outl);
//			}
//		} else {
//			Toast.makeText(getApplicationContext(),
//					"There is No Data For Display in Record..........!",
//					Toast.LENGTH_SHORT).show();
//		}
//
//	}
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
			t11.setText("From1to10");
			t22.setText("Feeling");
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
//						Toast.makeText(getApplicationContext(), coutv.getText().toString(), Toast.LENGTH_SHORT).show();
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
//				table.removeAllViews();
//				data = getDatafromDatabase();
//				setDataOnTable(data);
				table.removeAllViews();
				ArrayList<HashMap<String, String>> data=getDatafromDatabase();
				data = getDatafromDatabase();
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
				progressDialog2 = ProgressDialog.show(RecordListView.this, "", "Please wait for Loading...");
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
