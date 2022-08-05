package edu.wit.mobileapp.c_4_me_mobile;

import static java.sql.DriverManager.println;

import static edu.wit.mobileapp.c_4_me_mobile.SettingsActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.huawei.audiobluetooth.api.AudioBluetoothApi;
import com.huawei.audiobluetooth.api.data.SensorData;
import com.huawei.audiobluetooth.api.data.SensorDataHelper;
import com.huawei.audiobluetooth.constant.ConnectState;
import com.huawei.audiobluetooth.layer.bluetooth.BluetoothManager;
import com.huawei.audiobluetooth.layer.bluetooth.DiscoveryHelper;
import com.huawei.audiobluetooth.layer.bluetooth.IInitResultCallBack;
import com.huawei.audiobluetooth.layer.protocol.mbb.DeviceInfo;
import com.huawei.audiobluetooth.utils.LogUtils;


public class ConnectivityActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_BSCAN = 9;
    private static final int PERMISSION_REQUEST_AFL = 10;


    // adapter
    AudioBluetoothApi EAdapter;

    // Bluetooth Manager
    BluetoothManager DManager;

    // mac address
    private String mac;

    private View mLayout;
    // UI
    Button connect;
    Button scan;

    TextView textView;

    // Device Info
    DeviceInfo DInfo;


    // grabs App context
    public Context getContext() {
        return this;
    }




    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectivity_activity);
        mLayout = findViewById(R.id.ll_search);

        EAdapter = new AudioBluetoothApi();
        DInfo = new DeviceInfo();

        textView = (TextView) findViewById(R.id.textView2);
        scan = (Button) (findViewById(R.id.btn_scan));
        connect = (Button) (findViewById(R.id.btn_connect));


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 // start scan
                startScanPermission();
                // once scan completes, scan button disappears
                scan.setVisibility(View.GONE);


            }

        });

        connect = (Button) (findViewById(R.id.btn_connect));
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // need to make connect and scan buttons more responsive and fluid
                // should only have user scan for devices once at the top and make it look nice
                // then two connect buttons followed by the name of devices and their battery percentages
                Toast.makeText(getApplicationContext(),"Connecting...",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(2000); //1000 milliseconds is one second.
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                connect();
                try {
                    Thread.sleep(2000); //1000 milliseconds is one second.
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                connect.setText("Connected");
                connect.setEnabled(false);

            }
        });


        // battery updater here?





    }


    private void connect() {
        AudioBluetoothApi.getInstance().connect(DInfo.deviceBtMac, state -> {
            //LogUtils.i("adasf", "onConnectStateChanged state = " + state);
            BluetoothDevice device = BluetoothManager.getInstance().getBtDevice(DInfo.deviceBtMac);

            onConnectStateChanged(device, state);
        });

    }

    public void onConnectStateChanged(BluetoothDevice device, int state) {
        String stateInfo;
        switch (state) {
            case ConnectState.STATE_UNINITIALIZED:
                stateInfo = "NOT INITIALIZED";
                break;
            case ConnectState.STATE_CONNECTING:
                stateInfo = "CONNECTING";
                break;
            case ConnectState.STATE_CONNECTED:
                stateInfo = "CONNECTED";
                break;
            case ConnectState.STATE_DATA_READY:
                stateInfo = "DATA CHANNEL ALL SET AND FREDDY";
                AudioBluetoothApi.getInstance().registerListener(device.getAddress(), receiveDataEvent -> {
                    byte[] appData = receiveDataEvent.getAppData();
                    SensorData sensorData = SensorDataHelper.genSensorData(appData);
                });
                break;
            case ConnectState.STATE_DISCONNECTED:
                stateInfo = "DISCONNECTED";

                // add unregisterlistener here

                break;
            default:
                stateInfo = "no drip";
                break;
        }
    }


    // initializes Api Adapter
    // takes app context as parameter
    private void initBluetooth(Context context) {
        try {
            AudioBluetoothApi.getInstance().init(context, new IInitResultCallBack() {
                @Override
                public void onResult(boolean result) {
                    LogUtils.i("adf", "onResult result = " + result);
                }

                @Override
                public void onFinish() {
                    LogUtils.i("adf", "onFinish");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // starts search for eyewear
    // once found, saves mac into Dinfo
    private void startSearch() {
        AudioBluetoothApi.getInstance().stopSearch();
        AudioBluetoothApi.getInstance().searchDevice(new DiscoveryHelper.FoundCallback() {
            @Override
            public void onFound(DeviceInfo deviceInfo) {
                LogUtils.d("df", "startSearch Found device: " + deviceInfo);

                DInfo.deviceBtMac = deviceInfo.getDeviceBtMac();

            }

            @Override
            public void onFinish() {
                AudioBluetoothApi.getInstance().stopSearch();
                LogUtils.i("df", "searchDevice onFinish");
            }
        });
    }



    // start scan permission
    // checks for B_CONNECT AND AFL
    // if granted, shows snackbar (prob will change to toast)
    // initializes bluetooth with context
    // startSearch() method is called
    // otherwise, request permission function is called
    private void startScanPermission() {
<<<<<<< HEAD
<<<<<<< HEAD
        // Check if the permission has been granted
=======
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
>>>>>>> f821dcb (connectivity works, troubleshooting data retrieval and making UI more fluid)
=======
        // Check if the permission has been granted
>>>>>>> 5a05cb0 (small changes)
        String [] PERMISSIONS = {
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission )
                    == PackageManager.PERMISSION_GRANTED) {
<<<<<<< HEAD
<<<<<<< HEAD
                // Permission is already available
=======
                // Permission is already available, start camera preview
>>>>>>> f821dcb (connectivity works, troubleshooting data retrieval and making UI more fluid)
=======
                // Permission is already available
>>>>>>> 5a05cb0 (small changes)
                Snackbar.make(mLayout,
                        "Scan started",
                        Snackbar.LENGTH_SHORT).show();
                initBluetooth(getContext());
                startSearch();
            } else {
                // Permission is missing and must be requested.
                requestPermission();
            }
        }

<<<<<<< HEAD
<<<<<<< HEAD
=======
        // END_INCLUDE(startCamera)
>>>>>>> f821dcb (connectivity works, troubleshooting data retrieval and making UI more fluid)
=======
>>>>>>> 5a05cb0 (small changes)
    }

    // is called by ActivityCompat.requestPermissions
    // wont show on google pixel but new phone will
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_BSCAN || requestCode == PERMISSION_REQUEST_AFL)
            // Request for bletoothscanner
            if (grantResults.length == 2 && (grantResults[0] == PackageManager.PERMISSION_GRANTED) && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Snackbar.make(mLayout, "Request ",
                                Snackbar.LENGTH_SHORT)
                        .show();

            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, "denied",
                                Snackbar.LENGTH_SHORT)
                        .show();
            }
        }


    //
    private void requestPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.BLUETOOTH_CONNECT)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            // might change to just toast
            Snackbar.make(mLayout, R.string.bluetooth,
                    Snackbar.LENGTH_INDEFINITE).setAction("ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    //ActivityCompat.requestPermissions();
                    ActivityCompat.requestPermissions(ConnectivityActivity.this,
                            new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_AFL);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, "denied", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_AFL);
        }
    }

}