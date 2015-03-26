package com.yinli.ylsegmentedcontrol.utils;

import android.graphics.Color;

/**
 * Created by Yin Li on 26/03/15.
 */
public class YLColorHelper {
    public static int reduceColorOpacity(int color) {
        String colorHex = String.format("%06X", 0xFFFFFF & color);
        int resultColor = Color.parseColor("#55" + colorHex);
        return resultColor;
    }
}
