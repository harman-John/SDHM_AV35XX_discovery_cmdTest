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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.avnera.smartdigitalheadset.ANCAwarenessPreset;
import com.avnera.smartdigitalheadset.GraphicEQPreset;
import com.avnera.smartdigitalheadset.Bluetooth;
import com.avnera.smartdigitalheadset.BluetoothSocketWrapper;
import com.avnera.smartdigitalheadset.Command;
import com.avnera.smartdigitalheadset.Debug;
import com.avnera.smartdigitalheadset.LightX;
import com.avnera.smartdigitalheadset.Log;
import com.avnera.smartdigitalheadset.ModuleId;
import com.avnera.smartdigitalheadset.Utility;
import com.avnera.test.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity implements
		Bluetooth.Delegate,
		LightX.Delegate,
		Log.Delegate, View.OnClickListener, Spinner.OnItemSelectedListener
{
	private Button						mButtonQuit;
	private Bluetooth					mBluetooth;
	private BluetoothDevice				mBluetoothDevice;
	private int							mFirmwareBytesVerified;
	private byte[]						mFirmwareData;
	private LightX						mLightX;

	private String[] eqSettings = { "Select Eq Setting", "Off", "Jazz", "Vocal", "Bass" };
	public static int maxLimit = 10, minLimit = -10;
	private Spinner spin;
	private final int					kSizeofUInt32 = 4;
	int				major, minor, revision;
	// state tracking for anc-enable demo code
	int									mANCEnableDemoState;

	boolean								mANCEnabled;

	// AppGraphicEQLimits
	int									mGraphicEQLimitNumBands;
	int									mGraphicEQLimitNumSettings;
	int									mGraphicEQLimitSettingsMax;
	int									mGraphicEQLimitSettingsMin;

	// Sensor Status
	boolean								mMotionSensorPresent;
	boolean								mProximitySensorPresent;
    private boolean mClicked;
	private TextView mHeadphoneName, mHeadphoneConnected;
	private Button syncData;
	private TextView batteryPercentValue;
	private TextView firmwareVersionValue;
	private TextView leftAwarnessValue, rightAwarnessValue, rawAwarnessValue, ancValue;
	private TextView txtCurrentEQ;
	private TextView defaultANCValue;
	private Button setCustomANC;
	private SeekBar rightAWSeekBar, leftAWSeekbar;
    private SeekBar khz_16_sb;
    private SeekBar khx_8_sb;
    private SeekBar khz_4_sb;
    private SeekBar khz_2_sb;
    private SeekBar khz_1_sb;
    private SeekBar khz_500_sb;
    private SeekBar khz_250_sb;
    private SeekBar khz_125_sb;
    private SeekBar khz_64_sb;
    private SeekBar khz_32_sb;
    private int globalVal;
	private RelativeLayout relativeLayout;
	private ListView listView;
	private Button buttonGoFunc;

    @Override
	protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
		switch ( requestCode ) {
			case Bluetooth.REQUEST_ENABLE_BT: {
				if ( resultCode == RESULT_CANCELED ) {
					showExitDialog( "Unable to enable Bluetooth." );
				} else {
					mBluetooth.discoverBluetoothDevices();
				}
			}
			break;
		}
	}

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		Log.sLogDelegate = this;
		Log.d(String.format("main oncreate"));
		initButtons();

		//Creating the ArrayAdapter instance having the country list
		ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,eqSettings);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//Setting the ArrayAdapter data on the Spinner
		spin.setAdapter(aa);

		LightX.sEnablePacketDumps = true;
		Log.d(String.format("main oncreate 1"));
		startDiscovery();
		Log.d(String.format("main oncreate 2"));
	}

	private void startDiscovery(){
		try {
			mBluetooth = new Bluetooth( this, this, true );
			mBluetooth.start();
		} catch ( IOException e ) {
			showExitDialog( "Unable to enable Bluetooth." );
		}
	}

	private void stopDiscovery(){
		if (mBluetooth != null) {
			mBluetooth.close();
			mBluetooth = null;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	protected void initButtons() {
		relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayoutdigital);
		listView = (ListView)findViewById(R.id.listviewdigital);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (mLightX != null){
					FunctionTestSDHM.getInstance().goFunction(position);
				}
			}
		});
//		listView.setAdapter(new ArrayAdapter<String>(this,R.layout.listview_item, FunctionTestSDHM.getInstance().initFunctionList()));
		listView.setVisibility(View.GONE);
		spin = (Spinner) findViewById(R.id.spinner1);
		spin.setOnItemSelectedListener(MainActivity.this);
		buttonGoFunc = (Button)findViewById(R.id.buttonGoFunc);
		buttonGoFunc.setOnClickListener(this);
		mButtonQuit = (Button) findViewById( R.id.buttonQuit );
		setCustomANC = (Button) findViewById( R.id.set_anc_eq );
		setCustomANC.setOnClickListener(this);
		syncData = (Button) findViewById( R.id.syncData );
		syncData.setOnClickListener(this);
		mHeadphoneName = (TextView) findViewById(R.id.modelName);
		defaultANCValue = (TextView) findViewById(R.id.default_anc_value);
		txtCurrentEQ = (TextView) findViewById(R.id.eq_value);
		batteryPercentValue = (TextView) findViewById(R.id.percentValue);
		firmwareVersionValue = (TextView) findViewById(R.id.firmwareVersionValue);
		leftAwarnessValue = (TextView) findViewById(R.id.leftAwarnessValue);
		rightAwarnessValue = (TextView) findViewById(R.id.rightAwarnessValue);
		rawAwarnessValue = (TextView) findViewById(R.id.rawAwarnessValue);
		ancValue = (TextView) findViewById(R.id.ancValue);
		mHeadphoneConnected = (TextView) findViewById(R.id.modelNameConnected);
		mButtonQuit.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				quit();
			}
		} );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.menu_main, menu );
		return true;
	}

	@Override
	protected void onDestroy() {
		if ( mLightX != null ){
			mLightX.close();
		}

		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if ( id == R.id.action_settings ) {
			return true;
		}

		return super.onOptionsItemSelected( item );
	}

	// Members and methods to support Avnera hardware

	public void connect( BluetoothDevice device ) {
		try {
			mBluetooth.connect( device );
		} catch ( IOException e ) {
			Log.e("MainActivity11 " + mBluetooth.deviceName( device ) + " connect() failed: " + e.getLocalizedMessage() );
		}
	}

	public void log( Log.Level level, String tag, String message ) {
		switch ( level ) {
			case Debug:
				android.util.Log.i( tag, message );
				break;
			case Error:
				android.util.Log.e( tag, message );
				break;
			case Info:
				android.util.Log.i( tag, message );
				break;
			case Verbose:
				android.util.Log.v( tag, message );
				break;
			case Warning:
				android.util.Log.w( tag, message );
				break;
		}
	}

	// General Android Utils

	public void quit() {
        if(mClicked){
            mClicked = false;
        }
        else {
            mClicked = true;
        }
        if (mLightX != null)
        	mLightX.writeAppANCEnable(mClicked);

//		android.os.Process.killProcess( android.os.Process.myPid() );
//		System.exit( 0 );
	}

	public byte[] readRawResource( int rawResourceId ) {
		byte[]						buffer;
		InputStream					inputStream;
		int							read;
		ByteArrayOutputStream	outputStream;

		buffer = new byte[ 4096 ];
		inputStream = getResources().openRawResource( rawResourceId );
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

	public void showExitDialog( String message ) {
		AlertDialog.Builder			builder;

		builder = new AlertDialog.Builder( this );

		builder.setMessage( message )
				.setCancelable( false )
				.setPositiveButton( "Exit", new DialogInterface.OnClickListener() {
					public void onClick( DialogInterface dialog, int id ) {
						quit();
					}
				} );

		builder.create().show();
	}

	// Bluetooth Delegate

	@Override
	public void bluetoothAdapterChangedState( Bluetooth bluetooth, int currentState, int previousState ) {
		Log.d("MainActivity11 bluetoothAdapterChangedState");
		if ( currentState != BluetoothAdapter.STATE_ON ) {
			Log.e("MainActivity11 " + "The Bluetooth adapter is not enabled, cannot communicate with LightX device" );

			// Could ask the user if it's ok to call bluetooth.enableBluetoothAdapter() here, otherwise abort
		}
	}

	@Override
	public void bluetoothDeviceBondStateChanged( Bluetooth bluetooth, BluetoothDevice bluetoothDevice, int currentState, int previousState ) {
		Log.d("MainActivity11 bluetoothDeviceBondStateChanged");
		if ( currentState == BluetoothDevice.BOND_BONDED ) {
			Log.d( bluetooth.deviceName( bluetoothDevice ) + " bonded, initiating connect" );
            if (bluetoothDevice.getName().toLowerCase().contains("150") ||
                    bluetoothDevice.getName().toLowerCase().contains("av35") ||
                    bluetoothDevice.getName().toLowerCase().contains("akg")){
                if (mLightX != null) {
                    mLightX.close();
                    mLightX = null;
                }
//				stopDiscovery();
//                mBluetooth.close();
                openElite();
                finish();
                return;
            }
			connect( bluetoothDevice );
		} else if ( currentState == BluetoothDevice.BOND_BONDING && previousState == BluetoothDevice.BOND_BONDED ||
				    currentState == BluetoothDevice.BOND_NONE && previousState == BluetoothDevice.BOND_BONDING )
		{
			// This can happen when a LightX device that is paired has its firmware flashed (in which case it
			// forgets about the Android device), or the user intentionally unpairs with the device from the
			// Android settings.  In the first case, we want to automatically attempt to re-pair, in the second
			// case we should not re-pair, so this is a good place to ask the user if he/she wants to try to
			// re-pair.  I'm just going to automatically re-pair here by simulating a device discovered event.

			Log.d("MainActivity11 Received unpair event, attempting to re-pair" );
			bluetoothDeviceDiscovered( bluetooth, bluetoothDevice );
		}
	}

	@Override
	public void bluetoothDeviceConnected( Bluetooth bluetooth,final BluetoothDevice bluetoothDevice, BluetoothSocket bluetoothSocket ) {
		Log.d("MainActivity11 bluetoothDeviceConnected");
		if ( mLightX != null && mLightX.getSocket().equals( bluetoothSocket ) ) {
			Log.d("MainActivity11 bluetoothDeviceConnected() received for extant LightX/socket pair.  Ignoring." );
			return;
		}
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mHeadphoneName.setText(bluetoothDevice.getName());
				mHeadphoneConnected.setText("(Connected)");

			}
		});
		try {
			Log.d( bluetooth.deviceName( bluetoothDevice ) + " connected, instantiating LightX interface" );

			if ( mLightX != null ) mLightX.close();

			mBluetoothDevice = bluetoothDevice;
			mLightX = new LightX( ModuleId.Bluetooth, this, new BluetoothSocketWrapper( bluetoothSocket ) );

			// two execution paths are possible here:

			/*/
				// 1.  you are developing and want to put the LightX device in bootloader mode
				//
				// call readBootImageType() to get the bootloader execution state.
				// lightXIsInBootloader() will be called.
				mLightX.readBootImageType();
			/*/
				// 2.  you are compiling for the end-user/consumer
				//
				// assume application mode and do something
//				doSomethingWhileInApplicationMode();
			/**/

		} catch ( Exception e ) {
			// TODO: notify user in some way
			Log.e("MainActivity11 " + "Unable to create LightX handler for " + bluetooth.deviceName( bluetoothDevice ) + ": " + e.getLocalizedMessage() );
		}
	}

	@Override
	public void bluetoothDeviceDisconnected( Bluetooth bluetooth, BluetoothDevice bluetoothDevice, boolean b) {
		Log.d("MainActivity11 bluetoothDeviceDisconnected");
//		if ( willAutoReconnect ) {
			Log.d( bluetooth.deviceName( bluetoothDevice ) + " recoverable disconnect -- will auto-reconnect when possible." );
//		} else {
			Log.d( bluetooth.deviceName( bluetoothDevice ) + " unrecoverable disconnect -- attempting to reconnect." );
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mHeadphoneConnected.setText("Disconnected");
			}
		});

			// TODO: This may be received if the user manually disconnects, in that case, don't reconnect
//			connect( bluetoothDevice );
//		}
	}

	@Override
	public void bluetoothDeviceDiscovered( Bluetooth bluetooth, BluetoothDevice bluetoothDevice ) {
		String						name;

		// TODO: better device selection logic than hardcoding a MAC address
//		final String				kAvneraHardwareAddress = "00:17:E9:4F:15:3C";
		final String				kAvneraHardwareAddress = "98:5D:AD:12:28:B6";
		Log.d("MainActivity11 bluetoothDeviceDiscovered DeviceName = "+bluetoothDevice.getName());
		// TODO: write device-selection UI
        if(bluetoothDevice.getName().toLowerCase().contains("150") ){
            if (mLightX != null) {
                mLightX.close();
                mLightX = null;
            }
            openElite();
            finish();
			stopDiscovery();
        }
//		else if ( bluetoothDevice.getAddress().toUpperCase().equals( kAvneraHardwareAddress ) ) {
		else{
			name = bluetooth.deviceName( bluetoothDevice );
			mHeadphoneName.setText(name);
			Log.d("MainActivity11 Found LightX device \"" + name + "\", bond state is " + Bluetooth.bondStateDescription( bluetoothDevice.getBondState() ) );

			bluetooth.cancelDiscovery();

			switch ( bluetoothDevice.getBondState() ) {
				case BluetoothDevice.BOND_BONDED: {
					connect( bluetoothDevice );
				} break;

				default: {
					bluetooth.pair( bluetoothDevice );
				} break;
			}
		}
	}

    private void openElite() {
		Log.d("MainActivity11 start Elite150Activity");
        Intent intent  = new Intent(MainActivity.this, Elite150Activity.class);
        startActivity(intent);
    }

    // LightX App Delegate

	public void updateView(final TextView tv, final String value){
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tv.setText(value);
			}
		});
	}
	@Override
	public void lightXAppReadResult( LightX lightX, Command command, boolean success, byte[] buffer ) {
		boolean						boolValue;
		int							i, intValue, offset;
		final long						unsignedIntValue;
		Log.d("MainActivity11 lightXAppReadResult");
		if ( success ) {
			switch ( command ) {
				case AppANCEnable: {
					if ( mANCEnableDemoState > 0 ) {
						// only do this if running ANC-enable demo
						mANCEnabled = Utility.getBoolean( buffer, 0 );
						this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ancValue.setText(" "+mANCEnabled);
							}
						});
//						advanceANCEnableDemo();
						showDialog(command.value(),success,String.valueOf(mANCEnabled));
					}

				} // fall through

				// of course we could cache all of these as member variables too...
				case AppIsHeadsetOn:
				case AppOnEarDetectionWithAutoOff:
				case AppSmartButtonFeatureIndex:
				case AppVoicePromptEnable:
				{
					final boolean boolValue1 = Utility.getBoolean( buffer, 0 );
					Log.d( command + " is " + ( boolValue1 ? "enabled" : "disabled" ) );
					this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ancValue.setText(" "+ (boolValue1 ? "enabled" : "disabled") );
						}
					});
					showDialog(command.value(),success,String.valueOf(ancValue));
				} break;

				case AppANCAwarenessPreset: {
					intValue = Utility.getInt( buffer, 0 );
					Log.d( command + " is " + ANCAwarenessPreset.from( intValue ) );

						switch (intValue) {
							case 0:
								updateView(defaultANCValue, "NONE");
								break;
							case 1: //ANCAwarenessPreset.Low
								updateView(defaultANCValue, "LOW");

								break;
							case 2: //ANCAwarenessPreset.Medium
								updateView(defaultANCValue, "MEDIUM");

								break;
							case 3://ANCAwarenessPreset.High
								updateView(defaultANCValue, "HIGH");

								break;
						}
					}
				showDialog(command.value(),success,String.valueOf(intValue));

				 break;

				case AppAudioEQPreset: {
					intValue = Utility.getInt( buffer, 0 );
					Log.d( command + " is " + GraphicEQPreset.from( intValue ) );
					showDialog(command.value(),success,String.valueOf(intValue));
				} break;

				case AppAwarenessRawLeft:
					unsignedIntValue = Utility.getUnsignedInt( buffer, 0 );
					final long leftVal = unsignedIntValue;
					this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							leftAwarnessValue.setText(" "+leftVal);
						}
					});
					showDialog(command.value(),success,String.valueOf(unsignedIntValue));
					break;
				case AppAwarenessRawRight:
					unsignedIntValue = Utility.getUnsignedInt( buffer, 0 );
					final long rightVal = unsignedIntValue;

					this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							rightAwarnessValue.setText(""+rightVal);
						}
					});
					showDialog(command.value(),success,String.valueOf(unsignedIntValue));
					break;
				case AppAwarenessRawSteps:
					unsignedIntValue = Utility.getUnsignedInt( buffer, 0 );
					final long rawVal = unsignedIntValue;

					this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							rawAwarnessValue.setText(""+rawVal);
						}
					});
					showDialog(command.value(),success,String.valueOf(unsignedIntValue));
					break;
				case AppGraphicEQCurrentPreset:
				{
					unsignedIntValue = Utility.getUnsignedInt( buffer, 0 );
					Log.d( command + " is " + unsignedIntValue );

					switch ((int) unsignedIntValue) {
						case 0:
							updateView(txtCurrentEQ, "OFF" );
							break;
						case 1:
							updateView(txtCurrentEQ, "JAZZ" );
							break;
						case 2:
							updateView(txtCurrentEQ, "VOCAL" );
							break;
						case 3:
							updateView(txtCurrentEQ, "BASS" );
							break;
						case 4:
							updateView(txtCurrentEQ, "CUSTOM EQ IS PRESENT" );
//							String name = JBLPreferenceUtil.getString(EQSettingManager.EQKeyNAME, getActivity(), null);
//							if (name != null) {
//								txtCurrentEQ.setText(name);
//								if (txtCurrentEQ.getText().length() >= JBLConstants.MAX_MARQUEE_LEN) {
//									txtCurrentEQ.setSelected(true);
//									txtCurrentEQ.setMarqueeRepeatLimit(-1);
//								}
//							}
							break;
					}

					showDialog(command.value(),success,String.valueOf(unsignedIntValue));

				} break;

				case AppBatteryLevel: {
					unsignedIntValue = Utility.getUnsignedInt( buffer, 0 );
					Log.d( command + " is " + unsignedIntValue + "%" );
					final long value = unsignedIntValue;
					this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							batteryPercentValue.setText(""+value + " %");

						}
					});

					showDialog(command.value(),success,String.valueOf(unsignedIntValue) +"%");
				} break;

				case AppFirmwareVersion: {
					major = buffer[ 0 ];
					minor = buffer[ 1 ];
					revision = buffer[ 2 ];
					this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							firmwareVersionValue.setText(major+"." + minor + "." + revision);
						}
					});
					Log.d( String.format( "App firmware version is %d.%d.%d", major, minor, revision ) );
					showDialog(command.value(),success,String.format( "App firmware version is %d.%d.%d", major, minor, revision ));
				} break;

				case AppGraphicEQBand: {
					int				preset = Utility.getInt( buffer, 0 );
					int				band = Utility.getInt( buffer, 4 );
					int				value = Utility.getInt( buffer, 8 );

					Log.d( String.format( "graphic eq preset %d, band: %d value: %d", preset, band, value ) );
				} break;

				case AppGraphicEQBandFreq: {
					int				numBands = Utility.getInt( buffer, 0 );

					for ( i = 0, offset = kSizeofUInt32; i < numBands; ++i, offset += kSizeofUInt32 ) {
						long unsignedIntValue1 = Utility.getUnsignedInt( buffer, offset );
						Log.d( String.format( "graphic eq band freq %d: %d", i, unsignedIntValue1 ) );
					}
				} break;

				case AppGraphicEQLimits: {
					mGraphicEQLimitNumBands = Utility.getInt( buffer, 0 );
					mGraphicEQLimitNumSettings = Utility.getInt( buffer, 4 );
					mGraphicEQLimitSettingsMin = Utility.getInt( buffer, 8 );
					mGraphicEQLimitSettingsMax = Utility.getInt( buffer, 12 );

					Log.d( String.format( "graphic eq limits, num bands: %d, num settings: %d, settings min: %d, settings max: %d", mGraphicEQLimitNumBands, mGraphicEQLimitNumSettings, mGraphicEQLimitSettingsMin, mGraphicEQLimitSettingsMax ) );

					getGraphicEQValues();
				} break;

				case AppGraphicEQPresetBandSettings: {
					int				preset = Utility.getInt( buffer, 0 );
					int				numBands = Utility.getInt( buffer, 4 );

					for ( i = 0, offset = 2 * kSizeofUInt32; i < numBands; ++i, offset += kSizeofUInt32 ) {
						long unsignedIntValue2 = Utility.getUnsignedInt( buffer, offset );
						Log.d( String.format( "graphic eq preset %d band %d setting: %d", preset, i, unsignedIntValue2 ) );
					}
				} break;

				case AppSensorStatus: {
					unsignedIntValue = Utility.getUnsignedInt( buffer, 0 );

					mMotionSensorPresent = ( unsignedIntValue & 1 ) != 0;
					mProximitySensorPresent = ( unsignedIntValue & 2 ) != 0;

					if ( mMotionSensorPresent ) {
						mLightX.readAppTapDebounce();
						mLightX.readAppTapIdleDebounce();
						mLightX.readAppTapPulseWindow();
						mLightX.readAppTapThresholdHigh();
						mLightX.readAppTapThresholdLow();
						mLightX.readAppTapTimeout();
					}
 				} break;

				default: {
					Log.d("MainActivity11 received reply to " + command + " command" );
					showDialog(command.value(),success,(buffer == null ? "null " : HexHelper.encodeHexStr(buffer)));
				} break;
			}
		} else {
			switch (command) {
				case AppGraphicEQCurrentPreset:
					updateView(txtCurrentEQ,"EQ OFF / Custom EQ");
					break;
				case AppANCEnable:
//					boolean anc = Utility.getBoolean(buffer, 0);
			}
			Log.e("MainActivity11 " + "read command " + command + " failed" );
			showDialog(command.value(),success,(buffer == null ? "null " : HexHelper.encodeHexStr(buffer)));
		}
	}

    public void AncOff(View view){
        if (mLightX != null){
            mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.None);
        }
    }
    public void AncLow(View view){
        if (mLightX != null){
            mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.Low);
        }
    }
    public  void  TurnEqOff(View view){
		if (mLightX != null){
			mLightX.writeAppGraphicEQCurrentPreset(GraphicEQPreset.Off);
		}
	}

	public int getActualProgress(int progress){
		progress = progress - 50;
		if (progress > 0) {
			int steps = 50 / maxLimit;
			progress = progress / steps;
		} else if (progress < 0) {
			int steps = 50 / Math.abs(minLimit);
			progress = progress / steps;
		} else
			progress = 0;
		return  progress;
	}

	public void setCustomEQ1(View view) {
        Log.d("MainActivity11 HARMAN_TAG Enter Bootloader() ---> ");
        if (mLightX != null)
		    mLightX.readBootImageType();
//		    startWritingFirmware(3);
//			mLightX.enterBootloader();
	}
		public void setCustomEQ(View view){
//		if (mLightX != null){
//			mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.High);
//		}
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_set_custom_eq, null);
        dialogBuilder.setView(dialogView);

        khz_16_sb = (SeekBar) dialogView.findViewById( R.id.khz_16_sb );
        khz_16_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        khx_8_sb = (SeekBar) dialogView.findViewById( R.id.khx_8_sb );

        khx_8_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        khz_4_sb = (SeekBar) dialogView.findViewById( R.id.khz_4_sb );

        khz_4_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        khz_2_sb = (SeekBar) dialogView.findViewById( R.id.khz_2_sb );

        khz_2_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        khz_1_sb = (SeekBar) dialogView.findViewById( R.id.khz_1_sb );

        khz_1_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        khz_500_sb = (SeekBar) dialogView.findViewById( R.id.khz_500_sb );

        khz_500_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        khz_250_sb = (SeekBar) dialogView.findViewById( R.id.khz_250_sb );

        khz_250_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        khz_125_sb = (SeekBar) dialogView.findViewById( R.id.khz_125_sb );

        khz_125_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        khz_64_sb = (SeekBar) dialogView.findViewById( R.id.khz_64_sb );

        khz_64_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        khz_32_sb = (SeekBar) dialogView.findViewById( R.id.khz_32_sb );

        khz_32_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//				EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
//				editText.setText("test label");
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    public void AncMedium(View view){
        if (mLightX != null){
            mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.Medium);
        }
    }
    public void AncHigh(View view){
        if (mLightX != null){
            mLightX.writeAppANCAwarenessPreset(ANCAwarenessPreset.High);
        }
    }
	@Override
	public void lightXAppReceivedPush( LightX lightX, Command command, byte[] data ) {
		Log.d("MainActivity11 LightX instance sent push command " + command + ", buffer " + ( data == null ? "is null" : "contains " + data.length + " bytes" ) );
	}

	@Override
	public void lightXAppWriteResult( LightX lightX, Command command, boolean success ) {
		Log.d("MainActivity11 write " + command + " command " + ( success ? "succeeded" : "failed" ) );
		showDialog(command.value(),( success == true ? "WRITE SUCCESS" : "WRITE FAIL"),"null");
//		if ( success ) {
//			switch ( command ) {
//				case AppANCAwarenessPreset: {
//					// confirm the value written with a read
//					mLightX.readAppANCAwarenessPreset();
//				} break;
//
//				case AppGraphicEQBand: {
//					// see call to writeAppGraphicEQBand below
//					mLightX.readAppGraphicEQBand( GraphicEQPreset.User, 1 );
//				} break;
//			}
//		}
	}

	// LightX Delegate

	@Override
	public void lightXError( LightX lightX, Exception exception ) {
		Log.e("MainActivity11 " + "LightX error: " + exception.getLocalizedMessage() );
        updateView(mHeadphoneConnected, "Light X Error");
		showDialog(0,false,"Light X Error, Device Disconnected");
		lightX.close();
		mLightX = null;
//		connect( mBluetoothDevice );
	}

	@Override
	public boolean lightXFirmwareReadStatus( LightX lightX, LightX.FirmwareRegion region, int offset, byte[] buffer) {
		int i, n;
		for ( i = 0, n = buffer.length; i < n; ++i, ++offset, ++mFirmwareBytesVerified ) {
			if ( buffer[ i ] != mFirmwareData[ offset ] ) {
				Log.e("MainActivity11 " + "verification failed at offset " + offset );
				return true;
			}
		}

		if ( mFirmwareBytesVerified == mFirmwareData.length ) {
			Log.d("MainActivity11 firmware region " + region + " verified 100% ok" );
		} else {
			Log.d( String.format( "firmware region verified %.02f%% ok", (double) mFirmwareBytesVerified / (double) mFirmwareData.length * 100.0 ) );
		}

		return false;
	}

	@Override
	public boolean lightXFirmwareWriteStatus( LightX lightX, LightX.FirmwareRegion firmwareRegion, LightX.FirmwareWriteOperation firmwareWriteOperation, double progress, Exception exception ) {
		Log.d("MainActivity11 HARMAN_TAG lightXFirmwareWriteStatus() ---> ");

		if ( exception != null ) {
			Log.d( String.format( "%s firmware %s exception: %s", firmwareWriteOperation, firmwareRegion, exception.getLocalizedMessage() ) );
//            return false;
		} else {
            switch (firmwareWriteOperation){
                case Erase:
                    Log.d( String.format( "HARMAN_TAG ERASING....." ) );
                case Verify:
                    Log.d( String.format( "HARMAN_TAG Verify....." ) );
                case Write:
                    Log.d( String.format( "HARMAN_TAG %s firmware %s: %.02f%%", firmwareWriteOperation, firmwareRegion, progress * 100.0 ) );
                case Checksum:
                    Log.d( String.format( "HARMAN_TAG Checksum....." ) );
                    break;
                case Complete:
                    Log.d( String.format( "HARMAN_TAG %s firmware %s: %.02f%%", firmwareWriteOperation, firmwareRegion, progress * 100.0 ) );
                    if (globalVal == 3){
                        this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startWritingFirmware(2);
                            }
                        });
                    }
                    break;
                default:
                    Log.d( String.format( "HARMAN_TAG Default case" ) );
                    return false;
            }
		}
        return false;
	}

    public void startWritingFirmware(int val) {
            globalVal = val;
            byte[] data;
            switch (val) {
                case 2: {
                    data = readRawResource( R.raw.sdhm_d0_bluetooth_rsrc2_cfs );
                    Log.d("MainActivity11 first 1024 bytes of firmware to write:\n" + Debug.hexify( data, 0, 1024 ) );
                    mLightX.writeFirmware( LightX.FirmwareRegion.Resource, data  );
                } break;

                // write the application region
                case 3: {
                    data = readRawResource( R.raw.sdhm_d0_bluetooth_cfs );
                    Log.d("MainActivity11 HARMAN_TAG first 1024 bytes of firmware to write:\n" + Debug.hexify( data, 0, 1024 ) );
                    mLightX.writeFirmware( LightX.FirmwareRegion.Application, data );
                } break;

                case 0:
                    startWritingFirmware(3);
                    break;
            }
    }

	@Override
	public void lightXIsInBootloader( LightX lightX, boolean isInBootloader ) {
		Log.d("MainActivity11 HARMAN_TAG lightXIsInBootloader() in bootloader: " + isInBootloader );

		if ( isInBootloader ) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
					Log.d("MainActivity11 HARMAN_TAG lightXIsInBootloader() --> Writing Firmware"  );
					startWritingFirmware(3);
                }
            });
//			doSomethingWhileInBootloaderMode();
		} else {
            if (mLightX != null) {
                mLightX.enterBootloader();
//            lightX.enterApplication();
                mLightX.readBootImageType();
            }
		}
	}

	@Override
	public void lightXReadConfigResult( LightX lightX, Command command, boolean success, String value ) {
		if ( success ) {
			Log.d("MainActivity11 config string for " + command + ": " + value );
		} else {
			Log.e("MainActivity11 " + "failed to read config for " + command );
		}
	}

    @Override
    public boolean lightXWillRetransmit(LightX lightX, Command command) {
        return false;
    }

    // Examples of things you can do once connected:

	public void doSomethingWhileInApplicationMode() {
		int							codeToRun = 6;        // select a test case

	}

	public void advanceANCEnableDemo() {
		switch ( mANCEnableDemoState++ ) {
			// request the ANC Enabled state (see lightXReceivedAppBoolean for response handling)
			case 0: {
				mLightX.readAppANCEnable();
			} break;

			// toggle the state and read it again (do this twice to reset the state)
			case 1:
			case 2: {
				mLightX.writeAppANCEnable( !mANCEnabled );
				mLightX.readAppANCEnable();
			} break;

			default: {
				// all done
				mANCEnableDemoState = 0;	// reset
			} break;
		}
	}

	public void getGraphicEQValues() {
		int							i, j;

		// call readAppGraphicEQLimits() first
		for ( i = GraphicEQPreset.First.value(); i <= GraphicEQPreset.Last.value(); ++i ) {
			for ( j = 0; j < mGraphicEQLimitNumBands; ++j ) {
				mLightX.readAppGraphicEQBand( GraphicEQPreset.from( i ), j );
			}
		}

		mLightX.readAppGraphicEQBandFreq();
		mLightX.readAppGraphicEQCurrentPreset();

		for ( i = GraphicEQPreset.First.value(); i <= GraphicEQPreset.Last.value(); ++i ) {
			mLightX.readAppGraphicEQPresetBandSettings( GraphicEQPreset.from( i ) );
		}
	}

	// Here are examples of how to read and write firmware regions.  The LightX hardware
	// must be in authenticated bootloader mode (i.e. enterBootloader() succeeded) for any
	// of these to work.
	public void doSomethingWhileInBootloaderMode() {
		int							codeToRun = -1;        // select a test case
		byte[]						data;

		switch ( codeToRun ) {
			// verify resource region
			case 0: {
				mFirmwareBytesVerified = 0;
				mFirmwareData = readRawResource( R.raw.sdhm_d0_bluetooth_rsrc2_cfs );
				mLightX.readFirmware( LightX.FirmwareRegion.Resource, 0, mFirmwareData.length );
			} break;

			// verify application region
			case 1: {
				mFirmwareBytesVerified = 0;
				mFirmwareData = readRawResource( R.raw.sdhm_d0_bluetooth_cfs );
				mLightX.readFirmware( LightX.FirmwareRegion.Application, 0, mFirmwareData.length );
			} break;

			// write the resource region
			case 2: {
				data = readRawResource( R.raw.sdhm_d0_bluetooth_rsrc2_cfs );
				Log.d("MainActivity11 first 1024 bytes of firmware to write:\n" + Debug.hexify( data, 0, 1024 ) );
				mLightX.writeFirmware( LightX.FirmwareRegion.Resource, data  );
			} break;

			// write the application region
			case 3: {
				data = readRawResource( R.raw.sdhm_d0_bluetooth_cfs );
				Log.d("MainActivity11 first 1024 bytes of firmware to write:\n" + Debug.hexify( data, 0, 1024 ) );
				mLightX.writeFirmware( LightX.FirmwareRegion.Application, data );
			} break;

			default: {
				Log.d("MainActivity11 The LightX device is in bootloader mode!" );
			} break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.syncData:
				if (mLightX != null){
					mLightX.readApp(Command.AppBatteryLevel);
					mLightX.readAppFirmwareVersion();
					mLightX.readAppANCEnable();
					mLightX.readAppANCAwarenessPreset();
					mLightX.readAppAwarenessRawLeft();
					mLightX.readAppAwarenessRawRight();
					mLightX.readAppAwarenessRawSteps();
					mLightX.readAppGraphicEQCurrentPreset();
				}
				break;
			case R.id.set_anc_eq:
//				}
//				i.putExtra("BUNDLE", bundle);
//				startActivity(i);
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
				LayoutInflater inflater = this.getLayoutInflater();
				View dialogView = inflater.inflate(R.layout.activity_set_awarness, null);
				dialogBuilder.setView(dialogView);
				rightAWSeekBar = (SeekBar) dialogView.findViewById( R.id.right_aw_seekbar );
				leftAWSeekbar = (SeekBar) dialogView.findViewById( R.id.left_aw_seekbar );
				rightAWSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
						if (mLightX != null)
                                mLightX.writeAppWithUInt32Argument(Command.AppAwarenessRawRight, (long) i);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
				});

				leftAWSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
						if (mLightX != null)
							mLightX.writeAppWithUInt32Argument(Command.AppAwarenessRawLeft, (long) i);

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
				});
//				EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
//				editText.setText("test label");
				AlertDialog alertDialog = dialogBuilder.create();
				alertDialog.show();
				break;
			case R.id.buttonGoFunc:
				if (relativeLayout.isShown()) {
					relativeLayout.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}else{
					relativeLayout.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				}
				break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if (mLightX == null){
			return;
		}
		switch (i){
			case 0:
				break;
			case 1:
				mLightX.writeAppGraphicEQCurrentPreset(GraphicEQPreset.Off);
				break;
			case 2:
				mLightX.writeAppGraphicEQCurrentPreset(GraphicEQPreset.Jazz);
				break;
			case 3:
				mLightX.writeAppGraphicEQCurrentPreset(GraphicEQPreset.Vocal);
				break;
			case 4:
				mLightX.writeAppGraphicEQCurrentPreset(GraphicEQPreset.Bass);
				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
	private void showDialog(final int commandid, final String success, final String buffer){
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Read Test Result")
						.setMessage("CmdId: " + commandid+ " \n"
								+ "Result: " + success + " \n"
								+ "Value: " + buffer + " \n")
						.setPositiveButton("Back", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int whichButton) {
								dialog.dismiss();
							}
						}).create().show();
			}
		});
	}

	private void showDialog(final int commandid, final boolean success, final String buffer){
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Read Test Result")
						.setMessage("CmdId: " + commandid+ " \n"
								+ "Result: " + (success ? "READ SUCCESS" : "READ FAILED")+ " \n"
								+ "Value: " + buffer + " \n")
						.setPositiveButton("Back", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int whichButton) {
								dialog.dismiss();
							}
						}).create().show();
			}
		});
	}
}
