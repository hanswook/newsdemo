package com.hans.newslook.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

import com.hans.newslook.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hans
 * date: 2018/3/30 10:58.
 * e-mail: hxxx1992@163.com
 */

public class SaveImageUtils {
    /**
     * @param src      文件路径
     * @param context
     * @param pathName 文件存储路径
     *                 根据路径 获取图片，然后打上水印，并生成文件存储于此
     */
    public static File createFileWithWater(Bitmap src, Context context, String pathName, String location) {
        /**
         * 实例baos,将src写入baos，实例bais
         * */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(bais, null, newOpts);
        newOpts.inJustDecodeBounds = false;

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        LogUtils.e(w + "---------------" + h);
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1280f;// 这里设置高度为1920f
        float ww = 720f;// 这里设置宽度为1080f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        LogUtils.e(be + "scale缩放倍数");
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        bais.reset();
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bais = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(bais, null, newOpts);
        baos.reset();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Time t = new Time();
        t.setToNow();
        int www = bitmap.getWidth();
        int hhh = bitmap.getHeight();
        String mstrTitle = "拍照时间：" + t.hour + ":" + t.minute + ":" + t.second;
        Bitmap bmpTemp = Bitmap.createBitmap(www, hhh, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bmpTemp);
        Paint p = new Paint();
        String familyName = "宋体";
        Typeface font = Typeface.create(familyName, Typeface.NORMAL);
        p.setColor(context.getResources().getColor(R.color.waterMark));
        p.setTypeface(font);
        p.setTextSize(30);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawText(mstrTitle, 0, hhh / 2, p);
        canvas.drawText(location, 0, hhh / 2 - 30, p);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        bmpTemp.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (options >= 10 && baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bmpTemp.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            if (options <= 0) {
                options = 10;
            }
            LogUtils.e(options + "质量倍数:" + baos.toByteArray().length / 1024);
            options -= 20;// 每次都减少10
        }
        bais.reset();
        bais = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        bmpTemp = BitmapFactory.decodeStream(bais);// 把ByteArrayInputStream数据生成图片
        baos.reset();
        bais.reset();
        File file = new File(pathName);
        FileOutputStream bitmapWtriter = null;
        try {
            bitmapWtriter = new FileOutputStream(file);
            if (bmpTemp.compress(Bitmap.CompressFormat.JPEG, 100, bitmapWtriter)) {
                Log.d("TAG", "保存文件成功!");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bmpTemp != null) {
                    bmpTemp.recycle();
                }
                if (src != null) {
                    src.recycle();
                }
                baos.flush();
                baos.close();
                bais.close();
                bitmapWtriter.flush();
                bitmapWtriter.close();
                System.gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;

    }

    /**
     * 生成二维码图片保存在本地。
     *
     * @param src      文件路径
     * @param context
     * @param pathName 文件存储路径
     *                 根据路径 获取图片，然后打上水印，并生成文件存储于此
     */
    public static File createFile(Bitmap src, Context context, String pathName) {

        File pathFile = new File(pathName);
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }

        String imageName = pathName + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";
        LogUtils.e("SaveImageUtils", "imageName：" + imageName);
        File file = new File(imageName);
        FileOutputStream bitmapWtriter = null;
        try {
            bitmapWtriter = new FileOutputStream(file);
            LogUtils.e("SaveImageUtils", "createFile:src：" + src.isRecycled());
            if (src.compress(Bitmap.CompressFormat.JPEG, 100, bitmapWtriter)) {
                LogUtils.e("TAG", "保存文件成功!");
                ToastUtils.show("已保存于：" + pathName);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                assert src != null;
                src.recycle();
                assert bitmapWtriter != null;
                bitmapWtriter.close();
                System.gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;

    }

    /**
     * 生成二维码文件。二
     */
    public static void saveBitmapFile(Bitmap bitmap) {
        String hansPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "hans";
        LogUtils.e("SaveImageUtils", "saveBitmapFile:bitmap：" + bitmap.isRecycled());

        File temp = new File(hansPath);//要保存文件先创建文件夹
        if (!temp.exists()) {
            LogUtils.e("SaveImageUtils", "saveBitmapFile:temp：" + temp.exists());
            temp.mkdir();
        }
        ////重复保存时，覆盖原同名图片
        File file = new File(hansPath + File.separator + "1.jpg");//将要保存图片的路径和图片名称
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            LogUtils.e("SaveImageUtils", "saveBitmapFile:save success：");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
