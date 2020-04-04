package com.example.myfeeling;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class MyDownloads {
	public static void retrieveAllFiles(final Drive service,Context ctx) throws IOException {
		List<File> result = new ArrayList<File>();
		Files.List request = service.files().list();
		do {
			try {
				FileList files = request.execute();
				result.addAll(files.getItems());
				 for(File file:files.getItems()){
	                    String fieldId = file.getId();
	                    String title = file.getTitle();
	                    SharedPreferences channel=ctx.getSharedPreferences("com.example.myfeeling", Context.MODE_PRIVATE);
	                    Log.e("MS", "MSV::  Title-->"+title+"  FieldID-->"+fieldId+" DownloadURL-->"+file.getDownloadUrl());
	                    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0 && file.getId().equals(channel.getString("FileID",null).toString()))
	                    {
	                        GenericUrl url = new GenericUrl(file.getDownloadUrl());
	                        HttpResponse resp = service.getRequestFactory().buildGetRequest(url).execute();
	                        InputStream isd = resp.getContent();
	                        java.io.File mydata = new java.io.File(Environment.getExternalStorageDirectory()+"/"+"feeling.db");
	                        java.io.OutputStream out = new FileOutputStream(mydata);
	                        byte[] buf =new byte[2048];
	                        int len;
	                        while((len=isd.read(buf))>0)
	                        out.write(buf,0,len);
	                        out.close();
	                        isd.close();
	                    } else {
	                        Log.e("MS", "MSV:: downloadURL for this file is null");
	                    }
				 }
				request.setPageToken(files.getNextPageToken());
			} catch (IOException e) {
				System.out.println("An error occurred: " + e);
				request.setPageToken(null);
			}
		} while (request.getPageToken() != null
				&& request.getPageToken().length() > 0);
	}
}
