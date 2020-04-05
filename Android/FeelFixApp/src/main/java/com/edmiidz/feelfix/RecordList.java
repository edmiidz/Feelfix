package com.edmiidz.feelfix;


import java.util.ArrayList;
import java.util.HashMap;

import com.edmiidz.feelfix.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
	// This is the scrollable read-only view
	String field1,field2,field3;
	DBHelper h ;
	SQLiteDatabase db;
	int pos;
	String[] names;
	int columncounts;
	String searchstring;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.recordlist);
	 h = new DBHelper(this);
		 db = h.getWritableDatabase();
		 pos=Integer.parseInt(getIntent().getStringExtra("position"));
		 searchstring=getIntent().getStringExtra("searchstring");
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
		String query = "select from1to10,Feeling,feelingTime,feelingCauseDesc,status,Id,involvedpeople,latitude,longitude,mainaffectedinstinct,created,wouldabeenbetter,updatedLast from feelings_table ";
		
		
		if(searchstring.length() > 1){
            query = query + "where Feeling like '%" + searchstring + "%' or " +
                    "feelingCauseDesc like '%" + searchstring + "%' or " +
                    "involvedpeople like '%" + searchstring + "%' or " +
                    "status like '%" + searchstring + "%' or " +
                    "mainaffectedinstinct like '%" + searchstring + "%' or " +
                    "feelingTime like '%" + searchstring + "%' ";
            //		"from1to10 like '%" + searchstring + "%' or " +
			}
        query = query + " order by feelingTime desc LIMIT 0, 250";
			
		//String query = "select Id,"+field1+","+field2+","+field3+ ",status from feelings_table order by Id desc";
		//String query = "select * from feelings_table";
		String name = null;
		
		Cursor cursor = db.rawQuery(query, null);
		
//		cursor.moveToFirst();
  //      name = cursor.getString(cursor.getColumnIndex("name"));
		names = cursor.getColumnNames();
		columncounts = cursor.getColumnCount();
		Log.d("SilentModeApp",""+columncounts);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				HashMap<String, String> hm = new HashMap<String, String>();
					
					for (int i = 0; i < columncounts; i++) {
					//	Log.d("array", "column name " + names[i] + ", value: " + cursor.getString(i));
						hm.put(names[i], cursor.getString(i));
						
						//Log.d("SilentModeApp", i + "  column" + names[i] + ", value: " + cursor.getString(i));

			//			Log.d("SilentModeApp", "Nik 's query: " query);
					}
//				hm.put("Id", cursor.getString(0));
//				hm.put("from1to10", cursor.getString(1));
//				hm.put(field2, cursor.getString(2));
//				hm.put("status", cursor.getString(3));
////				hm.put("feelingCauseDesc", cursor.getString(3));
//				hm.put("status", cursor.getString(4));
////				
//				hm.put("test", cursor.getString(5));
//				hm.put("test", cursor.getString(6));
//				hm.put("test", cursor.getString(7));
				
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

        if (data2.size() > 0) for (int i = 0; i < data2.size(); i++) {
            final HashMap<String, String> hm = data2.get(i);

            final String coordinates = hm.get("latitude") + "," + hm.get("longitude");
            LinearLayout outl = new LinearLayout(this);
            outl.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            outl.setOrientation(LinearLayout.VERTICAL);
            outl.setGravity(Gravity.CENTER);
            LinearLayout l = new LinearLayout(this);
            l.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 5.0f));
            l.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout tab1 = new LinearLayout(this);
            tab1.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1.0f));
            tab1.setOrientation(LinearLayout.VERTICAL);
            tab1.setGravity(Gravity.CENTER | Gravity.RIGHT);
            LinearLayout tab2 = new LinearLayout(this);
            tab2.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1.0f));
            tab2.setOrientation(LinearLayout.VERTICAL);
            tab2.setGravity(Gravity.CENTER | Gravity.LEFT);


            LinearLayout rr = new LinearLayout(this);
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
            TextView t44 = new TextView(this);
            t44.setGravity(Gravity.CENTER);
            t44.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t55 = new TextView(this);
            t55.setGravity(Gravity.CENTER);
            t55.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t66 = new TextView(this);
            t66.setGravity(Gravity.CENTER);
            t66.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t77 = new TextView(this);
            t77.setGravity(Gravity.CENTER);
            t77.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t88 = new TextView(this);
            t88.setGravity(Gravity.CENTER);
            t88.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t99 = new TextView(this);
            t99.setGravity(Gravity.CENTER);
            t99.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t100 = new TextView(this);
            t100.setGravity(Gravity.CENTER);
            t100.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t101 = new TextView(this);
            t101.setGravity(Gravity.CENTER);
            t101.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));



            t11.setText("Rating" + ": ");
            t11.setGravity(Gravity.TOP | Gravity.RIGHT);
            t22.setText(getString(R.string.feelingtime) + ": ");
            t22.setGravity(Gravity.TOP | Gravity.RIGHT);
            t33.setText("Status" + ": ");
            t33.setGravity(Gravity.TOP | Gravity.RIGHT);
            t44.setText("What happened?" + ":  ");
            t44.setGravity(Gravity.CENTER | Gravity.RIGHT);
            t55.setText(getString(R.string.feelings) + "       :  ");
            t55.setGravity(Gravity.CENTER | Gravity.RIGHT);
            t66.setText("Involved People" + "  :  ");
            t66.setGravity(Gravity.CENTER | Gravity.RIGHT);
            t88.setText("Affected Instincts" + "  :  ");
            t88.setGravity(Gravity.CENTER | Gravity.RIGHT);

            t99.setText("Contributing Factors" + "  :  ");
            t99.setGravity(Gravity.CENTER | Gravity.RIGHT);


            t100.setText("Created Time" + "  :  ");
            t100.setGravity(Gravity.CENTER | Gravity.RIGHT);

            t101.setText("Updated Last" + "  :  ");
            t101.setGravity(Gravity.CENTER | Gravity.RIGHT);

            // t66.setMovementMethod(LinkMovementMethod.getInstance());

            if (!coordinates.equals("0,0")) {

                t77.setText("Where I recorded it." + "  :  ");
                t77.setGravity(Gravity.CENTER | Gravity.RIGHT);

            }


//				t66.setText("Edit");
//				rr.addView(t11);
//				rr.addView(t22);
//				rr.addView(t33);
//				rr.addView(t44);
//				rr.addView(t55);
//				rr.addView(t66);
//				l.addView(rr);
//				TableRow r = new TableRow(this);
            LinearLayout tb = new LinearLayout(this);
            tb.setGravity(Gravity.CENTER);
            //	tb.setLayoutParams(new LinearLayout.LayoutParams(
            //              LayoutParams.MATCH_PARENT,
            //               LayoutParams.WRAP_CONTENT, 1.0f));
            tb.setBackgroundResource(R.drawable.bg_button_grey);
            TextView ttb = new TextView(this);
            ttb.setText("Record List..");
            ttb.setGravity(Gravity.CENTER);
            ttb.setTextSize(22);
            ttb.setTextColor(Color.WHITE);
            tb.addView(ttb);
            LinearLayout r = new LinearLayout(this);
//				r.setBackgroundResource(R.drawable.bg_titlebar);
            TextView t1 = new TextView(this);
            t1.setGravity(Gravity.CENTER | Gravity.LEFT);
            t1.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t11);
            tab2.addView(t1);
            LinearLayout r1 = new LinearLayout(this);
//				r1.setBackgroundResource(R.drawable.bg_titlebar);
            TextView t2 = new TextView(this);
            t2.setGravity(Gravity.CENTER | Gravity.LEFT);
            t2.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t22);
            tab2.addView(t2);
            LinearLayout r2 = new LinearLayout(this);
//				r2.setBackgroundResource(R.drawable.bg_titlebar);
            TextView t3 = new TextView(this);
            t3.setGravity(Gravity.CENTER | Gravity.LEFT);
            t3.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t33);
            tab2.addView(t3);
            LinearLayout r3 = new LinearLayout(this);
            r3.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1.0f));
            r3.setOrientation(LinearLayout.HORIZONTAL);
            r3.setGravity(Gravity.CENTER);
//				r3.setBackgroundResource(R.drawable.bg_titlebar);
            TextView t4 = new TextView(this);
            t4.setGravity(Gravity.CENTER | Gravity.LEFT);
            t4.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t44);
            tab2.addView(t4);
            LinearLayout r4 = new LinearLayout(this);
//				r4.setBackgroundResource(R.drawable.bg_titlebar);
            TextView t5 = new TextView(this);
            t5.setGravity(Gravity.CENTER | Gravity.LEFT);
            t5.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t55);
            tab2.addView(t5);

            TextView t6 = new TextView(this);
            t6.setGravity(Gravity.CENTER | Gravity.LEFT);
            t6.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t66);
            tab2.addView(t6);

            TextView t7 = new TextView(this);
            t7.setGravity(Gravity.CENTER | Gravity.LEFT);
            t7.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t77);
            tab2.addView(t7);

            TextView t8 = new TextView(this);
            t8.setGravity(Gravity.CENTER | Gravity.LEFT);
            t8.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            tab1.addView(t88);
            tab2.addView(t8);

            TextView t9 = new TextView(this);
            t9.setGravity(Gravity.CENTER | Gravity.LEFT);
            t9.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));
            TextView t10 = new TextView(this);
            t10.setGravity(Gravity.CENTER | Gravity.LEFT);
            t10.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));


            TextView tt11 = new TextView(this);
            tt11.setGravity(Gravity.CENTER | Gravity.LEFT);
            tt11.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT, 1.0f));


            tab1.addView(t99);
            tab1.addView(t100);
            tab1.addView(t101);
            tab2.addView(t9);
            tab2.addView(t10);
            tab2.addView(tt11);


            t1.setText(hm.get("from1to10"));
            t2.setText(hm.get("feelingTime"));

            t3.setText(hm.get("status"));

            String desc = hm.get("feelingCauseDesc");
            String feeling = hm.get("Feeling");
            String involvedpeople = hm.get("involvedpeople");

            desc = truncate(desc);
            feeling = truncate(feeling);
            involvedpeople = truncate(involvedpeople);

            t4.setText(desc);
//				t5.setText(myvar);
//				t4.setText(hm.get("feelingCauseDesc"));
            t5.setText(feeling);
            t6.setText(involvedpeople);
            t8.setText(hm.get("mainaffectedinstinct"));
            t9.setText(truncate(hm.get("wouldabeenbetter")));
            t10.setText(hm.get("created"));
            tt11.setText(hm.get("updatedLast"));
//                t8.setText(hm.get("mainaffectedinstinct,created,wouldabeenbetter"));

            //t7.setText(coordinates);

            if (!coordinates.equals("0,0")) {

                t7.setText(
                        Html.fromHtml(
                                "<a href=\"http://www.google.com\">" + coordinates + "</a> "));
                t7.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Intent i = new
                                Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + coordinates));

                        //Uri.parse("geo:" + coordinates));
                        startActivity(i);
                    }
                });
            }

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


            Button b = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    120);

            params.setMargins(0, 0, 5, 0);
//				b.setHeight(22);
            b.setLayoutParams(params);

            b.setText("Update Feeling");
            b.setTextColor(Color.WHITE);
            b.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //why did we start to use UpdateCollectionDemoActivity.class in the first place?
                    // niktodo: 20141031
                    Intent in = new Intent(getApplicationContext(), UpdateCollectionDemoActivity.class);
                    //	Intent in= new Intent(getApplicationContext(),EditRecord.class);
                    in.putExtra("ID", hm.get("Id"));
                    String s = hm.get("Id");
                    System.out.println(hm.get("Id"));
                    startActivity(in);
                    //	Toast.makeText(getApplicationContext(), hm.get("Id").toString(), Toast.LENGTH_SHORT).show();

                }
            });
            b.setBackgroundResource(R.xml.btn_bg);
            Button delete = new Button(this);
            delete.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    120));
            delete.setText("Delete Feeling");
            delete.setTextColor(Color.WHITE);
            delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            RecordList.this);
                    alertDialog.setMessage(getString(R.string.deletesingleconfirm));
                    //		Log.d("SilentModeApp", "YESSSSSSSSSSSSSSSSS");

                    alertDialog.setPositiveButton(
                            "Delete", new DialogInterface.OnClickListener() {


                                @SuppressWarnings("deprecation")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    db.execSQL("delete from feelings_table where Id='" + hm.get("Id") + "'");
                                    Toast.makeText(getApplicationContext(), "Feeling has been deleted", Toast.LENGTH_SHORT).show();
                                    finish();

                                    //refresh
//										dialog.cancel();
//										finish();
                                }
                            });
                    alertDialog.setNegativeButton(
                            "Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                    alertDialog.setCancelable(false);
                    alertDialog.show();


                }
            });
            delete.setBackgroundResource(R.xml.btn_bg);
            b.setPadding(5, 5, 5, 5);

            l.setGravity(Gravity.CENTER);
            LinearLayout tsw = new LinearLayout(this);
            tsw.setGravity(Gravity.CENTER);
//				tb.setLayoutParams(new LinearLayout.LayoutParams(
//                        LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT, 1.0f));
            tsw.setBackgroundResource(R.drawable.bg_button_grey);
            TextView ttbsw = new TextView(this);
            ttbsw.setText("<< Swipe For Next  >>");

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
        else {
			Toast.makeText(getApplicationContext(),
					"There is No Data To Display..........!",
					Toast.LENGTH_SHORT).show();
		}

	}

    private String truncate(String field){
        if(field.length()>14){
            field =  field.substring(0, 14) + "...";
        }
        return field;
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
