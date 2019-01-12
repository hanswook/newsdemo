package com.hans.newslook.test.download;

import android.content.Context;
import android.content.Intent;

import com.hans.newslook.test.download.db.DownloadDao;
import com.hans.newslook.test.download.db.DownloadDaoImpl;
import com.hans.newslook.test.download.db.ThreadBean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Hans
 * @create 2019/1/11
 * @Describe
 */
public class DownloadThread extends Thread {

    /**
     * 下载线程的信息
     */
    private ThreadBean mThreadBean;

    /**
     * 下载文件的信息
     */
    private FileBean mFileBean;

    /**
     * 已下载的长度
     */
    private long mLoadedLen;

    /**
     * 是否在下载
     */
    public boolean isDownLoading;

    /**
     * 数据访问接口
     */
    private DownloadDao mDao;

    /**
     * 上下文
     */
    private Context mContext;

    public DownloadThread(ThreadBean mThreadBean, FileBean mFileBean, Context mContext) {
        this.mThreadBean = mThreadBean;
        this.mFileBean = mFileBean;
        this.mContext = mContext;
        mDao = new DownloadDaoImpl(mContext);
    }

    @Override
    public void run() {
        super.run();

        if (mThreadBean == null) {
            return;
        }

        if (!mDao.isExist(mThreadBean.getUrl(), mThreadBean.getId())) {
            mDao.insertThread(mThreadBean);
        }

        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream is = null;

        try {
            URL url = new URL(mThreadBean.getUrl());
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");


            long start = mThreadBean.getStart() + mThreadBean.getLoadedLen();

            conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadBean.getEnd());
            File file = new File(Constant.FILE_DOWNLOAD_DIR, mFileBean.getFileName());
            raf = new RandomAccessFile(file, "rwd");

            raf.seek(start);

            Intent intent = new Intent(Constant.ACTION_UPDATE);

            mLoadedLen += mThreadBean.getLoadedLen();

            if (conn.getResponseCode() == 206) {
                is = conn.getInputStream();

                byte[] buf = new byte[1024 * 4];
                int len = 0;
                long time = System.currentTimeMillis();

                while ((len = is.read(buf)) != -1) {
                    raf.write(buf, 0, len);
                    mLoadedLen += len;
                    if (System.currentTimeMillis() - time > 500) {
//                        mContext.sendBroadcast(intent);
                        intent.putExtra(Constant.SEND_LOADED_PROGRESS,
                                Integer.valueOf(String.valueOf(mLoadedLen * 100 / mFileBean.getLength()))
                        );
                        mContext.sendBroadcast(intent);
                        time = System.currentTimeMillis();
                    }
                    if (!isDownLoading) {
                        mDao.updateThread(mThreadBean.getUrl(), mThreadBean.getId(), mLoadedLen);
                        return;
                    }
                }
            }

            mDao.deleteThread(mThreadBean.getUrl(),mThreadBean.getId());

            intent.putExtra(Constant.SEND_LOADED_PROGRESS,100);
            mContext.sendBroadcast(intent);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (raf != null) {
                    raf.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
