package com.edmiidz.feelfix;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

import com.edmiidz.feelfix.R;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

/*import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;*/

//import com.google.api.client.json.jackson.JacksonFactory;
//import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;

public class UploadonGoogleDrive extends Activity {
	static final int REQUEST_ACCOUNT_PICKER = 1;
	static final int REQUEST_AUTHORIZATION = 2;
	static final int CAPTURE_IMAGE = 3;
	
	private static Drive service;
	private GoogleAccountCredential credential;
	private ProgressDialog pDialog;
	public static final int progress_bar_type = 0;
	private String fileID="";
	//private static final String API_KEY = "AIzaSyB1_oj8OzHCv3T7It-PTxAziVUXVyzZ3AQ";
	//AIzaSyA_fFoVh5qSSxNf03vEbJGfND_QKWmA0rQ;
	/*private String q="title contains 'spotcheck-skj43gsj' and mimeType = 'application/vnd.google-apps.spreadsheet' and trashed = false";*/
	public String q="title='spotcheck-skj43gsj' and mimeType = 'application/vnd.google-apps.spreadsheet' and trashed = false";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloads);
		if (!isOnline()) {
			Toast.makeText(
				getApplicationContext(), "There is No Internet Connection",
				Toast.LENGTH_SHORT).show();
			finish();
		}
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		// credential = GoogleAccountCredential.usingAudience(this,"server:client_id:1096788177139.apps.googleusercontent.com");
		credential = GoogleAccountCredential.usingOAuth2(
			this, DriveScopes.DRIVE);
		System.out.println("SCOPE : " + credential.getScope());
                    startActivityForResult(
                            credential.newChooseAccountIntent(),
                            REQUEST_ACCOUNT_PICKER);
		
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(
			final int requestCode, final int resultCode, final Intent data) {
		System.out.println(requestCode + "	**** 	" + resultCode);
		switch (requestCode) {
		case REQUEST_ACCOUNT_PICKER:
			if (resultCode == RESULT_OK && data != null
					&& data.getExtras() != null) {
				String accountName = data
					.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				System.out.println("Account Name : " + accountName);
				if (accountName != null) {
					System.out.println("Account Name1 : " + accountName);
					
					try {
			            // Trying to get a token right away to see if we are authorized
			            GoogleAuthUtil.getToken(UploadonGoogleDrive.this,
			            	accountName, "oauth2:" + DriveScopes.DRIVE_FILE,
			                null);//, mAuthority, mSyncBundle);
			            } 
			        catch (Exception e) {
			            Log.e("uploadonGoogleDrive", "error can't get token GREG GREG GREG", e);
			        }

					credential.setSelectedAccountName(accountName);
					service = getDriveService(credential);
					// service.
					DBHelper h = new DBHelper(this);
					SQLiteDatabase db = h.getWritableDatabase();
					MakeCsvData(db);
					db.close();
					h.close();
					new UploadFile().execute("");
				}
			}
			break;
		case REQUEST_AUTHORIZATION:
			if (resultCode == Activity.RESULT_OK) {
				new UploadFile().execute("");
			} else {
				startActivityForResult(
					credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
			}
			break;
		case CAPTURE_IMAGE:
			if (resultCode == Activity.RESULT_OK) {
				new UploadFile().execute("");
			}
		}
	}

	private Drive getDriveService(GoogleAccountCredential credential) {
		return new Drive.Builder(
			AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
			.build();
	}

	public void showToast(final String toast) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(
					getApplicationContext(), toast, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type: // we set this to 0
			pDialog = new ProgressDialog(this);
			pDialog
				.setMessage("Please wait While Data is Uploading on Google Drive");
			pDialog.setIndeterminate(false);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(false);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}
	
	/*public static Drive getDriveService() throws GeneralSecurityException,
		    IOException, URISyntaxException {
				System.out.println("REST**********************");
		  HttpTransport httpTransport = new NetHttpTransport();
		  JsonFactory jsonFactory = new JacksonFactory();
		  AppIdentityCredential credential =
		      new AppIdentityCredential.Builder(DriveScopes.DRIVE).build();
		  GoogleClientRequestInitializer keyInitializer =
		      new CommonGoogleClientRequestInitializer(API_KEY);
		  Drive service = new Drive.Builder(httpTransport, jsonFactory, null)
		      .setHttpRequestInitializer(credential)
		      .setGoogleClientRequestInitializer(keyInitializer)
		      .build();
		  return service;
		}*/
	
	private void MakeCsvData(SQLiteDatabase db) {
		java.io.File dataFile, df1;
		try {
			dataFile = new java.io.File(
				Environment.getExternalStorageDirectory() + "/yourfile.xls");
			dataFile.createNewFile();

			df1 = new java.io.File(Environment.getExternalStorageDirectory()
					+ "/spotcheck-skj43gsj.csv");
			df1.createNewFile();

			CSVWriter writerx = new CSVWriter(new FileWriter(df1), ',');
			// feed in your array (or convert your data to an array)
			String[] entries = "Id	Feeling	from1to10	feelingTime	feelingCauseDesc	mainaffectedinstinct	status	involvedpeople	wouldabeenbetter	wouldabeenworse	created\tupdatedLast\tlatitude\tlongitude"
				.split("	");
			writerx.writeNext(entries);
			
			/* String[] values = {"1","first","second","Third one quoted with a, comma","fourth \"double quotes\"\n line break"};
	        writerx.writeNext(values);
	        values = new String[]{"2","erst","zweite","Dritte,mit Komma","viertl"};
	        writerx.writeNext(values);
	        values = new String[]{"3","primero","segundo","tercero","cuarto,con la coma"};
	        writerx.writeNext(values);
	        */

			FileOutputStream fOut = new FileOutputStream(dataFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			// myOutWriter.append("Id,Feeling,from1to10,feelingTime,feelingCauseDesc,mainaffectedinstinct,status,involvedpeople,wouldabeenbetter,wouldabeenworse,created,updatedLast");
			myOutWriter
                    .append("Id	Feeling	from1to10	feelingTime	feelingCauseDesc	mainaffectedinstinct	status	involvedpeople	wouldabeenbetter	wouldabeenworse	created updatedLast latitude longitude");
			myOutWriter.append("\n");
			Cursor c = db
				.rawQuery(
					"SELECT Id,Feeling,from1to10,feelingTime,feelingCauseDesc,mainaffectedinstinct,status,involvedpeople,wouldabeenbetter,wouldabeenworse,created,updatedLast,latitude,longitude FROM feelings_table",
					null);
			if (c != null) {
				if (c.moveToFirst()) {
					do {
						String Id = c.getString(c.getColumnIndex("Id"));
						String Feeling = c.getString(c
							.getColumnIndex("Feeling"));

						// Feeling = Feeling.replace(",", "@");

						// String positivefeeling =
						// c.getString(c.getColumnIndex("positivefeeling"));
						String from1to10 = c.getString(c
							.getColumnIndex("from1to10"));
						String feelingTime = c.getString(c
							.getColumnIndex("feelingTime"));
						String feelingCauseDesc = c.getString(c
							.getColumnIndex("feelingCauseDesc"));
						String effectedinstinct = c.getString(c
							.getColumnIndex("mainaffectedinstinct"));
						String status = c.getString(c.getColumnIndex("status"));
						String involvedpeople = c.getString(c
							.getColumnIndex("involvedpeople"));
						String wouldabeenbetter = c.getString(c
							.getColumnIndex("wouldabeenbetter"));
						String wouldabeenworse = c.getString(c
							.getColumnIndex("wouldabeenworse"));
						String created = c.getString(c
							.getColumnIndex("created"));
						String updatedLast = c.getString(c
							.getColumnIndex("updatedLast"));
                        double latitude = c.getDouble(c.getColumnIndex("latitude"));
                        double longitude = c.getDouble(c.getColumnIndex("longitude"));


						// myOutWriter.append(Id + "," + Feeling + "," +
						// from1to10 + ","+ feelingTime + "," +
						// feelingCauseDesc+ ","+ effectedinstinct + "," +
						// status + ","+ involvedpeople + "," +
						// wouldabeenbetter+ "," + wouldabeenworse + "," +
						// created+ "," + updatedLast);
						myOutWriter.append(Id + "\t" + Feeling + "\t"
								+ from1to10 + "\t" + feelingTime + "\t"
								+ feelingCauseDesc + "\t" + effectedinstinct
								+ "\t" + status + "\t" + involvedpeople + "\t"
								+ wouldabeenbetter + "\t" + wouldabeenworse
								+ "\t" + created + "\t" + updatedLast + "\t" + latitude + "\t" + longitude);
						// myOutWriter.append(Id + "\",\"" + Feeling + "\",\"" +
						// from1to10 + "\",\""+ feelingTime + "\",\"" +
						// feelingCauseDesc+ "\",\""+ mainaffectedinstinct +
						// "\",\"" + status + "\",\""+ involvedpeople + "\",\""
						// + wouldabeenbetter+ "\",\"" + wouldabeenworse +
						// "\",\"" + created+ "\",\"" + updatedLast);
						myOutWriter.append("\n");
						String xxx = Id + "\t" + Feeling + "\t" + from1to10
								+ "\t" + feelingTime + "\t" + feelingCauseDesc
								+ "\t" + effectedinstinct + "\t" + status
								+ "\t" + involvedpeople + "\t"
								+ wouldabeenbetter + "\t" + wouldabeenworse
								+ "\t" + created + "\t" + updatedLast + "\t" + latitude + "\t" + longitude;
						String ent[] = xxx.split("\t");
						writerx.writeNext(ent);
					}
					while (c.moveToNext());
				}
				c.close();
				myOutWriter.close();
				writerx.close();
				fOut.close();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	
	class UploadFile extends AsyncTask<String, String, String> {
		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(progress_bar_type);
		}

		@Override
		protected String doInBackground(String... f_url) {
			com.google.api.services.drive.model.File file = null;
			try {

				java.io.File fileContent = new java.io.File(
					Environment.getExternalStorageDirectory()
							+ "/spotcheck-skj43gsj.csv");
				FileContent mediaContent = new FileContent(
					"text/csv", fileContent);
				File body = new File();
				System.out.println("*****TEST******");
				// System.out.println(fileContent.getName().substring(0,fileContent.getName().lastIndexOf(".")));
				body.setTitle(fileContent.getName().substring(
					0, fileContent.getName().lastIndexOf(".")));
				body.setMimeType("text/csv");
				System.out.println("*****TESTz******");

				FileList request = service.files().list().setQ(q).execute();
				//FileList request = service.files().list().execute();
				System.out.println("*****TESTz1******");
				if (request.getItems().size() > 0) {
					System.out.println("Test2 ------>");
					file = request.getItems().get(0);
					file.setMimeType("text/csv");

					File updatedFile = service
						.files().update(file.getId(), file, mediaContent)
						.setConvert(true).execute();
					file = updatedFile;
					System.out.println("Test22 ------>");

				} else {
					System.out.println("Test1 ------>");
					Drive.Files.Insert insert = service
						.files().insert(body, mediaContent).setConvert(true);
					System.out.println("Test11 ------>");
					file = insert.execute();
					System.out.println("Test111 ------>");

				}
				if (file != null) {
					System.out.println("Test3 ------>");
					SharedPreferences.Editor edit = getApplicationContext()
						.getSharedPreferences(
							"com.edmiidz.feelfix", MODE_PRIVATE).edit();
					edit.putString("FileID", file.getId());
					edit.commit();
					fileID = file.getId();
					System.out.println("Test33 ------>" + file.getId());
				}
				long count = fileContent.length();
				pDialog.setMax((int) count);
				if (fileContent.exists()) {
					fileContent.delete();
				}
				for (int z = 1; z < count; z++) {
					pDialog.setProgress(z * 10);
				}

			} catch (UserRecoverableAuthIOException e) {
				System.out.println("UserRecoverable : " + e.getMessage());
				startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
			} catch (IOException e) {
				System.out.println("UserRecoverable1 : " + e.getMessage());
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
			if (fileID.toString().length() > 1) {
				dismissDialog(progress_bar_type);
				finish();
			}

		}

	}
}