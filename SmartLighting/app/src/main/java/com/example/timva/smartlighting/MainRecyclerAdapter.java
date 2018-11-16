package com.example.timva.smartlighting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Lamp> lamps;

    public MainRecyclerAdapter(Context context, List<Lamp> lamps) {
        this.context = context;
        this.lamps = lamps;
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
        float[] lampHSV = new float[3];
        lampHSV[0] = ((float) lamp.getHue()/65535.f) * 360;
        lampHSV[1] = (float) lamp.getSat()/254.f;
        lampHSV[2] = (float) lamp.getBri()/254.f;
        holder.lampColor.setBackgroundColor(Color.HSVToColor(lampHSV));
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
        }
    }
}
