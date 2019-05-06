package com.example.usk.glotus_final.SuperviserApp.BluetoothPrintService;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Other {
    private static final int WIDTH_58 = 384;
    private static final int WIDTH_80 = 576;
    private static final byte[] chartobyte;
    private static int[] p0;
    private static int[] p1;
    private static int[] p2;
    private static int[] p3;
    private static int[] p4;
    private static int[] p5;
    private static int[] p6;
    public byte[] buf;
    public int index = 0;

    static {
        int[] iArr = new int[2];
        iArr[1] = 128;
        p0 = iArr;
        iArr = new int[2];
        iArr[1] = 64;
        p1 = iArr;
        iArr = new int[2];
        iArr[1] = 32;
        p2 = iArr;
        iArr = new int[2];
        iArr[1] = 16;
        p3 = iArr;
        iArr = new int[2];
        iArr[1] = 8;
        p4 = iArr;
        iArr = new int[2];
        iArr[1] = 4;
        p5 = iArr;
        iArr = new int[2];
        iArr[1] = 2;
        p6 = iArr;
        byte[] bArr = new byte[23];
        bArr[1] = (byte) 1;
        bArr[2] = (byte) 2;
        bArr[3] = (byte) 3;
        bArr[4] = (byte) 4;
        bArr[5] = (byte) 5;
        bArr[6] = (byte) 6;
        bArr[7] = (byte) 7;
        bArr[8] = (byte) 8;
        bArr[9] = (byte) 9;
        bArr[17] = (byte) 10;
        bArr[18] = (byte) 11;
        bArr[19] = (byte) 12;
        bArr[20] = (byte) 13;
        bArr[21] = (byte) 14;
        bArr[22] = (byte) 15;
        chartobyte = bArr;
    }

    public Other(int length) {
        this.buf = new byte[length];
    }

    public static StringBuilder RemoveChar(String str, char c) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char tmp = str.charAt(i);
            if (tmp != c) {
                sb.append(tmp);
            }
        }
        return sb;
    }

    public static boolean IsHexChar(char c) {
        if ((c < '0' || c > '9') && ((c < 'a' || c > 'f') && (c < 'A' || c > 'F'))) {
            return false;
        }
        return true;
    }

    public static byte HexCharsToByte(char ch, char cl) {
        return (byte) (((chartobyte[ch - 48] << 4) & 240) | (chartobyte[cl - 48] & 15));
    }

    public static byte[] HexStringToBytes(String str) {
        int count = str.length();
        if (count % 2 != 0) {
            return null;
        }
        byte[] data = new byte[(count / 2)];
        for (int i = 0; i < count; i += 2) {
            char ch = str.charAt(i);
            char cl = str.charAt(i + 1);
            if (!IsHexChar(ch) || !IsHexChar(cl)) {
                return null;
            }
            if (ch >= 'a') {
                ch = (char) (ch - 32);
            }
            if (cl >= 'a') {
                cl = (char) (cl - 32);
            }
            data[i / 2] = HexCharsToByte(ch, cl);
        }
        return data;
    }

    public void UTF8ToGBK(String Data) {
        try {
            for (byte b : Data.getBytes("GBK")) {
                byte[] bArr = this.buf;
                int i = this.index;
                this.index = i + 1;
                bArr[i] = b;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] StringTOGBK(String data) {
        byte[] buffer = null;
        try {
            buffer = data.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    public static byte[] byteArraysToBytes(byte[][] data) {
        int i;
        int length = 0;
        for (byte[] length2 : data) {
            length += length2.length;
        }
        byte[] send = new byte[length];
        int k = 0;
        for (i = 0; i < data.length; i++) {
            int j = 0;
            while (j < data[i].length) {
                int k2 = k + 1;
                send[k] = data[i][j];
                j++;
                k = k2;
            }
        }
        return send;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        float scaleWidth = ((float) w) / ((float) width);
        float scaleHeight = ((float) h) / ((float) height);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        Bitmap bmpGrayscale = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        c.drawBitmap(bmpOriginal, 0.0f, 0.0f, paint);
        return bmpGrayscale;
    }

    public static void saveMyBitmap(Bitmap mBitmap, String name) {
        FileOutputStream fileOutputStream;
        File f = new File(Environment.getExternalStorageDirectory().getPath(), name);
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        try {
            FileOutputStream fOut = new FileOutputStream(f);
            try {
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                fileOutputStream = fOut;
            } catch (FileNotFoundException e2) {
                fileOutputStream = fOut;
            } catch (IOException e3) {
                fileOutputStream = fOut;
            }
        } catch (FileNotFoundException e4) {
            Log.i("toleTag", "FileNotFound other.java");
        }
    }

    public static byte[] thresholdToBWPic(Bitmap mBitmap,int srift_s_x) {
        int[] pixels = new int[(mBitmap.getWidth() * mBitmap.getHeight())];
        byte[] data = new byte[(mBitmap.getWidth() * mBitmap.getHeight())];
        mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        format_K_threshold(pixels, mBitmap.getWidth(), mBitmap.getHeight(), data,srift_s_x);
        return data;
    }

    private static void format_K_threshold(int[] orgpixels, int xsize, int ysize, byte[] despixels,int srift_s_x) {//There we can change xsize of shrift
        int i;
        int j;
        int graytotal = 0;
        int k = 0;
        for (i = 0; i < ysize; i++) {
            for (j = 0; j < xsize; j++) {
                graytotal += orgpixels[k] & srift_s_x;
                k++;
            }
        }
        int grayave = (graytotal / ysize) / xsize;
        k = 0;
        for (i = 0; i < ysize; i++) {
            for (j = 0; j < xsize; j++) {
                if ((orgpixels[k] & srift_s_x) > grayave) {
                    despixels[k] = (byte) 0;
                } else {
                    despixels[k] = (byte) 1;
                }
                k++;
            }
        }
    }

    public static void overWriteBitmap(Bitmap mBitmap, byte[] dithered) {
        int ysize = mBitmap.getHeight();
        int xsize = mBitmap.getWidth();
        int k = 0;
        for (int i = 0; i < ysize; i++) {
            for (int j = 0; j < xsize; j++) {
                if (dithered[k] == (byte) 0) {
                    mBitmap.setPixel(j, i, -1);
                } else {
                    mBitmap.setPixel(j, i, -16777216);
                }
                k++;
            }
        }
    }

    public static byte[] eachLinePixToCmd(byte[] src, int nWidth, int nMode) {
        int nHeight = src.length / nWidth;
        int nBytesPerLine = nWidth / 8;
        byte[] data = new byte[((nBytesPerLine + 8) * nHeight)];

        int k = 0;
        for (int i = 0; i < nHeight; i++) {
            int offset = i * (nBytesPerLine + 8);
            data[offset + 0] = (byte) 29;
            data[offset + 1] = (byte) 118;
            data[offset + 2] = (byte) 48;
            data[offset + 3] = (byte) (nMode & 1);
            data[offset + 4] = (byte) (nBytesPerLine % 256);
            data[offset + 5] = (byte) (nBytesPerLine / 256);
            data[offset + 6] = (byte) 1;
            data[offset + 7] = (byte) 0;
            for (int j = 0; j < nBytesPerLine; j++) {
                data[(offset + 8) + j] = (byte) (((((((p0[src[k]] + p1[src[k + 1]]) + p2[src[k + 2]]) + p3[src[k + 3]]) + p4[src[k + 4]]) + p5[src[k + 5]]) + p6[src[k + 6]]) + src[k + 7]);
                k += 8;
            }
        }

        return data;
    }
    @SuppressLint("WrongConstant")
    public static Bitmap createAppIconText(String txt, String font_name , float size, int hight, Layout.Alignment align) {
        Bitmap canvasBitmap = Bitmap.createBitmap(WIDTH_58, hight, Bitmap.Config.ARGB_8888);
        int width = canvasBitmap.getWidth()+65;
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.setBitmap(canvasBitmap);
        canvas.drawColor(-1);
        TextPaint paint = new TextPaint();
        paint.setColor(Color.parseColor("#FF0A0909"));
        paint.setTextSize(size);
        paint.setAntiAlias(false);
        paint.setTypeface(Typeface.create(font_name,Typeface.BOLD));
        paint.setFakeBoldText(false);
        StaticLayout layout = new StaticLayout(txt, 0, txt.length(), paint, width,align, 0.8f, 0.0f, true, TextUtils.TruncateAt.END, width);
        canvas.translate(0.0f, 0.0f);
        layout.draw(canvas);
        canvas.save();
        canvas.restore();
        return canvasBitmap;

    }


}
