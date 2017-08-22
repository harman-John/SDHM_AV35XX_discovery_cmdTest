package com.avnera.smartdigitalheadset.android.example;



import android.widget.Toast;

import com.avnera.smartdigitalheadset.ANCAwarenessPreset;
import com.avnera.smartdigitalheadset.AudioEQPreset;
import com.avnera.smartdigitalheadset.Command;
import com.avnera.smartdigitalheadset.GraphicEQPreset;
import com.avnera.smartdigitalheadset.LightX;

import java.util.ArrayList;
import java.util.List;

public class FunctionTestSmartDigtal {
    private List<String> list;
    private static FunctionTestSmartDigtal instance;

    public static FunctionTestSmartDigtal getInstance(){
        if (instance == null) {
            instance = new FunctionTestSmartDigtal();
        }
        return instance;
    }

    public List<String> initFunctionList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        int i =0;
        list.add(i+".writeAppANCEnable");
        list.add((++i)+".readAppANCEnable");
        list.add((++i)+".writeAppANCLevel");
        list.add((++i)+".writeAppANCAwarenessPreset Low");
        list.add((++i)+".writeAppANCAwarenessPreset Medium");
        list.add((++i)+".writeAppANCAwarenessPreset High");
        list.add((++i)+".readAppANCAwarenessPreset");
        list.add((++i)+".readAppAwarenessRawLeft");
        list.add((++i)+".readAppAwarenessRawRight");
        list.add((++i)+".readAppAwarenessRawSteps");
        list.add((++i)+".AppBatteryLevel");
        list.add((++i)+".readAppFirmwareVersion");
        list.add((++i)+". ");
        list.add((++i)+".writeAppOnEarDetectionWithAutoOff");
        list.add((++i)+".Command.AppOnEarDetectionWithAutoOff");
        list.add((++i)+".writeAppVoicePromptEnable");
        list.add((++i)+".readAppVoicePromptEnable");
        list.add((++i)+".writeAppSmartButtonFeatureIndex");
        list.add((++i)+".readAppSmartButtonFeatureIndex");
        list.add((++i)+". ");
        list.add((++i)+".writeAppGraphicEQBand");
        list.add((++i)+".readAppGraphicEQBand");
        list.add((++i)+".readAppGraphicEQLimits");
        list.add((++i)+".writeAppAudioEQPreset");
        list.add((++i)+".readAppAudioEQPreset");
        list.add((++i)+".readAppGraphicEQBandFreq");
        list.add((++i)+".writeAppGraphicEQPresetBandSettings");
        list.add((++i)+".readAppGraphicEQPresetBandSettings");
        list.add((++i)+".writeAppGraphicEQCurrentPreset");
        list.add((++i)+".readAppGraphicEQCurrentPreset");
        list.add((++i)+".writeAppGraphicEQPersistPreset");
        list.add((++i)+".writeAppGraphicEQDefaultPreset");
        list.add((++i)+".AppGraphicEQDefaultPreset");
        list.add((++i)+".writeAppGraphicEQFactoryResetPreset User");
        list.add((++i)+".readBootImageType: lightXIsInBootloader callback will come");
        list.add((++i)+".enterBootloader");
        list.add((++i)+".enterApplication");
        return list;
    }

    public void goFunction(LightX lightX , int pos) {
        switch (pos) {
            case 0:
                lightX.writeAppANCEnable(true);
                break;
            case 1:
                lightX.readAppANCEnable();
                break;
            case 2:
                lightX.writeAppANCLevel(0.5F);
                break;
            case 3:
                lightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.Low);
                break;
            case 4:
                lightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.Medium);
                break;
            case 5:
                lightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.High);
                break;
            case 6:
                lightX.readAppANCAwarenessPreset();
                break;
            case 7:
                lightX.readAppAwarenessRawLeft();
                break;
            case 8:
                lightX.readAppAwarenessRawRight();
                break;
            case 9:
                lightX.readAppAwarenessRawSteps();
                break;
            case 10:
                lightX.readApp(Command.AppBatteryLevel);
                break;
            case 11:
                lightX.readAppFirmwareVersion();
                break;
            case 12:
                //resource version
                break;
            case 13:
                lightX.writeAppOnEarDetectionWithAutoOff(true);
                break;
            case 14:
                lightX.readApp(Command.AppOnEarDetectionWithAutoOff);
                break;
            case 15:
                lightX.writeAppVoicePromptEnable(true);
                break;
            case 16:
                lightX.readAppVoicePromptEnable();
                break;
            case 17:
                lightX.writeAppSmartButtonFeatureIndex(true);
                break;
            case 18:
                lightX.readAppSmartButtonFeatureIndex();
                break;
            case 19:
                //calibration status which api
            case 20:
                lightX.writeAppGraphicEQBand(GraphicEQPreset.User, 2, 6);
                break;
            case 21:
                lightX.readAppGraphicEQBand(GraphicEQPreset.User, 1);
                break;
            case 22:
                lightX.readAppGraphicEQLimits();
                break;
            case 23:
                lightX.writeAppAudioEQPreset(AudioEQPreset.Music);
                break;
            case 24:
                lightX.readAppAudioEQPreset();
                break;
            case 25:
                lightX.readAppGraphicEQBandFreq();
                break;
            case 26:
                int[] bands = new int[10];
                for (int i=0;i<bands.length;i++) {
                    bands[i] = 5;
                }
                lightX.writeAppGraphicEQPresetBandSettings(GraphicEQPreset.User, bands);
                break;
            case 27:
                lightX.readAppGraphicEQPresetBandSettings(GraphicEQPreset.User);
                break;
            case 28:
                lightX.writeAppGraphicEQCurrentPreset(GraphicEQPreset.User);
                break;
            case 29:
                lightX.readAppGraphicEQCurrentPreset();
                break;
            case 30:
                lightX.writeAppGraphicEQPersistPreset(GraphicEQPreset.User);
                break;
            case 31:
                lightX.writeAppGraphicEQDefaultPreset(GraphicEQPreset.User);
                break;
            case 32:
                lightX.readApp(Command.AppGraphicEQDefaultPreset);
                break;
            case 33:
                lightX.writeAppGraphicEQFactoryResetPreset(GraphicEQPreset.User);
                break;
            case 34:
                lightX.readBootImageType();
                break;
            case 35:
                lightX.enterBootloader();
                break;
            case 36:
                lightX.enterApplication();
                break;
        }
    }


}
