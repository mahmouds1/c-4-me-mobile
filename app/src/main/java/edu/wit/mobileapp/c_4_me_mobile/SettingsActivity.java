package edu.wit.mobileapp.c_4_me_mobile;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.VibrationEffect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SettingsActivity extends AppCompatActivity {

    //region vars
    static String TAG = "myApp";
    public static final String myPreferences = "myPref";
    public Vibrator vibrator;

    private Spinner crowdSpinner, cautionSpinner, messageSpinner, arrivedSpinner;
    public static int crowdSpinPos, cautionSpinPos, messageSpinPos, arrivedSpinPos;

    public static RadioGroup locationRG, messageRG, notesRG;
    public int locationRBPos, messageRBPos, notesRBPos;

    public SharedPreferences sharedPref;
    public SharedPreferences.Editor prefEdit;
    public static boolean firstTime = true;
    //endregion vars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.v(TAG, "SA onCreate() is called");
        //loadSharedPref();

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

        //shared pref initialization
        sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        prefEdit = sharedPref.edit();

        //region spinners init
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.OptionsTexts, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        crowdSpinner = (Spinner) findViewById(R.id.crowdOption);
        crowdSpinner.setAdapter(spinnerAdapter);

        cautionSpinner = findViewById(R.id.cautionOption);
        cautionSpinner.setAdapter(spinnerAdapter);

        messageSpinner = findViewById(R.id.messageOption);
        messageSpinner.setAdapter(spinnerAdapter);

        arrivedSpinner = findViewById(R.id.arrivedOption);
        arrivedSpinner.setAdapter(spinnerAdapter);
        //endregion spinners init

        //region radio buttons data

        //input value from radio buttons - location
        locationRG = findViewById(R.id.radioGroup);
        locationRG.setOnCheckedChangeListener((group, checkedId) -> {

            //what was selected index - used internally
            locationRBPos = locationRG.indexOfChild(findViewById(locationRG.getCheckedRadioButtonId()));
            Log.v(TAG, "location index chosen: " + locationRBPos);
            prefEdit.putInt("touchLocationIn", locationRBPos);
            Log.v(TAG, "touchLocation saving .." + locationRBPos);

            /*used to see what was selected -- testing purposes only
            locationRadio = (RadioButton) locationRG.getChildAt(locationRBPos);
            String selectedText = locationRadio.getText().toString();
            //Log.v(TAG,"selectedText output: "+ selectedText);*/

            prefEdit.commit();

            //Toast.makeText(this, "Setting Saved", Toast.LENGTH_SHORT).show();
        });

        //input value from radio buttons - message
        messageRG = findViewById(R.id.radioGroup2);
        messageRG.setOnCheckedChangeListener((group, checkedId) -> {

            //what was selected index - used internally
            messageRBPos = messageRG.indexOfChild(findViewById(messageRG.getCheckedRadioButtonId()));
            Log.v(TAG, "message index chosen: " + messageRBPos);
            prefEdit.putInt("touchMessageIn", messageRBPos);
            Log.v(TAG, "touchMessageIn saving .." + messageRBPos);

            /*used to see what was selected -- testing purposes only
            messageRadio = (RadioButton) messageRG.getChildAt(messageRBPos);
            String selectedText = messageRadio.getText().toString();
            //Log.v(TAG,"selectedText output: "+ selectedText);*/

            prefEdit.commit();
            //Toast.makeText(this, "Setting Saved", Toast.LENGTH_SHORT).show();
        });

        //input value from radio buttons - notes
        notesRG = findViewById(R.id.radioGroup3);
        notesRG.setOnCheckedChangeListener((group, checkedId) -> {

            //what was selected index - used internally
            notesRBPos = notesRG.indexOfChild(findViewById(notesRG.getCheckedRadioButtonId()));
            Log.v(TAG, "notes index chosen: " + notesRBPos);
            prefEdit.putInt("touchNotesIn", notesRBPos);
            Log.v(TAG, "touchNotesIn saving .." + notesRBPos);

            /*used to see what was selected -- testing purposes only
            notesRadio = (RadioButton) notesRG.getChildAt(notesRBPos);
            String selectedText = notesRadio.getText().toString();
            //Log.v(TAG,"selectedText output: "+ selectedText);*/

            prefEdit.commit();
            //Toast.makeText(this, "Setting Saved", Toast.LENGTH_SHORT).show();
        });

        //endregion radio buttons data

        if (firstTime == true) {

            Log.v(TAG, "inside if statement where firstTime = true");

            crowdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        crowdSpinPos = crowdSpinner.getSelectedItemPosition();
                        Log.v(TAG, "crowd selection string is:" + crowdSpinPos);

                        switch (crowdSpinPos) {
                            case 1:
                                vibratePhone();
                                break;
                            case 2:
                                sendPushNotification();
                                break;
                            case 3:
                                //TODO:add glasses beep
                                break;
                        }

                        prefEdit.putInt("crowdSpinIn", crowdSpinPos);
                        prefEdit.commit();

                       /*used to see what was selected -- testing purposes only
                       //crowdSpinIn = crowdSpinner.getSelectedItem().toString(); //input to string
                       //Log.v(TAG, "crowd selection string is:" + crowdSpinIn);*/

                        //Toast.makeText(getApplicationContext(), "Saved Crowd Alert Changes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            cautionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        cautionSpinPos = cautionSpinner.getSelectedItemPosition();
                        Log.v(TAG, "caution selection string is:" + cautionSpinPos);

                        switch (cautionSpinPos) {
                            case 1:
                                vibratePhone();
                                break;
                            case 2:
                                sendPushNotification();
                                break;
                            case 3:
                                //TODO:add glasses beep
                                break;
                        }

                        prefEdit.putInt("cautionSpinIn", cautionSpinPos);
                        prefEdit.commit();

                        /*used to see what was selected -- testing purposes only
                        //cautionSpinIn = cautionSpinner.getSelectedItem().toString(); //input to string
                        //Log.v(TAG, "caution selection string is:" + cautionSpinIn);*/

                        //Toast.makeText(getApplicationContext(), "Saved Crowd Alert Changes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            messageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        messageSpinPos = messageSpinner.getSelectedItemPosition();
                        Log.v(TAG, "crowd selection string is:" + messageSpinPos);

                        switch (messageSpinPos) {
                            case 1:
                                vibratePhone();
                                break;
                            case 2:
                                sendPushNotification();
                                break;
                            case 3:
                                //TODO:add glasses beep
                                break;
                        }

                        prefEdit.putInt("messageSpinIn", messageSpinPos);
                        prefEdit.commit();

                        /*used to see what was selected -- testing purposes only
                        //messageSpinIn = messageSpinner.getSelectedItem().toString(); //input to string
                        //Log.v(TAG, "crowd selection string is:" + messageSpinIn);*/

                        //Toast.makeText(getApplicationContext(), "Saved Crowd Alert Changes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            arrivedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        arrivedSpinPos = arrivedSpinner.getSelectedItemPosition();
                        Log.v(TAG, "crowd selection string is:" + arrivedSpinPos);

                        switch (arrivedSpinPos) {
                            case 1:
                                vibratePhone();
                                break;
                            case 2:
                                sendPushNotification();
                                break;
                            case 3:
                                //TODO:add glasses beep
                                break;
                        }

                        prefEdit.putInt("arrivedSpinIn", arrivedSpinPos);
                        prefEdit.commit();

                        /*used to see what was selected -- testing purposes only
                        //arrivedSpinIn = arrivedSpinner.getSelectedItem().toString(); //input to string
                        //Log.v(TAG, "crowd selection string is:" + messageSpinIn);*/

                        //Toast.makeText(getApplicationContext(), "Saved Crowd Alert Changes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //loadSharedPref();
            firstTime = false;

        } else {

            Log.v(TAG, "inside else statement where firstTime = false");

             crowdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        crowdSpinPos = crowdSpinner.getSelectedItemPosition();
                        //Log.v(TAG, "crowd selection string is:" + crowdSpinPos);

                        prefEdit.putInt("crowdSpinIn", crowdSpinPos);
                        prefEdit.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
             cautionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        cautionSpinPos = cautionSpinner.getSelectedItemPosition();
                        prefEdit.putInt("cautionSpinIn", cautionSpinPos);
                        prefEdit.commit();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
             messageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        messageSpinPos = messageSpinner.getSelectedItemPosition();
                        Log.v(TAG, "crowd selection string is:" + messageSpinPos);

                        prefEdit.putInt("messageSpinIn", messageSpinPos);
                        prefEdit.commit();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
             arrivedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        arrivedSpinPos = arrivedSpinner.getSelectedItemPosition();
                        prefEdit.putInt("arrivedSpinIn", arrivedSpinPos);
                        prefEdit.commit();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

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

    protected void onDestroy(){
        super.onDestroy();

        Log.v(TAG,"SA onDestroy() is called");
    }

    //endregion application lifecycle methods

    public void loadSharedPref(){
        Log.v(TAG, "loadSharedPref is called");
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        //get spinner choices
        int getCrowdSpinIn = sharedPref.getInt("crowdSpinIn", 99);
        int getCautionSpinIn = sharedPref.getInt("cautionSpinIn", 99);
        int getMessageSpinIn = sharedPref.getInt("messageSpinIn", 99);
        int getArrivedSpinIn = sharedPref.getInt("arrivedSpinIn", 99);

        //region Log.v(TAG, "getCrowdSpinIn= " + getCrowdSpinIn);
        //Log.v(TAG, "getCautionSpinIn= " + getCautionSpinIn);
        //Log.v(TAG, "getMessageSpinIn= " + getMessageSpinIn);
        //endregionLog.v(TAG, "getArrivedSpinIn= " + getArrivedSpinIn);

        //get radio button choices
        int getTouchLocationIn = sharedPref.getInt("touchLocationIn", 99);
        int getTouchMessageIn = sharedPref.getInt("touchMessageIn", 99);
        int getTouchNotesIn = sharedPref.getInt("touchNotesIn", 99);

        //regionLog.v(TAG, "getTouchLocationIndex= " + getTouchLocationIn);
        //Log.v(TAG, "getTouchMessageIndex= " + getTouchMessageIn);
        //endregionLog.v(TAG, "getTouchNotesIndex= " + getTouchNotesIn);

        //region --assigning spinners--
        //assign spinner appropriate position - crowd
        switch(getCrowdSpinIn){
            case 99:
                //do nothing
                break;
            default:
                //do something
                crowdSpinner.setSelection(crowdSpinPos, true);
                break;

        }

        //assign spinner appropriate position - caution
        switch(getCautionSpinIn){
            case 99:
                //do nothing
                break;
            default:
                //do something
                cautionSpinner.setSelection(cautionSpinPos, true);
                break;
        }

        //assign spinner appropriate position - message
        switch(getMessageSpinIn){
            case 99:
                //do nothing
                break;
            default:
                //do something
                messageSpinner.setSelection(messageSpinPos, true);
                break;
        }

        //assign spinner appropriate position - arrived
        switch(getArrivedSpinIn){
            case 99:
                //do nothing
                break;
            default:
                //do something
                arrivedSpinner.setSelection(arrivedSpinPos, true);
                break;
        }
        //endregion --assigning spinners--

        //region --assigning radio buttons--
        //assign radio button appropriate index - location
        switch (getTouchLocationIn){
            case 0:
                ((RadioButton) locationRG.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton) locationRG.getChildAt(1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) locationRG.getChildAt(2)).setChecked(true);
                break;
            case 99:
                //do nothing
                Log.v(TAG, "no location radio button preferences found");
                break;
        }

        //assign radio button appropriate index - message
        switch (getTouchMessageIn){
            case 0:
                ((RadioButton) messageRG.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton) messageRG.getChildAt(1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) messageRG.getChildAt(2)).setChecked(true);
                break;
            case 99:
                //do nothing
                Log.v(TAG, "no message radio button preferences found");
                break;
        }

        //assign radio button appropriate index - notes
        switch (getTouchNotesIn){
            case 0:
                ((RadioButton) notesRG.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton) notesRG.getChildAt(1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) notesRG.getChildAt(2)).setChecked(true);
                break;
            case 99:
                //do nothing
                Log.v(TAG, "no notes radio button preferences found");
                break;
        }
        //endregion --assigning radio buttons--

        //Log.v(TAG, "done");
    }

    //method to vibrate phone
    @SuppressLint("MissingPermission")
    public void vibratePhone(){

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //check if device has vibrator hardware or not
        if (!vibrator.hasVibrator()) {
            Log.v(TAG, "phone has no vibrator");
        } else {
            vibrator.vibrate(
                    VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE)
            );
        }
    }

    public void sendPushNotification(){
        Log.v(TAG, "inside push notification method");

        NotificationChannel channel = new NotificationChannel("my notification", "my notification", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(SettingsActivity.this, "my notification");
        nBuilder.setContentTitle("C-4-ME");
        nBuilder.setContentText("C-4-ME will send a notification for Alert");
        nBuilder.setSmallIcon(R.drawable.ic_launcher_background); //this is the icon we should add to drawable file
        //nBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        nBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        nBuilder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SettingsActivity.this);
        managerCompat.notify(1, nBuilder.build());

    }
}
