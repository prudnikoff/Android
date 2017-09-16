package com.worlds.prudnikoff.worlds;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;

class Database extends SQLiteAssetHelper {

    private static final int WORDS_MAX = 5;

    Database(Context context) {
        super(context, Fields.DATABASE_NAME, null, Fields.DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Fields.WORDS_TABLE_NAME);
        onCreate(db);
    }

    ArrayList<String> getWordsList() {
        ArrayList<String> wordsList;
        SQLiteDatabase db = this.getReadableDatabase();
        wordsList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + Fields.KEY_HEADWORD +
                " FROM " + Fields.KEYS_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            String word = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_HEADWORD)));
            wordsList.add(word.toLowerCase());
        }
        cursor.close();
        db.close();
        return wordsList;
    }

    ArrayList<WordModel> getWordsByQuery(String query) {
        query = query.replaceAll("'", "''");
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<WordModel> words = new ArrayList<>();
        Cursor cursor =  db.rawQuery("SELECT " + Fields._ID + ", " + Fields.KEY_HEADWORD +" FROM "
                + Fields.KEYS_TABLE_NAME + " WHERE " + Fields.KEY_HEADWORD + " LIKE '" + query
                + "%' LIMIT " + WORDS_MAX, null);
        while (cursor.moveToNext() && words.size() < WORDS_MAX) {
            String headword = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_HEADWORD)));
            String id = String.valueOf(cursor.getString(cursor.getColumnIndex(Fields.KEY_ID)));
            Cursor cursor2 =  db.rawQuery("SELECT " + Fields.KEY_DEF + ", " + Fields.KEY_POS + ", " +
                    Fields.KEY_EXM + ", " + Fields.KEY_SYN + " FROM " + Fields.WORDS_TABLE_NAME + " WHERE "
                    + Fields.KEY_ID + " = '" + id + "'", null);
            while (cursor2.moveToNext() && words.size() < WORDS_MAX) {
                String definition = String.valueOf(cursor2.getString(cursor2.getColumnIndex(Fields.KEY_DEF)));
                String pos = String.valueOf(cursor2.getString(cursor2.getColumnIndex(Fields.KEY_POS)));
                String examples = String.valueOf(cursor2.getString(cursor2.getColumnIndex(Fields.KEY_EXM)));
                String synonyms = String.valueOf(cursor2.getString(cursor2.getColumnIndex(Fields.KEY_SYN)));
                synonyms = synonyms.replaceAll("\n", ", ");
                words.add(new WordModel(pos, headword.toLowerCase(), definition, examples, synonyms));
            }
            cursor2.close();
        }
        cursor.close();
        db.close();
        return words;
    }

    private class Fields implements BaseColumns {
        private static final String KEY_ID = "_id";
        private static final String KEY_HEADWORD = "headword";
        private static final String KEY_DEF = "definition";
        private static final String KEY_POS = "pos";
        private static final String KEY_EXM = "examples";
        private static final String KEY_SYN = "synonyms";
        private static final String DATABASE_NAME = "words.db";
        private static final String KEYS_TABLE_NAME = "keys";
        private static final String WORDS_TABLE_NAME = "words";
        private static final int DATABASE_VERSION = 1;
    }
}
