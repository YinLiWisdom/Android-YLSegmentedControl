package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
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

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateButtonAppearance();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        resizeButtonIcon();
    }

    private void updateButtonAppearance() {
        Resources res = getResources();

        float verticalPadding = res.getDimension(R.dimen.default_button_vertical_padding);
        float horizontalPadding = res.getDimension(R.dimen.default_button_horizontal_padding);
        setPadding((int) horizontalPadding, (int) verticalPadding, (int) horizontalPadding, (int) verticalPadding);
        setMinHeight((int) res.getDimension(R.dimen.default_button_min_height));
        setGravity(Gravity.CENTER);
        setButtonDrawable(new ColorDrawable((Color.TRANSPARENT)));
    }

    private void resizeButtonIcon() {
        Drawable[] drawables = getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                float scaleFactor = (float)getHeight() / (float)drawables[i].getIntrinsicHeight();
                drawables[i] = scaleDrawable(drawables[i], scaleFactor);
            }
        }
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private Drawable scaleDrawable (Drawable drawable, float scaleFactor) {
        if ((drawable == null) || !(drawable instanceof BitmapDrawable)) {
            return drawable;
        }

        Bitmap b = ((BitmapDrawable)drawable).getBitmap();

        int sizeX = Math.round(drawable.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(drawable.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        drawable = new BitmapDrawable(getResources(), bitmapResized);
        return drawable;
    }
}
