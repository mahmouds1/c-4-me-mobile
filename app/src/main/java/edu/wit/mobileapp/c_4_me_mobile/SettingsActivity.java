package edu.wit.mobileapp.c_4_me_mobile;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.os.VibrationEffect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SettingsActivity extends AppCompatActivity {

    //region declaring vars needed throughout activity
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

    }

    //region application lifecycle methods
    @Override
    protected void onStart() {
        super.onStart();

        Log.v(TAG, "SA onStart() is called");
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

        //region radio buttons init data

        //input value from radio buttons - location
        locationRG = findViewById(R.id.radioGroup);
        locationRG.setOnCheckedChangeListener((group, checkedId) -> {

            //what was selected index - used internally
            locationRBPos = locationRG.indexOfChild(findViewById(locationRG.getCheckedRadioButtonId()));
            prefEdit.putInt("touchLocationIn", locationRBPos);
            prefEdit.commit();

        });

        //input value from radio buttons - message
        messageRG = findViewById(R.id.radioGroup2);
        messageRG.setOnCheckedChangeListener((group, checkedId) -> {

            //what was selected index - used internally
            messageRBPos = messageRG.indexOfChild(findViewById(messageRG.getCheckedRadioButtonId()));
            prefEdit.putInt("touchMessageIn", messageRBPos);
            prefEdit.commit();
        });

        //input value from radio buttons - notes
        notesRG = findViewById(R.id.radioGroup3);
        notesRG.setOnCheckedChangeListener((group, checkedId) -> {

            //what was selected index - used internally
            notesRBPos = notesRG.indexOfChild(findViewById(notesRG.getCheckedRadioButtonId()));
            prefEdit.putInt("touchNotesIn", notesRBPos);
            prefEdit.commit();
        });

        //endregion radio buttons data

        //if statements to check whether it was the first time to enter page or not - used for the switch haptic feedback (for spinners) statement
        if (firstTime == true) {

            //Log.v(TAG, "inside if statement where firstTime = true");

            crowdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        crowdSpinPos = crowdSpinner.getSelectedItemPosition();

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
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            firstTime = false;

        } else {

            //Log.v(TAG, "inside else statement where firstTime = false");

             crowdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).equals("None")) {
                        //do nothing
                    } else {
                        crowdSpinPos = crowdSpinner.getSelectedItemPosition();
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

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Log.v(TAG,"SA onDestroy() is called");
    }

    //endregion application lifecycle methods

    /**
     * method to laoad any shared preferences that were saved throughout the activity in previous sessions
     */
    public void loadSharedPref(){
        //Log.v(TAG, "loadSharedPref is called");
        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        //get spinner choices
        int getCrowdSpinIn = sharedPref.getInt("crowdSpinIn", 99);
        int getCautionSpinIn = sharedPref.getInt("cautionSpinIn", 99);
        int getMessageSpinIn = sharedPref.getInt("messageSpinIn", 99);
        int getArrivedSpinIn = sharedPref.getInt("arrivedSpinIn", 99);

        //get radio button choices
        int getTouchLocationIn = sharedPref.getInt("touchLocationIn", 99);
        int getTouchMessageIn = sharedPref.getInt("touchMessageIn", 99);
        int getTouchNotesIn = sharedPref.getInt("touchNotesIn", 99);


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

    /**
     * method to vibrate phone when haptic feedback is needed, added suppression because everything was imported properly but error persisted
     */
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

    /**
     * method to produce a notification as a haptic feedback
     */
    public void sendPushNotification(){
        Log.v(TAG, "inside push notification method");

        NotificationChannel channel = new NotificationChannel("my notification", "my notification", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(SettingsActivity.this, "my notification");
        nBuilder.setContentTitle("C-4-ME");
        nBuilder.setContentText("C-4-ME will send a notification for Alert");
        //nBuilder.setSmallIcon(R.drawable.ic_launcher_background); //this is the icon we would add to drawable file when we create our own icon
        //nBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        nBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        nBuilder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(SettingsActivity.this);
        managerCompat.notify(1, nBuilder.build());

    }
}
