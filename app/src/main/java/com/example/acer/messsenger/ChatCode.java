package com.example.acer.messsenger;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;

/**
 * Created by acer on 3/11/2017.
 */

public class ChatCode {
    Handler h;
    ChatCode(Handler h)
    {
        this.h=h;
    }
    ChatCode(BluetoothDevice bd, Handler h)
    {

    }
}
