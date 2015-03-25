package com.yinli.ylsegmentedcontrol;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * Created by yinli on 22/03/15.
 */
public class YLSegmentedRadioButton extends RadioButton {

    public YLSegmentedRadioButton(Context context) {
        super(context);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*    public YLSegmentedRadioButton(Context context) {
        super(context, null);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }*/

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateButtonAppearance();
    }

    private void updateButtonAppearance() {
        Resources res = getResources();

        float verticalPadding = res.getDimension(R.dimen.default_button_vertical_padding);
        float horizontalPadding = res.getDimension(R.dimen.default_button_horizontal_padding);
        setPadding((int) horizontalPadding, (int) verticalPadding, (int) horizontalPadding, (int) verticalPadding);
        setMinHeight((int) res.getDimension(R.dimen.default_button_min_height));
        setGravity(Gravity.CENTER);
        setButtonDrawable(null);
    }
}
