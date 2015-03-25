package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

/**
 * Created by yinli on 22/03/15.
 */
public class YLSegmentedRadioGroup extends RadioGroup {

    /* Color attributes */
    private int mCheckedTextColor, mDisabledTextColor, mNormalTextColor, mPressedTextColor;
    private int mCheckedBackgroundColor, mDisabledBackgroundColor, mNormalBackgroundColor, mPressedBackgroundColor;
    private int mBorderColor;

    private Resources res;

    private enum ButtonType {
        LEFT, TOP, MIDDLE, RIGHT, BOTTOM, SINGLE
    }

    public YLSegmentedRadioGroup(Context context) {
        this(context, null);
    }

    public YLSegmentedRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        res = getResources();
        loadDefaultValues();
        init();
    }

    private void loadDefaultValues() {
        loadDefaultColors();
    }

    private void loadDefaultColors() {
        /* Load default colors for text */
        mCheckedTextColor = res.getColor(R.color.default_text_color_checked);
        mDisabledTextColor = res.getColor(R.color.default_text_color_disabled);
        mNormalTextColor = res.getColor(R.color.default_text_color_normal);
        mPressedTextColor = res.getColor(R.color.default_text_color_pressed);

        /* Load default colors for background */
        mCheckedBackgroundColor = res.getColor(R.color.default_background_color_checked);
        mDisabledBackgroundColor = res.getColor(R.color.default_background_color_disabled);
        mNormalBackgroundColor = res.getColor(R.color.default_background_color_normal);
//        mPressedBackgroundColor = Color.parseColor("#99" + res.getColor(R.color.default_background_color_checked));
        mPressedBackgroundColor = res.getColor(R.color.default_background_color_pressed);

        mBorderColor = res.getColor(R.color.default_border_color);
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count == 1) {
            updateButtonAppearance(getChildAt(0), ButtonType.SINGLE);
        } else if (count > 1) {
            int orientaion = getOrientation();
            if (orientaion == LinearLayout.HORIZONTAL) {
                updateButtonAppearance(getChildAt(0), ButtonType.LEFT);
                updateButtonAppearance(getChildAt(count - 1), ButtonType.RIGHT);
            } else {
                updateButtonAppearance(getChildAt(0), ButtonType.TOP);
                updateButtonAppearance(getChildAt(count - 1), ButtonType.BOTTOM);
            }
            for (int i = 1; i < count - 1; i++) {
                updateButtonAppearance(getChildAt(i), ButtonType.MIDDLE);
            }
        }
    }

    public void updateButtonAppearance(View view, ButtonType type) {
        updateButtonText(view);
        updateButtonIcon(view);
        updateButtonBackground(view, type);
    }

    private void updateButtonIcon(View view) {
        StateListDrawable stateListDrawable[] = new StateListDrawable[4];
        Drawable[] drawables = ((Button) view).getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                Bitmap bitmap = drawableToBitmap(drawables[i]);
                stateListDrawable[i] = new StateListDrawable();

                stateListDrawable[i].addState(new int[]{-android.R.attr.state_pressed, -android.R.attr.state_checked}, processIconDrawable(bitmap, mNormalTextColor));
                stateListDrawable[i].addState(new int[]{-android.R.attr.state_pressed, -android.R.attr.state_enabled}, processIconDrawable(bitmap, mDisabledTextColor));
                stateListDrawable[i].addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_checked}, processIconDrawable(bitmap, mCheckedTextColor));
                stateListDrawable[i].addState(new int[]{android.R.attr.state_pressed}, processIconDrawable(bitmap, mPressedTextColor));
            }
        }
        ((Button) view).setCompoundDrawablesWithIntrinsicBounds(stateListDrawable[0], stateListDrawable[1], stateListDrawable[2], stateListDrawable[3]);
    }

    private Drawable processIconDrawable(Bitmap bitmap, int color) {
        Bitmap temp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(temp);
        Paint p = new Paint();
        p.setColorFilter(new LightingColorFilter(color, 1));
        c.drawBitmap(bitmap, 0, 0, p);
        return new BitmapDrawable(temp);
    }

    private static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void updateButtonText(View view) {
        int[][] states = new int[][]{
                {-android.R.attr.state_pressed, -android.R.attr.state_checked},
                {-android.R.attr.state_pressed, -android.R.attr.state_enabled},
                {-android.R.attr.state_pressed, android.R.attr.state_checked},
                {android.R.attr.state_pressed}
        };

        int[] colors = new int[]{
                mNormalTextColor,
                mDisabledTextColor,
                mCheckedTextColor,
                mPressedTextColor
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);
        ((Button) view).setTextColor(colorStateList);
    }

    private void updateButtonBackground(View view, ButtonType type) {

        GradientDrawable normalDrawable = createDrawable(type);
        GradientDrawable disabledDrawable = createDrawable(type);
        GradientDrawable pressedDrawable = createDrawable(type);
        GradientDrawable checkedDrawable = createDrawable(type);

        normalDrawable.setColor(mNormalBackgroundColor);
        disabledDrawable.setColor(mDisabledBackgroundColor);
        pressedDrawable.setColor(mPressedBackgroundColor);
        checkedDrawable.setColor(mCheckedBackgroundColor);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed, -android.R.attr.state_checked}, normalDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed, -android.R.attr.state_enabled}, disabledDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_checked}, checkedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(stateListDrawable);
        } else {
            view.setBackground(stateListDrawable);
        }
    }

    private GradientDrawable createDrawable(ButtonType type) {

        float radius = res.getDimension(R.dimen.default_corner_radius);
        GradientDrawable drawable = (GradientDrawable) res.getDrawable(R.drawable.button_background).mutate();
        if (type == ButtonType.LEFT) {
            drawable.setCornerRadii(new float[]{radius, radius, 0, 0, 0, 0, radius, radius});
        } else if (type == ButtonType.RIGHT) {
            drawable.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        } else if (type == ButtonType.SINGLE) {
            drawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
        } else if (type == ButtonType.TOP) {
            drawable.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        } else if (type == ButtonType.BOTTOM) {
            drawable.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
        }

        return drawable;
    }

}
