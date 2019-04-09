package com.example.android.projekt1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "productslist.db";
    public static final String TABLE_NAME = "productslist_table";
    public static final String ID = BaseColumns._ID;
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String SHOP = "SHOP";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String RADIUS = "RADIUS";
    public static final String ISFAVOURITESHOP = "ISFAVOURITESHOP";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LATITUDE + " TEXT, "
                + LONGITUDE + " TEXT, "
                + SHOP + " TEXT, "
                + DESCRIPTION + " TEXT, "
                + RADIUS + " INT, "
                + ISFAVOURITESHOP + " INT " + " );";
        db.execSQL(SQL_CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public long addShops(String latitude, String longitude, String shop, String description,
                         String radius){
        SQLiteDatabase db = this.getWritableDatabase();
        if (db != getWritableDatabase()){
            return -1;
        } else {
            ContentValues values = new ContentValues();
            values.put(LATITUDE, latitude);
            values.put(LONGITUDE, longitude);
            values.put(SHOP, shop);
            values.put(DESCRIPTION, description);
            values.put(RADIUS, radius);
            values.put(ISFAVOURITESHOP, 0);
            return db.insert(TABLE_NAME, null, values);
        }
    }

    public void updateValidation(int id, boolean isValid){
        Log.v("id + boolean", String.valueOf(id) + " " + String.valueOf(isValid));
        SQLiteDatabase db = this.getWritableDatabase();
        int valid;
        if (isValid) {
            valid = 1;
        } else {
            valid = 0;
        }
        String SQL_UPDATE_STATEMENT = "UPDATE " + TABLE_NAME + " SET " + ISFAVOURITESHOP + " = " + valid + " WHERE " + ID + " = " + id + ";";
        db.execSQL(SQL_UPDATE_STATEMENT);
    }

}
