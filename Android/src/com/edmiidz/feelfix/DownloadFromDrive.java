package com.edmiidz.feelfix;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class DownloadFromDrive extends Activity {
	static final int REQUEST_ACCOUNT_PICKER = 1;
	static final int REQUEST_AUTHORIZATION = 2;
	private static Drive service;
	private GoogleAccountCredential credential;
	private ProgressDialog pDialog;
	public static final int progress_bar_type = 0;
	public static final String tableName="feelings_table";
	public String q="title contains 'feelings' and mimeType = 'application/vnd.google-apps.spreadsheet' and trashed = false";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downlods);
		if(!isOnline()){
			Toast.makeText(getApplicationContext(), "There is No Internet Connection", Toast.LENGTH_SHORT).show();
			finish();
		}
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				DownloadFromDrive.this);
		alertDialog.setMessage("Would You Like To Restore Backup?");
		alertDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(DialogInterface dialog, int which) {
						credential = GoogleAccountCredential.usingOAuth2(DownloadFromDrive.this, DriveScopes.DRIVE);
						startActivityForResult(
								credential.newChooseAccountIntent(),
								REQUEST_ACCOUNT_PICKER);
						
					}
				});
		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						finish();
					}
				});
		alertDialog.setCancelable(false);
		alertDialog.show();
		
	}

	public boolean isOnline() {
	    ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) 
	    {
	        return true;
	    }
	    return false;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type: // we set this to 0
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Please wait While Backup is Restoring From Google Drive");
			pDialog.setIndeterminate(false);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(false);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		switch (requestCode) {
		case REQUEST_ACCOUNT_PICKER:
			if (resultCode == RESULT_OK && data != null
					&& data.getExtras() != null) {
				String accountName = data
						.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				if (accountName != null) {
					credential.setSelectedAccountName(accountName);
					service = getDriveService(credential);
					if (service != null) {
					  new DownloadFile().execute(credential.getSelectedAccount());
					}
					
				}
			}
			break;
		case REQUEST_AUTHORIZATION:
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(getApplicationContext(), "Hello",
						Toast.LENGTH_LONG).show();
			} else {
				startActivityForResult(credential.newChooseAccountIntent(),
						REQUEST_ACCOUNT_PICKER);
			}
			break;
		}
	}

	private Drive getDriveService(GoogleAccountCredential credential) {
		return new Drive.Builder(AndroidHttp.newCompatibleTransport(),
				new GsonFactory(), credential).build();
	}

	class DownloadFile extends AsyncTask<Account, String, String> {
		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(progress_bar_type);
		}
		@Override
		protected String doInBackground(Account... params) {
			try {
				
				FileList request = service.files().list().setQ(q).execute();
				if(request.getItems().size()>0)
				{
					File file = request.getItems().get(0);				
					String docKey=file.getId();
					String gDocsDownloadURL="https://docs.google.com/spreadsheet/ccc?key="+docKey+"&output=csv&gid=0";
					System.out.println(gDocsDownloadURL);
					GenericUrl url = new GenericUrl(gDocsDownloadURL);	
				
					System.out.println(url);
					HttpResponse response = service.getRequestFactory().buildGetRequest(url).execute();					
					String contents = new Scanner(response.getContent()).useDelimiter("\\A").next();
					System.out.println(contents);
					java.io.File mydata = new java.io.File(Environment.getExternalStorageDirectory() + "/"+ "feeling.csv");
					java.io.OutputStream out = new FileOutputStream(mydata);
					byte[] buf = contents.getBytes();
					out.write(buf);
					out.close();
					int length = contents.length();
					pDialog.setMax(length);
					String path =Environment.getExternalStorageDirectory() + "/"+ "feeling.csv";
					System.out.println(path);
					RestoreDb(path);
					for(int i=1;i<length;i++)
					{
						pDialog.setProgress(i*10);
					}
					
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
			return null;
		}

		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
			pDialog.setProgress(Integer.parseInt(progress[0]));
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			dismissDialog(progress_bar_type);
			pDialog.setTitle("DataBase Has Been Restore..");
			pDialog.dismiss();
			finish();
		}
		
		protected void RestoreDb(String FilePath)
		{
			try
			{
				DBHelper h = new DBHelper(getApplicationContext());
				SQLiteDatabase db = h.getWritableDatabase();
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(new FileReader(FilePath));
				String line="";
				int count=0;
				System.out.println("I am Here");
				/****************************** Before Insertion Delete All Rows****************************************************/
	            db.execSQL("Delete from "+tableName);
				db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='"+tableName+"'");
				/****************************** Start Insertion ****************************************************/				
				while ((line = br.readLine())!=null) 
				{
				  	if(count>0)
				  	{
				  		ContentValues cv = new ContentValues();
				  		String[]data = line.split(",");
				  		cv.put(DBHelper.Feeling,data[1].trim().toString());
//				  		cv.put(DBHelper.positivefeeling, data[2].trim().toString());
						cv.put(DBHelper.from1to10, data[2].trim().toString());
						cv.put(DBHelper.feelingTime, data[3].trim().toString());
						cv.put(DBHelper.feelingCauseDesc,data[4].trim().toString());
						cv.put(DBHelper.effectedinstinct,data[5].trim().toString());
						cv.put(DBHelper.status,data[6].trim().toString());
						cv.put(DBHelper.involvedpeople,data[7].trim().toString());					
						cv.put(DBHelper.wouldabeenbetter,data[8].trim().toString());
						cv.put(DBHelper.wouldabeenworse,data[9].trim().toString());
						cv.put(DBHelper.created,data[10].trim().toString());
						cv.put(DBHelper.updatedLast,data[11].trim().toString());
						db.insert(tableName,null, cv);
						
				  	}
				  	count++;
				}
				db.close();
				h.close();
				java.io.File f = new java.io.File(FilePath);
				if(f.exists())
				{
					f.delete();
				}
			}catch (Exception e) 
			{
			  	e.printStackTrace();
			}
		}

	}

}
