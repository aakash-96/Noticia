package com.example.android.newsfeed.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

/**
 * Created by HP PC on 17-02-2017.
 */

public class ArticleDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "NewsArticles.db";
    private static final int DATABASE_VERSION = 2;

    public ArticleDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.EntertainmentEntry.TABLE_NAME + "(" +
                ArticleContract.EntertainmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.EntertainmentEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.EntertainmentEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleContract.EntertainmentEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.EntertainmentEntry.COLUMN_URL + " TEXT, " +
                ArticleContract.EntertainmentEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                ArticleContract.EntertainmentEntry.COLUMN_DATE + " TEXT, " +
                ArticleContract.EntertainmentEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

        SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.TechnologyEntry.TABLE_NAME + "(" +
                ArticleContract.TechnologyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.TechnologyEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.TechnologyEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleContract.TechnologyEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.TechnologyEntry.COLUMN_URL + " TEXT, " +
                ArticleContract.TechnologyEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                ArticleContract.TechnologyEntry.COLUMN_DATE + " TEXT, " +
                ArticleContract.TechnologyEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

        SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.topTechnologyEntry.TABLE_NAME + "(" +
                ArticleContract.topTechnologyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.topTechnologyEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.topTechnologyEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleContract.topTechnologyEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.topTechnologyEntry.COLUMN_URL + " TEXT, " +
                ArticleContract.topTechnologyEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                ArticleContract.topTechnologyEntry.COLUMN_DATE + " TEXT, " +
                ArticleContract.topTechnologyEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

        SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.SportsEntry.TABLE_NAME + "(" +
                ArticleContract.SportsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.SportsEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.SportsEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleContract.SportsEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.SportsEntry.COLUMN_URL + " TEXT, " +
                ArticleContract.SportsEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                ArticleContract.SportsEntry.COLUMN_DATE + " TEXT, " +
                ArticleContract.SportsEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

        SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.topSportsEntry.TABLE_NAME + "(" +
                ArticleContract.topSportsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.topSportsEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.topSportsEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleContract.topSportsEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.topSportsEntry.COLUMN_URL + " TEXT, " +
                ArticleContract.topSportsEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                ArticleContract.topSportsEntry.COLUMN_DATE + " TEXT, " +
                ArticleContract.topSportsEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

        SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.BusinessEntry.TABLE_NAME + "(" +
                ArticleContract.BusinessEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.BusinessEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.BusinessEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleContract.BusinessEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.BusinessEntry.COLUMN_URL + " TEXT, " +
                ArticleContract.BusinessEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                ArticleContract.BusinessEntry.COLUMN_DATE + " TEXT, " +
                ArticleContract.BusinessEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

        SQL_CREATE_TABLE = "CREATE TABLE " + ArticleContract.topBusinessEntry.TABLE_NAME + "(" +
                ArticleContract.topBusinessEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleContract.topBusinessEntry.COLUMN_TITLE + " TEXT, " +
                ArticleContract.topBusinessEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleContract.topBusinessEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleContract.topBusinessEntry.COLUMN_URL + " TEXT, " +
                ArticleContract.topBusinessEntry.COLUMN_URLTOIMAGE + " TEXT, " +
                ArticleContract.topBusinessEntry.COLUMN_DATE + " TEXT, " +
                ArticleContract.topBusinessEntry.COLUMN_ISIMAGEDOWNLOADED + " TEXT" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("Database","changing");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleContract.EntertainmentEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleContract.TechnologyEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleContract.SportsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleContract.BusinessEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
