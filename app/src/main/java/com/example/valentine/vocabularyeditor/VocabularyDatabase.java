package com.example.valentine.vocabularyeditor;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import java.io.File;

/**
 * Interface with vocabulary's database
 * Created by vofedoseenko on 27.10.2014.
 */
public class VocabularyDatabase {

    private SQLiteDatabase db;

    public VocabularyDatabase(String pathToVocabulary) {
        pathToVocabulary = "/mnt/sdcard/Android/data/com.dropbox.android/files/scratch/LinguaSubtitle/Vocabulary.db";
        File dbfile = new File(pathToVocabulary);
        db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
       // db = SQLiteDatabase.openDatabase(pathToVocabulary, null, SQLiteDatabase.OPEN_READWRITE);
        int i = 4+6;
    }
}
