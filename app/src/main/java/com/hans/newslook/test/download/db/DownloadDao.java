package com.hans.newslook.test.download.db;

import com.hans.newslook.test.download.db.ThreadBean;

import java.util.List;

/**
 * @author Hans
 * @create 2019/1/11
 * @description
 */
public interface DownloadDao {

    void insertThread(ThreadBean threadBean);

    void deleteThread(String url, int threadId);

    void updateThread(String url, int threadId, long loadedLen);

    List<ThreadBean> getThreads(String url);

    boolean isExist(String url, int threadId);

}
