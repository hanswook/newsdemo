package com.hans.newslook.utils.permission;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

/**
 * Created by hans
 * date: 2018/4/13 17:02.
 * e-mail: hxxx1992@163.com
 */

public class PermissionUtil {

    public static boolean checkPermission(Activity activity, String permission, int RequestCode) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        if (result != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, RequestCode);
        } else {
            return true;
        }
        return false;
    }


}
