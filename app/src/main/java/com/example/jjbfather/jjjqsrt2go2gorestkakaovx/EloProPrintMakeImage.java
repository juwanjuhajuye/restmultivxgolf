package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;

import static com.starmicronics.starioextension.StarIoExt.Emulation;


public class EloProPrintMakeImage {

    public static final String TAG = "EloProPrintMake";

    public static final int PAPER_SIZE_TWO_INCH = 384;
    public static final int PAPER_SIZE_THREE_INCH = 576;
    public static final int PAPER_SIZE_FOUR_INCH = 832;

    public static final Emulation emulation = Emulation.StarGraphic;

    static public Bitmap createBitmapFromText(String printText, int textSize, int printWidth, Typeface typeface, String paramAlignment) {
        Paint paint = new Paint();
        Bitmap bitmap;
        Canvas canvas;

        paint.setTextSize(textSize);
        paint.setTypeface(typeface);

        paint.getTextBounds(printText, 0, printText.length(), new Rect());

        TextPaint textPaint = new TextPaint(paint);
        StaticLayout staticLayout = new StaticLayout(printText, textPaint, printWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        switch (paramAlignment) {
            case "LEFT" : {
                staticLayout = new StaticLayout(printText, textPaint, printWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
                break;
            }
            case "CENTER" : {
                staticLayout = new StaticLayout(printText, textPaint, printWidth, Layout.Alignment.ALIGN_CENTER, 1, 0, false);
                break;
            }
            case "RIGHT" : {
                staticLayout = new StaticLayout(printText, textPaint, printWidth, Layout.Alignment.ALIGN_OPPOSITE, 1, 0, false);
                break;
            }
        }

        // Create bitmap
        bitmap = Bitmap.createBitmap(staticLayout.getWidth(), staticLayout.getHeight(), Bitmap.Config.ARGB_8888);

        // Create canvas
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        canvas.translate(0, 0);
        staticLayout.draw(canvas);

        return bitmap;
    }

    public static byte[] createScaleRasterReceiptData(int width, boolean bothScale, Bitmap paramImage) {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);
        builder.beginDocument();

        // add text info
        //Bitmap image = create3inchRasterReceiptImage(paramContents, paramAlignment);
        Bitmap image = paramImage;
        builder.appendBitmap(image, false, width, bothScale);

        return builder.getCommands();
    }


    public static byte[] getPrintContentsForEloPro(Bitmap paramImage)
    {
        return createScaleRasterReceiptData(PAPER_SIZE_THREE_INCH, true, paramImage);
    }
}
