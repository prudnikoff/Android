package com.worlds.prudnikoff.worlds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wordsDatabase.db";
    private static final String WORDS = "WORDS";
    private static final int DATABASE_VERSION = 1;

    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public void addWord() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Fields.KEY_HEADWORD, "hello");
        values.put(Fields.KEY_DEF, "it's me");
        values.put(Fields.KEY_POS, "noun");
        db.insert(WORDS, null, values);
        /*values.put(Fields.KEY_HEADWORD, "vlad");
        values.put(Fields.KEY_DEF, "perfect");
        values.put(Fields.KEY_POS, "verb");
        db.insert(WORDS, null, values);*/
        db.close();
    }

    public void getWord() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*String[] projection = {
                Fields._ID,
                Fields.KEY_HEADWORD,
                Fields.KEY_DEF,
                Fields.KEY_POS
        };*/
        Cursor cursor = db.query(WORDS, null, null, null, null, null, null);
        int i = cursor.getCount();
        while (cursor.moveToNext()) {
            Log.e("Database", String.valueOf(cursor.getInt(cursor.getColumnIndex(Fields._ID))) + " "
                    + cursor.getString(cursor.getColumnIndex(Fields.KEY_HEADWORD)) +
                    " " + cursor.getString(cursor.getColumnIndex(Fields.KEY_DEF)) + " "
                    + cursor.getString(cursor.getColumnIndex(Fields.KEY_POS)));
        }
        cursor.close();
    }

    private class Fields implements BaseColumns {
        private static final String KEY_HEADWORD = "HEADWORD";
        private static final String KEY_DEF = "DEFINITION";
        private static final String KEY_POS = "PART_OF_SPEECH";
    }
}
