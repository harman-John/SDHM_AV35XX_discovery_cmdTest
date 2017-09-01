package com.avnera.test;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.avnera.av35xx.ANCAwarenessPreset;
import com.avnera.av35xx.AudioEQPreset;
import com.avnera.av35xx.BluetoothSocketWrapper;
import com.avnera.av35xx.Command;
import com.avnera.av35xx.GraphicEQPreset;
import com.avnera.av35xx.LightX;
import com.avnera.av35xx.ModuleId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class FunctionTestAV35OTA {
    private final static String TAG = FunctionTestAV35OTA.class.getSimpleName();
    private static FunctionTestAV35OTA instance;
    private LightX mLightX;
    private Context mContext;
    private String mDeviceName;
    private OnReceiveListener onReceiveListener;

    public static FunctionTestAV35OTA getInstance(){
        if (instance == null) {
            instance = new FunctionTestAV35OTA();
        }
        return instance;
    }

    public void initLightX(Context context,String deviceName ,BluetoothSocket bluetoothSocket){
        mContext = context;
        mDeviceName = deviceName;
        if ( mLightX != null && mLightX.getSocket().equals( bluetoothSocket ) ) {
            Log.d(TAG," Already initialized lightX" );
            return;
        }
        unInitLightX();
        if (mLightX == null){
            try {
                mLightX =  new LightX(ModuleId.Bluetooth, delegate , new BluetoothSocketWrapper(bluetoothSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void unInitLightX(){
        if ( mLightX != null ) {
            mLightX.close();
            mLightX = null;
        }
    }

    public void setOnReceiveMessage(OnReceiveListener l){
        onReceiveListener = l;
    }

    public void readBootImageType(){
        if (mLightX== null){
            Log.e(TAG,"mLightX is null");
            return;
        }
        mLightX.readBootImageType();
    }

    public void enterApplication(){
        if (mLightX== null){
            Log.e(TAG,"mLightX is null");
            return;
        }
        mLightX.enterApplication();
    }

    private LightX.Delegate delegate = new LightX.Delegate() {
        @Override
        public void lightXAppReadResult(LightX lightX, Command command, boolean b, byte[] bytes) {
            Log.d(TAG,"lightXAppReadResult commandId= "
                    +command.value()+",success="+b +",bytes ="+ HexHelper.byteToArray(bytes));

        }

        @Override
        public void lightXAppReceivedPush(LightX lightX, Command command, byte[] bytes) {
            Log.d(TAG,"lightXAppReceivedPush commandId= "
                    +command.value() +",bytes ="+ HexHelper.byteToArray(bytes));
        }

        @Override
        public void lightXAppWriteResult(LightX lightX, Command command, boolean b) {
            Log.d(TAG,"lightXAppWriteResult commandId= "+command.value()+",success="+b );
        }

        @Override
        public boolean lightXAwaitingReply(LightX lightX, Command command, int i) {
            Log.d(TAG,"lightXAwaitingReply commandId= "+command.value()+",i="+i );
            return false;
        }

        @Override
        public void lightXError(LightX lightX, Exception e) {
            Log.d(TAG,"lightXError e ="+ e);
            onReceiveListener.onReceive(e.getMessage());
        }

        @Override
        public boolean lightXFirmwareReadStatus(LightX lightX, LightX.FirmwareRegion firmwareRegion, int i, byte[] bytes, Exception e) {
            Log.d(TAG,"lightXFirmwareReadStatus firmwareRegion= "
                    +firmwareRegion+",i ="+ i+",bytes ="+ HexHelper.byteToArray(bytes)+",e = "+e);
            return false;
        }

        @Override
        public boolean lightXFirmwareWriteStatus(LightX lightX, LightX.FirmwareRegion firmwareRegion, LightX.FirmwareWriteOperation firmwareWriteOperation, double progress, Exception e) {
            Log.d(TAG,"lightXFirmwareWriteStatus firmwareRegion= "
                    +firmwareRegion+",firmwareWriteOperation="+firmwareWriteOperation
                    +",progress ="+ progress+",e = "+e);
            if ( e != null ) {
                Log.i(TAG,"lightXFirmwareWriteStatus: %s", e);
            } else {
                switch (firmwareWriteOperation){
                    case Erase:
                        Log.d( TAG, " ERASING....."  );
                    case Verify:
                        Log.d( TAG, " Verify....."  );
                    case Write:
                        onReceiveListener.onReceive(firmwareWriteOperation.toString() + " firmware " + firmwareRegion.toString() +
                                        " progress: " + (int)( progress * 100.0 ));
                    case Checksum:
                        Log.d( TAG, " Checksum....." );
                        break;
                    case Complete:
                        if (globalVal == 3){
                            startWritingFirmware(4);
                        }else if(globalVal == 4){
                            startWritingFirmware(2);
                        }else if(globalVal == 2){
                            if (mLightX != null)
                                 mLightX.enterApplication();
                        }
                        break;
                    default:
                        Log.d( TAG," Default case" );
                        return false;
                }
            }
            return false;
        }

        @Override
        public void lightXIsInBootloader(LightX lightX, boolean isInBootloader) {
            Log.d(TAG,"lightXIsInBootloader isInBootloader="+isInBootloader );
            onReceiveListener.onReceive(Boolean.toString(isInBootloader));
            if ( isInBootloader ) {
                startWritingFirmware(3);
            }else{
                if (mLightX != null) {
                    mLightX.enterBootloader();
                    mLightX.readBootImageType();
                }
            }
        }

        @Override
        public void lightXReadConfigResult(LightX lightX, Command command, boolean b, String s) {
            Log.d(TAG,"lightXReadConfigResult commandId= "
                    +command.value()+",success="+b +",s ="+ s);
        }
    };

    private int globalVal ;
    public void startWritingFirmware(int val) {
        globalVal = val;
        byte[] data;
        switch (val) {
            case 2: {
                if (mDeviceName.contains("150") || mDeviceName.contains("AV35XX")) {
                    data = readRawResource(R.raw.e150_act_pj_rsrc2);
                    Log.d(TAG,"Upgrade Resource2 e150_act_pj_rsrc2 addr = 0x00145000");
                }else{
                    data = readRawResource(R.raw.akg_act_pj_rsrc2);
                    Log.d(TAG,"Upgrade Resource2 akg_act_pj_rsrc2 addr = 0x00145000");
                }
                mLightX.writeFirmware( LightX.FirmwareRegion.Resource2, 0x00145000, data  );
            } break;

            case 3: {
                if (mDeviceName.contains("150")|| mDeviceName.contains("AV35XX")) {
                    data = readRawResource(R.raw.e150_act_pj_rsrc1);
                    Log.d(TAG,"Upgrade Resource1 e150_act_pj_rsrc1 addr = 0x00088000");
                }else{
                    data = readRawResource(R.raw.akg_act_pj_rsrc1);
                    Log.d(TAG,"Upgrade Resource1 akg_act_pj_rsrc1 addr = 0x00088000");
                }
                mLightX.writeFirmware( LightX.FirmwareRegion.Resource1, 0x00088000, data );
            } break;
            case 4: {
                if (mDeviceName.contains("150")|| mDeviceName.contains("AV35XX")) {
                    data = readRawResource(R.raw.e150_app);
                    Log.d(TAG,"Upgrade Application e150_app addr = 0x00098000");
                }else{
                    data = readRawResource(R.raw.akg_n70_app);
                    Log.d(TAG,"Upgrade Application akg_n70_app addr = 0x00098000");
                }
                mLightX.writeFirmware( LightX.FirmwareRegion.Application, 0x00098000, data );
            } break;

            case 0:
//                startWritingFirmware(3);
                break;
        }
    }


    public byte[] readRawResource( int rawResourceId ) {
        byte[]						buffer;
        InputStream inputStream;
        int							read;
        ByteArrayOutputStream outputStream;

        buffer = new byte[ 4096 ];
        inputStream = mContext.getResources().openRawResource( rawResourceId );
        outputStream = new ByteArrayOutputStream();

        try {
            for (; ; ) {
                if ( ( read = inputStream.read( buffer ) ) < 0 ) break;

                outputStream.write( buffer, 0, read );
            }

            return outputStream.toByteArray();
        } catch ( IOException e ) {
        } finally {
            try { inputStream.close(); } catch ( Exception e ) { }
        }

        return null;
    }
}
