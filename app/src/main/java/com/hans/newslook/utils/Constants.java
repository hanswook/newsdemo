package com.hans.newslook.utils;

import android.Manifest;
import android.os.Environment;

import java.io.File;

/**
 * Created by hans
 * date: 2017/4/11 14:57 .
 * e-mail: hxxx1992@163.com
 */

public class Constants {
    public static String IMAGE_BASE_URL;
    /**
     * 是否为debug模式
     */
    public static final boolean IS_DEBUG_MODE = true;


    public static final String SUB_TYPE = "subtype";

    public static final String FLODER_NAME = "hans";

    public static final String HOME_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + FLODER_NAME;


    /**
     * app存储目录路径
     */
    public static String DIRECTORY = "base_demo_android";

    /**
     * 默认图片存储路径
     */
    public static final String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + DIRECTORY
            + File.separator
            + "images";

    /**
     * 默认文件存储路径
     */
    public static final String DEFAULT_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + DIRECTORY;


    /**
     * 微信常量
     */

    public static final String WX_APP_SECRET = "8215d3450bf320369002c1fab36b1d1d";
    public static final String WX_APP_ID = "wx3490e598dd08cc63";    //这个APP_ID就是注册APP的时候生成的
    public static final String BUGLY_APP_ID = "0cf59e02b7";    //这个APP_ID就是注册APP的时候生成的


    private String sign = "629f39fe0343313cb7d36aa0efb66fe7";


    /**
     * map
     */
    public static final String map_ak = "a4Qz2VozZVZF2IF6IWcBzgtLRzDSeGPL";


}