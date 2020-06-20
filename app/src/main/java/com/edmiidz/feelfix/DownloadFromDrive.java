package com.edmiidz.feelfix;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.Manifest;
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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class DownloadFromDrive extends AppCompatActivity {
	static final int REQUEST_ACCOUNT_PICKER = 1;
	static final int REQUEST_AUTHORIZATION = 2;
	static final int REQUEST_ACCOUNTS = 1002;
	private static Drive service;
	private GoogleAccountCredential credential;
	private ProgressDialog pDialog;
	public static final int progress_bar_type = 0;
	public static final String tableName = "feelings_table";
	private String fileID="";
    private boolean mergeDownload = true;
	Calendar c = Calendar.getInstance();
//    System.out.println("Current time => "+c.getTime());
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	String formattedDate = df.format(c.getTime());

	public String backupQ="title='spotcheck-backup' and mimeType = 'application/vnd.google-apps.spreadsheet' and trashed = false";

    public String q="title='spotcheck-skj43gsj' and mimeType = 'application/vnd.google-apps.spreadsheet' and trashed = false";
	private ProgressDialog progressDialog;
	private AlertDialog.Builder alertDialog;

	//@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloads);
		if (!isOnline()) {
			Toast.makeText(
				getApplicationContext(), "There is No Internet Connection",
				Toast.LENGTH_SHORT).show();
			finish();
		} else {
						Toast.makeText(
				getApplicationContext(), "Internet Connection is good.",
				Toast.LENGTH_SHORT).show();

		}

		StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
		StrictMode.setThreadPolicy(policy1);
		alertDialog = new AlertDialog.Builder(
			DownloadFromDrive.this);
		alertDialog.setMessage("Do you want to overwrite your local data or merge with it?");
		alertDialog.setPositiveButton(
			"Merge", new DialogInterface.OnClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					credential = GoogleAccountCredential.usingOAuth2(
						DownloadFromDrive.this, DriveScopes.DRIVE);
					startActivityForResult(
						credential.newChooseAccountIntent(),
						REQUEST_ACCOUNT_PICKER);
                    mergeDownload = true;

				}
			});
		alertDialog.setNeutralButton(
			"Overwrite", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
                    credential = GoogleAccountCredential.usingOAuth2(
                            DownloadFromDrive.this, DriveScopes.DRIVE);
                    startActivityForResult(
                            credential.newChooseAccountIntent(),
                            REQUEST_ACCOUNT_PICKER);
                    mergeDownload = false;

//					dialog.cancel();
//					finish();
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

		if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.GET_ACCOUNTS)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(DownloadFromDrive.this,
					new String[]{Manifest.permission.GET_ACCOUNTS}, REQUEST_ACCOUNTS);

		} else {
			alertDialog.setCancelable(true);
			alertDialog.show();
		}

	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == REQUEST_ACCOUNTS) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				alertDialog.setCancelable(true);
				alertDialog.show();
			} else {
				Toast.makeText(getApplicationContext(), "You have to grant permission to restore from google drive", Toast.LENGTH_LONG).show();
				finish();
			}

		}
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
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type: // we set this to 0
			pDialog = new ProgressDialog(this);
			pDialog
				.setMessage("Please wait While Backup is Restoring From Google Drive");
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
	protected void onActivityResult(
			final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
							new DownloadFile().execute(credential
									.getSelectedAccount());
						}

					}
				}
				break;
			case REQUEST_AUTHORIZATION:
				if (resultCode == Activity.RESULT_OK) {
					Toast.makeText(
							getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
				} else {
					startActivityForResult(
							credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
				}
				break;
		}
	}

	private Drive getDriveService(GoogleAccountCredential credential) {
		return new Drive.Builder(
			AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
			.build();
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

				DBHelper h = new DBHelper(getApplicationContext());
				SQLiteDatabase db = h.getWritableDatabase();

				if (request.getItems().size() > 0) {
					File file = request.getItems().get(0);
					String docKey = file.getId();
					System.out.println("docKey = " + docKey);

                    String gDocsDownloadURL = file.getExportLinks().get("text/csv");

					//System.out.println(gDocsDownloadURL);
					GenericUrl url = new GenericUrl(gDocsDownloadURL);

					System.out.println(url);
					HttpResponse response = service
						.getRequestFactory().buildGetRequest(url).execute();

					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader( new InputStreamReader( response.getContent() ) );
					String line = "";

					int count = 0;


                    if(mergeDownload==false) {
                        /******************************* Before Insertion Delete All Rows ****************************************************/
                        db.execSQL("Delete from " + tableName);
                        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name='"
                                + tableName + "'");
                        /****************************** Start Insertion ****************************************************/
                    }
					while ((line = br.readLine()) != null) {
						if (count > 0) {
							System.out.println(line.toString());
							ContentValues cv = new ContentValues();

							String[] data = line.split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?");

							cv.put(DBHelper.Feeling, data[1].trim().toString());

							cv.put(DBHelper.from1to10, data[2].trim().toString());
							cv.put(DBHelper.feelingTime, iSO801ifydate(data[3].trim().toString()));
							cv.put(DBHelper.feelingCauseDesc, data[4].trim().toString());
							cv.put(DBHelper.mainaffectedinstinct, data[5].trim().toString());
							cv.put(DBHelper.status, data[6].trim().toString());
							cv.put(DBHelper.involvedpeople, data[7].trim().toString());
							cv.put(DBHelper.wouldabeenbetter, data[8].trim().toString());
							cv.put(DBHelper.wouldabeenworse, data[9].trim().toString());
							cv.put(DBHelper.created, iSO801ifydate(data[10].trim().toString()));
                            cv.put(DBHelper.updatedLast, iSO801ifydate(data[11].trim().toString()));
                            cv.put(DBHelper.latitude, Double.parseDouble(data[12]));
                            cv.put(DBHelper.longitude, Double.parseDouble(data[13]));

                            if(mergeDownload) {
                                db.delete(tableName, "created='" + iSO801ifydate(data[10].trim().toString()) + "'", null);
                            }
							db.insert(tableName, null, cv);

						}
						count++;
					}
					db.close();

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


		public String iSO801ifydate(String mydate){

			SimpleDateFormat fmt = new SimpleDateFormat("M/d/yyyy H:mm:ss");
            Date d;
            try {
                d = fmt.parse(mydate);
            } catch (java.text.ParseException e) {

                e.printStackTrace();
                return null;
            }
            SimpleDateFormat prnt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String newDate=prnt.format(d);


			return newDate;
		}

		/**
		 * Updating progress bar
		 * */
		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
			pDialog.setProgress(Integer.parseInt(progress[0]));
		}

		protected void checkDateFormat(String... mydate){

			Log.d("SilentModeApp", "mydate:");

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			dismissDialog(progress_bar_type);
			pDialog.setTitle("Database Has Been Restore..");
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), "Backup Restored...", Toast.LENGTH_LONG).show();
			finish();
		}


	}


    private void MakeCsvData(SQLiteDatabase db) {
        java.io.File dataFile, df1;
        try {
            dataFile = new java.io.File(
                    Environment.getExternalStorageDirectory() + "/yourfile.xls");
            dataFile.createNewFile();

            df1 = new java.io.File(Environment.getExternalStorageDirectory()
                    + "/spotcheck-n87iaugjjhj.csv");
            df1.createNewFile();

            CSVWriter writerx = new CSVWriter(new FileWriter(df1), ',');
            // feed in your array (or convert your data to an array)
            String[] entries = "Id	Feeling	from1to10	feelingTime	feelingCauseDesc	mainaffectedinstinct	status	involvedpeople	wouldabeenbetter	wouldabeenworse	created\tupdatedLast\tlatitude\tlongitude"
                    .split("	");
            writerx.writeNext(entries);


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

                        myOutWriter.append(Id + "\t" + Feeling + "\t"
                                + from1to10 + "\t" + feelingTime + "\t"
                                + feelingCauseDesc + "\t" + effectedinstinct
                                + "\t" + status + "\t" + involvedpeople + "\t"
                                + wouldabeenbetter + "\t" + wouldabeenworse
                                + "\t" + created + "\t" + updatedLast + "\t" + latitude + "\t" + longitude);

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


    class BackupFile extends AsyncTask<String, String, String> {
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
                                + "/spotcheck-backup.csv");
                FileContent mediaContent = new FileContent(
                        "text/csv", fileContent);
                File body = new File();
                System.out.println("*****TEST******");
                // System.out.println(fileContent.getName().substring(0,fileContent.getName().lastIndexOf(".")));
                body.setTitle(fileContent.getName().substring(
                        0, fileContent.getName().lastIndexOf(".")));
                body.setMimeType("text/csv");
                System.out.println("*****TESTr******");

                FileList request = service.files().list().setQ(backupQ).execute();
                //FileList request = service.files().list().execute();
                System.out.println("*****TESTr1******");
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
