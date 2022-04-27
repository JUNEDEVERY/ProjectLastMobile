package com.example.carmotoshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "infodb";
    public static final String TABLE_CARS = "cars";

    public static final String KEY_ID1 = "_id1";
    public static final String KEY_CARBRAND = "brand";
    public static final String KEY_CARMODEL = "model";
    public static final String KEY_PRICECAR = "carPrice";


    public static final String TABLE_MOTO = "motorcycles";

    public static final String KEY_ID2 = "_id2";
    public static final String KEY_MOTOBRAND = "brand";
    public static final String KEY_MOTOMODEL = "model";
    public static final String KEY_PRICEMOTO = "motoPrice";


    public static final String TABLE_USERS = "userTable";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CARS + "(" + KEY_ID1
                + " integer primary key," + KEY_CARBRAND + " text," + KEY_CARMODEL + " text," + KEY_PRICECAR + " text" + ")");

        db.execSQL("create table " + TABLE_MOTO + "(" + KEY_ID2
                + " integer primary key," + KEY_MOTOBRAND + " text," + KEY_MOTOMODEL + " text," + KEY_PRICEMOTO + " text" + ")");

        db.execSQL("create table " + TABLE_USERS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_PASSWORD + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion > newVersion){
            db.execSQL("drop table if exists " + TABLE_CARS);
            onCreate(db);

            db.execSQL("drop table if exists " + TABLE_MOTO);
            onCreate(db);

            db.execSQL("drop table if exists " + TABLE_USERS);
            onCreate(db);
        }
    }
}
