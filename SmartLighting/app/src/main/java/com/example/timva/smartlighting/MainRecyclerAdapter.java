package com.example.timva.smartlighting;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    }

    @Override
    public int getItemCount() {
        return lamps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView onText;

        public ViewHolder(View itemView, final Context c) {
            super(itemView);
            context = c;

            title = itemView.findViewById(R.id.DetailListTitle);
            onText = itemView.findViewById(R.id.DetailedOnText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Lamp lamp = lamps.get(getAdapterPosition());
                    fragmentInteractionListener.onFragmentInteraction(lamp);
                }
            });


        }
    }


}
