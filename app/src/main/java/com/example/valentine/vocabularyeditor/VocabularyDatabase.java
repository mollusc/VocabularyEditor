package com.example.valentine.vocabularyeditor;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import java.io.File;
import java.util.Dictionary;

/**
 * Interface with vocabulary's database
 * Created by vofedoseenko on 27.10.2014.
 */
public class VocabularyDatabase {

    private SQLiteDatabase db;

    public VocabularyDatabase(String pathToVocabulary) {
        pathToVocabulary = "/mnt/sdcard/Android/data/com.dropbox.android/files/scratch/LinguaSubtitle/Vocabulary.db";
        File dbfile = new File(pathToVocabulary);
        //db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
        db = SQLiteDatabase.openDatabase(pathToVocabulary, null, 0);
    }

    // Функция по получения 15 кратных слов, в качестве возвращ значения
    // использовать список классов Word как и в LinguaSubtitle
    //public  Get15
    // String selectQuery = "SELECT lastchapter FROM Bookdetails WHERE bookpath=?";
    // Cursor c = db.rawQuery(selectQuery, new String[] { fileName });
    // if (c.moveToFirst()) {
    //    temp_address = c.getString(c.getColumnIndex("lastchapter"));
    //}
    //c.close();

}
