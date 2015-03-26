package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
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

import com.yinli.ylsegmentedcontrol.utils.YLColorHelper;

/**
 * Created by yinli on 22/03/15.
 */
public class YLSegmentedRadioGroup extends RadioGroup {

    /* Color attributes */
    private int mCheckedTextColor, mDisabledTextColor, mNormalTextColor, mPressedTextColor;
    private int mCheckedBackgroundColor, mDisabledBackgroundColor, mNormalBackgroundColor, mPressedBackgroundColor;
    private int mBorderColor;
    private float mStrokeWidth;

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
        /* Load default colors for text */
        final int defaultCheckedTextColor = res.getColor(R.color.default_text_color_checked);
        final int defaultDisabledTextColor = res.getColor(R.color.default_text_color_disabled);
        final int defaultNormalTextColor = res.getColor(R.color.default_text_color_normal);
        final int defaultPressedTextColor = res.getColor(R.color.default_text_color_pressed);

        /* Load default colors for background */
        final int defaultCheckedBackgroundColor = res.getColor(R.color.default_background_color_checked);
        final int defaultDisabledBackgroundColor = res.getColor(R.color.default_background_color_disabled);
        final int defaultNormalBackgroundColor = res.getColor(R.color.default_background_color_normal);
        /* Convert checked background color to pressed background color by reducing opacity */
        final int defaultPressedBackgroundColor = YLColorHelper.reduceColorOpacity(defaultCheckedBackgroundColor);

        final int defaultBorderColor = res.getColor(R.color.default_border_color);

        final float defaultStrokeWidth = res.getDimension(R.dimen.default_stroke_width);

        /* Retrieve styles attributes */
        TypedArray typedArray = res.obtainAttributes(attrs, R.styleable.YLSegmentedRadioGroup);
        try {
            mCheckedTextColor = typedArray.getColor(R.styleable.YLSegmentedRadioGroup_inactiveColor, defaultCheckedTextColor);
            mDisabledTextColor = defaultDisabledTextColor;
            mNormalTextColor = typedArray.getColor(R.styleable.YLSegmentedRadioGroup_activeColor, defaultNormalTextColor);
            mPressedTextColor = typedArray.getColor(R.styleable.YLSegmentedRadioGroup_inactiveColor, defaultPressedTextColor);

            mCheckedBackgroundColor = typedArray.getColor(R.styleable.YLSegmentedRadioGroup_activeColor, defaultCheckedBackgroundColor);
            mDisabledBackgroundColor = defaultDisabledBackgroundColor;
            mNormalBackgroundColor = typedArray.getColor(R.styleable.YLSegmentedRadioGroup_activeColor, defaultNormalBackgroundColor);
            mPressedBackgroundColor = YLColorHelper.reduceColorOpacity(mCheckedBackgroundColor);

            mBorderColor = typedArray.getColor(R.styleable.YLSegmentedRadioGroup_borderColor, defaultBorderColor);

            mStrokeWidth = typedArray.getDimension(R.styleable.YLSegmentedRadioGroup_borderWeight, defaultStrokeWidth);
        } finally {
            typedArray.recycle();
        }
        init();
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count == 1) {
            updateButtonAppearance(getChildAt(0), ButtonType.SINGLE, -1);
        } else if (count > 1) {
            int orientaion = getOrientation();
            if (orientaion == LinearLayout.HORIZONTAL) {
                updateButtonAppearance(getChildAt(0), ButtonType.LEFT, orientaion);
                updateButtonAppearance(getChildAt(count - 1), ButtonType.RIGHT, orientaion);
            } else {
                updateButtonAppearance(getChildAt(0), ButtonType.TOP, orientaion);
                updateButtonAppearance(getChildAt(count - 1), ButtonType.BOTTOM, orientaion);
            }
            for (int i = 1; i < count - 1; i++) {
                updateButtonAppearance(getChildAt(i), ButtonType.MIDDLE, orientaion);
            }
        }
    }

    public void updateButtonAppearance(View view, ButtonType type, int orientation) {
        /* Handle duplicated stroke */
        int offset = (int)-mStrokeWidth;
        if(orientation == LinearLayout.HORIZONTAL) {
            if (type == ButtonType.MIDDLE || type == ButtonType.RIGHT) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(offset, 0, 0, 0);
                view.setLayoutParams(params);
            }
        } else {
            if (type == ButtonType.MIDDLE || type == ButtonType.BOTTOM) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(0, offset, 0, 0);
                view.setLayoutParams(params);
            }
        }

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
        p.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
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
