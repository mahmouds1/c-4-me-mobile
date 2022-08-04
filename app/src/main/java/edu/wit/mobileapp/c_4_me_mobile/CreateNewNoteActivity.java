package edu.wit.mobileapp.c_4_me_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);

        // Buttons
        Button goBackBtn = (Button) findViewById(R.id.create_note_back_button);
        Button createNoteBtn = (Button) findViewById(R.id.create_note_submit_button);

        // Go Back button listener
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Go Back Button Clicked");

                // intent for activity switch
                Intent goBackIntent = new Intent();
                goBackIntent.setClass(CreateNewNoteActivity.this, NotesActivity.class);

                startActivity(goBackIntent);
            }
        });//end of goBackBtn listener

        // Create Note button listener
        createNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Create Note Button Clicked");

                NotesDBHelper db = new NotesDBHelper(CreateNewNoteActivity.this);
                DateHelper dh = new DateHelper();

                EditText title, date, content;
                title = (EditText) findViewById(R.id.create_note_title_field);
                date = (EditText) findViewById(R.id.create_note_date_field);
                content = (EditText) findViewById(R.id.create_note_content_field);

                String titleText = title.getText().toString();
                String dateText = date.getText().toString();
                String contentText = content.getText().toString();

                // Check if dateText is valid date, and get timestamp from it
                Long timestamp = null;
                if (dh.isValidDateInput(dateText)) {
                    timestamp = dh.getUnixTimestampDateFromInput(dateText);
                } else {
                    // Error timestamp
                    Toast.makeText(CreateNewNoteActivity.this, "Please enter a correct date (mm/dd/yyyy)!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean insertNewNoteData = db.insertNewNote(titleText, timestamp, contentText);
                if (insertNewNoteData) {
                    // Success!
                    Toast.makeText(CreateNewNoteActivity.this, "Note successfully created!", Toast.LENGTH_SHORT).show();
                    Intent goBackIntent = new Intent();
                    goBackIntent.setClass(CreateNewNoteActivity.this, NotesActivity.class);
                    startActivity(goBackIntent);
                } else {
                    // Failure!
                    Toast.makeText(CreateNewNoteActivity.this, "Error: note not created!", Toast.LENGTH_SHORT).show();
                }
            }
        });//end of createNoteBtn listener
    }
}