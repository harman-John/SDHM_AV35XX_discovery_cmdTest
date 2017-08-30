package com.avnera.test;

/**
 * Created by Johngan on 28/08/2017.
 */

public class Constants {


    public static final String[] COMMAND_NAME = {
            ".writeAppANCEnableTrue",
            ".writeAppANCEnableFalse",
            ".readAppANCEnable",
            ".writeAppANCLevel",
            ".writeAppANCAwarenessPreset Low",
            ".writeAppANCAwarenessPreset Medium",
            ".writeAppANCAwarenessPreset High",
            ".readAppANCAwarenessPreset",
            ".readAppAwarenessRawLeft",
            ".readAppAwarenessRawRight",
            ".readAppAwarenessRawSteps",
            ".AppBatteryLevel",
            ".readAppFirmwareVersion",
            ".readAppResourceVersion not Imp",
            ".writeAppOnEarDetectionWithAutoOff",
            ".AppOnEarDetectionWithAutoOff",
            ".writeAppVoicePromptEnable",
            ".readAppVoicePromptEnable",
            ".writeAppSmartButtonFeatureIndex",
            ".readAppSmartButtonFeatureIndex",
            ".startCalibration",
            ".readCalibration",
            ".writeAppGraphicEQBand",
            ".readAppGraphicEQBand",
            ".readAppGraphicEQLimits",
            ".writeAppAudioEQPreset",
            ".readAppAudioEQPreset",
            ".readAppGraphicEQBandFreq",
            ".writeAppGraphicEQPresetBandSettings",
            ".readAppGraphicEQPresetBandSettings",
            ".writeAppGraphicEQCurrentPreset",
            ".readAppGraphicEQCurrentPreset",
            ".writeAppGraphicEQPersistPreset",
            ".writeAppGraphicEQDefaultPreset",
            ".AppGraphicEQDefaultPreset",
            ".writeAppGraphicEQFactoryResetPreset",
            ".readBootImageType",
            ".enterBootloader",
            ".enterApplication",
    };

    public static final int WRITE_ANC_ENABLE_TRUE = 0;
    public static final int WRITE_ANC_ENABLE_FALSE = 1;
    public static final int READ_ANC_ENABLE = 2;
    public static final int WRITE_ANC_LEVEL = 3;
    public static final int WRITE_ANC_AWARENESS_PRESET_LOW = 4;
    public static final int WRITE_ANC_AWARENESS_PRESET_MEDIUM = 5;
    public static final int WRITE_ANC_AWARENESS_PRESET_HIGH = 6;
    public static final int READ_ANC_AWARENESS_PRESET = 7;
    public static final int READ_AWARENESS_RAW_LEFT = 8;
    public static final int READ_AWARENESS_RAW_RIGHT = 9;
    public static final int READ_AWARENESS_RAW_STEPS = 10;

    public static final int READ_APP_BATTERY_LEVEL = 11;
    public static final int READ_APP_FIRMWARE_VERSION = 12;
    public static final int READ_RESOURCE_VERSION = 13;
    public static final int WRITE_APP_ON_EAR_DETECTION_WITH_AUTO_OFF = 14;
    public static final int READ_APP_ON_EAR_DETECTION_WITH_AUTO_OFF = 15;
    public static final int WRITE_APP_VOICE_PROMPT_ENABLE = 16;
    public static final int READ_APP_VOICE_PROMPT_ENABLE = 17;
    public static final int WRITE_APP_SMART_BUTTON_FEATURE_INDEX_TRUE = 18;
    public static final int READ_APP_SMART_BUTTON_FEATURE_INDEX = 19;
    public static final int START_CALIBRATION_STATUS = 20;

    public static final int READ_CALIBRATION_STATUS = 21;
    public static final int WRITE_APP_GRAPHIC_EQ_BAND = 22;
    public static final int READ_APP_GRAPHIC_EQ_BAND = 23;
    public static final int READ_APP_GRAPHIC_EQ_LIMITS = 24;
    public static final int WRITE_APP_AUDIO_EQ_PRESET = 25;
    public static final int READ_APP_AUDIO_EQ_PRESET = 26;
    public static final int READ_APP_GRAPHIC_EQ_BAND_REQ = 27;
    public static final int WRITE_APP_GRAPHIC_EQ_PRESET_BAND_SETTINGS = 28;
    public static final int READ_APP_GRAPHIC_EQ_PRESET_BAND_SETTINGS = 29;
    public static final int WRITE_APP_GRAPHIC_EQ_CURRENT_PRESET = 30;

    public static final int READ_APP_GRAPHIC_EQ_CURRENT_PRESET = 31;
    public static final int WRITE_APP_GRAPHIC_EQ_PERSIST_PRESET = 32;
    public static final int WRITE_APP_GRAPHIC_EQ_DEFAULT_PRESET = 33;
    public static final int READ_APP_GRAPHIC_EQ_DEFAULT_PRESET = 34;
    public static final int WRITE_APP_GRAPHIC_EQ_FACTORY_RESET_PRESET = 35;
    public static final int READ_BOOT_IMAGE_TYPE = 36;
    public static final int ENTER_BOOTLOADER = 37;
    public static final int ENTER_APPLICATION = 38;

}
