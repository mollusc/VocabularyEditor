package com.example.valentine.vocabularyeditor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vofedoseenko on 28.10.2014.
 */
public class VocabularyListAdapter extends ArrayAdapter<ItemVocabulary> {
    private List<ItemVocabulary> _itemList;
    private Context _context;

    public VocabularyListAdapter(Context ctx, List<ItemVocabulary> items){
        super(ctx, R.layout.rowlayout, items);
        _itemList = items;
        _context = ctx;
    }

    public int getCount() {
        if (_itemList != null)
            return _itemList.size();
        return 0;
    }

    public ItemVocabulary getItem(int position) {
        if (_itemList != null)
            return _itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (_itemList != null)
            return _itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemVocabulary i = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rowlayout, parent, false);
        }
        TextView wordView = (TextView) convertView.findViewById(R.id.WordView);
        TextView translateView = (TextView) convertView.findViewById(R.id.TranslateView);
        wordView.setText(i.word);
        translateView.setText(i.translate);

        wordView.setTextColor(Color.BLACK);
        translateView.setTextColor(Color.BLACK);
        convertView.setBackgroundColor(Color.WHITE);

        if(i.study){
            wordView.setTextColor(_context.getResources().getColor(R.color.text_study_word));
            translateView.setTextColor(_context.getResources().getColor(R.color.text_study_word));
        }

        if(i.known){
            convertView.setBackgroundColor(_context.getResources().getColor(R.color.background_known_word));
        }

        return convertView;
    }
    public void add(List<ItemVocabulary> itemList) {
        _itemList.addAll(itemList);
    }
}
