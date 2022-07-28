package edu.wit.mobileapp.c_4_me_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static String TAG = "myApp";
    public static final String myPreferences = "myPref";
    private Spinner crowdSpinner, cautionSpinner, messageSpinner, arrivedSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.v(TAG, "onCreate() is called");
        Button saveBtn = (Button) findViewById(R.id.saveButt);

        //spinner adapters for alert settings
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //crowd spinner
        crowdSpinner = (Spinner) findViewById(R.id.crowdOption);
        crowdSpinner.setAdapter(adapter);
        crowdSpinner.setOnItemSelectedListener(this);
        Log.v(TAG, "crowdSpinner" + crowdSpinner.getSelectedItem().toString());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "saveButton clicked");

                String text = crowdSpinner.getSelectedItem().toString();
                Log.v(TAG, "spinner selection is:" + text);

                saveSharedPref(text);

            }
        });

        loadSharedPref();

        /* //do we need this??

         Button homeBtn = (Button) findViewById(R.id.settingsSwitchButton);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("None")){
            //do nothing
        } else {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Alert changed to: " + text, Toast.LENGTH_LONG).show();
        }

    }

    public void saveSharedPref(String text){
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = sharedPref.edit();

        prefEdit.putString("crowdSpinner", text);
        prefEdit.apply();
        Log.v(TAG, "data saved i hope");

    }

    public void loadSharedPref(){
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        String ans = sharedPref.getString("crowdSpinner", "");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
