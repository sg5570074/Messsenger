package com.example.acer.messsenger;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class new_devices extends Fragment {
    ListView lv2;
    ArrayList<String> name,mac;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_new_devices, container, false);
        lv2=(ListView) v.findViewById(R.id.listview2);
        lv2.setAdapter(MainActivity.arr2);
        return v;
    }
}
