package com.edmiidz.feelfix;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.edmiidz.feelfix.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class More extends Activity {
	String id;
	EditText EditText_havebeenbetter,EditText_have_been_worse,EditText_created,EditText_updatedlast;
	DBHelper h;
	SQLiteDatabase db;
	Button save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		id=getIntent().getStringExtra("id");
		h=new DBHelper(this);
		db= h.getWritableDatabase();
		EditText_havebeenbetter=(EditText)findViewById(R.id.EditText_havebeenbetter);
		EditText_have_been_worse=(EditText)findViewById(R.id.EditText_have_been_worse);
//		EditText_created=(EditText)findViewById(R.id.EditText_created);
//		EditText_updatedlast=(EditText)findViewById(R.id.EditText_updatedlast);
		setData(id);
//		save=(Button)findViewById(R.id.Button_more_save);
//		save.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				insertData(id);
//				Toast.makeText(getApplicationContext(), "Feeling has been sucessfully Updated", Toast.LENGTH_LONG).show();
//				finish();
//			}
//		});
	}
	
	
	private void setData(String id) {
		// TODO Auto-generated method stub
		String query="select wouldabeenbetter,wouldabeenworse,created,updatedLast from feelings_table where Id='"+id+"'";
		Cursor cursor= db.rawQuery(query, null);
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				EditText_havebeenbetter.setText(cursor.getString(cursor.getColumnIndex("wouldabeenbetter")));
				EditText_have_been_worse.setText(cursor.getString(cursor.getColumnIndex("wouldabeenworse")));
				EditText_created.setText(cursor.getString(cursor.getColumnIndex("created")));
				EditText_updatedlast.setText(cursor.getString(cursor.getColumnIndex("updatedLast")));
				
			}
		}
		cursor.close();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();
		h.close();
	}
	private void insertData(String id) {
		// TODO Auto-generated method stub
		
//		String curtime=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
		ContentValues cv= new ContentValues();
		
		cv.put(DBHelper.wouldabeenbetter, EditText_havebeenbetter.getText().toString());
		cv.put(DBHelper.wouldabeenworse, EditText_have_been_worse.getText().toString());
		cv.put(DBHelper.updatedLast, formattedDate);
		db.update(DBHelper.feelings_table, cv, DBHelper.Id+"= '"+id+"'", null);
//		db.close();
//		h.close();
		
		
		
	}
}
