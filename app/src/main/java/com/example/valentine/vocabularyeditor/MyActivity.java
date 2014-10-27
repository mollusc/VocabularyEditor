package com.example.valentine.vocabularyeditor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //getSharedPreferences("settings", MODE_PRIVATE);
        String pathToDatabase = sp.getString("filePicker", "");
        try {
            VocabularyDatabase vd = new VocabularyDatabase(pathToDatabase);
        }
        catch (Exception ex) {
            Log.e("ERROR", "ERROR IN CODE: " + ex.toString());
            ex.printStackTrace();
        }
    }
}
