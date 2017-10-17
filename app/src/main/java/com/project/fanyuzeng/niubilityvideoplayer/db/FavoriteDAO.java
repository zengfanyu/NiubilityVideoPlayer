package com.project.fanyuzeng.niubilityvideoplayer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.project.fanyuzeng.niubilityvideoplayer.db.FavoriteDBHelper.KEY_ALBUM_CREATE_TIME;
import static com.project.fanyuzeng.niubilityvideoplayer.db.FavoriteDBHelper.KEY_ALBUM_ID;
import static com.project.fanyuzeng.niubilityvideoplayer.db.FavoriteDBHelper.KEY_ALBUM_JSON;
import static com.project.fanyuzeng.niubilityvideoplayer.db.FavoriteDBHelper.KEY_ALBUM_SITE;
import static com.project.fanyuzeng.niubilityvideoplayer.db.FavoriteDBHelper.TABLE_NAME;

/**
 * Created by fanyuzeng on 2017/10/16.
 * Function:
 */

public class FavoriteDAO implements IFavoriteDAO {
    private static final String TAG = "FavoriteDAO";
    FavoriteDBHelper mDBHelper = null;

    public FavoriteDAO(Context context) {
        mDBHelper = new FavoriteDBHelper(context);
    }

    @Override
    public boolean add(Album album) {
        Album oldAlbum = getAlbumById(album.getAlbumId(), album.getSite().getSiteId());
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        if (oldAlbum == null) {
            ContentValues values = new ContentValues();
            values.put(KEY_ALBUM_ID, album.getAlbumId());
            values.put(KEY_ALBUM_SITE, album.getSite().getSiteId());
            values.put(KEY_ALBUM_JSON, album.toJson());
            values.put(KEY_ALBUM_CREATE_TIME, getCurrentTime());
            db.insert(TABLE_NAME, null, values);
            db.close();
        }
        return false;
    }

    @Override
    public boolean delete(String albumId, int siteId) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ALBUM_ID + "=? and " + KEY_ALBUM_SITE + "=?", new String[]{albumId, String.valueOf(siteId)});
        db.close();
        return false;
    }

    public AlbumList getAllData() {
        AlbumList albumList = new AlbumList();
        String SELECTQUERY = "select * from " + TABLE_NAME + " order by datetime(" + KEY_ALBUM_CREATE_TIME + ") desc";
        Log.d(TAG,">> getAllData >> " + "select query sql:"+SELECTQUERY);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECTQUERY, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                do {
                    Album album = Album.fromJson(cursor.getString(3));
                    albumList.add(album);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }

        db.close();
        return albumList;
    }


    public Album getAlbumById(String albumId, int siteId) {
        if (mDBHelper != null) {
            SQLiteDatabase db = mDBHelper.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ALBUM_ID, KEY_ALBUM_SITE, KEY_ALBUM_JSON, KEY_ALBUM_CREATE_TIME},
                    KEY_ALBUM_ID + "=? and " + KEY_ALBUM_SITE + "=?", new String[]{albumId, String.valueOf(siteId)}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String json = cursor.getString(2);//2表示第2个columns
                    Album album = Album.fromJson(json);
                    Log.d(TAG, ">> getAlbumById >> " + "json:" + json);
                    cursor.close();//取到值后,关闭游标,关闭数据库,防止泄漏
                    db.close();
                    return album;
                } else {
                    cursor.close();
                    db.close();
                    return null;
                }
            } else {
                db.close();
            }
        }
        return null;
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return format.format(date);

    }
}
