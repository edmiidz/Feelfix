package com.edmiidz.feelfix;

import com.edmiidz.feelfix.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//CIE (think this was used when the backup was a button)

public class DataSyn extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datasyn);
		Button uploadondrive =(Button)findViewById(R.id.button_upload);
		uploadondrive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),UploadonGoogleDrive.class);
				startActivity(in);
				
			}
		});
		Button downloadfromdrive = (Button) findViewById(R.id.button_download);
		downloadfromdrive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						DownloadFromDrive.class);
				startActivity(in);

			}
		});
	}
}
