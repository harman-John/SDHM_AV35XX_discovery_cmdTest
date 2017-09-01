package com.avnera.test;

/**
 * Created by Johngan on 28/08/2017.
 */

public class Constants {

    public static final byte[][] Command = {
            {//writeAppANCEnable true
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x17, (byte) 0x0F, (byte) 0x43, (byte) 0x82, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0xF0, (byte) 0x0C, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0xBB
            },
            {//writeAppANCEnable false
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x17, (byte) 0x0F, (byte) 0x43, (byte) 0x82, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0xF0, (byte) 0x0C, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0xBC
            },
            {//readAppANCEnable
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0xF0, (byte) 0x0C, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xA5
            },
            {//writeAppANCLevel

            },
            {//writeAppANCAwarenessPreset low
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x17, (byte) 0x0F, (byte) 0x43, (byte) 0x82, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x86, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x30
            },
            {//writeAppANCAwarenessPreset mid
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x17, (byte) 0x0F, (byte) 0x43, (byte) 0x82, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x86, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x30
            },
            {//writeAppANCAwarenessPreset high
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x17, (byte) 0x0F, (byte) 0x43, (byte) 0x82, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x86, (byte) 0x04,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x30
            },
            {//readAppANCAwarenessPreset
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x86, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1B
            },
            {//readAppAwarenessRawLeft
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x89, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x18

            },
            {//readAppAwarenessRawRight
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x8A, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x17

            },
            {//readAppAwarenessRawSteps
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x8B, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x16

            },
            {//battery level
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xB0, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xF1

            },
            {//firmware version
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x87, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xB0, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1A

            },
            {// resource version

            },
            {//writeAppOnEarDetectionWithAutoOff
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0xB6 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x01 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x01
            },
            {//AppOnEarDetectionWithAutoOff
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xB6, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xEB
            },
            {//writeAppVoicePromptEnable
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0xB7 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x01 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00
            },
            {//readAppVoicePromptEnable
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xB7, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xEA

            },
            {//writeAppSmartButtonFeatureIndex
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0xB5 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x01 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x02

            },
            {//readAppSmartButtonFeatureIndex
                    (byte) 0xFF, (byte) 0x5A, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x23, (byte) 0x0F, (byte) 0x43, (byte) 0x81, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xB5, (byte) 0x10,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xEC

            },
            {//startCallibration
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0xB2 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x01 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x06
            },
            {//stopCallibration
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x1F , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF3 , (byte) 0x03 , (byte) 0x0C ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x02 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x06 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0xA6
            },
            {//writeAppGraphicEQBand
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x1F , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF3 , (byte) 0x03 , (byte) 0x0C ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x02 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x06 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0xA6
            },
            {//readAppGraphicEQBand
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x1F , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF3 , (byte) 0x03 , (byte) 0x0C ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x01 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0xAE
            },
            {//readAppGraphicEQLimits
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x23 , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF1 , (byte) 0x90 , (byte) 0x10 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x20
            },
            {//writeAppAudioEQPreset

            },
            {//readAppAudioEQPreset
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x23 , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x81 , (byte) 0x10 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x20
            },
            {//readAppGraphicEQBandFreq
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x3F , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF3 , (byte) 0x04 , (byte) 0x2C ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x0A , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x68

            },
            {//writeAppGraphicEQPresetBandSettings
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x43 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF1 , (byte) 0x94 , (byte) 0x30 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x0A , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x05 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x05 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x05 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x05 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x05 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x05 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x05 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x05 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x05 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x05 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x9B
            },
            {//readAppGraphicEQPresetBandSettings
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x43 , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF1 , (byte) 0x94 , (byte) 0x30 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x0A , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0xCE

            },

            {//writeAppGraphicEQCurrentPreset
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF1 , (byte) 0x92 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x31
            },
            {//readAppGraphicEQCurrentPreset
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x23 , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0xF1 , (byte) 0x92 , (byte) 0x10 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x1E

            },
            {//writeAppGraphicEQPersistPreset
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x96 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x1E
            },
            {//writeAppGraphicEQDefaultPreset
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x91 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x23

            },
            {//AppGraphicEQDefaultPreset
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x23 , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x91 , (byte) 0x10 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x10

            },
            {//writeAppGraphicEQFactoryResetPreset
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x17 , (byte) 0x0F , (byte) 0x43 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x98 , (byte) 0x04 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x04 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x1c

            },
            {//readBootImageType
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x23 , (byte) 0x0F , (byte) 0x43 , (byte) 0x81 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x20 , (byte) 0x10 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x84
            },
            {//enterBootloader
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x23 , (byte) 0x0F , (byte) 0x40 , (byte) 0x82 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x25 , (byte) 0x10 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x7E
            },
            {//enterApplication
                    (byte) 0xFF , (byte) 0x5A , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 ,
                    (byte) 0x13 , (byte) 0x0F , (byte) 0x41 , (byte) 0x03 , (byte) 0x00 ,
                    (byte) 0x7E , (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0x01 ,
                    (byte) 0x00 , (byte) 0x00 , (byte) 0x00 , (byte) 0xC2
            },

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
