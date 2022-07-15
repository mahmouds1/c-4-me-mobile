package edu.wit.mobileapp.c_4_me_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "onCreate() is called");

        Button mapBtn = (Button) findViewById(R.id.mapButton);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent and bundle for MapsActivity
                Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class );
                Bundle mapBundle = new Bundle();

                mapIntent.putExtras(mapBundle);

                // Send to map
                startActivity(mapIntent);
            }
        });

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