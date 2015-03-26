package com.yinli.ylsegmentedcontrol.utils;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Yin Li on 26/03/15.
 */
public class YLMeasureHelper {

    public static Drawable scaleDrawable (Drawable drawable, float scaleFactor) {
        if ((drawable == null) || !(drawable instanceof BitmapDrawable)) {
            return drawable;
        }

        if (scaleFactor <= 0) {
            scaleFactor = 1;
        }

        Bitmap b = ((BitmapDrawable)drawable).getBitmap();

        int sizeX = Math.round(drawable.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(drawable.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        return new BitmapDrawable(bitmapResized);
    }

    public static Rect getTextBounds(float textSize, String text) {
        Rect bounds = new Rect();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);

        paint.getTextBounds(text, 0, text.length(), bounds);

        bounds.height();
        return bounds;
    }
}
