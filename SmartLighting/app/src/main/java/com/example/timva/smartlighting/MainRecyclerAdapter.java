package com.example.timva.smartlighting;

import android.content.Context;
import android.graphics.Color;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.timva.smartlighting.Database.DatabaseHandler;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Locale;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Lamp> lamps;
    private OnFragmentInteractionListener fragmentInteractionListener;
    private TreeMap<String, Lamp> favColorLamps;
    private DatabaseHandler database;

    public MainRecyclerAdapter(Context context, List<Lamp> lamps, OnFragmentInteractionListener fragmentInteractionListener, DatabaseHandler database) {
        this.context = context;
        this.lamps = lamps;
        this.fragmentInteractionListener = fragmentInteractionListener;
        this.favColorLamps = new TreeMap<>();
        this.database = database;
    }

    @NonNull
    @Override
    public MainRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_list,parent,false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.ViewHolder holder, int position) {
        Lamp lamp = lamps.get(position);

        Lamp databaseLamp = database.getLamp(lamp.getId());
        if (databaseLamp != null)
            setFavColorLamp(databaseLamp);

        holder.title.setText("Lamp " + lamp.getId());
        if(lamp.isOn())
        {
            if(Locale.getDefault().getLanguage() == "nl")
            {
                holder.onText.setText("Aan");
            }
            else
            {
                holder.onText.setText("On");
            }
        }
        else {
            if(Locale.getDefault().getLanguage() == "nl")
            {
                holder.onText.setText("Uit");
            }
            else
            {
                holder.onText.setText("Off");
            }
        }
        if(lamp.isOn())
            holder.lampColor.setVisibility(View.VISIBLE);
        else
            holder.lampColor.setVisibility(View.INVISIBLE);

        float[] lampHSV = new float[3];
        lampHSV[0] = ((float) lamp.getHue()/65535.f) * 360;
        lampHSV[1] = (float) lamp.getSat()/254.f;
        lampHSV[2] = (float) lamp.getBri()/254.f;
        holder.lampColor.setColorFilter(Color.HSVToColor(lampHSV));

        Lamp favColorLamp = this.favColorLamps.get(Integer.toString(lamp.getId()));
        if(favColorLamp != null){
            float[] favLampHSV = new float[3];
            favLampHSV[0] = ((float) favColorLamp.getHue()/65535.f) * 360;
            favLampHSV[1] = (float) favColorLamp.getSat()/254.f;
            favLampHSV[2] = (float) favColorLamp.getBri()/254.f;
            holder.favColorView.setColorFilter(Color.HSVToColor(favLampHSV));
        }
    }

    @Override
    public int getItemCount() {
        return lamps.size();
    }

    public void setFavColorLamp(Lamp lamp){
        Lamp favColorLamp = new Lamp(lamp.getId(), lamp.isOn(), lamp.getBri(), lamp.getHue(), lamp.getSat());
        this.favColorLamps.put(Integer.toString(favColorLamp.getId()), favColorLamp);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView onText;
        ImageView lampColor;
        ImageView favColorView;

        public ViewHolder(View itemView, final Context c) {
            super(itemView);
            context = c;

            title = itemView.findViewById(R.id.DetailListTitle);
            onText = itemView.findViewById(R.id.DetailedOnText);
            lampColor = itemView.findViewById(R.id.DetailedListImage);
            favColorView = itemView.findViewById(R.id.FavImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getAdapterPosition() >= 0) {
                        Lamp lamp = lamps.get(getAdapterPosition());
                        fragmentInteractionListener.onFragmentInteraction(lamp);
                    }
                }
            });
        }
    }


}
