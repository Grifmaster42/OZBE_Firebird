package com.bdn.ozbe;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpinnerAdapter extends BaseAdapter {
    private final int[] img_res;
    private final LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext, int[] img_res) {
        this.img_res = img_res;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return img_res.length;
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
        view = inflter.inflate(R.layout.spinner_item, null);

        CircleImageView image = view.findViewById(R.id.profile_pic_n);
        image.setImageResource(img_res[i]);
        return view;
    }
}