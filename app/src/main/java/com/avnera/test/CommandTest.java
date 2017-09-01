package com.avnera.test;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.avnera.av35xx.ANCAwarenessPreset;
import com.avnera.av35xx.AudioEQPreset;
import com.avnera.av35xx.BluetoothSocketWrapper;
import com.avnera.av35xx.Command;
import com.avnera.av35xx.GraphicEQPreset;
import com.avnera.av35xx.LightX;
import com.avnera.av35xx.ModuleId;
import com.avnera.smartdigitalheadset.Bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class CommandTest {
    private final static String TAG = CommandTest.class.getSimpleName();
    private static CommandTest instance;
    private LightX mLightX;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Context mContext;
    private OnReceiveListener onReceiveListener;
    public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static CommandTest getInstance(){
        if (instance == null) {
            instance = new CommandTest();
        }
        return instance;
    }

    public void setOnReceiveMessage(OnReceiveListener l){
        onReceiveListener = l;
    }

    public synchronized void connect(Bluetooth bluetooth,BluetoothDevice device) throws IOException {
        bluetooth.cancelDiscovery();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetooth.deviceSupportsProfile(device, SPP_UUID)) {
            throw new IOException("Bluetooth devices does not support serial port profile");
        }
        socket = createBluetoothSocket(device);
        if (socket == null){
            Log.i(TAG,"createBluetoothSocket failed");
            return;
        }
        socket.connect();
        if (socket != null && socket.isConnected()) {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            DiscoveryMessageThread discoveryMessageThread = new DiscoveryMessageThread();
            discoveryMessageThread.start();
        }
    }

    public BluetoothSocket getSocket(){
        if (socket != null && socket.isConnected()) {
            return socket;
        }
        return null;
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) {
        BluetoothSocket socket;
        try {
            socket = device.createRfcommSocketToServiceRecord(SPP_UUID);
        } catch (Exception e) {
            try
            {
                socket = device.createInsecureRfcommSocketToServiceRecord(SPP_UUID);
            }
            catch(Exception ex)
            {
                socket=null;
            }
        }
        return socket;
    }

    public synchronized void disconnect() {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
                inputStream = null;
            }
        } catch (IOException var7) {
            ;
        }
    }

    public void sendCommand(byte[] bytes){
        if (outputStream == null){
            Log.d(TAG,"outputStream is null");
            return;
        }
        try {
            Log.d(TAG,"bytes ="+HexHelper.encodeHexStr(bytes));
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goFunction(int pos) {
        if (outputStream == null){
            Log.d(TAG,"outputStream is null");
            return;
        }
        switch (pos) {
            case Constants.WRITE_ANC_ENABLE_TRUE: {
                byte[] bytes = new byte[]{
                        (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x17, (byte) 0x0F, (byte) 0x43, (byte) 0x82, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xF0, (byte) 0x0C, (byte) 0x04,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xBB};
                try {
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Constants.WRITE_ANC_ENABLE_FALSE: {
                byte[] bytes = new byte[]{
                        (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x17, (byte) 0x0F, (byte) 0x43, (byte) 0x82, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xF0, (byte) 0x0C, (byte) 0x04,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xBC};
                try {
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Constants.READ_ANC_ENABLE:{
                byte[] bytes = new byte[]{
                        (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0xF0, (byte) 0x0C, (byte) 0x10,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xA5};
                try {
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Constants.WRITE_ANC_LEVEL:
                mLightX.writeAppANCLevel(0F);
                break;
            case Constants.WRITE_ANC_AWARENESS_PRESET_LOW:
                mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.Low);
                break;
            case Constants.WRITE_ANC_AWARENESS_PRESET_MEDIUM:
                mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.Medium);
                break;
            case Constants.WRITE_ANC_AWARENESS_PRESET_HIGH:
                mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.High);
                break;
            case Constants.READ_ANC_AWARENESS_PRESET:
                mLightX.readAppANCAwarenessPreset();
                break;
            case Constants.READ_AWARENESS_RAW_LEFT:
                mLightX.readAppAwarenessRawLeft();
                break;
            case Constants.READ_AWARENESS_RAW_RIGHT:
                mLightX.readAppAwarenessRawRight();
                break;
            case Constants.READ_AWARENESS_RAW_STEPS:
                mLightX.readAppAwarenessRawSteps();
                break;
            case Constants.READ_APP_BATTERY_LEVEL:
                mLightX.readApp(Command.AppBatteryLevel);
                break;
            case Constants.READ_APP_FIRMWARE_VERSION:
                mLightX.readAppFirmwareVersion();
                break;
            case Constants.READ_RESOURCE_VERSION:
                //resource version
                break;
            case Constants.WRITE_APP_ON_EAR_DETECTION_WITH_AUTO_OFF:
                mLightX.writeAppOnEarDetectionWithAutoOff(true);
                break;
            case Constants.READ_APP_ON_EAR_DETECTION_WITH_AUTO_OFF:
                mLightX.readApp(Command.AppOnEarDetectionWithAutoOff);
                break;
            case Constants.WRITE_APP_VOICE_PROMPT_ENABLE:
                mLightX.writeAppVoicePromptEnable(true);
                break;
            case Constants.READ_APP_VOICE_PROMPT_ENABLE:
                mLightX.readAppVoicePromptEnable();
                break;
            case Constants.WRITE_APP_SMART_BUTTON_FEATURE_INDEX_TRUE:
                mLightX.writeAppSmartButtonFeatureIndex(true);
                break;
            case Constants.READ_APP_SMART_BUTTON_FEATURE_INDEX:
                mLightX.readAppSmartButtonFeatureIndex();
                break;
            case Constants.START_CALIBRATION_STATUS:
                mLightX.writeAppWithUInt32Argument(Command.App_0xB2, true, 0);
                break;
            case Constants.READ_CALIBRATION_STATUS:
                mLightX.readApp(Command.App_0xB3);
                break;
            case Constants.WRITE_APP_GRAPHIC_EQ_BAND:
                mLightX.writeAppGraphicEQBand(GraphicEQPreset.User, 2, 6);
                break;
            case Constants.READ_APP_GRAPHIC_EQ_BAND:
                mLightX.readAppGraphicEQBand(GraphicEQPreset.User, 1);
                break;
            case Constants.READ_APP_GRAPHIC_EQ_LIMITS:
                mLightX.readAppGraphicEQLimits();
                break;
            case Constants.WRITE_APP_AUDIO_EQ_PRESET:
                mLightX.writeAppAudioEQPreset(AudioEQPreset.Conference);
                break;
            case Constants.READ_APP_AUDIO_EQ_PRESET:
                mLightX.readAppAudioEQPreset();
                break;
            case Constants.READ_APP_GRAPHIC_EQ_BAND_REQ:
                mLightX.readAppGraphicEQBandFreq();
                break;
            case Constants.WRITE_APP_GRAPHIC_EQ_PRESET_BAND_SETTINGS:
                int[] bands = new int[10];
                for (int i=0;i<bands.length;i++) {
                    bands[i] = 5;
                }
                mLightX.writeAppGraphicEQPresetBandSettings(GraphicEQPreset.User, bands);
                break;
            case Constants.READ_APP_GRAPHIC_EQ_PRESET_BAND_SETTINGS:
                mLightX.readAppGraphicEQPresetBandSettings(GraphicEQPreset.User);
                break;
            case Constants.WRITE_APP_GRAPHIC_EQ_CURRENT_PRESET:
                mLightX.writeAppGraphicEQCurrentPreset(GraphicEQPreset.User);
                break;
            case Constants.READ_APP_GRAPHIC_EQ_CURRENT_PRESET:
                mLightX.readAppGraphicEQCurrentPreset();
                break;
            case Constants.WRITE_APP_GRAPHIC_EQ_PERSIST_PRESET:
                mLightX.writeAppGraphicEQPersistPreset(GraphicEQPreset.User);
                break;
            case Constants.WRITE_APP_GRAPHIC_EQ_DEFAULT_PRESET:
                mLightX.writeAppGraphicEQDefaultPreset(GraphicEQPreset.User);
                break;
            case Constants.READ_APP_GRAPHIC_EQ_DEFAULT_PRESET:
                mLightX.readApp(Command.AppGraphicEQDefaultPreset);
                break;
            case Constants.WRITE_APP_GRAPHIC_EQ_FACTORY_RESET_PRESET:
                mLightX.writeAppGraphicEQFactoryResetPreset(GraphicEQPreset.User);
                break;
            case Constants.READ_BOOT_IMAGE_TYPE:
                mLightX.readBootImageType();
                break;
            case Constants.ENTER_BOOTLOADER:
                mLightX.enterBootloader();
                break;
            case Constants.ENTER_APPLICATION:
                mLightX.enterApplication();
                break;
        }
    }

    public class DiscoveryMessageThread extends Thread {

        public DiscoveryMessageThread() {

        }

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Log.i(TAG , " start command dealing thread");
            while (true && !Thread.interrupted()) {

                if (inputStream == null) return;
                byte[] msg = new byte[1024];
                try {

                    int readed = inputStream.read(msg);
                    byte[] buffer = new byte[readed];
                    for (int i = 0; i < readed; i++) {
                        buffer[i] = msg[i];
                    }
                    Log.i(TAG , " msg : " +HexHelper.encodeHexStr(buffer));
                    onReceiveListener.onReceive(HexHelper.encodeHexStr(buffer));
                } catch (IOException e) {
                    Log.i(TAG , "read error: "+e);
                }
            }
        }
    }
}
