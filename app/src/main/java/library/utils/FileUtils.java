package com.wallan.multimediacamera.library.utils;

import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by hanxu on 2018/6/5.
 */

public class FileUtils {

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }


    public static void deleteFiles(List<String> files) {
        for (String s : files) {
            File file = new File(s);
            boolean delete = file.delete();
            Log.e("", "delete:" + delete);
        }
    }
}
