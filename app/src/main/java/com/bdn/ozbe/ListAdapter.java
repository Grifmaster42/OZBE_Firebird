package com.bdn.ozbe;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<User> {

    //Test
    public ListAdapter(Context context, ArrayList<User> userArrayList){

        super(context,R.layout.list_item,userArrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView userName = convertView.findViewById(R.id.raumID);
        TextView lastMsg = convertView.findViewById(R.id.AnzPlatze);
        TextView tische = convertView.findViewById(R.id.AnzTische);
        TextView ausstattung = convertView.findViewById(R.id.textView9);
        TextView mangel = convertView.findViewById(R.id.ListeMängel);
        //TextView time = convertView.findViewById(R.id.msgtime);

        imageView.setImageResource(user.getImageId());
        userName.setText(user.raumID);
        lastMsg.setText(user.stuhle);
        tische.setText(user.tische);
        ausstattung.setText(user.austattung);
        mangel.setText(user.mangel);
        //time.setText(user.lastMsgTime);


        return convertView;
    }
}