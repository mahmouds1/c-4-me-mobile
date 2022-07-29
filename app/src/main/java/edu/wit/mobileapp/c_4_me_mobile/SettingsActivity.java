package edu.wit.mobileapp.c_4_me_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static String TAG = "myApp";
    public static final String myPreferences = "myPref";
    public static int crowdspinPos;
    private Spinner crowdSpinner, cautionSpinner, messageSpinner, arrivedSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.v(TAG, "SA onCreate() is called");
        Button saveBtn = (Button) findViewById(R.id.saveButt);

        //spinner adapters for alert settings
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //crowd spinner
        crowdSpinner = (Spinner) findViewById(R.id.crowdOption);
        Log.v(TAG, "adapter " + adapter);
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


        //region radio buttons data
        //input value from radio buttons - location
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int rdButtId = radioGroup.getCheckedRadioButtonId();
        RadioButton locationRadio = (RadioButton) radioGroup.findViewById(rdButtId);

        //Log.v(TAG, "where am i location Q answer: " + locationRadio.getText().toString());


        //input value from radio buttons - message
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        int rdButtId2 = radioGroup2.getCheckedRadioButtonId();
        RadioButton messageRadio = (RadioButton) radioGroup.findViewById(rdButtId2);

        //Log.v(TAG, "where am i message Q answer: " + messageRadio.getText().toString());

        //input value from radio buttons - notes
        RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        int rdButtId3 = radioGroup3.getCheckedRadioButtonId();
        RadioButton notesRadio = (RadioButton) radioGroup.findViewById(rdButtId3);

        //Log.v(TAG, "where am i notes Q answer: " + notesRadio.getText().toString());

        //bundle ig
        /*Bundle bundleRadio = new Bundle();
        bundleRadio.putString("location", locationRadio.getText().toString());
        bundleRadio.putString("message", messageRadio.getText().toString());
        bundleRadio.putString("notes", notesRadio.getText().toString());*/

        //endregion radio bundle data

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

        Log.v(TAG, "SA onStart() is called");
        //loadSharedPref();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v(TAG, "SA onResume() is called");
        loadSharedPref();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.v(TAG, "SA onPause() is called");

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.v(TAG, "SA onStop() is called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.v(TAG, "SA onRestart() is called");

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("None")){
            //do nothing
        } else {
            crowdspinPos = position;
            //Log.v(TAG, "crowdSpinPosUpdated " +crowdspinPos);
            String text = parent.getItemAtPosition(position).toString();
            //Toast.makeText(parent.getContext(), "Alert changed to: " + text, Toast.LENGTH_LONG).show();
        }

    }

    public void saveSharedPref(String text){
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = sharedPref.edit();

        prefEdit.putString("crowdSpinner", text);
        prefEdit.apply();
        Log.v(TAG, "data saved");

    }

    public void loadSharedPref(){
        Log.v(TAG, "loadSharedPref is called");
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        String ans = sharedPref.getString("crowdSpinner", "");
        Log.v(TAG, "answer= " +ans);

        if(ans != null || ans != ""){
            //update text for  spinner
            Log.v(TAG, "crowdSpinPos= " + crowdspinPos);
            crowdSpinner.setSelection(crowdspinPos, true);
        }else {
            //do nothing
            Log.v(TAG, "no preferences");
        }

        Log.v(TAG, "done");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
