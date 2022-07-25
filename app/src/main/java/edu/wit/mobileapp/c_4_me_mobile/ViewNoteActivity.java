package edu.wit.mobileapp.c_4_me_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        // Bundle
        Bundle thisBundle = getIntent().getExtras();

        // TextView items
        TextView title, date, content;
        title = (TextView) findViewById(R.id.view_note_title_field);
        date = (TextView) findViewById(R.id.view_note_date_field);
        content = (TextView) findViewById(R.id.view_note_content_field);

        // Set string values
        title.setText(thisBundle.getString("currentTitle"));
        date.setText(thisBundle.getString("currentDate"));
        content.setText(thisBundle.getString("currentContent"));

        // Buttons
        Button goBackBtn = (Button) findViewById(R.id.view_note_back_button);
        Button editNoteBtn = (Button) findViewById(R.id.view_note_edit_button);

        // Go Back button listener
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Go Back Button Clicked");

                // intent for activity switch
                Intent goBackIntent = new Intent();
                goBackIntent.setClass(ViewNoteActivity.this, NotesActivity.class);
                startActivity(goBackIntent);
            }
        });//end of goBackBtn listener

        // Edit Note button listener
        editNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Edit Note Button Clicked");

                // intent for activity switch
                Intent editNoteIntent = new Intent();
                editNoteIntent.setClass(ViewNoteActivity.this, EditNoteActivity.class);
                editNoteIntent.putExtras(thisBundle);
                startActivity(editNoteIntent);
            }
        });//end of editNoteBtn listener
    }
}