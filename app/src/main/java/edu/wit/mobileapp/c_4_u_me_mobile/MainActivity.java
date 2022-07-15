package edu.wit.mobileapp.c_4_u_me_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //linking java with layout

        Log.v(TAG, "onCreate() is called");


        //buttons
        Button notesBtn = (Button) findViewById(R.id.notesSwitchButton);
        Button mapsBtn = (Button) findViewById(R.id.mapsSwitchButton);
        Button connectivityBtn = (Button) findViewById(R.id.connectivitySwitchButton);
        Button settingsBtn = (Button) findViewById(R.id.settingsSwitchButton);


        //notes activity switch button listener
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "notes button clicked");

                //intent for activity switch
                Intent notesIntent = new Intent();
                //notesIntent.setClass(MainActivity.this, NotesActivity.class);

                startActivity(notesIntent);
            }
        });//end of mapsBtn listener

        //maps activity switch button listener
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "maps button clicked");

                //intent for activity switch
                Intent mapsIntent = new Intent();
                //mapsIntent.setClass(MainActivity.this, MapsActivity.class);

                startActivity(mapsIntent);
            }
        });//end of mapsBtn listener


        //connectivity activity switch button listener
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "connectivity button clicked");

                //intent for activity switch
                Intent connectivityIntent = new Intent();
                //connectivityIntent.setClass(MainActivity.this, ConnectivityActivity.class);

                startActivity(connectivityIntent);
            }
        });//end of mapsBtn listener


        //settings activity switch button listener
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "settings button clicked");

                //intent for activity switch
                Intent settingsIntent = new Intent();
                settingsIntent.setClass(MainActivity.this, SettingsActivity.class);

                startActivity(settingsIntent);
            }
        });//end of settingsBtn listener
    }//end of onCreate()


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