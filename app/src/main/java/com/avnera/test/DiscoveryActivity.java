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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.avnera.av35xx.Log;
import com.avnera.smartdigitalheadset.Bluetooth;
import com.avnera.smartdigitalheadset.LightX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;


public class DiscoveryActivity extends ActionBarActivity implements
        Bluetooth.Delegate,com.avnera.av35xx.Log.Delegate
        ,com.avnera.smartdigitalheadset.Log.Delegate,View.OnClickListener {
    private Button mButtonStartDiscovery;
    private Button mButtonCloseDiscovery;
    private TextView mTextView;
    private Spinner mSpinnerFuncSelect;
    private ListView mListviewDevices;
    private List<String> list;
    private ArrayAdapter mAdapter;
    private Map<String,BluetoothDevice> mMap;
    private LinearLayout mLinearLayoutCmdTest;
    private EditText mEditTextSendCmd;
    private Spinner mSpinner;
    private Button mButtonSend;
    private TextView mTextViewReceiveCmd;

    private LinearLayout mLinearLayoutUpgrage;
    private Button mButtonUpgrade;
    private ProgressBar mProgressBar;
    private TextView mTextViewUpgrage;
    private Button mButtonEnterApp;


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

    private int useCommandTest = 0;
    private void initUI() {
        mLinearLayoutUpgrage = (LinearLayout)findViewById(R.id.linearLayout_upgrade);
        mButtonUpgrade = (Button)findViewById(R.id.button_upgrade);
        mButtonUpgrade.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mTextViewUpgrage = (TextView)findViewById(R.id.textview_detail);
        mButtonEnterApp = (Button) findViewById(R.id.button_enter_app);
        mButtonEnterApp.setOnClickListener(this);

        mLinearLayoutCmdTest = (LinearLayout)findViewById(R.id.linearLayout_cmd_test);
        mLinearLayoutCmdTest.setVisibility(View.GONE);
        mEditTextSendCmd = (EditText)findViewById(R.id.editText_cmd);
        mButtonSend = (Button)findViewById(R.id.button_send);
        mButtonSend.setOnClickListener(this);
        mTextViewReceiveCmd = (TextView)findViewById(R.id.textview_msg_received);
        mSpinner = (Spinner)findViewById(R.id.spinner_cmd_list);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String commd = HexHelper.encodeHexStr(Constants.Command[position]);
                mEditTextSendCmd.setText(commd);
                Log.d("position="+position+",id="+id);
                mTextViewReceiveCmd.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerFuncSelect = (Spinner)findViewById(R.id.spinner_cmdtest);
        mSpinnerFuncSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                useCommandTest = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTextView = (TextView) findViewById(R.id.text_view_connected);
        mTextView.setVisibility(View.GONE);
        mButtonStartDiscovery = (Button) findViewById(R.id.start_discovery_button);
        mButtonStartDiscovery.setOnClickListener(this);
        mButtonCloseDiscovery = (Button) findViewById(R.id.close_discovery_button);
        mButtonCloseDiscovery.setOnClickListener(this);
        mListviewDevices = (ListView) findViewById(R.id.discovery_device_list);
        mListviewDevices.setOnItemClickListener(onItemClickListener);
        mAdapter = new ArrayAdapter<>(this, R.layout.listview_item, getData());
        mListviewDevices.setAdapter(mAdapter);
        mListviewDevices.setVisibility(View.VISIBLE);
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
            if (connectedDevice == null) {// Device list,select one device to connect.
                textView.setText(textView.getText()+" Waiting...");
                if (useCommandTest == 0) { // For Avnera lib Test.
                    connectDevice(bluetoothDevice);
                }else if (useCommandTest == 1){// For Command test.
                    connectDeviceUseCommandTest(bluetoothDevice);
                }else{// For OTA test.
                    connectDevice(bluetoothDevice);
                }
            }else{// Connected, choose function to test
                if (useCommandTest == 0) {
                    if (connectedDevice.contains("150")
                            || connectedDevice.contains("N70")) {
                        FunctionTestAV35.getInstance().goFunction(position);
                    } else {
                        FunctionTestSDHM.getInstance().goFunction(position);
                    }
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
                    showExitDialog("Can't connect "+bluetoothDevice.getName()+",UUID Not Supported");
                }
            }
            break;

            default: {
                mBluetooth.pair(bluetoothDevice);
            }
            break;
        }
    }

    private void connectDeviceUseCommandTest(BluetoothDevice bluetoothDevice) {
        CommandTest.getInstance().setOnReceiveMessage(new OnReceiveListener() {
            @Override
            public void onReceive(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(" --------- msg = " + msg);
                        mTextViewReceiveCmd.setText(msg);
                    }
                });
            }
        });
        try {
            CommandTest.getInstance().connect(mBluetooth, bluetoothDevice);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BluetoothSocket socket = CommandTest.getInstance().getSocket();
        if (socket != null) {
            showConnectedCmdTest(bluetoothDevice);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_discovery_button: {
                startDiscovery();
                break;
            }
            case R.id.close_discovery_button: {
                showDiscovery();
                if (useCommandTest == 0){
                    FunctionTestAV35.getInstance().unInitLightX();
                    FunctionTestSDHM.getInstance().unInitLightX();
                }else if (useCommandTest == 1) {
                    CommandTest.getInstance().disconnect();
                }else if (useCommandTest == 2){
                    FunctionTestAV35OTA.getInstance().unInitLightX();
                    FunctionTestAV35OTA.getInstance().unInitLightX();
                }
                closeDiscovery();
                break;
            }
            case R.id.button_send: {
                mTextViewReceiveCmd.setText("");
                String temp = mEditTextSendCmd.getText().toString();
                Log.i(" temp command ="+temp);
                byte[] bytes = HexHelper.hexStringToByteArray(temp);
                CommandTest.getInstance().sendCommand(bytes);
                break;
            }
            case R.id.button_upgrade: {
                FunctionTestAV35OTA.getInstance().readBootImageType();
                break;
            }
            case R.id.button_enter_app:{
                FunctionTestAV35OTA.getInstance().enterApplication();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useCommandTest == 0){
            FunctionTestAV35.getInstance().unInitLightX();
            FunctionTestSDHM.getInstance().unInitLightX();
        }else if (useCommandTest ==1) {
            CommandTest.getInstance().disconnect();
        }else if (useCommandTest == 2){
            FunctionTestAV35OTA.getInstance().unInitLightX();
            FunctionTestAV35OTA.getInstance().unInitLightX();
        }
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
                if (useCommandTest == 0) {
                    showConnectedDevice(bluetoothDevice);
                }else if (useCommandTest == 2){
                    showOTAUpgrade(bluetoothDevice);
                }
            }
        });
        if (bluetoothDevice.getName().contains("150")
                ||bluetoothDevice.getName().contains("AV35XX")
                ||bluetoothDevice.getName().contains("N70")) {
            if (useCommandTest == 0) {
                FunctionTestAV35.getInstance().initLightX(bluetoothSocket);
            }else if (useCommandTest == 2){
                FunctionTestAV35OTA.getInstance().initLightX(this,bluetoothDevice.getName(),bluetoothSocket);
                FunctionTestAV35OTA.getInstance().setOnReceiveMessage(new OnReceiveListener() {
                    @Override
                    public void onReceive(final String msg) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextViewUpgrage.setText(msg);
                                if(msg.contains(":")) {
                                    String[] tmp = msg.split(":");
                                    if (tmp.length>=2) {
                                        mProgressBar.setProgress(Integer.valueOf(tmp[1].trim()));
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }else{
            if (useCommandTest == 0) {
                FunctionTestSDHM.getInstance().initLightX(bluetoothSocket);
            }else if (useCommandTest == 2){
                FunctionTestSDHMOTA.getInstance().initLightX(bluetoothSocket);
            }
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
        if (useCommandTest == 0){
            FunctionTestAV35.getInstance().unInitLightX();
            FunctionTestSDHM.getInstance().unInitLightX();
        }else if (useCommandTest==1) {
            CommandTest.getInstance().disconnect();
        }else if (useCommandTest == 2){
            FunctionTestAV35OTA.getInstance().unInitLightX();
            FunctionTestAV35OTA.getInstance().unInitLightX();
        }
    }

    private List<String> initFunctionList() {
        if (list == null) {
            list = new ArrayList<>();
        }

        String[] cmdName = getResources().getStringArray(R.array.function_list);
        for(int i =0;i <cmdName.length;i++) {
            list.add((i+1) + cmdName[i]);
        }
        return list;
    }

    private void showConnectedDevice(BluetoothDevice bluetoothDevice){
        mSpinnerFuncSelect.setVisibility(View.GONE);
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
        list.clear();
        initFunctionList();
        mAdapter.notifyDataSetChanged();
        mLinearLayoutCmdTest.setVisibility(View.GONE);
        mListviewDevices.setVisibility(View.VISIBLE);
        mLinearLayoutUpgrage.setVisibility(View.GONE);
    }

    private void showConnectedCmdTest(BluetoothDevice bluetoothDevice){
        mSpinnerFuncSelect.setVisibility(View.GONE);
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
        mListviewDevices.setVisibility(View.GONE);
        mLinearLayoutCmdTest.setVisibility(View.VISIBLE);
        mLinearLayoutUpgrage.setVisibility(View.GONE);
    }

    private void showOTAUpgrade(BluetoothDevice bluetoothDevice){
        mTextViewUpgrage.setText("");
        mProgressBar.setProgress(0);
        mSpinnerFuncSelect.setVisibility(View.GONE);
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
        mLinearLayoutCmdTest.setVisibility(View.GONE);
        mListviewDevices.setVisibility(View.GONE);
        mLinearLayoutUpgrage.setVisibility(View.VISIBLE);
    }

    private void showDiscovery(){
        mSpinnerFuncSelect.setVisibility(View.VISIBLE);
        mButtonStartDiscovery.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);
        list.clear();
        connectedDevice = null;
        mAdapter.notifyDataSetChanged();
        mLinearLayoutCmdTest.setVisibility(View.GONE);
        mListviewDevices.setVisibility(View.VISIBLE);
        mLinearLayoutUpgrage.setVisibility(View.GONE);
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
