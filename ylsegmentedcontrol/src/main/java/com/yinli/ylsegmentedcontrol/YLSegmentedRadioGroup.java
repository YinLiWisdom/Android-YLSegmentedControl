package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
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
        updateButtonBackground(view, type);
        updateIcon(view);
    }

    private void updateIcon(View view) {
        Drawable[] drawables = ((Button) view).getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                drawables[i].setColorFilter(mNormalTextColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
        ((Button) view).setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void updateButtonText(View view) {
        int[][] states = new int[][]{
                {-android.R.attr.state_checked},
                {-android.R.attr.state_enabled},
                {android.R.attr.state_checked},
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
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, normalDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, disabledDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
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
