package com.example.timva.smartlighting;

import android.content.Context;
import android.graphics.Color;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Lamp> lamps;
    private OnFragmentInteractionListener fragmentInteractionListener;

    public MainRecyclerAdapter(Context context, List<Lamp> lamps, OnFragmentInteractionListener fragmentInteractionListener) {
        this.context = context;
        this.lamps = lamps;
        this.fragmentInteractionListener = fragmentInteractionListener;
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

        holder.title.setText("Lamp " + lamp.getId());
        holder.onText.setText("On: " + lamp.isOn());
        if(lamp.isOn())
            holder.lampColor.setVisibility(View.VISIBLE);
        else
            holder.lampColor.setVisibility(View.INVISIBLE);

        float[] lampHSV = new float[3];
        lampHSV[0] = ((float) lamp.getHue()/65535.f) * 360;
        lampHSV[1] = (float) lamp.getSat()/254.f;
        lampHSV[2] = (float) lamp.getBri()/254.f;
        holder.lampColor.setColorFilter(Color.HSVToColor(lampHSV));
    }

    @Override
    public int getItemCount() {
        return lamps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView onText;
        ImageView lampColor;

        public ViewHolder(View itemView, final Context c) {
            super(itemView);
            context = c;

            title = itemView.findViewById(R.id.DetailListTitle);
            onText = itemView.findViewById(R.id.DetailedOnText);
            lampColor = itemView.findViewById(R.id.DetailedListImage);

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
