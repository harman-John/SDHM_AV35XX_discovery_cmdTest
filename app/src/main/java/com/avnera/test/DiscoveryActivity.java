/*
 * MainActivity.java
 * Smart Digital Headset
 *
 * Created by Brian Doyle on 6/27/15
 * Copyright (c) 2015 Avnera Corporation
 */
package com.avnera.test;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.avnera.av35xx.Log;
import com.avnera.smartdigitalheadset.Bluetooth;
import com.avnera.smartdigitalheadset.LightX;
import com.avnera.test.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DiscoveryActivity extends ActionBarActivity implements
        Bluetooth.Delegate,com.avnera.av35xx.Log.Delegate
        ,com.avnera.smartdigitalheadset.Log.Delegate,View.OnClickListener {
    private Button mButtonStartDiscovery;
    private Button mButtonCancelDiscovery;
    private Button mButtonCloseDiscovery;
    private TextView mTextView;
    private ListView mListviewDevices;
    private List<String> list;
    private ArrayAdapter mAdapter;
    private Map<String,BluetoothDevice> mMap;



    private Bluetooth mBluetooth;
    private String connectedDevice;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Bluetooth.REQUEST_ENABLE_BT: {
                if (resultCode == RESULT_CANCELED) {
                    showExitDialog("Unable to enable Bluetooth.");
                } else {
                    mBluetooth.discoverBluetoothDevices();
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        Log.d(String.format("Discovery onCreate"));
        com.avnera.av35xx.Log.sLogDelegate = this;
        com.avnera.av35xx.LightX.sEnablePacketDumps = true;
        com.avnera.smartdigitalheadset.Log.sLogDelegate = this;
        com.avnera.smartdigitalheadset.LightX.sEnablePacketDumps = true;
        Log.sLogDelegate = this;
        LightX.sEnablePacketDumps = true;
        connectedDevice = null;
        initUI();
        if (mMap == null){
            mMap = new HashMap<>();
        }
    }

    private void initUI() {
        mTextView = (TextView) findViewById(R.id.text_view_connected);
        mTextView.setVisibility(View.GONE);
        mButtonStartDiscovery = (Button) findViewById(R.id.start_discovery_button);
        mButtonStartDiscovery.setOnClickListener(this);
        mButtonCancelDiscovery = (Button) findViewById(R.id.cancel_discovery_button);
        mButtonCancelDiscovery.setOnClickListener(this);
        mButtonCloseDiscovery = (Button) findViewById(R.id.close_discovery_button);
        mButtonCloseDiscovery.setOnClickListener(this);
        mListviewDevices = (ListView) findViewById(R.id.discovery_device_list);
        mListviewDevices.setOnItemClickListener(onItemClickListener);
        mAdapter = new ArrayAdapter<>(this, R.layout.listview_item, getData());
        mListviewDevices.setAdapter(mAdapter);
    }

    public List<String> getData() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            String mac = text.substring(0, 17);
            Log.d("DiscoveryActivityLog mac =" + mac);
            BluetoothDevice bluetoothDevice = mMap.get(mac);
            if (connectedDevice == null) {
                textView.setText(textView.getText()+" Waiting...");
                connectDevice(bluetoothDevice);
            }else{
//                showExitDialog("Connected");
                if (connectedDevice.contains("150")
                        ||connectedDevice.contains("N70")) {
                    FunctionTestAV35.getInstance().goFunction(position);
                }else{
                    FunctionTestSDHM.getInstance().goFunction(position);
                }
            }
        }
    };

    private void startDiscovery() {
        try {
            if (mBluetooth == null) {
                mBluetooth = new Bluetooth(this, this, true);
            }
            mBluetooth.start();
        } catch (IOException e) {
            showExitDialog("Unable to enable Bluetooth.");
        }
    }

    private void cancelDiscovery() {
        if (mBluetooth != null) {
            mBluetooth.cancelDiscovery();
        }
    }

    private void closeDiscovery() {
        if (mBluetooth != null) {
            mBluetooth.close();
            mBluetooth = null;
        }
    }

    private void connectDevice(BluetoothDevice bluetoothDevice){
        if (mBluetooth == null){
            Log.e("DiscoveryActivityLog mBluetooth is null ");
            return;
        }
        switch (bluetoothDevice.getBondState()) {
            case BluetoothDevice.BOND_BONDED: {
                try {
                    mBluetooth.connect(bluetoothDevice);
                } catch ( IOException e ) {
                    // Other device can't connected .
                    Log.e("DiscoveryActivityLog connect Failed msg: " + e.getLocalizedMessage() );
                    showExitDialog("Can't connect "+bluetoothDevice.getName()+",Not Supported");
                }
            }
            break;

            default: {
                mBluetooth.pair(bluetoothDevice);
            }
            break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_discovery_button: {
                startDiscovery();
                break;
            }
            case R.id.cancel_discovery_button: {
                cancelDiscovery();
                break;
            }
            case R.id.close_discovery_button: {
                showDiscovery();
                FunctionTestAV35.getInstance().unInitLightX();
                FunctionTestSDHM.getInstance().unInitLightX();
                closeDiscovery();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FunctionTestAV35.getInstance().unInitLightX();
        FunctionTestSDHM.getInstance().unInitLightX();
        closeDiscovery();
    }

    @Override
    public void bluetoothDeviceDiscovered(Bluetooth bluetooth, BluetoothDevice bluetoothDevice) {
        String name, mac;
        Log.d("DiscoveryActivityLog bluetoothDeviceDiscovered DeviceName = " + bluetoothDevice.getName());
        mac = bluetoothDevice.getAddress();
        name = bluetoothDevice.getName();
        if (!list.contains(mac)) {
            list.add(mac +" (" +name+")");
        }
        mMap.put(mac,bluetoothDevice);
        connectedDevice = null;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
        Log.d("DiscoveryActivityLog Found LightX device \"" + name + "\", bond state is " + Bluetooth.bondStateDescription(bluetoothDevice.getBondState()));
    }

    @Override
    public void bluetoothDeviceBondStateChanged(Bluetooth bluetooth, BluetoothDevice bluetoothDevice, int currentState, int previousState) {
        Log.d("DiscoveryActivityLog bluetoothDeviceBondStateChanged currentState="+currentState+",previousState="+previousState);
        if (currentState == BluetoothDevice.BOND_BONDED) {
            Log.d("DiscoveryActivityLog "+bluetooth.deviceName(bluetoothDevice));
        } else if (currentState == BluetoothDevice.BOND_BONDING && previousState == BluetoothDevice.BOND_BONDED ||
                currentState == BluetoothDevice.BOND_NONE && previousState == BluetoothDevice.BOND_BONDING) {
            Log.d("DiscoveryActivityLog Received unpair event, attempting to re-pair");
        }
    }

    @Override
    public void bluetoothAdapterChangedState(Bluetooth bluetooth, int currentState, int previousState) {
        Log.d("DiscoveryActivityLog bluetoothAdapterChangedState currentState="+currentState+",previousState="+previousState);
        if (currentState != BluetoothAdapter.STATE_ON) {
            Log.e("DiscoveryActivityLog " + "The Bluetooth adapter is not enabled, cannot communicate with LightX device");
        }
    }

    @Override
    public void bluetoothDeviceConnected(Bluetooth bluetooth, final BluetoothDevice bluetoothDevice, BluetoothSocket bluetoothSocket) {
        Log.d("DiscoveryActivityLog bluetoothDeviceConnected");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showConnectedDevice(bluetoothDevice);
            }
        });
        if (bluetoothDevice.getName().contains("150")
                ||bluetoothDevice.getName().contains("N70")) {
            FunctionTestAV35.getInstance().initLightX(bluetoothSocket);
        }else{
            FunctionTestSDHM.getInstance().initLightX(bluetoothSocket);
        }
    }

    @Override
    public void bluetoothDeviceDisconnected(Bluetooth bluetooth, BluetoothDevice bluetoothDevice, boolean b) {
        Log.d("DiscoveryActivityLog bluetoothDeviceDisconnected");
        connectedDevice = null;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDiscovery();
            }
        });
        FunctionTestAV35.getInstance().unInitLightX();
        FunctionTestSDHM.getInstance().unInitLightX();
    }

    private List<String> initFunctionList() {
        if (list == null) {
            list = new ArrayList<>();
        }

        for(int i =0;i <Constants.COMMAND_NAME.length;i++) {
            list.add((i+1) + Constants.COMMAND_NAME[i]);
        }
        return list;
    }

    private void showConnectedDevice(BluetoothDevice bluetoothDevice){
        mButtonStartDiscovery.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Connected:\n");
        builder.append(bluetoothDevice.getAddress());
        builder.append("(" + bluetoothDevice.getName() + ")");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.rgb(254, 58, 54));
        builder.setSpan(span, 0, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(builder);
        connectedDevice = bluetoothDevice.getName();
        mButtonCancelDiscovery.setVisibility(View.GONE);
        list.clear();
        initFunctionList();
        mAdapter.notifyDataSetChanged();
    }

    private void showDiscovery(){
        mButtonStartDiscovery.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);
        mButtonCancelDiscovery.setVisibility(View.VISIBLE);
        list.clear();
        connectedDevice = null;
        mAdapter.notifyDataSetChanged();
    }

    public void showExitDialog(String message) {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }



    public void log(com.avnera.av35xx.Log.Level level, String tag, String message) {
        switch (level) {
            case Debug:
                android.util.Log.i(tag, message);
                break;
            case Error:
                android.util.Log.e(tag, message);
                break;
            case Info:
                android.util.Log.i(tag, message);
                break;
            case Verbose:
                android.util.Log.v(tag, message);
                break;
            case Warning:
                android.util.Log.w(tag, message);
                break;
        }
    }

    @Override
    public void log(com.avnera.smartdigitalheadset.Log.Level level, String tag, String message) {
        switch (level) {
            case Debug:
                android.util.Log.i(tag, message);
                break;
            case Error:
                android.util.Log.e(tag, message);
                break;
            case Info:
                android.util.Log.i(tag, message);
                break;
            case Verbose:
                android.util.Log.v(tag, message);
                break;
            case Warning:
                android.util.Log.w(tag, message);
                break;
        }
    }
}
