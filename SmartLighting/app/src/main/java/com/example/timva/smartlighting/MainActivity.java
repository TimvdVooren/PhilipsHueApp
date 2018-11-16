package com.example.timva.smartlighting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VolleyListener{
    private Switch emulatorSwitch;
    private RecyclerView recycler;
    private VolleyConnection connection;
    private RecyclerView lampList;
    private ArrayList<Lamp> lamps;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private LampFragment lampFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = new VolleyConnection(getApplicationContext(), this);
        lamps = new ArrayList<>();

        emulatorSwitch = findViewById(R.id.EmulatorSwitch);
        emulatorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamps.clear();
                mainRecyclerAdapter.notifyDataSetChanged();
                if(emulatorSwitch.isChecked()){
                    connection.establishConnection("http://145.49.12.150:80/api/");
                }
            }
        });

        lampFragment = (LampFragment) getFragmentManager().findFragmentById(R.id.LampFragment);

        lampList = findViewById(R.id.MainList);
        lampList.setHasFixedSize(true);
        lampList.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerAdapter = new MainRecyclerAdapter(this, lamps);
        lampList.setAdapter(mainRecyclerAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    @Override
    public void OnLampAvailable(Lamp lamp) {
        lamps.add(lamp);
        mainRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnLampError(String error) {
        System.out.println(error);
    }
}
