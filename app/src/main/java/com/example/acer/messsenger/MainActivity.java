package com.example.acer.messsenger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements Runnable {
    public static ActionBar ab;
    TabLayout tb;
    ViewPager vp;
    FragmentAdapterClass fac;
    BluetoothAdapter ba;
    //public static ListView lv1;
    //public static ListView lv2;
    ArrayList<String> pname, pmac, name, mac;
    public static ArrayList<BluetoothDevice> pdevices;
    public static ArrayAdapter<String> arr1, arr2;
    Fragment pair,chat,newdevice;

    private GoogleApiClient client;
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ba = BluetoothAdapter.getDefaultAdapter();
        tb = (TabLayout) findViewById(R.id.tablayout);
        vp = (ViewPager) findViewById(R.id.viewpager);
        fac = new FragmentAdapterClass(getSupportFragmentManager());
        pair=new paired_devices();
        chat=new chats();
        newdevice=new new_devices();
        fac.addTitleAndFragment(pair, "PAIRED DEVICES");
        fac.addTitleAndFragment(chat, "CHATS");
        fac.addTitleAndFragment(newdevice, "NEW Devices");
        vp.setAdapter(fac);
        tb.setupWithViewPager(vp);
        pname = new ArrayList<String>();
        pmac = new ArrayList<String>();
        name = new ArrayList<String>();
        mac = new ArrayList<String>();
        pdevices = new ArrayList<BluetoothDevice>();
        arr1 = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, pname);
        arr2 = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, name);
        // Log.e("list is :", pname.get(0)+"\n"+pname.get(1));
        //lv1.setAdapter(arr1);
        //lv2.setAdapter(arr2);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        if (ba.isEnabled()) {
            MenuItem menuItem = (MenuItem) findViewById(R.id.bluetooth1);
            menuItem.setIcon(R.drawable.bluetooth1_on);
        }
        return (true);
    }

    MenuItem item, ditem;

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        this.item = item;
        if (id == R.id.bluetooth1) {
            if (!ba.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1);
            } else {
                ba.disable();
                item.setIcon(R.drawable.bluetooth1_off);
            }
        } else if (id == R.id.discoverable) {
            if (ba.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20);
                startActivityForResult(intent, 2);
            } else {
                // Snackbar.make((CoordinatorLayout)findViewById(R.id.coor),"PLEASE TURN ON BLUETOOTH FIRST",Snackbar.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "Please turn on the Bluetooth..", Toast.LENGTH_SHORT).show();

            }

        } else if (id == R.id.server) {
            /*Intent intent = new Intent(MainActivity.this, chats.class);
             intent.putExtra("type","server");
             startActivity(intent);*/
          //  public void changefragmet()
             FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
             fm.replace(R.id.defaultfragment,chat);
             fm.commit();
        }
        return true;
    }

    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == 1) {
            if (resultcode == RESULT_OK) {
                item.setIcon(R.drawable.bluetooth1_on);
                getPairedDevices();
                IntentFilter intn1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);//Remote device discovered.
                //    Sent when a remote device is found during discovery
                IntentFilter intn2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                registerReceiver(receiver1, intn1);//for regisetring the broadcastreciever
                registerReceiver(receiver2, intn2);
                new DiscoveryThread().start();
            }
        } else if (requestcode == 2) {
            ditem = item;
            ditem.setIcon(R.drawable.dis1_on);

            new Thread(this).start();
        }
    }

    public class DiscoveryThread extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                ba.startDiscovery();//data packets will emits from remote device
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }

    BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override  //intent ki information aayegi like bluetooth info
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            name.add(bluetoothDevice.getName());
            mac.add(bluetoothDevice.getAddress());
            arr2.notifyDataSetChanged();
        }
    };
    BroadcastReceiver receiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(receiver1);
            unregisterReceiver(receiver2);
        }
    };

    public void getPairedDevices() {
        for (int i = pname.size() - 1; i >= 0; i--) {
            pname.remove(i);
            pmac.remove(i);
            pdevices.remove(i);
        }
        Set<BluetoothDevice> devices = ba.getBondedDevices();
        Iterator<BluetoothDevice> iterator = devices.iterator();
        while (iterator.hasNext()) {
            BluetoothDevice b = iterator.next();
            pname.add(b.getName());
            pmac.add(b.getAddress());
            pdevices.add(b);
        }
        arr1.notifyDataSetChanged();
    }


    Handler h = new Handler();

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
        }
        h.post(new Runnable() {
            @Override
            public void run() {
                ditem.setIcon(R.drawable.dis1_off);
            }
        });
    }

    class FragmentAdapterClass extends FragmentPagerAdapter {
        ArrayList<Fragment> al1 = new ArrayList<Fragment>();
        ArrayList<String> al2 = new ArrayList<String>();

        public FragmentAdapterClass(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return al1.get(position);
        }

        @Override
        public int getCount() {
            return al1.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return al2.get(position);
        }

        public void addTitleAndFragment(Fragment f, String s) {
            al1.add(f);
            al2.add(s);
        }

    }

    public void send(View view) {
        Toast.makeText(this, "asdfghj", Toast.LENGTH_SHORT).show();

    }


}
