package com.example.valentine.vocabularyeditor;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;


public class MyActivity extends  ListActivity {

    private VocabularyListAdapter _adapter;
    private VocabularyDatabase _vocabularyDatabase;
    private AsyncListViewLoader _loadingTask;
    private int _offset;
    private AsyncUpdateDatabase _asyncUpdateDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadDatabase();
        _loadingTask = new AsyncListViewLoader();
        _asyncUpdateDatabase = new AsyncUpdateDatabase();
        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
                boolean loadMore = firstVisible + visibleCount >= totalCount;
                if (loadMore && (_loadingTask.getStatus() == AsyncTask.Status.FINISHED || _loadingTask.getStatus() == AsyncTask.Status.PENDING)) {
                    _loadingTask = new AsyncListViewLoader();
                    _loadingTask.execute();
                }
            }
        });
    }

    private void loadDatabase() {
        _vocabularyDatabase = null;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String pathToDatabase = sp.getString("filePicker", "");
        try {
            _vocabularyDatabase = new VocabularyDatabase(pathToDatabase);
            _offset = 0;
            _adapter = new VocabularyListAdapter(this,  _vocabularyDatabase.GetRows(_offset));
            setListAdapter(_adapter);
            return;
        }
        catch (Exception ex) {
            Log.e("ERROR", "ERROR IN CODE: " + ex.toString());
            ex.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), 1);
            return true;
        }
        if (id == R.id.action_refresh)
        {
            loadDatabase();
            return true;
        }
        if (id == R.id.action_close){
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(data.getBooleanExtra("update",false))
                loadDatabase();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if(_vocabularyDatabase != null)
            _vocabularyDatabase.close();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);

        ItemVocabulary i = (ItemVocabulary)this.getListAdapter().getItem(pos);
        if (_asyncUpdateDatabase.getStatus() == AsyncTask.Status.FINISHED || _asyncUpdateDatabase.getStatus() == AsyncTask.Status.PENDING) {
            _asyncUpdateDatabase = new AsyncUpdateDatabase();
            _asyncUpdateDatabase.execute(i);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_vocabularyDatabase != null)
            _vocabularyDatabase.close();
    }

    /**
     * Load more words for the ListView
     */
    private class AsyncListViewLoader extends AsyncTask<Void, Void, List<ItemVocabulary>> {
        private ProgressDialog dialog = new ProgressDialog(MyActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Load words");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(List<ItemVocabulary> result) {
            super.onPostExecute(result);
            if(result != null) {
                _adapter.add(result);
                _adapter.notifyDataSetChanged();
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        protected List<ItemVocabulary> doInBackground(Void... params) {
            List<ItemVocabulary> result;
            try {
                if(_vocabularyDatabase != null) {
                    _offset++;
                    result = _vocabularyDatabase.GetRows(_offset * 15);
                    return result;
                }
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Update state of the words in database
     */
    private class AsyncUpdateDatabase extends AsyncTask<ItemVocabulary, Void, Void> {

        @Override
        protected Void doInBackground(ItemVocabulary... itemVocabularies) {
            if(_vocabularyDatabase != null) {
                for (ItemVocabulary i : itemVocabularies) {
                    boolean k = i.known, s = i.study;
                    i.study = !(k && s);
                    i.known = !(k && s) && (k || s);
                    _vocabularyDatabase.SetState(i.stem, i.study, i.known);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            _adapter.notifyDataSetChanged();

        }
    }
}
