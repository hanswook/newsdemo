package com.hans.newslook.test.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hans.newslook.test.download.Constant;

/**
 * @author Hans
 * @create 2019/1/11
 * @Describe
 */
public class DownloadDBHelper extends SQLiteOpenHelper {

    public DownloadDBHelper(Context context) {
        this(context, Constant.DB_NAME, null, Constant.VERSION);
    }

    public DownloadDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constant.DB_SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Constant.DB_SQL_DROP);
        db.execSQL(Constant.DB_SQL_CREATE);

    }
}
