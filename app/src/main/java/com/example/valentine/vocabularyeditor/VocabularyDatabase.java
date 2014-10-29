package com.example.valentine.vocabularyeditor;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Interface with vocabulary's database
 * Created by vofedoseenko on 27.10.2014.
 */
public class VocabularyDatabase {

    private SQLiteDatabase db;
    private final int _limit = 15;

    public VocabularyDatabase(String pathToVocabulary) {
        pathToVocabulary = "/mnt/sdcard/Android/data/com.dropbox.android/files/scratch/LinguaSubtitle/Vocabulary.db";
        db = SQLiteDatabase.openDatabase(pathToVocabulary, null, 0);
    }

    public ArrayList<ItemVocabulary> GetRows(int offset){
        ArrayList<ItemVocabulary> words = new ArrayList<ItemVocabulary>();
        String query = "SELECT * FROM Stems WHERE Known=0 ORDER BY Meeting DESC LIMIT ? OFFSET ?";
        Cursor c = db.rawQuery(query, new String[] {Integer.toString(_limit), Integer.toString(offset)});
        while (c.moveToNext()) {
            String stem = c.getString(c.getColumnIndex("Stem"));
            String word = c.getString(c.getColumnIndex("Word"));
            String translate = c.getString(c.getColumnIndex("Translate"));
            boolean known = c.getString(c.getColumnIndex("Known")).equals("1");
            boolean study = c.getString(c.getColumnIndex("Study")).equals("1");
            int meeting = Integer.parseInt(c.getString(c.getColumnIndex("Meeting")));
            words.add(new ItemVocabulary(stem, word, translate, known, meeting, study));
        }
        c.close();
        return words;
    }

    public void SetState(String stem, boolean study, boolean known){
        ContentValues cv = new ContentValues();
        cv.put("Study", study?"1":"0");
        cv.put("Known", known?"1":"0");
        db.update("Stems",cv, "Stem='" + stem + "'", null);
    }

    public void close(){
       db.close();
    }
}
