package com.example.timva.smartlighting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements VolleyListener{
    private Switch emulatorSwitch;
    private RecyclerView recycler;
    private VolleyConnection connection;
    private ListView lampList;
    private ArrayList<Lamp> lamps;
    private ArrayAdapter<Lamp> arrayAdapter;

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
//                if(emulatorSwitch.isChecked()){
//                    connection.establishConnection("http://localhost:80/api/153c1ccc98939a3873887cf718cf9f1");
//                }
            }
        });
        //recycler = findViewById(R.id.LampRecycler);

        lampList = findViewById(R.id.MainList);
        arrayAdapter = new CustomAdapter(getApplicationContext(), lamps);
        lampList.setAdapter(arrayAdapter);

        connection.getLamps("http://192.168.2.12/api/153c1ccc98939a3873887cf718cf9f1");
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void OnLampAvailable(Lamp lamp) {
        lamps.add(lamp);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnLampError(String error) {
        System.out.println(error);
    }
}
