package edu.wit.mobileapp.c_4_me_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // Get all note data
        NotesDBHelper db = new NotesDBHelper(NotesActivity.this);
        Cursor notes = db.getAllNotes();

        // Build note data into array of objects
        DateHelper dh = new DateHelper();
        List<NoteListItem> notesList = new ArrayList<>();
        while (notes.moveToNext()) {
            String newTitle = notes.getString(0);
            String newDate = dh.getDateStringFromTimestamp(notes.getLong(1));
            String newContent = notes.getString(2);
            notesList.add(new NoteListItem(newTitle, newDate, newContent));
        }

        // Create NoteListItemAdapter and assign to ListView
        NoteListItemAdapter adapter = new NoteListItemAdapter(this, 0, notesList);
        ListView listOfNotes = (ListView) findViewById(R.id.notes_list_view);
        listOfNotes.setAdapter(adapter);

        // OnClick listener for each note item in the list
        listOfNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewNoteIntent = new Intent();
                viewNoteIntent.setClass(NotesActivity.this, ViewNoteActivity.class);

                NoteListItem item = (NoteListItem) parent.getItemAtPosition(position);

                Bundle viewNoteBundle = new Bundle();
                viewNoteBundle.putString("currentTitle", item.title);
                viewNoteBundle.putString("currentDate", item.date);
                viewNoteBundle.putString("currentContent", item.note_content);

                viewNoteIntent.putExtras(viewNoteBundle);
                startActivity(viewNoteIntent);
            }
        });

        // New Note Button
        Button newNoteBtn = (Button) findViewById(R.id.create_new_note_button);

        // Create new note activity switch button listener
        newNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "New Note Button Clicked");

                // intent for activity switch
                Intent notesIntent = new Intent();
                notesIntent.setClass(NotesActivity.this, CreateNewNoteActivity.class);
                startActivity(notesIntent);
            }
        });//end of newNoteBtn listener

        Button goBackBtn = (Button) findViewById(R.id.back_button);

        // Go Back button listener
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Go Back Button Clicked");

                // intent for activity switch
                Intent goBackIntent = new Intent();
                goBackIntent.setClass(NotesActivity.this, MainActivity.class);
                startActivity(goBackIntent);
            }
        });//end of goBackBtn listener
    }
}