package com.example.timva.smartlighting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Switch emulatorSwitch;
    private RecyclerView recycler;
    private LampFragment lampFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emulatorSwitch = findViewById(R.id.EmulatorSwitch);
        recycler = findViewById(R.id.LampRecycler);
        lampFragment = findViewById(R.id.LampFragment);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }
}
