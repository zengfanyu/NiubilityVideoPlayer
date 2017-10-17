package com.project.fanyuzeng.niubilityvideoplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by fanyuzeng on 2017/10/16.
 * Function:
 */

public class FavoriteDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "FavoriteDBHelper";
    public static final String DATA_BASE_NAME = "favorite.db";
    public static final String TABLE_NAME = "favorite";
    public static final int DATA_BASE_VERSITON = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_ALBUM_ID = "albumid";
    public static final String KEY_ALBUM_SITE = "albumsite";
    public static final String KEY_ALBUM_JSON = "albumjson";
    public static final String KEY_ALBUM_CREATE_TIME = "createtime";

    public FavoriteDBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSITON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + KEY_ID + " integer primary key,"
                + KEY_ALBUM_ID + " text,"
                + KEY_ALBUM_SITE + " integer,"
                + KEY_ALBUM_JSON + " text,"
                + KEY_ALBUM_CREATE_TIME + " text" + ")";
        Log.d(TAG, ">> onCreate >> " + "create table sql:" + CREATE_TABLE);
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DELETE_TABLE = "drop table if exists " + TABLE_NAME;
        Log.d(TAG, ">> onUpgrade >> " + "delete table sql:" + DELETE_TABLE);
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }
}
