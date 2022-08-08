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


    // permission request IDs
    private static final int PERMISSION_REQUEST_BCON = 8;
    private static final int PERMISSION_REQUEST_BSCAN = 9;
    private static final int PERMISSION_REQUEST_AFL = 10;



    // TAG for debugging purposes
    private static final String TAG = "EYEWEAR DEBUG/INFO";


    // Eyewear Bluetooth Adapter
    AudioBluetoothApi EAdapter;

    // MAC address
    private String mac;


    // UI Components
    private View mLayout;
    Button connect;
    Button scan;
    TextView textView;

    // Device Info (where mac address is saved)
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

        getSupportActionBar().setTitle("Connectivity");
        mLayout = findViewById(R.id.ll_search);

        getSupportActionBar().setTitle("Connectivity");

        EAdapter = new AudioBluetoothApi();
        DInfo = new DeviceInfo();

        //textView = (TextView) findViewById(R.id.textView2);
        scan = (Button) (findViewById(R.id.btn_scan));
        connect = (Button) (findViewById(R.id.btn_connect));

        // when clicked starts scan
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean check = startScanPermission();
                if (check) {
                    startSearch();
                    scan.setEnabled(false);
                    connect.setEnabled(true);
                    Toast.makeText(ConnectivityActivity.this, "Device Found", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ConnectivityActivity.this, "Scanning Failed...", Toast.LENGTH_SHORT).show();
                }

            }

        });

        connect = (Button) (findViewById(R.id.btn_connect));
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
                try {
                    Thread.sleep(2000); //1000 milliseconds is one second.
                    Toast.makeText(getApplicationContext(),"Connecting...",Toast.LENGTH_LONG).show();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if (AudioBluetoothApi.getInstance().isConnected(DInfo.getDeviceBtMac())) {
                    connect.setText("Connected");
                    connect.setEnabled(false);
                    Toast.makeText(ConnectivityActivity.this, "Successfully Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConnectivityActivity.this, "Connection Failed..Retry", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    private void connect() {
        AudioBluetoothApi.getInstance().connect(DInfo.deviceBtMac, state -> {
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

                    // add UI updater here
                    // changeUI(sensorData);
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


    private void changeUI(SensorData data) {

        String batteryPercent = data.getBattPercent() + "";

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // handle updating text
            }
        });
    }


    // initializes Eyewear Adapter
    // takes app context as parameter
    private void initBluetooth(Context context) {
        try {
            AudioBluetoothApi.getInstance().init(context, new IInitResultCallBack() {
                @Override
                public void onResult(boolean result) {
                    LogUtils.i(TAG, "onResult result = " + result);
                }

                @Override
                public void onFinish() {
                    LogUtils.i(TAG, "onFinish");
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
                LogUtils.d(TAG, "startSearch Found device: " + deviceInfo);

                DInfo.deviceBtMac = deviceInfo.getDeviceBtMac();

            }

            @Override
            public void onFinish() {
                AudioBluetoothApi.getInstance().stopSearch();
                LogUtils.i(TAG, "searchDevice onFinish");
            }
        });
    }



    // start scan permission
    // checks for B_CONNECT AND AFL
    // if granted, shows snackbar (prob will change to toast)
    // initializes bluetooth with context
    // startSearch() method is called
    // otherwise, request permission function is called
    private boolean startScanPermission() {

        boolean flag = false;
        String [] PERMISSIONS = {
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT
        };
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission )
                    == PackageManager.PERMISSION_GRANTED) {

                Snackbar.make(mLayout,
                        "Scan started",
                        Snackbar.LENGTH_SHORT).show();
                initBluetooth(getContext());
                flag = true;
                // wait to start search after

            } else {
                // Permission is missing and must be requested.
                requestPermission();
            }


        }

        return flag;
    }

    // is called by ActivityCompat.requestPermissions
    // wont show on google pixel but new phone will
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_BSCAN || requestCode == PERMISSION_REQUEST_AFL || requestCode == PERMISSION_REQUEST_BCON)
            if (grantResults.length ==  3 && (grantResults[0] == PackageManager.PERMISSION_GRANTED) && grantResults[1] == PackageManager.PERMISSION_GRANTED
            && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ConnectivityActivity.this,"Requesting Permissions",Toast.LENGTH_SHORT).show();


            } else {
                // Permission request was denied.
                Toast.makeText(ConnectivityActivity.this,"Permissions Denied",Toast.LENGTH_SHORT).show();

            }
        }


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
                            new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_CONNECT},
                            PERMISSION_REQUEST_AFL);
                }
            }).show();

        } else {
            Snackbar.make(mLayout, "Denied: Check Permissions", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_SCAN}, PERMISSION_REQUEST_AFL);
        }
    }

}