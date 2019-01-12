package com.hans.newslook.test.download;

import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Hans
 * @create 2019/1/11
 * @Describe
 */
public class LinkUrlThread extends Thread {

    private FileBean mFileBean;
    private Handler mHandler;

    public LinkUrlThread() {
    }

    public LinkUrlThread(FileBean mFileBean, Handler handler) {
        this.mFileBean = mFileBean;
        this.mHandler = handler;
    }

    @Override
    public void run() {
        super.run();


        HttpURLConnection connection = null;
        RandomAccessFile raf = null;

        try {
            URL url = new URL(mFileBean.getUrl());

            connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);

            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                long length = connection.getContentLength();

                if (length > 0) {
                    File dir = new File(Constant.FILE_DOWNLOAD_DIR);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, mFileBean.getFileName());

                    raf = new RandomAccessFile(file, "rwd");

                    raf.setLength(length);
                    mFileBean.setLength(length);
                    mHandler.obtainMessage(Constant.CODE_CREATE_OK, mFileBean).sendToTarget();
                }

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
            try {
                if (null != raf) {
                    raf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
