package com.example.acer.messsenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class chats extends Fragment {
    BluetoothDevice bd;
    BluetoothAdapter ba;
    ListView listView;
    EditText et;
    String name;
    ChatCode cc;
    ArrayList<String> messages = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    public static final int CONNECTED = 1;
    public static final int TYPING = 2;
    public static final int SENDING = 3;
    public static final int READING = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listView = (ListView) container.findViewById(R.id.msgListView);
        et = (EditText) container.findViewById(R.id.messageEditText);


        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

}
