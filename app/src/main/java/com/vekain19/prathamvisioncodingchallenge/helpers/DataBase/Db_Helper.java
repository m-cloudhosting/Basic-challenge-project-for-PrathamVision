package com.vekain19.prathamvisioncodingchallenge.helpers.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class Db_Helper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";


    // columns of the User_master table
    private static final String USER_LOGIN_DETAILS= "USER_LOGIN_DETAILS";
    private static final String user_id = "_id";
    private static final String USER_NAME = "User_Name";
    private static final String USER_NUMBER = "User_Number";
    private static final String USER_PASSWORD = "User_Password";

    private static final String DATABASE_NAME = "KissanNetwork.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_1 = "CREATE TABLE " + USER_LOGIN_DETAILS + " (" + user_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " VARCHAR(50)," + USER_NUMBER + " VARCHAR(50)," + USER_PASSWORD + " VARCHAR(50) )";

    public Db_Helper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading the database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + USER_LOGIN_DETAILS);
    }


    public boolean inserNewUserData(String UserName, String UserNumber, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, UserName);
        contentValues.put(USER_NUMBER, UserNumber);
        contentValues.put(USER_PASSWORD, userPassword);
        long result = db.insert(USER_LOGIN_DETAILS, null, contentValues);
        return result != -1;
    }

    public ArrayList<String> getUserByName(String name) {
        ArrayList<String> passwords = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String querry = "Select " + USER_PASSWORD + " from " + USER_LOGIN_DETAILS + " where " + USER_NAME + " = '"+ name+"'";
        Cursor cursor=db.rawQuery(querry,null);
        while (cursor.moveToNext())
        {
            passwords.add(cursor.getString(0));
        }
        cursor.close();
        return passwords;
    }
}