package com.example.timva.smartlighting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LampFragment extends Fragment {

    private View lampView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        lampView = inflater.inflate(R.layout.lamp_fragment, container, false);
        return lampView;
    }

    public void setView(Lamp lamp)
    {
        TextView title = lampView.findViewById(R.id.MainLampFragmentTitle);
        title.setText("Lamp " + lamp.getId());
    }
}
