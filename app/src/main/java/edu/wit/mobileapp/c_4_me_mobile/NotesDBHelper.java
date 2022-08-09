package edu.wit.mobileapp.c_4_me_mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NotesDBHelper extends SQLiteOpenHelper {

    public NotesDBHelper(Context context) {
        super(context, "Notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Notes(title TEXT primary key, date INTEGER, note TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Notes");
    }

    /**
     * Inserts a new note into the Notes database
     * @param title title of note
     * @param dateTimestamp timestamp of note (long)
     * @param content note content
     * @return true if success, else false
     */
    public Boolean insertNewNote(String title, Long dateTimestamp, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", dateTimestamp);
        contentValues.put("note", content);
        long results = db.insert("Notes", null, contentValues);
        return results != -1;
    }

    /**
     * Updates the date and content an existing note from the Notes database
     * @param title title of note
     * @param dateTimestamp timestamp of note (long)
     * @param content note content
     * @return true if success, else false
     */
    public Boolean updateExistingNote(String title, Long dateTimestamp, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", dateTimestamp);
        contentValues.put("note", content);

        String[] titleQuery = { title };
        Cursor cursor = db.rawQuery("Select * from Notes where title = ?", titleQuery);
        if (cursor.getCount() > 0) {
            long results = db.update("Notes", contentValues, "title=?", titleQuery);
            return results != -1;
        } else {
            return false;
        }
    }

    /*
     * Deletes an existing note from the Notes database
     * @param title title of note
     * @return true if success, else false

    CURRENTLY COMMENTED OUT UNTIL USAGE
    public Boolean deleteExistingNote(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] titleQuery = { title };
        Cursor cursor = db.rawQuery("Select * from Notes", null);
        if (cursor.getCount() > 0) {
            long results = db.delete("Notes", "title=?", titleQuery);
            return results != -1;
        } else {
            return false;
        }
    }
    */

    /*
     * Gets an existing note from the Notes database
     * @param title title of note
     * @return note data

    public Cursor getExistingNote(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] titleQuery = { title };
        return db.rawQuery("Select * from Notes where title = ?", titleQuery);
    }
    */

    /**
     * Gets ALL existing notes from the Notes database
     * @return ALL note data
     */
    public Cursor getAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from Notes", null);
    }
}
