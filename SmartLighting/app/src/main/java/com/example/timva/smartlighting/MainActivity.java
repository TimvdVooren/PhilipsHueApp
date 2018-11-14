package com.example.timva.smartlighting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Switch emulatorSwitch;
    private RecyclerView recycler;
    private VolleyConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = new VolleyConnection(getApplicationContext());

        emulatorSwitch = findViewById(R.id.EmulatorSwitch);
        emulatorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(emulatorSwitch.isChecked()){
                    connection.establishConnection("http://localhost/api/7a1a5a592120accb29a0c6e394a148a");
                }
            }
        });
        //recycler = findViewById(R.id.LampRecycler);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
