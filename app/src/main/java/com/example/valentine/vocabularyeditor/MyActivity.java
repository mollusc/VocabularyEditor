package com.example.valentine.vocabularyeditor;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends  ListActivity {

    private VocabularyListAdapter _adapter;
    private VocabularyDatabase _vocabularyDatabase;
    private AsyncListViewLoader _loadingTask;
    private int _offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDatabase();
        //_loadingTask = new AsyncListViewLoader();
        _offset = 0;
        _adapter = new VocabularyListAdapter(this,  _vocabularyDatabase.GetRows(_offset));
        setListAdapter(_adapter);

        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
                boolean loadMore = firstVisible + visibleCount >= totalCount;
                if (loadMore && _loadingTask.getStatus() == AsyncTask.Status.FINISHED) {
                    _loadingTask = new AsyncListViewLoader();
                    _loadingTask.execute();
                }
            }
        });
    }

    private void loadDatabase() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String pathToDatabase = sp.getString("filePicker", "");
        try {
            _vocabularyDatabase = new VocabularyDatabase(pathToDatabase);
        }
        catch (Exception ex) {
            Log.e("ERROR", "ERROR IN CODE: " + ex.toString());
            ex.printStackTrace();
        }
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

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<ItemVocabulary>> {

        @Override
        protected void onPostExecute(List<ItemVocabulary> result) {
            super.onPostExecute(result);
            _adapter.add(result);
            _adapter.notifyDataSetChanged();
        }

        @Override
        protected List<ItemVocabulary> doInBackground(String... params) {
            List<ItemVocabulary> result;
            try {
                _offset++;
                result = _vocabularyDatabase.GetRows(_offset * 15);
                return result;
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return null;
        }
    }
}
