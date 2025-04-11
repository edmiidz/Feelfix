package com.edmiidz.feelfix;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nedmiidz on 2016/1/5.
 */
public class RowColor extends AppCompatActivity {
    Spinner sp;
    String rowcolor;
    Button save_rowcolor;

    private ArrayList<String> rowColorsList;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rowcolorsettings);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        sp=(Spinner)findViewById(R.id.rowColorListSpinner);
        ArrayAdapter<CharSequence> adapternames = ArrayAdapter.createFromResource(this, R.array.rowcolors,android.R.layout.simple_spinner_item);
        adapternames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapternames);
        if(!preferences.getString("rowcolor","Highlight Weekends").equals(null)){
            int SpinnerPosition = adapternames.getPosition(preferences.getString("rowcolor","Highlight Weekends"));
            sp.setSelection(SpinnerPosition);
            SpinnerPosition = 0;
        }

        rowColorsList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.rowcolors)));

        final SharedPreferences.Editor editor = preferences.edit();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                rowcolor = rowColorsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save_rowcolor = (Button)findViewById(R.id.button_save_rowcolor);
        save_rowcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("rowcolor",rowcolor);
                editor.apply();

                Toast.makeText(getApplicationContext(),"Row Colors have been Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
