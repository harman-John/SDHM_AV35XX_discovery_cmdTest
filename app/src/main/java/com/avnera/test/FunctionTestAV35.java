package com.avnera.test;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.avnera.av35xx.ANCAwarenessPreset;
import com.avnera.av35xx.AudioEQPreset;
import com.avnera.av35xx.BluetoothSocketWrapper;
import com.avnera.av35xx.Command;
import com.avnera.av35xx.GraphicEQPreset;
import com.avnera.av35xx.LightX;
import com.avnera.av35xx.ModuleId;

import java.io.IOException;

public class FunctionTestAV35 {
    private final static String TAG = FunctionTestAV35.class.getSimpleName();
    private static FunctionTestAV35 instance;
    private LightX mLightX;

    public static FunctionTestAV35 getInstance(){
        if (instance == null) {
            instance = new FunctionTestAV35();
        }
        return instance;
    }

    public void initLightX(BluetoothSocket bluetoothSocket){
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
        }

        @Override
        public boolean lightXFirmwareReadStatus(LightX lightX, LightX.FirmwareRegion firmwareRegion, int i, byte[] bytes, Exception e) {
            Log.d(TAG,"lightXFirmwareReadStatus firmwareRegion= "
                    +firmwareRegion+",i ="+ i+",bytes ="+ HexHelper.byteToArray(bytes)+",e = "+e);
            return false;
        }

        @Override
        public boolean lightXFirmwareWriteStatus(LightX lightX, LightX.FirmwareRegion firmwareRegion, LightX.FirmwareWriteOperation firmwareWriteOperation, double v, Exception e) {
            Log.d(TAG,"lightXFirmwareWriteStatus firmwareRegion= "
                    +firmwareRegion+",firmwareWriteOperation="+firmwareWriteOperation
                    +",v ="+ v+",e = "+e);
            return false;
        }

        @Override
        public void lightXIsInBootloader(LightX lightX, boolean b) {
            Log.d(TAG,"lightXIsInBootloader success="+b );
        }

        @Override
        public void lightXReadConfigResult(LightX lightX, Command command, boolean b, String s) {
            Log.d(TAG,"lightXReadConfigResult commandId= "
                    +command.value()+",success="+b +",s ="+ s);
        }
    };

    public void goFunction(int pos) {
        if (mLightX == null){
            Log.d(TAG,"mLightX is null");
            return;
        }
        switch (pos) {
            case Constants.WRITE_ANC_ENABLE_TRUE:
                mLightX.writeAppANCEnable(true);
                break;
            case Constants.WRITE_ANC_ENABLE_FALSE:
                mLightX.writeAppANCEnable(false);
                break;
            case Constants.READ_ANC_ENABLE:
                mLightX.readAppANCEnable();
                break;
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


}
