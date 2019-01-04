package com.wallan.multimediacamera.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import com.wallan.common.utils.library.EmptyUtils;
import com.wallan.common.utils.library.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hanxu on 2018/6/8.
 */

public class ImageSaveUtils {

    public interface OnImageSaveListener {
        void saveImageCompletion(String path);
    }

    public static File getSaveImageFile(String folderName, String fileName) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(path, folderName + "/" + fileName);
        file.getParentFile().mkdirs();
        return file;
    }

    public static File multiShotSaveImage(File file, final Bitmap image, int saveQuanlity) {
        if (EmptyUtils.isEmpty(image)) {
            return null;
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, saveQuanlity, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void checkImageSaveResult(final Activity activity, File file, final OnImageSaveListener onImageSaveListener) {
        MediaScannerConnection.scanFile(activity,
                new String[]{file.toString()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(final String path, final Uri uri) {
                        LogUtils.d("save success path:" + path + ",\nuri:" + uri.toString());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onImageSaveListener.saveImageCompletion(path);
                            }
                        });
                    }
                });
    }
}
