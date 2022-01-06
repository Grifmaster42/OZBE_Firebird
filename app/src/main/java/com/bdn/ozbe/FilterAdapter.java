package com.bdn.ozbe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class FilterAdapter extends BaseAdapter {
    private final String[] filter_array;
    private final LayoutInflater inflter;

    public FilterAdapter(Context applicationContext, String[] filter_array) {
        this.filter_array = filter_array;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return filter_array.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.filter_item, null);

        TextView text1 = view.findViewById(R.id.text1);
        text1.setText(filter_array[i]);
        return view;
    }
}