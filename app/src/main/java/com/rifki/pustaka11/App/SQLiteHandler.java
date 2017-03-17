package com.rifki.pustaka11.App;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Muhammad Rifqi on 11/01/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    //All Static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "mobile_pustaka";
//    private static final String DATABASE_NAME = "rivkieyc_gojek";

    //Login table name
    private static final String TABLE_USER = "user_pustaka";

    //Login table column name
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE, " + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT " + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database tables created");
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        //Create tables again
        onCreate(db);
    }
    /*
    * Storing user details in database
    * */
    public void addUser(String name, String email, String uid, String created_at)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); //Name
        values.put(KEY_EMAIL, email); //Email
        values.put(KEY_UID, uid); //uid
        values.put(KEY_CREATED_AT, created_at); //created at

        //Inserting row
        long id = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }
    /*
    * Getting user data from database
    * */
    public HashMap<String, String> getUserDetails()
    {
        HashMap<String,String> user = new HashMap<String, String>();
        String selectQuery = " SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        //Move to first row
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        //return user
        Log.d(TAG, "Fetching user from sqlite: " + user.toString());
        return user;
    }
    /*
    * Re create database delete all tables and create them again
    * */
    public void deleteUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete all rows
        db.delete(TABLE_USER,null,null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
