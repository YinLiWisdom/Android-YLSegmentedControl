package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by yinli on 22/03/15.
 */
public class YLSegmentedRadioButton extends RadioButton {

    public YLSegmentedRadioButton(Context context) {
        this(context, null);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
