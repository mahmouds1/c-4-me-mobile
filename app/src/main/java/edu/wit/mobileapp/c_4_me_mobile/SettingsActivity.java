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
    public static int crowdSpinPos, cautionSpinPos, messageSpinPos, arrivedSpinPos;
    public static String crowdSpinIn, cautionSpinIn, messageSpinIn, arrivedSpinIn;
    private Spinner crowdSpinner, cautionSpinner, messageSpinner, arrivedSpinner;

    public static String touchLocationIn, touchMessageIn, touchNotesIn;
    public static RadioButton locationRadio, messageRadio, notesRadio;
    public static RadioGroup radioGroup, radioGroup2, radioGroup3;
    public static int rdButtId, rdButtId2, rdButtId3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.v(TAG, "SA onCreate() is called");

        //sav button
        Button saveBtn = (Button) findViewById(R.id.saveButt);


        //region spinners

            //region crowd spinner
            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            crowdSpinner = (Spinner) findViewById(R.id.crowdOption);
            crowdSpinner.setAdapter(spinnerAdapter);
            crowdSpinner.setOnItemSelectedListener(this);

            //endregion

            //region caution spinner
            //spinner adapters for alert settings
            cautionSpinner = findViewById(R.id.cautionOption);
            cautionSpinner.setAdapter(spinnerAdapter);
            cautionSpinner.setOnItemSelectedListener(this);
            //endregion

            //region message spinner
            messageSpinner = findViewById(R.id.messageOption);
            messageSpinner.setAdapter(spinnerAdapter);
            messageSpinner.setOnItemSelectedListener(this);
            //endregion

            //region arrived spinner
            arrivedSpinner = findViewById(R.id.arrivedOption);
            arrivedSpinner.setAdapter(spinnerAdapter);
            arrivedSpinner.setOnItemSelectedListener(this);
            //endregion

        //endregion

        //region radio buttons data
        //input value from radio buttons - location
        radioGroup = findViewById(R.id.radioGroup);
        rdButtId = radioGroup.getCheckedRadioButtonId();
        locationRadio = radioGroup.findViewById(rdButtId);



        //Log.v(TAG, "where am i location Q answer: " + locationRadio.getText().toString());

        //input value from radio buttons - message
        radioGroup2 = findViewById(R.id.radioGroup2);
        rdButtId2 = radioGroup2.getCheckedRadioButtonId();
        messageRadio = radioGroup2.findViewById(rdButtId2);

        //Log.v(TAG, "where am i message Q answer: " + messageRadio.getText().toString());

        //input value from radio buttons - notes
        /*radioGroup3 = findViewById(R.id.radioGroup3);
        rdButtId3 = radioGroup3.getCheckedRadioButtonId();
        notesRadio = radioGroup3.findViewById(rdButtId3);

        Log.v(TAG, "where am i notes Q answer: " + notesRadio.getText().toString());*/

        //bundle ig
        /*Bundle bundleRadio = new Bundle();
        bundleRadio.putString("location", locationRadio.getText().toString());
        bundleRadio.putString("message", messageRadio.getText().toString());
        bundleRadio.putString("notes", notesRadio.getText().toString());*/

        //endregion radio bundle data


        locationRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get input from radio buttons
                touchLocationIn = locationRadio.getText().toString();
                touchMessageIn = messageRadio.getText().toString();
                //touchNotesIn = notesRadio.getText().toString();*/

                Log.v(TAG, "location selection is:" + touchLocationIn);
                //Log.v(TAG, "message selection is:" + touchMessageIn);

                //saveSharedPref(touchLocationIn);
                //saveSharedPref(touchMessageIn);
                //saveSharedPref(touchNotesIn);

                //Log.v(TAG, "notes selection is:" + touchNotesIn);

                Toast.makeText(getApplicationContext(), "Saved Changes", Toast.LENGTH_SHORT).show();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get input from spinners
                crowdSpinIn = crowdSpinner.getSelectedItem().toString();
                cautionSpinIn = cautionSpinner.getSelectedItem().toString();
                messageSpinIn = messageSpinner.getSelectedItem().toString();
                arrivedSpinIn = arrivedSpinner.getSelectedItem().toString();

                //regionLog.v(TAG, "crowd selection is:" + crowdSpinIn);
                //Log.v(TAG, "caution selection is:" + cautionSpinIn);
                //Log.v(TAG, "message selection is:" + messageSpinIn);
                //sLog.v(TAG, "arrived selection is:" + arrivedSpinIn);//endregion

                //save inputs
                saveSharedPref(crowdSpinIn);
                saveSharedPref(cautionSpinIn);
                saveSharedPref(messageSpinIn);
                saveSharedPref(arrivedSpinIn);

                Toast.makeText(getApplicationContext(), "Saved Changes", Toast.LENGTH_SHORT).show();
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

    //region application lifecycle methods
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

    //endregion application lifecycle methods

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).equals("None")){
            //do nothing
        } else {
            switch(parent.getId()){
                case R.id.crowdOption:
                    crowdSpinPos = position;
                    break;
                case R.id.cautionOption:
                    cautionSpinPos = position;
                    break;
                case R.id.messageOption:
                    messageSpinPos = position;
                    break;
                case R.id.arrivedOption:
                    arrivedSpinPos = position;
                    break;
            }

                //region Log.v(TAG, "crowdSpinPos Updated " + crowdSpinPos);
                Log.v(TAG, "cautionSpinPos Updated " + cautionSpinPos);
                Log.v(TAG, "messageSpinPos Updated " + messageSpinPos);
                Log.v(TAG, "arrivedSpinPos Updated " + arrivedSpinPos);
                //endregionString text = parent.getItemAtPosition(position).toString();
        }

    }

    public void saveSharedPref(String text){
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = sharedPref.edit();

        //save spinner choices
        prefEdit.putString("crowdSpinIn", text);
        prefEdit.putString("cautionSpinIn", text);
        prefEdit.putString("messageSpinIn", text);
        prefEdit.putString("arrivedSpinIn", text);

        //save radio buttons choices
        prefEdit.putString("touchLocationIn", text);
        //prefEdit.putString("touchMessageIn", text);
        //prefEdit.putString("touchNotesIn", text);


        prefEdit.commit();

    }

    public void loadSharedPref(){
        Log.v(TAG, "loadSharedPref is called");
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        //get spinner choices
        String getCrowdSpinIn = sharedPref.getString("crowdSpinIn", "");
        String getCautionSpinIn = sharedPref.getString("cautionSpinIn", "");
        String getMessageSpinIn = sharedPref.getString("messageSpinIn", "");
        String getArrivedSpinIn = sharedPref.getString("arrivedSpinIn", "");


        //get radio button choices
        String touchLocationIn = sharedPref.getString("touchLocationIn", "");
        //String touchMessageIn = sharedPref.getString("touchMessageIn", "");
        //String touchNotesIn = sharedPref.getString("touchNotesIn", "");



        //regionLog.v(TAG, "getCrowdSpinIn= " + getCrowdSpinIn);
        //Log.v(TAG, "getCautionSpinIn= " + getCautionSpinIn);
        //Log.v(TAG, "getMessageSpinIn= " + getMessageSpinIn);
        //Log.v(TAG, "getArrivedSpinIn= " + getArrivedSpinIn);//endregion

        //check if null or empty
        if((getCrowdSpinIn != null || getCrowdSpinIn != "") && (getCautionSpinIn != null || getCautionSpinIn != "")
                && (getMessageSpinIn != null || getMessageSpinIn != "") && (getArrivedSpinIn != null || getArrivedSpinIn != "")){

            //update text for  spinner
            crowdSpinner.setSelection(crowdSpinPos, true);
            cautionSpinner.setSelection(cautionSpinPos, true);
            messageSpinner.setSelection(messageSpinPos, true);
            arrivedSpinner.setSelection(arrivedSpinPos, true);

        } else {
            //do nothing
            Log.v(TAG, "no spinner preferences found");
        }


        /*if((touchLocationIn != null || touchLocationIn != "") && (touchMessageIn != null || touchMessageIn != "")
                && (touchNotesIn != null || touchNotesIn != "")){

            //update text for  spinner
            if(){

            }
            locationRadio.setSe(touchLocationIn, true);
            messageRadio.setSelection(touchMessageIn, true);
            notesRadio.setSelection(touchNotesIn, true);

        } else {
            //do nothing
            Log.v(TAG, "no radio button preferences found");
        }*/


        Log.v(TAG, "done");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
