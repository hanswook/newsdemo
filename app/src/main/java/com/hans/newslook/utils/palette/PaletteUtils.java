package com.hans.newslook.utils.palette;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.hans.newslook.callbacks.PaletteCallBack;
import com.hans.newslook.utils.baseutils.LogUtils;

import java.util.List;
import java.util.Observable;

/**
 * Created by hans
 * date: 2018/4/11 17:15.
 * e-mail: hxxx1992@163.com
 */

public class PaletteUtils {


    public static void getBitmap(Bitmap bitmap, PaletteCallBack paletteCallBack) {

        new Thread(new Runnable() {
            @Override
            public void run() {
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

                        List<Palette.Swatch> swatches = palette.getSwatches();
                        Palette.Swatch populationSwatch = getPopulationColor(swatches);
                        paletteCallBack.onCallBack(populationSwatch);
                    }
                });
            }
        }).start();

    }

    private static Palette.Swatch getPopulationColor(List<Palette.Swatch> swatches) {
        if (swatches == null) {
            return null;
        }
        if (swatches.size() <= 0) {
            return null;
        }
        Palette.Swatch currentSwatch = null;
        for (Palette.Swatch swatch : swatches) {
            if (swatch != null && swatch.getPopulation() > 0) {
                if (currentSwatch == null) {
                    currentSwatch = swatch;
                }
                if (swatch.getPopulation() > currentSwatch.getPopulation()) {
                    currentSwatch = swatch;
                }
            }
        }
        return currentSwatch;

    }
}
