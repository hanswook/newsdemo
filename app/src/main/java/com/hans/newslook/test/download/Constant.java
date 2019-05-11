package com.hans.newslook.test.download;

import android.os.Environment;

import java.io.File;

/**
 * @author Hans
 * @create 2019/1/11
 * @Describe
 */
public class Constant {

    public static final String KEY_SEND_FILE_BEAN = "key_send_file_bean";
    public static final String SEND_LOADED_PROGRESS = "send_loaded_progress";

    public static final String FILE_URL = "https://imtt.dd.qq.com/16891/E87B0F09A20642314BFBCB148891DD45.apk?fsname=com.daimajia.gold_5.6.9_209.apk&amp;csr=1bbd";


    public static String FILE_DOWNLOAD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hans_download";

    public static final int CODE_CREATE_OK = 10;


    public static final String ACTION_START = "action_start";
    public static final String ACTION_UPDATE = "action_update";

    public static final String ACTION_END = "action_end";

    public static final int VERSION = 1;

    public static final String DB_NAME = "download_db.db";

    public static final String DB_TABLE_NAME = "thread_info";

    public static final String DB_SQL_CREATE =
            "CREATE TABLE " + DB_TABLE_NAME + "(\n" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "thread_id INTEGER,\n" +
                    "url TEXT,\n" +
                    "start INTEGER,\n" +
                    "end INTEGER,\n" +
                    "loadedLen INTEGER\n" +
                    ")";


    public static final String DB_SQL_DROP =
            "DROP TABLE IF EXISTS" + DB_TABLE_NAME;

    /**
     * 插入
     */
    public static final String DB_SQL_INSERT =
            "INSERT INTO " + DB_TABLE_NAME + " (thread_id,url,start,end,loadedLen) values(?,?,?,?,?)";

    /**
     * 删除
     */
    public static final String DB_SQL_DELETE =
            "DELETE FROM " + DB_TABLE_NAME + " WHERE url = ? AND thread_id = ?";

    /**
     * 更新
     */
    public static final String DB_SQL_UPDATE =
            "UPDATE " + DB_TABLE_NAME + " SET loadedLen = ? WHERE url = ? AND thread_id = ?";

    /**
     * 查询
     */
    public static final String DB_SQL_FIND =
            "SELECT * FROM " + DB_TABLE_NAME + " WHERE url = ?";

    /**
     * 查询是否存在
     */
    public static final String DB_SQL_FIND_IS_EXISTS =
            "SELECT * FROM " + DB_TABLE_NAME + " WHERE url = ? AND thread_id = ?";


}
