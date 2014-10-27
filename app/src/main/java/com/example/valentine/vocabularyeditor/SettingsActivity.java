package com.example.valentine.vocabularyeditor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

import java.net.URISyntaxException;

/**
 * Created by valentine on 25.10.14.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int FILE_SELECT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        sp.registerOnSharedPreferenceChangeListener(this);
        Preference filePicker = findPreference("filePicker");
        filePicker.setSummary(sp.getString("filePicker", getString(R.string.interface_settings_set_new_vocabulary_path)));
        filePicker.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mediaIntent.setType("*/*");
                startActivityForResult(mediaIntent, FILE_SELECT_CODE);
                return true;
            }
        });
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        Preference pref = findPreference(key);
        String prefixStr = sharedPreferences.getString(key, "");
        pref.setSummary(prefixStr);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // Get the path
                    String path = getRealPathFromURI(this, uri);
                    // Write Preferences
                    SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("filePicker", path);
                    editor.commit();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    // http://stackoverflow.com/a/3414749
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}