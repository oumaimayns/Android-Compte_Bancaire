package com.example.appsqlite2;

import android.util.Log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "accounts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_BALANCE = "balance";

    DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_BALANCE + " INTEGER);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean addAccount(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_BALANCE, 5000);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor ViewDataById(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM accounts WHERE _id= " +id;
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    boolean updateData(String row_id, String username, String password ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            return false;
        }else {
           return true;
        }

    }

    boolean credit(String row_id, String amount){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM accounts WHERE _id= " +row_id;
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int balance = cursor.getInt(cursor.getColumnIndexOrThrow("balance"));
            balance = Integer.parseInt(amount) + balance;
            cv.put(COLUMN_BALANCE, balance);
        }
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }


    boolean debit(String row_id, String amount){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM accounts WHERE _id= " +row_id;
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int balance = cursor.getInt(cursor.getColumnIndexOrThrow("balance"));
            balance = balance-Integer.parseInt(amount);
            cv.put(COLUMN_BALANCE, balance);
        }
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }


    boolean payment(String id_destination,String id_source , String amount){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        String queryDest = "SELECT * FROM accounts WHERE _id= " +id_destination;
        String querySource = "SELECT * FROM accounts WHERE _id= " +id_source;
        Cursor cursorDest = null;
        Cursor cursorSource = null;
        cursorDest = db.rawQuery(queryDest, null);
        cursorSource = db.rawQuery(querySource, null);

        if (cursorDest.moveToFirst()) {
            int balance = cursorDest.getInt(cursorDest.getColumnIndexOrThrow("balance"));
            balance = balance + Integer.parseInt(amount);
            cv1.put(COLUMN_BALANCE, balance);
        }

        if (cursorSource.moveToFirst()) {
            int balance = cursorSource.getInt(cursorSource.getColumnIndexOrThrow("balance"));
            balance = balance - Integer.parseInt(amount);
            cv2.put(COLUMN_BALANCE, balance);
        }
        long result1 = db.update(TABLE_NAME, cv1, "_id=?", new String[]{id_destination});
        long result2 = db.update(TABLE_NAME, cv2, "_id=?", new String[]{id_source});
        if(result1 == -1 && result1 == -1){
            return false;
        }else {
            return true;
        }

    }

    boolean deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public Integer CheckUser(String username,String password){
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cursor= myDB.rawQuery("select * from accounts where username =? and password =?",new String[]{username,password});
        if (cursor.moveToFirst()) {
            int accountId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            return accountId;
        }
        else
            return 0;
    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
