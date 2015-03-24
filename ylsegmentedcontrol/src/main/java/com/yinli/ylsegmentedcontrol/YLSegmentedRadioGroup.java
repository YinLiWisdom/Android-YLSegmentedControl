package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * Created by yinli on 22/03/15.
 */
public class YLSegmentedRadioGroup extends RadioGroup {

    /* Color attributes */
    private int mCheckedTextColor, mDisabledTextColor, mNormalTextColor, mPressedTextColor;
    private int mCheckedBackgroundColor, mDisabledBackgroundColor, mNormalBackgroundColor, mPressedBackgroundColor;


    public YLSegmentedRadioGroup(Context context) {
        this(context, null);
    }

    public YLSegmentedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    private void loadDefaultValues() {

    }

    private void loadDefaultColors() {
        Resources res = getResources();
        mCheckedTextColor = res.getColor(R.color.default_text_color_normal);
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateButtonAppearance();
    }

    public void updateButtonAppearance() {
        int count = getChildCount();
        if (count == 1) {

        } else if (count > 1) {

        }
    }

    private void updateButtonText(View view, Color color) {
        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked}, // normal
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] { android.R.attr.state_checked}, // checked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        Resources res = getResources();
        int[] colors = new int[] {

        };

        ColorStateList myList = new ColorStateList(states, colors);
    }

    private void updateButtonBackground() {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.RED, Color.GREEN});
        gd.setStroke(10, Color.BLUE);
    }
}
