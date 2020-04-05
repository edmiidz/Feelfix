package com.edmiidz.feelfix;

import com.edmiidz.feelfix.R;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		DBHelper h= new DBHelper(this);
		SQLiteDatabase db = h.getWritableDatabase();
		db.close();
		h.close();
		Button start= (Button)findViewById(R.id.button_start);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),CollectionDemoActivity.class);
				startActivity(in);
			}
		});
		
		Button search= (Button) findViewById(R.id.button_search);
		search.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),SearchView.class);
				startActivity(in);
			}
			
			
		});
		
//		Button uploadondrive =(Button)findViewById(R.id.button_upload);
//		uploadondrive.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent in = new Intent(getApplicationContext(),UploadonGoogleDrive.class);
//				startActivity(in);
//				
//			}
//		});
//		Button downloadfromdrive = (Button) findViewById(R.id.button_download);
//		downloadfromdrive.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent in = new Intent(getApplicationContext(),
//						DownloadFromDrive.class);
//				startActivity(in);
//
//			}
//		});
		/*Button syn=(Button)findViewById(R.id.button_syn);
		syn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(getApplicationContext(),
						DataSyn.class);
				startActivity(in);
			}
		});
*/
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inf=getMenuInflater();
		inf.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.item1){
			Intent in = new Intent(getApplicationContext(),UploadonGoogleDrive.class);
			startActivity(in);
		}
		else if(item.getItemId()==R.id.item2){
			//todo: add a backup class (which will prompt for an authentication)
			//Intent in2 = new Intent(getApplicationContext(),BackUpOnGoogleDrive.class);
			Intent in = new Intent(getApplicationContext(),DownloadFromDrive.class);
			
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
}
