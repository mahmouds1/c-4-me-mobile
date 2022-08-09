package edu.wit.mobileapp.c_4_me_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        // Get bundle data
        Bundle thisBundle = getIntent().getExtras();

        // Buttons
        Button goBackBtn = (Button) findViewById(R.id.edit_note_back_button);
        Button editNoteBtn = (Button) findViewById(R.id.edit_note_submit_button);

        // TextView and EditText items
        TextView title;
        title = (TextView) findViewById(R.id.edit_note_title_field);

        EditText date, content;
        date = (EditText) findViewById(R.id.edit_note_date_field);
        content = (EditText) findViewById(R.id.edit_note_content_field);

        // Set initial values
        title.setText(thisBundle.getString("currentTitle"));
        date.setText(thisBundle.getString("currentDate"));
        content.setText(thisBundle.getString("currentContent"));

        // Go Back button listener
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Go Back Button Clicked");

                // intent for activity switch
                Intent goBackIntent = new Intent();
                goBackIntent.setClass(EditNoteActivity.this, ViewNoteActivity.class);
                goBackIntent.putExtras(thisBundle);
                startActivity(goBackIntent);
            }
        });//end of goBackBtn listener

        // Submit Edit Note button listener
        editNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Create Note Button Clicked");

                NotesDBHelper db = new NotesDBHelper(EditNoteActivity.this);
                DateHelper dh = new DateHelper();

                TextView title = (TextView) findViewById(R.id.edit_note_title_field);

                EditText date, content;
                date = (EditText) findViewById(R.id.edit_note_date_field);
                content = (EditText) findViewById(R.id.edit_note_content_field);

                String titleText = title.getText().toString();
                String dateText = date.getText().toString();
                String contentText = content.getText().toString();

                // Check if dateText is valid date, and get timestamp from it
                Long timestamp = null;
                if (dh.isValidDateInput(dateText)) {
                    timestamp = dh.getUnixTimestampDateFromInput(dateText);
                } else {
                    // Error timestamp
                    Toast.makeText(EditNoteActivity.this, "Please enter a correct date (mm/dd/yyyy)!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean updateNoteData = db.updateExistingNote(titleText, timestamp, contentText);
                if (updateNoteData) {
                    // Success!
                    Toast.makeText(EditNoteActivity.this, "Note successfully updated!", Toast.LENGTH_SHORT).show();
                    Intent goBackIntent = new Intent();
                    goBackIntent.setClass(EditNoteActivity.this, ViewNoteActivity.class);
                    Bundle newBundle = new Bundle();
                    newBundle.putString("currentTitle", titleText);
                    newBundle.putString("currentDate", dateText);
                    newBundle.putString("currentContent", contentText);
                    goBackIntent.putExtras(newBundle);
                    startActivity(goBackIntent);
                } else {
                    // Failure!
                    Toast.makeText(EditNoteActivity.this, "Error: note not updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });//end of editNoteBtn listener
    }
}