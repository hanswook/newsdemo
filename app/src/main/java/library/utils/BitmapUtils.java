package com.wallan.multimediacamera.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;

import com.wallan.multimediacamera.library.camera.CameraHomeFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hanxu on 2018/5/22.
 */

public class BitmapUtils {

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    public static Bitmap cropBitmapWithMode(Bitmap bitmap, Camera.Size size, int photoMode, boolean isface) {
        if (bitmap.getWidth() > bitmap.getHeight()) {
            bitmap = rotate(bitmap);
        }
        if (isface) {
            bitmap = verticalMirrorBitmap(bitmap);
        }

        /* 拿到bitmap尺寸,相机语言size尺寸。 计算需要裁切的bitmap位置。 */
        double bitmapWidth = bitmap.getWidth();
        double bitmapHeight = bitmap.getHeight();

        double priviewWidth = size.height;
        double priviewHeight = size.width;


        double afterWidth = 0d;
        double afterHeight = 0d;

        double xIndex = 0d;
        double yIndex = 0d;

        int sizeMode = 0;

        double scale = 0d;      //scale的值是bitmap/priview  值应该大于0/ 为放大比例。
        /*
        * sizemode 1: bitmap的 width需要被裁切 ,以height比率为基准。
        * sizemode 2: bitmap的 height需要被裁切 ，以width比率为基准。
        * sizemode 3: bitmap的 不需要被裁切
        *
        * */
        if ((bitmapWidth / bitmapHeight) > (priviewWidth / priviewHeight)) {
            sizeMode = 1;
        } else if ((bitmapWidth / bitmapHeight) < (priviewWidth / priviewHeight)) {
            sizeMode = 2;
        } else if ((bitmapWidth / bitmapHeight) == (priviewWidth / priviewHeight)) {
            sizeMode = 3;
        }
        switch (sizeMode) {
            case 1:
                scale = bitmapHeight / priviewHeight;
                break;
            case 2:
                scale = bitmapWidth / priviewWidth;
                break;
            case 3:
                scale = bitmapHeight / priviewHeight;
                break;
            default:
                scale = bitmapHeight / priviewHeight;
                break;
        }
        afterWidth = scale * priviewWidth;
        afterHeight = scale * priviewHeight;

        /* 计算 展示比例 和 预览尺寸 对比。 以预览尺寸为基准设置长宽比，计算预览图在裁切好的bitmap中的位置。*/
        double cropAspectRatio = 0d;
        switch (photoMode) {
            case CameraHomeFragment.FULL_SCREEN_MODE:
                cropAspectRatio = priviewHeight / priviewWidth;
                break;
            case CameraHomeFragment.FOUR_TO_THREE_SCREEN_MODE:
                cropAspectRatio = 4d / 3d;
                break;
            case CameraHomeFragment.THREE_TO_TWO_SCREEN_MODE:
                cropAspectRatio = 3d / 2d;
                break;
            case CameraHomeFragment.ONE_TO_ONE_SCREEN_MODE:
                cropAspectRatio = 1d;
                break;

        }

        /**
         *  高 宽 比。 一般来讲,对比预览尺寸与高宽比，计算以那条边为缩放基准。
         *  h/w小于预期高宽比的，以h为基准，裁掉w的左右等距离。
         *  h/w 大于 预期高宽比，以w为基准，裁掉h的上下等距离。
         *  h/w 等于 预期高宽比，不用裁切，直接得到裁切后尺寸与裁切前一致。。
         *  */
        int priviewMode = 0;
        if ((priviewHeight / priviewWidth) < (cropAspectRatio)) {
            priviewMode = 1;
        } else if ((priviewHeight / priviewWidth) > (cropAspectRatio)) {
            priviewMode = 2;
        } else if ((priviewHeight / priviewWidth) == (cropAspectRatio)) {
            priviewMode = 3;
        }
        double cropWidth = 0d;
        double cropHeight = 0d;

        switch (priviewMode) {
            case 1:
                cropHeight = afterHeight;
                cropWidth = cropHeight / cropAspectRatio;
                break;
            case 2:
                cropWidth = afterWidth;
                cropHeight = cropWidth * cropAspectRatio;
                break;
            case 3:
            default:
                cropHeight = afterHeight;
                cropWidth = afterWidth;
                break;
        }

        /* 计算xIndex, yIndex 分别相对bitmap的偏移尺寸。*/

        xIndex = (bitmapWidth - cropWidth) / 2d;
        yIndex = (bitmapHeight - cropHeight) / 2d;
        return Bitmap.createBitmap(bitmap, (int) xIndex, (int) (yIndex), (int) cropWidth, (int) cropHeight, null, false);
    }

    public static Bitmap verticalMirrorBitmap(Bitmap bitmap) {

        float[] floats = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};

        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return null;
    }

    public static Bitmap horizontalMirrorBitmap(Bitmap bitmap) {

        float[] floats = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};

        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return null;
    }


    /**
     * 多张连拍。目前只进行了尺寸压缩。没进行质量压缩。
     */
    public static Bitmap createMultiShotBitmap(List<String> multiShotPathCache, int multiShotMode) {

        List<Bitmap> bitmapChche = new ArrayList<>();
        int inSampleSize = 2;
        if (multiShotMode == CameraHomeFragment.MULTISHOT_MODE_NINE) {
            inSampleSize = 3;
        }
        for (String path : multiShotPathCache) {
            bitmapChche.add(compress(path, inSampleSize));
        }

        /**
         * 根据模式，计算出targetBitmap画布的尺寸。
         * */

        Bitmap atomBitmap = bitmapChche.get(0);
        int atomHeight = atomBitmap.getHeight();
        int atomWidth = atomBitmap.getWidth();

        int targetHeight = atomHeight;
        int targetWidth = atomWidth;

        switch (multiShotMode) {
            case CameraHomeFragment.MULTISHOT_MODE_TWO_H:
                targetHeight = atomHeight * 2;
                targetWidth = atomWidth;
                break;
            case CameraHomeFragment.MULTISHOT_MODE_TWO_W:
                targetHeight = atomHeight;
                targetWidth = atomWidth * 2;
                break;
            case CameraHomeFragment.MULTISHOT_MODE_FOUR:
                targetHeight = atomHeight * 2;
                targetWidth = atomWidth * 2;
                break;
            case CameraHomeFragment.MULTISHOT_MODE_NINE:
                targetHeight = atomHeight * 3;
                targetWidth = atomWidth * 3;
                break;
        }
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBitmap);

        /**
         * 根据模式，向bitmap向draw 小bitmap。
         * **/

        switch (multiShotMode) {
            case CameraHomeFragment.MULTISHOT_MODE_TWO_H:
                for (int i = 0; i < bitmapChche.size(); i++) {
                    targetCanvas.drawBitmap(bitmapChche.get(i), null, new Rect(0, atomHeight * i, atomWidth, atomHeight * i + atomHeight), null);
                }
                break;
            case CameraHomeFragment.MULTISHOT_MODE_TWO_W:
                for (int i = 0; i < bitmapChche.size(); i++) {
                    targetCanvas.drawBitmap(bitmapChche.get(i), null, new Rect(atomWidth * (i % 2), atomHeight * (i / 2), atomWidth * (i % 2) + atomWidth, atomHeight * (i / 2) + atomHeight), null);
                }
                break;
            case CameraHomeFragment.MULTISHOT_MODE_FOUR:
                for (int i = 0; i < bitmapChche.size(); i++) {
                    targetCanvas.drawBitmap(bitmapChche.get(i), null, new Rect(atomWidth * (i % 2), atomHeight * (i / 2), atomWidth * (i % 2) + atomWidth, atomHeight * (i / 2) + atomHeight), null);
                }
                break;
            case CameraHomeFragment.MULTISHOT_MODE_NINE:
                for (int i = 0; i < bitmapChche.size(); i++) {
                    targetCanvas.drawBitmap(bitmapChche.get(i), null, new Rect(atomWidth * (i % 3), atomHeight * (i / 3), atomWidth * (i % 3) + atomWidth, atomHeight * (i / 3) + atomHeight), null);
                }
                break;
        }

        return targetBitmap;
    }


    public static Bitmap compress(String srcPath, int inSampleSize) {

        BitmapFactory.Options newOpts = getBitmapOption(inSampleSize);
        return BitmapFactory.decodeFile(srcPath, newOpts);// 压缩好比例大小后再进行质量压缩
    }

    private static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }


    public static Bitmap rotate(Bitmap bitmap) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            try {
                m.setRotate(90);//90就是我们需要选择的90度
                Bitmap bmp2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap = bmp2;
            } catch (Exception ex) {
                System.out.print("创建图片失败！" + ex);
            }
        }
        return bitmap;
    }
}
