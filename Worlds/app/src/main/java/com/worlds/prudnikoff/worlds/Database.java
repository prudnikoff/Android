package com.worlds.prudnikoff.worlds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;
import java.io.File;
import java.util.ArrayList;

class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "words.db";
    private static final String WORDS = "WORDS";
    private static final int DATABASE_VERSION = 1;

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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Fields.KEY_HEADWORD, headword);
        values.put(Fields.KEY_DEF, definition);
        values.put(Fields.KEY_POS, partOfSpeech);
        db.insert(WORDS, null, values);
        db.close();
    }

    void getWords(ArrayList<String> suggestions, String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT " + Fields.KEY_HEADWORD +
                " FROM " + WORDS +
                " WHERE " + Fields.KEY_HEADWORD + " LIKE '%" + query + "%' LIMIT 10", null);
        int i = cursor.getCount();
        while (cursor.moveToNext()) {
            String l = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_HEADWORD)));
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
