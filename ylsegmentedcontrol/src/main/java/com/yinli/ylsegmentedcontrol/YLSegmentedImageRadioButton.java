package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

/**
 * Created by Yin Li on 26/03/15.
 */
public class YLSegmentedImageRadioButton extends RadioButton{

    private Resources res;
    private Drawable mIconDrawable;

    public Drawable getIconDrawable() {
        return mIconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.mIconDrawable = iconDrawable;
        invalidate();
    }

    public YLSegmentedImageRadioButton(Context context) {
        this(context, null);
    }

    public YLSegmentedImageRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public YLSegmentedImageRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        res = getResources();

        /* Retrieve styles attributes */
        TypedArray typedArray = res.obtainAttributes(attrs, R.styleable.YLSegmentedRadioGroup);
        try {
            int iconSrcId = typedArray.getInt(R.styleable.YLSegmentedImageRadioButton_iconImageSrc, -1);
            if (iconSrcId == -1) {
                throw new IllegalArgumentException("iconImageSrc must be set");
            }
            mIconDrawable = res.getDrawable(iconSrcId);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateButtonAppearance();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();


    }

    private void updateButtonAppearance() {
        float verticalPadding = res.getDimension(R.dimen.default_button_vertical_padding);
        float horizontalPadding = res.getDimension(R.dimen.default_button_horizontal_padding);
        setPadding((int) horizontalPadding, (int) verticalPadding, (int) horizontalPadding, (int) verticalPadding);
        setMinHeight((int) res.getDimension(R.dimen.default_button_min_height));
        setGravity(Gravity.CENTER);
        setButtonDrawable(new ColorDrawable((Color.TRANSPARENT)));
    }
}
