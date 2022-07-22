package edu.wit.mobileapp.c_4_me_mobile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.huawei.audiobluetooth.api.AudioBluetoothApi;
import com.huawei.audiobluetooth.constant.ConnectState;
import com.huawei.audiobluetooth.layer.bluetooth.BluetoothManager;
import com.huawei.audiobluetooth.layer.bluetooth.DiscoveryHelper;
import com.huawei.audiobluetooth.layer.bluetooth.IInitResultCallBack;
import com.huawei.audiobluetooth.layer.data.entity.INotifyListener;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiobluetooth.layer.bluetooth.IInitResultCallBack;



public class ConnectivityActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.

                    Log.v( "TAG","error on callback");
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    Log.v( "TAG","error on callback");
                }
            });


    // adapter
    AudioBluetoothApi EAdapter;

    // info
    private String mac;

    // UI
    Button connect;
    Button scan;

    // Device Info
    DeviceInfo DInfo = new DeviceInfo();


    // to grab apps context
    public Context getContext() {
        return this;
    }

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivity_activity);

        // initializing bluetooth manager
        //BManager = (BluetoothManager) (getSystemService(Context.BLUETOOTH_SERVICE));

        // initializing phone adapter bluetooth from bluetooth manager
        //BAdapter = BManager.getBluetoothAdapter();
        EAdapter = new AudioBluetoothApi();
        // initializing eyewear bluetooth
        EAdapter.init(this,null);


        // search for devices (this should be done automatically once
        // activity is loaded to ease access for user
        // will continue to search for device for a minute and if not
        // found, will let user know
        scan = (Button) (findViewById(R.id.btn_scan));
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // this might have been the one
                if (ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.BLUETOOTH_SCAN) ==
                        PackageManager.PERMISSION_GRANTED) {
                    // You can use the API that requires the permission.
                    EAdapter.searchDevice(new DiscoveryHelper.FoundCallback() {
                        @Override
                        public void onFound(DeviceInfo deviceInfo) {
                            // finds current device mac
                            DInfo.getDeviceBtMac();
                        }

                        @Override
                        public void onFinish() {

                        }
                    });
                } else {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissionLauncher.launch(
                            Manifest.permission.BLUETOOTH_SCAN);
                }


                connect.setVisibility(View.VISIBLE);
            }

            // execute on callback
            // if true, then
            // switch scan visiblility to off
            // show connect button instead
        });



        // connect to device via connect button
        connect = (Button) (findViewById(R.id.btn_connect));

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EAdapter.connect(DInfo.deviceBtMac, state -> {
                    BluetoothDevice device = BluetoothManager.getInstance().getBtDevice(DInfo.deviceBtMac);
                    switch (state) {
                        case ConnectState.STATE_UNINITIALIZED:
                            break;
                        case ConnectState.STATE_CONNECTING:
                            break;
                        case ConnectState.STATE_CONNECTED:

                            break;
                        case ConnectState.STATE_DATA_READY:
                            EAdapter.registerListener(DInfo.deviceBtMac, result -> {


                            });

                            break;
                        case ConnectState.STATE_DISCONNECTED:
                            EAdapter.unregisterListener(DInfo.deviceBtMac);
                            break;
                        default:
                            break;
                }});


            }
        });




        // check if it connected, display connection status

        boolean check = EAdapter.isConnected(mac);



        // show battery percentage




    }




}