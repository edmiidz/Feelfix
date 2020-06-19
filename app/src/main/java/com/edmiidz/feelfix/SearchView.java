/**
 * 
 */
package com.edmiidz.feelfix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * @author nedmiidz
 *
 */
public class SearchView extends AppCompatActivity {
    protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
    String field1, field2, field3, field4, rowcolor;
    String field1name, field2name, field3name, field4name;

    // private ArrayList<String> fieldNameList;
    private ArrayList<String> fieldValueList;

    //  private String fieldNameList[] = getResources().getStringArray(R.array.fieldname);


    ArrayList<HashMap<String, String>> data;
//	ArrayList<HashMap<String, String>> data=getDatafromDatabase();

    TableLayout table;
    EditText searchEdit;

//	setDataOnTable(data);	

    RealViewSwitcher realViewSwitcher;

    DBHelper h;
    SQLiteDatabase db;
    //	Button find;
    String[] names;
    int columncounts;

    int i;
//	mytask t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);
        h = new DBHelper(this);
        db = h.getWritableDatabase();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);



        field1 = preferences.getString("field1", "from1to10");
        field2 = preferences.getString("field2", "Feeling");
        field3 = preferences.getString("field3", "feelingTime");
        field4 = preferences.getString("field4", "mainaffectedinstinct");
        rowcolor = preferences.getString("rowcolor","Highlight Weekends");

        //if(rowcolor.isEmpty()) rowcolor = "Highlight Weekends";
        //rowcolor = "Highlight Weekends";

        Log.d("SilentModeApp", "on rowcolorrrrrrrrrrrrrrrrrrr");
//        Log.d("SilentModeApp", rowcolor);
        //Highlight Weekends


        fieldValueList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.fieldvalues)));
        //  String fieldValueList[] = getResources().getStringArray(R.array.fieldvalues);
        String fieldNameList[] = getResources().getStringArray(R.array.fieldname);

        //   fieldNameList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.fieldname)));

//        String fieldname1 = "";
        field1name = fieldNameList[fieldValueList.indexOf(field1)];
        field2name = fieldNameList[fieldValueList.indexOf(field2)];
        field3name = fieldNameList[fieldValueList.indexOf(field3)];
        field4name = fieldNameList[fieldValueList.indexOf(field4)];

//        Toast.makeText(this, "field1:" + "\n(" + field1 + ")", Toast.LENGTH_LONG).show();
//
//        System.out.println("field1name:" + "\n(" + field1name + ")");
//        Log.d("SilentModeApp", "HHHHHHHHHHHHHHHH");


//		 table = (TableLayout) findViewById(R.id.mytable);
        table = (TableLayout) findViewById(R.id.mytable);
        searchEdit = (EditText) findViewById(R.id.editText1);

        table.removeAllViews();

        //	insertData();
//			Toast.makeText(getApplicationContext(),
//					"Finding your results",
//					Toast.LENGTH_LONG).show();
//			//finish();

//			data = getDatafromDatabase(searchEdit.getText().toString());
//
//			setDataOnTable(data);
//			data.clear();

        //find = (Button) findViewById(R.id.button_search);

        searchEdit.addTextChangedListener(new TextWatcher() {


            private Timer timer = new Timer();
            private final long DELAY = 500; // in ms


            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1,
                                      int arg2, int arg3) {
                // TODO Auto-generated method stub


//					timer.cancel();
//                    timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            // TODO: do what you need here (refresh list)
//                            // you will probably need to use runOnUiThread(Runnable action) for some specific actions
//                        }
//
//                    }, DELAY);


                table.removeAllViews();

                //	insertData();
                //finish();

                data = getDatafromDatabase(searchEdit.getText().toString());

                setDataOnTable(data);
                data.clear();


            }


        });




    }




    private ArrayList<HashMap<String, String>> getDatafromDatabase(String searchstring) {
        // TODO Auto-generated method stub
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        //String query = "select from1to10,Feeling,feelingTime,feelingCauseDesc,status,Id,involvedpeople from feelings_table order by Id desc";
        //	String query = "select from1to10,Feeling,feelingTime,mainaffectedinstinct,status,feelingCauseDesc,involvedpeople from feelings_table ";
        String query = "select from1to10 as f1to10, feelingTime as ft, " + field1 + "," + field2 + "," + field3 + "," + field4 + " from feelings_table ";

        if (searchstring.length() > 2) {
            query = query + "where Feeling like '%" + searchstring + "%' or " +
                    "feelingCauseDesc like '%" + searchstring + "%' or " +
                    "involvedpeople like '%" + searchstring + "%' or " +
                    "status like '%" + searchstring + "%' or " +
                    "mainaffectedinstinct like '%" + searchstring + "%' or " +
                    "feelingTime like '%" + searchstring + "%' ";
            //		"from1to10 like '%" + searchstring + "%' or " +
        }
//		query = query + " order by Id desc ";
        query = query + " order by feelingTime desc LIMIT 0, 250";

        Log.d("SilentModeApp", query);


        String name = null;

        Cursor cursor = db.rawQuery(query, null);

        names = cursor.getColumnNames();
        columncounts = cursor.getColumnCount();
        Log.d("SilentModeApp", "" + columncounts);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                HashMap<String, String> hm = new HashMap<String, String>();

                hm.put("f1to10", cursor.getString(0));
                hm.put("ft", cursor.getString(1));

                hm.put(field1, truncateForSearch(cursor.getString(2)));
                hm.put(field2, truncateForSearch(cursor.getString(3)));
                hm.put(field3, truncateForSearch(cursor.getString(4)));
                hm.put(field4, truncateForSearch(cursor.getString(5)));

//				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//				String formattedDate = df.format(cursor.getString(0));
//				hm.put("ft", formattedDate);

//				hm.put("feelingTime", cursor.getString(2));
//				hm.put("feelingCauseDesc", cursor.getString(3));
//				hm.put("status", cursor.getString(5));
                //	hm.put("Id", cursor.getString(6));
                list.add(hm);
            }
        }
        cursor.close();
        //	db.close();
        //	h.close();

        return list;
    }

    protected String truncateForSearch(String field){


        Calendar now = Calendar.getInstance();   
		int year = now.get(Calendar.YEAR);
        String pattern = "(" + year + "\\/)(.*)";
        

        if(field.matches(pattern)){
            field = field.substring(5, field.length());

        } else {

            if (field.length() > 14) {
                field = field.substring(0, 11) + "...";
            }
        }
        return field;

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        db.close();
        h.close();
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inf=getMenuInflater();
		inf.inflate(R.menu.search_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.item1){
			//Intent in = new Intent(getApplicationContext(),UploadonGoogleDrive.class);
			//startActivity(in);
            Toast.makeText(getApplicationContext(), "Advanced Search is still under construction.", Toast.LENGTH_LONG).show();
		}
		else if(item.getItemId()==R.id.item2){
			Intent in = new Intent(getApplicationContext(),RowColor.class);
			
			//startActivity(in2);
			startActivity(in);
		}
		else if(item.getItemId()==R.id.item3){
			Intent in = new Intent(getApplicationContext(),
					Setting.class);
			startActivity(in);
		}
		
		return super.onOptionsItemSelected(item);
	}

    @SuppressWarnings("deprecation")
    private void setDataOnTable(ArrayList<HashMap<String, String>> data2) {
        // TODO Auto-generated method stub
        if (data2.size() > 0) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

            rowcolor = preferences.getString("rowcolor","Highlight Weekends");

            TableRow rr = new TableRow(this);

            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int height = metrics.heightPixels / 2; // quarter of screen height
            rr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, height));


            rr.setBackgroundResource(R.drawable.bg_button);
            TextView t11 = new TextView(this);
            t11.setTextColor(Color.WHITE);
            t11.setGravity(Gravity.CENTER);
            TextView t22 = new TextView(this);
            t22.setTextColor(Color.WHITE);
            t22.setGravity(Gravity.CENTER);
            TextView t33 = new TextView(this);
            t33.setTextColor(Color.WHITE);
            t33.setGravity(Gravity.CENTER);
//			TextView t44= new TextView(this);
//			t44.setGravity(Gravity.CENTER);
            TextView t44 = new TextView(this);
            t44.setTextColor(Color.WHITE);
            t44.setGravity(Gravity.CENTER);
//			t11.setText("Rating");
//			t22.setText("Feelings");
//			t33.setText("Time");
////			t44.setText("Desc");
//			//t44.setText(fieldNameList[fieldValueList.indexOf(field4)]);
//            t44.setText("stattus");
            t11.setText(field1name);
            t22.setText(field2name);
            t33.setText(field3name);
            t44.setText(field4name);
            rr.addView(t11);
            rr.addView(t22);
            rr.addView(t33);
//			rr.addView(t44);
            rr.addView(t44);
            rr.setGravity(Gravity.CENTER);
            table.addView(rr);


            // TableLayout table = (TableLayout) findViewById(R.id.TableLayout1);
            TableRow tr1 = new TableRow(this);
            tr1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            TextView textview = new TextView(this);
            textview.setText("Hello world");   //or whatever you want

            //   tr1.addView(textview);
            table.addView(tr1, new TableLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

            Log.d("SilentModeApp", "before loops if starting nowwwwwwwwwwwwwww");


            for (i = 0; i < data2.size(); i++) {

                HashMap<String, String> hm = data2.get(i);
                final TableRow r = new TableRow(this);
                r.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 100));
                r.setBackgroundResource(R.drawable.bg_titlebar);
                TextView t1 = new TextView(this);

                t1.setGravity(Gravity.CENTER);
                TextView t2 = new TextView(this);
                t2.setGravity(Gravity.CENTER);
                TextView t3 = new TextView(this);
                t3.setGravity(Gravity.CENTER);
//				TextView t4= new TextView(this);
//				t4.setGravity(Gravity.CENTER);
                TextView t4 = new TextView(this);
                t4.setGravity(Gravity.CENTER);
//				t1.setText(hm.get("from1to10"));
//				t2.setText(hm.get("Feeling"));
//				t3.setText(hm.get("feelingTime"));
//                t4.setText(hm.get("status"));

                t1.setText(hm.get(field1));
                t2.setText(hm.get(field2));
                t3.setText(hm.get(field3));
                t4.setText(hm.get(field4));


                Calendar c = Calendar.getInstance();
                try {
                    Date d;
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                    d = fmt.parse(hm.get("ft"));

                    c.setTime(d);

                    if(rowcolor.equals("Highlight Weekends")){

                        Log.d("SilentModeApp", "Highlight Weekends");


                        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                        if ((dayOfWeek == 7) || (dayOfWeek == 1)) {
                            r.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                        } else {
                            r.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                            Log.d("SilentModeApp", "not Highlight Weekends");

                        }
                    } else {

                            r.setBackgroundColor(Color.rgb(128, 128 + Integer.parseInt(hm.get("f1to10"))*10, 128 - Integer.parseInt(hm.get("f1to10"))*10));
                        //}
                    }




                    //	System.out.println("I am Here");
                    //	System.out.println(dayOfWeek);

                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //	 System.out.println("I am NOOOOT Here");
                //		Log.d("SilentModeApp", "GGGGGGGGGGGGGGGGG");


//				t4.setText(hm.get("feelingCauseDesc"));
                r.addView(t1);
                r.addView(t2);
                r.addView(t3);
                r.addView(t4);
                table.addView(r);
                final TextView coutv = new TextView(this);
                coutv.setText(i + "");

                r.setGravity(Gravity.CENTER);
                r.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Toast.makeText(
                        getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();

                        r.setBackgroundColor(Color.parseColor("#a068ff"));

                        // TODO pass correct ID of the row clicked.
                        Intent in = new Intent(getApplicationContext(), RecordList.class);
                        in.putExtra("position", coutv.getText().toString());
                        in.putExtra("searchstring", searchEdit.getText().toString());

                        //Log.d("SilentModeApp", "Nik 's searchstring: " +  searchEdit.getText().toString());

                        //		Toast.makeText(getApplicationContext(), coutv.getText().toString(), Toast.LENGTH_SHORT).show();
                        startActivity(in);

                    }
                });


            }
        } else {
            Toast.makeText(getApplicationContext(), "There is No Data For Display in Record..........!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        field1 = preferences.getString("field1", "from1to10");
        field2 = preferences.getString("field2", "Feeling");
        field3 = preferences.getString("field3", "feelingTime");
        field4 = preferences.getString("field4", "mainaffectedinstinct");
        rowcolor = preferences.getString("rowcolor","Highlight Weekends");


        getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
        // TODO Auto-generated method stub
        super.onResume();

        table.removeAllViews();
        //ArrayList<HashMap<String, String>> data = getDatafromDatabase("");

        ArrayList<HashMap<String, String>> data = getDatafromDatabase(searchEdit.getText().toString());

        setDataOnTable(data);
        data.clear();

        Log.d("SilentModeApp", "on resumeeeeeeeeeeeeeeeee");
    }

    class mytask extends AsyncTask {
        ProgressDialog progressDialog2;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//				progressDialog.setIcon(R.drawable.icon);
            progressDialog2 = ProgressDialog.show(SearchView.this, "", "Please wait for Loading...");
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
//				table.removeAllViews();
//				data = getDatafromDatabase();
//				setDataOnTable(data);
            return null;
        }


    }



}
 
