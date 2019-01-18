package com.hans.newslook.test.download;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.hans.newslook.test.download.db.DownloadDao;
import com.hans.newslook.test.download.db.DownloadDaoImpl;
import com.hans.newslook.test.download.db.ThreadBean;
import com.hans.newslook.utils.baseutils.LogUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxxx1
 */
public class DownloadService extends Service {

    private DownloadThread mDownLoadThread;

    /**
     * 数据访问接口
     */
    private DownloadDao mDao;

    /**
     * 下载线程的信息
     */
    private ThreadBean mThreadBean;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.CODE_CREATE_OK:
                    FileBean fileBean = (FileBean) msg.obj;
                    LogUtils.e("handleMessage fileBean:" + fileBean.toString());
                    download(fileBean);
                    break;
                default:
                    break;
            }
        }
    };

    private void download(FileBean fileBean) {
        //从数据获取线程信息
        List<ThreadBean> threads = mDao.getThreads(fileBean.getUrl());
        //如果没有线程信息，就新建线程信息
        if (threads.size() == 0) {
            //初始化线程信息对象
            mThreadBean = new ThreadBean(
                    0, fileBean.getUrl(), 0, fileBean.getLength(), 0);
        } else {
            //否则取第一个
            mThreadBean = threads.get(0);
        }
        //创建下载线程
        mDownLoadThread = new DownloadThread(mThreadBean, fileBean, this);
        //开始线程
        mDownLoadThread.start();
        mDownLoadThread.isDownLoading = true;

    }

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mDao = new DownloadDaoImpl(this);
        String action = intent.getAction();
        if (null != action) {
            switch (action) {
                case Constant.ACTION_START:
                    FileBean fileBean = (FileBean) intent.getSerializableExtra(Constant.KEY_SEND_FILE_BEAN);
                    LogUtils.e("ACTION_START fileBean:" + fileBean.toString());
                    if (mDownLoadThread != null) {
                        if (mDownLoadThread.isDownLoading) {
                            return super.onStartCommand(intent, flags, startId);
                        }
                    }
                    startDownload(fileBean);
                    break;
                case Constant.ACTION_END:
                    LogUtils.e("ACTION_END");
                    if (mDownLoadThread != null) {
                        mDownLoadThread.isDownLoading = false;
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void startDownload(FileBean fileBean) {
        new LinkUrlThread(fileBean, mHandler).start();

    }
}
