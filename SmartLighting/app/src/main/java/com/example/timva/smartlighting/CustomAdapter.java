package com.example.timva.smartlighting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Lamp> {

    public CustomAdapter(Context context, ArrayList<Lamp> lamps)
    {
        super(context,0,lamps);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Furby ophalen
        Lamp lamp = getItem(position);

        // View aanmaken of herbruiken
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.detailed_list,
                    parent,
                    false
            );
        }

        TextView id = (TextView) convertView.findViewById(R.id.DetailedListText);

        id.setText(lamp.getId() + "on: " + lamp.isOn());

        return convertView;
    }
}
