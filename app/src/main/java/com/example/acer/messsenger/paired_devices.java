package com.example.acer.messsenger;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class paired_devices extends Fragment  {
    ListView lv1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_paired_devices, container, false);
        lv1=(ListView) view.findViewById(R.id.listview1);
       // lv1.setOnItemClickListener((AdapterView.OnItemClickListener) getContext());
        lv1.setAdapter(MainActivity.arr1);
        return view;
    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice bd = MainActivity.pdevices.get(position);
        Intent intent=new Intent(getContext(),chats.class);
        intent.putExtra("type","client");
        intent.putExtra("device",bd);
        startActivity(intent);*/
    //}
}
