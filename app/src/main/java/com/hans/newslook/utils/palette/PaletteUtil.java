package com.hans.newslook.utils.palette;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.hans.newslook.utils.baseutils.LogUtils;

/**
 * Created by hans
 * date: 2018/4/11 17:15.
 * e-mail: hxxx1992@163.com
 */

public class PaletteUtil {
    public static void getBitmap(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //1.活力颜色
                Palette.Swatch a = palette.getVibrantSwatch();
                //2.亮的活力颜色
                Palette.Swatch b = palette.getLightVibrantSwatch();
                //3.暗的活力颜色
                Palette.Swatch c = palette.getDarkVibrantSwatch();
                //4.柔色
                Palette.Swatch d = palette.getMutedSwatch();
                //5.亮的柔色
                Palette.Swatch e = palette.getLightMutedSwatch();
                //6.暗的柔色
                Palette.Swatch f = palette.getDarkMutedSwatch();
                f.getRgb(); //rgb颜色
                f.getTitleTextColor();//文本颜色

                //返回float[]，可以进行修改，后使用ColorUtils工具类进行转换
                f.getHsl();
                f.getBodyTextColor();//和文本颜色一样

                LogUtils.e("a:" + a + "b:" + b + "c:" + c + "d:" + d + "e:" + e + "f:" + f);
            }
        });
    }
}
