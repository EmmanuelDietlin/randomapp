package com.example.randomapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;

public class MetaphorsDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILE_NAME = "randomappdatabase";
    private static final String DATABASE_TABLE_NAME = "randomappdatabase";
    private static final String PKEY = "pkey";
    private static final String COL1 = "metaphor";

    MetaphorsDatabase(Context context){
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String DATABASE_TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
                    PKEY + " INTEGER PRIMARY KEY," +
                    COL1 + " TEXT);";
            db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String s)
    {
        Log.i("JFL"," Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COL1, s);
        db.insertOrThrow(DATABASE_TABLE_NAME,null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Vector<String[]> readData()
    {
        Vector<String[]> result = new Vector<String[]>();
        Log.i("JFL", "Reading database...");
        String select = new String("SELECT * from " + DATABASE_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.i("JFL", "Number of entries: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String[] metaphor = {cursor.getString(cursor.getColumnIndex(PKEY)), cursor.getString(cursor.getColumnIndex(COL1))};
                result.add(metaphor);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(db);

    }

    public void deleteData(String row) {
        SQLiteDatabase db = getWritableDatabase();
        String DELETE_REQUEST = "DELETE FROM " + getDatabaseName() + " WHERE PKEY="+row;
        db.execSQL(DELETE_REQUEST);
    }


}
