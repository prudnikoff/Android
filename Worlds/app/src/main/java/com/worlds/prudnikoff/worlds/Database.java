package com.worlds.prudnikoff.worlds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wordsDatabase";
    private static final String WORDS = "headwords";
    private static final String KEY_ID = "id";
    private static final String KEY_HEADWORD = "headword";
    private static final String KEY_DEF = "definition";
    private static final String KEY_POS = "partOfSpeech";
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
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + WORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_HEADWORD + " TEXT,"
                + KEY_DEF + " TEXT" + KEY_POS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORDS);
        onCreate(db);
    }

    public void addContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HEADWORD, "hello");
        values.put(KEY_DEF, "it's me");
        values.put(KEY_POS, "noun");
        db.insert(WORDS, null, values);
        db.close();
    }

    /*public void getContact() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(WORDS, new String[] { KEY_ID,
                        KEY_HEADWORD, KEY_DEF, KEY_POS}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return contact
        return contact;
    }*/
}
