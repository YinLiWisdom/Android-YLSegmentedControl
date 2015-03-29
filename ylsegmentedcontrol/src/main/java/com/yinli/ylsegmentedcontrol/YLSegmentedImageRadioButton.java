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
public class YLSegmentedImageRadioButton extends RadioButton {

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
        TypedArray typedArray = res.obtainAttributes(attrs, R.styleable.YLSegmentedImageRadioButton);
        try {
            mIconDrawable = typedArray.getDrawable(R.styleable.YLSegmentedImageRadioButton_iconImageSrc);
            if (mIconDrawable == null) {
                throw new IllegalArgumentException("iconImageSrc must be set");
            }
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (mIconDrawable != null) {
            int[] myDrawableState = getDrawableState();
            // Set the state of the Drawable
            mIconDrawable.setState(myDrawableState);
            invalidate();
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

        if (mIconDrawable != null) {
            int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            int drawableWidth = mIconDrawable.getIntrinsicWidth();
            int drawableHeight = mIconDrawable.getIntrinsicHeight();
            float scaleRatio = 1;
            if (drawableWidth > width || drawableHeight > height) {
                scaleRatio = Math.min((float) width / (float) drawableWidth, (float) height / (float) drawableHeight);
            }

            int left = (int) ((width - drawableWidth * scaleRatio) / 2.0f) + getPaddingLeft();
            int top = (int) ((height - drawableHeight * scaleRatio) / 2.0f) + getPaddingTop();
            int right = left + (int) (drawableWidth * scaleRatio);
            int bottom = top + (int) (drawableHeight * scaleRatio);
            mIconDrawable.setBounds(left, top, right, bottom);
            mIconDrawable.draw(canvas);
        }
    }

    private void updateButtonAppearance() {
        /* Set paddings but will prevent zero-padding by assign a default value */
        float verticalPadding = res.getDimension(R.dimen.default_image_radio_button_vertical_padding);
        float horizontalPadding = res.getDimension(R.dimen.default_image_radio_button_horizontal_padding);
        float paddingLeft = getPaddingLeft() <= 0 ? horizontalPadding : getPaddingLeft();
        float paddingRight = getPaddingRight() <= 0 ? horizontalPadding : getPaddingRight();
        float paddingTop = getPaddingTop() <= 0 ? verticalPadding : getPaddingTop();
        float paddingBottom = getPaddingBottom() <= 0 ? verticalPadding : getPaddingBottom();
        setPadding((int) horizontalPadding, (int) verticalPadding, (int) horizontalPadding, (int) verticalPadding);

        setMinHeight((int) res.getDimension(R.dimen.default_button_min_height));
        setGravity(Gravity.CENTER);
        setButtonDrawable(new ColorDrawable((Color.TRANSPARENT)));
    }
}
