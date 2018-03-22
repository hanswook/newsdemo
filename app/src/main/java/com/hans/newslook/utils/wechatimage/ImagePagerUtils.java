package com.hans.newslook.utils.wechatimage;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by hasee on 2016/8/30.
 */
public class ImagePagerUtils {
    public static void imageBrower(int position, ArrayList<String> urls2, Context mContext) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }
    public static void imageBrower(int position, String url, Context mContext) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        ArrayList<String> urlList=new ArrayList<String>();
        urlList.add(url);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urlList);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }
    public static void imageBrower(int position,int drawableurl, Context mContext) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        ArrayList<Integer> urlList=new ArrayList<Integer>();
        urlList.add(drawableurl);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urlList);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }
}
