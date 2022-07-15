package edu.wit.mobileapp.c_4_me_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    static String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        Log.v(TAG, "onCreate() is called");


        Button homeBtn = (Button) findViewById(R.id.settingsSwitchButton);

        //do we need this??
        /*
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "in settings page, going back to home");

                Intent homeIntent = new Intent();
                homeIntent.setClass(SettingsActivity.this, MainActivity.class);

                startActivity(homeIntent);

            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.v(TAG, "onStart() is called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v(TAG, "onResume() is called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.v(TAG, "onPause() is called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.v(TAG, "onStop() is called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.v(TAG, "onRestart() is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "onDestroy() is called");
    }

}
