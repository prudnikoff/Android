package com.worlds.prudnikoff.worlds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;

class Database extends SQLiteAssetHelper {

    Database(Context context) {
        super(context, Fields.DATABASE_NAME, null, Fields.DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Fields.TABLE_NAME);
        onCreate(db);
    }

    void addWord(String headword, String definition, String partOfSpeech) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Fields.KEY_HEADWORD, headword);
        values.put(Fields.KEY_DEF, definition);
        values.put(Fields.KEY_POS, partOfSpeech);
        db.insert(Fields.TABLE_NAME, null, values);
        db.close();
    }

    ArrayList<String> getWordsList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> wordsList = new ArrayList<>();
        Cursor cursor =  db.rawQuery("SELECT " + Fields.KEY_HEADWORD +
                " FROM " + Fields.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            String word = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_HEADWORD)));
            wordsList.add(word);
        }
        cursor.close();
        db.close();
        return wordsList;
    }

    ArrayList<WordModel> getWordsByQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<WordModel> words = new ArrayList<>();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + Fields.TABLE_NAME + " WHERE "
                + Fields.KEY_HEADWORD + " = '" + query + "';", null);
        while (cursor.moveToNext()) {
            String headword = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_HEADWORD)));
            String definition = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_DEF)));
            String pos = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_POS)));
            String example = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_EXM)));
            words.add(new WordModel(pos, headword, definition, example));
        }
        cursor.close();
        db.close();
        return words;
    }

    private class Fields implements BaseColumns {
        private static final String KEY_HEADWORD = "HEADWORD";
        private static final String KEY_DEF = "DEFINITION";
        private static final String KEY_POS = "PART_OF_SPEECH";
        private static final String KEY_EXM = "EXAMPLE";
        private static final String DATABASE_NAME = "words.db";
        private static final String TABLE_NAME = "WORDS";
        private static final int DATABASE_VERSION = 1;
    }
}
