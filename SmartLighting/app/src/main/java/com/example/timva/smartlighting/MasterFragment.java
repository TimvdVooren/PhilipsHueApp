package com.example.timva.smartlighting;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class MasterFragment extends Fragment {
    View masterview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        masterview = inflater.inflate(R.layout.master_fragment, container, false);


        return masterview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
