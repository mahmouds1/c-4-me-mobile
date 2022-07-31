package edu.wit.mobileapp.c_4_me_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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

    public SharedPreferences sharedPref;
    public SharedPreferences.Editor prefEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Log.v(TAG, "SA onCreate() is called");

        //save button + shared pref initialization
        Button saveBtn = (Button) findViewById(R.id.saveButt);
        sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        prefEdit = sharedPref.edit();

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

        //input value from radio buttons - message
        radioGroup2 = findViewById(R.id.radioGroup2);

        //input value from radio buttons - notes
        radioGroup3 = findViewById(R.id.radioGroup3);


        //radioGroup option check 1
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //what was selected index - used internally
                int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
                Log.v(TAG,"location index chosen: "+ index);
                prefEdit.putInt("touchLocationIn", index);
                Log.v(TAG, "touchLocation saving .." + index);

                /*used to see what was selected -- testing purposes only
                locationRadio = (RadioButton) radioGroup.getChildAt(index);
                String selectedtext = locationRadio.getText().toString();
                //Log.v(TAG,"selectedText output: "+ selectedtext);*/

                prefEdit.commit();
            }
        });

        //radioGroup2 option check
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //what was selected index - used internally
                int index = radioGroup2.indexOfChild(findViewById(radioGroup2.getCheckedRadioButtonId()));
                Log.v(TAG,"message index chosen: "+ index);
                prefEdit.putInt("touchMessageIn", index);
                Log.v(TAG, "touchMessageIn saving .." + index);

                /*used to see what was selected -- testing purposes only
                messageRadio = (RadioButton) radioGroup2.getChildAt(index);
                String selectedtext = messageRadio.getText().toString();
                //Log.v(TAG,"selectedText output: "+ selectedtext);*/

                prefEdit.commit();
            }
        });

        //radioGroup3 option check
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //what was selected index - used internally
                int index = radioGroup3.indexOfChild(findViewById(radioGroup3.getCheckedRadioButtonId()));
                Log.v(TAG,"notes index chosen: "+ index);
                prefEdit.putInt("touchNotesIn", index);
                Log.v(TAG, "touchNotesIn saving .." + index);

                /*used to see what was selected -- testing purposes only
                notesRadio = (RadioButton) radioGroup3.getChildAt(index);
                String selectedtext = notesRadio.getText().toString();
                //Log.v(TAG,"selectedText output: "+ selectedtext);*/

                prefEdit.commit();
            }
        });

        //endregion radio buttons data

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

        //check what option was selected
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
                //Log.v(TAG, "cautionSpinPos Updated " + cautionSpinPos);
                //Log.v(TAG, "messageSpinPos Updated " + messageSpinPos);
                //Log.v(TAG, "arrivedSpinPos Updated " + arrivedSpinPos);
                //endregionString text = parent.getItemAtPosition(position).toString();
        }

    }

    public void saveSharedPref(String text){
        sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        prefEdit = sharedPref.edit();
        //prefEdit.clear();

        //save spinner choices
        prefEdit.putString("crowdSpinIn", text);
        prefEdit.putString("cautionSpinIn", text);
        prefEdit.putString("messageSpinIn", text);
        prefEdit.putString("arrivedSpinIn", text);
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
        int getTouchLocationIn = sharedPref.getInt("touchLocationIn", 99);
        int getTouchMessageIn = sharedPref.getInt("touchMessageIn", 99);
        int getTouchNotesIn = sharedPref.getInt("touchNotesIn", 99);


        //regionLog.v(TAG, "getCrowdSpinIn= " + getCrowdSpinIn);
        //Log.v(TAG, "getCautionSpinIn= " + getCautionSpinIn);
        //Log.v(TAG, "getMessageSpinIn= " + getMessageSpinIn);
        //Log.v(TAG, "getArrivedSpinIn= " + getArrivedSpinIn);
        // endregion

        //regionLog.v(TAG, "getTouchLocationIndex= " + getTouchLocationIn);
        //Log.v(TAG, "getTouchMessageIndex= " + getTouchMessageIn);
        //endregionLog.v(TAG, "getTouchNotesIndex= " + getTouchNotesIn);

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

        //assign radio button appropriate index - location
        switch (getTouchLocationIn){
            case 0:
                ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) radioGroup.getChildAt(2)).setChecked(true);
                break;
            case 99:
                //do nothing
                Log.v(TAG, "no location radio button preferences found");
                break;
        }

        //assign radio button appropriate index - message
        switch (getTouchMessageIn){
            case 0:
                ((RadioButton) radioGroup2.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton) radioGroup2.getChildAt(1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) radioGroup2.getChildAt(2)).setChecked(true);
                break;
            case 99:
                //do nothing
                Log.v(TAG, "no message radio button preferences found");
                break;
        }

        //assign radio button appropriate index - notes
        switch (getTouchNotesIn){
            case 0:
                ((RadioButton) radioGroup3.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton) radioGroup3.getChildAt(1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) radioGroup3.getChildAt(2)).setChecked(true);
                break;
            case 99:
                //do nothing
                Log.v(TAG, "no notes radio button preferences found");
                break;
        }

        //Log.v(TAG, "done");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
