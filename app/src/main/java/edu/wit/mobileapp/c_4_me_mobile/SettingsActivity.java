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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.v(TAG, "SA onCreate() is called");

        //sav button
        Button saveBtn = (Button) findViewById(R.id.saveButt);

        //region crowd spinner
        ArrayAdapter<CharSequence> crowdAdapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
        crowdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        crowdSpinner = (Spinner) findViewById(R.id.crowdOption);
        crowdSpinner.setAdapter(crowdAdapter);
        crowdSpinner.setOnItemSelectedListener(this);
        Log.v(TAG, "---crowd spinner " + crowdSpinner.getSelectedItem().toString());

        //endregion

        //region caution spinner
        //spinner adapters for alert settings
        ArrayAdapter<CharSequence> cautionAdapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
        cautionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cautionSpinner = findViewById(R.id.cautionOption);
        cautionSpinner.setAdapter(cautionAdapter);
        cautionSpinner.setOnItemSelectedListener(this);
        Log.v(TAG, "---caution spinner " + cautionSpinner.getSelectedItem().toString());
        //endregion

        //region message spinner
        ArrayAdapter<CharSequence> messageAdapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
        messageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        messageSpinner = findViewById(R.id.messageOption);
        messageSpinner.setAdapter(messageAdapter);
        messageSpinner.setOnItemSelectedListener(this);
        Log.v(TAG, "---message spinner " + messageSpinner.getSelectedItem().toString());
        //endregion

        //region arrived spinner
        ArrayAdapter<CharSequence> arrivedAdapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
        arrivedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrivedSpinner = findViewById(R.id.arrivedOption);
        arrivedSpinner.setAdapter(arrivedAdapter);
        arrivedSpinner.setOnItemSelectedListener(this);
        //endregion



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get input
                crowdSpinIn = crowdSpinner.getSelectedItem().toString();
                cautionSpinIn = cautionSpinner.getSelectedItem().toString();
                messageSpinIn = messageSpinner.getSelectedItem().toString();
                arrivedSpinIn = arrivedSpinner.getSelectedItem().toString();

                Log.v(TAG, "crowd selection is:" + crowdSpinIn);
                Log.v(TAG, "caution selection is:" + cautionSpinIn);
                Log.v(TAG, "message selection is:" + messageSpinIn);
                Log.v(TAG, "arrived selection is:" + arrivedSpinIn);

                //save inputs
                saveSharedPref(crowdSpinIn);
                saveSharedPref(cautionSpinIn);
                saveSharedPref(messageSpinIn);
                saveSharedPref(arrivedSpinIn);

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
                //Log.v(TAG, "parent position get id: " + par);
            switch(parent.getId()){
                case R.id.crowdOption:
                    crowdSpinPos = position;
                case R.id.cautionOption:
                    cautionSpinPos = position;
                case R.id.messageOption:
                    messageSpinPos = position;
                case R.id.arrivedOption:
                    arrivedSpinPos = position;
            }

                //2131296403 crowd
                //2131296367 caution
                //message 2131296546
                //arrived 2131296344



                //arrivedSpinPos = (int) arrivedSpinner.getItemAtPosition(position);

                Log.v(TAG, "crowdSpinPos Updated " + crowdSpinPos);
                Log.v(TAG, "cautionSpinPos Updated " + cautionSpinPos);
                Log.v(TAG, "messageSpinPos Updated " + messageSpinPos);
                Log.v(TAG, "arrivedSpinPos Updated " + arrivedSpinPos);
                //String text = parent.getItemAtPosition(position).toString();
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
        prefEdit.apply();

    }

    public void loadSharedPref(){
        Log.v(TAG, "loadSharedPref is called");
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        //get spinner choices
        String getCrowdSpinIn = sharedPref.getString("crowdSpinIn", "");
        String getCautionSpinIn = sharedPref.getString("cautionSpinIn", "");
        String getMessageSpinIn = sharedPref.getString("messageSpinIn", "");
        String getArrivedSpinIn = sharedPref.getString("arrivedSpinIn", "");
        Log.v(TAG, "getCrowdSpinIn= " + getCrowdSpinIn);
        Log.v(TAG, "getCautionSpinIn= " + getCautionSpinIn);
        Log.v(TAG, "getMessageSpinIn= " + getMessageSpinIn);
        Log.v(TAG, "getArrivedSpinIn= " + getArrivedSpinIn);

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
            Log.v(TAG, "no preferences found");
        }

        Log.v(TAG, "done");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
