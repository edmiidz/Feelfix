package com.edmiidz.feelfix;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DBHelper extends SQLiteOpenHelper{
	Context context;
	public static String databasename="feelings.db";
	public static String feelings_table="feelings_table";
	public static String Id="Id";
	public static String Feeling="Feeling";
//	public static String positivefeeling="positivefeeling";
	public static String from1to10="from1to10";
	public static String feelingTime="feelingTime";
	public static String feelingCauseDesc="feelingCauseDesc";
//	public static String mainaffectedinstinct="mainaffectedinstinct";
	public static String effectedinstinct="effectedinstinct";
	public static String status="status";
	public static String involvedpeople="involvedpeople";
	public static String wouldabeenbetter="wouldabeenbetter";
	public static String wouldabeenworse="wouldabeenworse";
	public static String created="created";
	public static String updatedLast="updatedLast";
	
	
	
	public DBHelper(Context context) {
		super(context,Environment.getExternalStorageDirectory()+"/"+databasename, null, 1);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query="create table feelings_table(Id integer primary key autoincrement,Feeling text,from1to10 text,feelingTime datetime,feelingCauseDesc text,effectedinstinct text,status text," +
				"involvedpeople text,wouldabeenbetter text,wouldabeenworse text,created datetime,updatedLast datetime)";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
