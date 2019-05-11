package com.hans.newslook.test.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hans.newslook.test.download.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hans
 * @create 2019/1/11
 * @Describe
 */
public class DownloadDaoImpl implements DownloadDao {

    private DownloadDBHelper mDBHelper;
    private Context mContext;

    public DownloadDaoImpl(Context mContext) {
        this.mDBHelper = new DownloadDBHelper(mContext);
        this.mContext = mContext;
    }

    @Override
    public void insertThread(ThreadBean threadBean) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        db.execSQL(Constant.DB_SQL_INSERT,
                new Object[]{threadBean.getId(), threadBean.getUrl(),
                        threadBean.getStart(), threadBean.getEnd(), threadBean.getLoadedLen()
                });

        db.close();

    }

    @Override
    public void deleteThread(String url, int threadId) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL(Constant.DB_SQL_DELETE, new Object[]{url, threadId});
        db.close();
    }

    @Override
    public void updateThread(String url, int threadId, long loadedLen) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.execSQL(Constant.DB_SQL_UPDATE, new Object[]{loadedLen, url, threadId});
        db.close();
    }

    @Override
    public List<ThreadBean> getThreads(String url) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(Constant.DB_SQL_FIND, new String[]{url});
        List<ThreadBean> threadBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThreadBean threadBean = new ThreadBean();
            threadBean.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadBean.setStart(cursor.getLong(cursor.getColumnIndex("start")));
            threadBean.setEnd(cursor.getLong(cursor.getColumnIndex("end")));
            threadBean.setLoadedLen(cursor.getLong(cursor.getColumnIndex("loadedLen")));
            threadBeans.add(threadBean);
        }
        cursor.close();
        db.close();

        return threadBeans;
    }

    @Override
    public boolean isExist(String url, int threadId) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(Constant.DB_SQL_FIND_IS_EXISTS, new String[]{url, String.valueOf(threadId)});

        boolean exists = cursor.moveToNext();

        cursor.close();
        db.close();

        return exists;
    }

}
