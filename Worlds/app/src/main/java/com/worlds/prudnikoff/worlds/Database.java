package com.worlds.prudnikoff.worlds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wordsDatabase.db";
    private static final String WORDS = "WORDS";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db = this.getWritableDatabase();

    Database(Context context) {
        super(context, Environment.getExternalStorageDirectory().getPath() + File.separator
                + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + WORDS + " (" +
                Fields._ID + " INTEGER PRIMARY KEY," +
                Fields.KEY_HEADWORD + " TEXT," +
                Fields.KEY_DEF + " TEXT," +
                Fields.KEY_POS + " TEXT)";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORDS);
        onCreate(db);
    }

    void addWord(String headword, String definition, String partOfSpeech) {
        ContentValues values = new ContentValues();
        values.put(Fields.KEY_HEADWORD, headword);
        values.put(Fields.KEY_DEF, definition);
        values.put(Fields.KEY_POS, partOfSpeech);
        db.insert(WORDS, null, values);
    }

    void closeIt() {
        db.close();
    }

    void getWords(ArrayList<String> suggestions, String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        /*String[] projection = {
                Fields._ID,
                Fields.KEY_HEADWORD,
                Fields.KEY_DEF,
                Fields.KEY_POS
        };*/
        String[] columns = new String[] { Fields.KEY_HEADWORD };
        //Cursor cursor = db.query(WORDS, columns, "WHERE ", null, null, null, null);
        Cursor cursor =  db.rawQuery("SELECT " + Fields.KEY_HEADWORD +
                " FROM " + WORDS +
                " WHERE " + Fields.KEY_HEADWORD + " LIKE '%" + query + "%'", null);
        while (cursor.moveToNext()) {
            suggestions.add(String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_HEADWORD))));
        }
        cursor.close();
    }

    private class Fields implements BaseColumns {
        private static final String KEY_HEADWORD = "HEADWORD";
        private static final String KEY_DEF = "DEFINITION";
        private static final String KEY_POS = "PART_OF_SPEECH";
    }
}
