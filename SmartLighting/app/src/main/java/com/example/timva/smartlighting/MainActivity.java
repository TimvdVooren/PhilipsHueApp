package com.example.timva.smartlighting;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import com.example.timva.smartlighting.Database.DatabaseHandler;

import java.util.*;
import java.net.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VolleyListener, OnFragmentInteractionListener{
    private Switch emulatorSwitch;
    private VolleyConnection connection;
    private RecyclerView lampList;
    private ArrayList<Lamp> lamps;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private LampFragment lampFragment;
    private MasterFragment masterFragment;
    private DatabaseHandler database;
    private Button setFav;
    private Button saveFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        connection = VolleyConnection.getInstance(getApplicationContext(), this);
        lamps = new ArrayList<>();

        saveFav = findViewById(R.id.MainSaveFav);
        setFav = findViewById(R.id.MainSetFav);
        saveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lampFragment.getLamp() != null) {
                    database.addLamp(lampFragment.getLamp());
                    mainRecyclerAdapter.setFavColorLamp(lampFragment.getLamp());
                    mainRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

        setFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lampFragment.getLamp() != null) {
                    Lamp lamp = database.getLamp(lampFragment.getLamp().getId());
                    if(lamp != null) {
                        lampFragment.setLampView(lamp);
                    }
                }
            }
        });

        emulatorSwitch = findViewById(R.id.EmulatorSwitch);
        emulatorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamps.clear();
                connection.setApiCode(null);
                mainRecyclerAdapter.notifyDataSetChanged();
                if(emulatorSwitch.isChecked()) {
                    connection.establishEmulatorConnection("http://145.49.58.161:80/api/");
                    //connection.establishEmulatorConnection("http://145.49.12.150:80/api/");
                }
                else
                    connection.establishConnection();
            }
        });
        if(Locale.getDefault().getLanguage() == "nl")
        {
            emulatorSwitch.setText("Gebruik lamp emulator ");
            saveFav.setText("Opslaan");
        }
        else
        {
            emulatorSwitch.setText("Use lamp emulator ");
            saveFav.setText("Save");
        }
        lampFragment = (LampFragment) getFragmentManager().findFragmentById(R.id.LampFragment);
        lampFragment.setConnection(connection);
        masterFragment = (MasterFragment) getFragmentManager().findFragmentById(R.id.MainMasterFragment);


        lampList = findViewById(R.id.MainList);
        lampList.setHasFixedSize(true);
        lampList.setLayoutManager(new LinearLayoutManager(this));
        database = new DatabaseHandler(this);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, lamps, this, database);
        lampList.setAdapter(mainRecyclerAdapter);

        connection.establishConnection();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void OnLampAvailable(Lamp lamp) {
        Log.i("Available", "Available");

        boolean alreadyPresent = false;
        for(int i = 0; i < lamps.size(); i++) {
            if (lamps.get(i).getId() == lamp.getId()) {
                lamps.set(i, lamp);
                alreadyPresent = true;
            }
        }
        if(!alreadyPresent)
            lamps.add(lamp);
        mainRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnLampError(String error) {
        System.out.println(error);
    }

    @Override
    public void onFragmentInteraction(Lamp lamp) {
        lampFragment.setLampView(lamp);
    }
}
