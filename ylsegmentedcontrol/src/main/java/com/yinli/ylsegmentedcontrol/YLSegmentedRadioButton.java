package com.yinli.ylsegmentedcontrol;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.yinli.ylsegmentedcontrol.utils.YLMeasureHelper;

/**
 * Created by yinli on 22/03/15.
 */
public class YLSegmentedRadioButton extends RadioButton {

    private Context mContext;
    public YLSegmentedRadioButton(Context context) {
        super(context);
        this.mContext = context;
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateButtonAppearance();
        resizeButtonIcon();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        Drawable[] res = new Drawable[4];
        Drawable[] drawables = getCompoundDrawables();

        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                float scaleFactor = (float) YLMeasureHelper.getTextBounds(getTextSize(), getText().toString()).height() * 2 * metrics.scaledDensity / (float)drawables[i].getIntrinsicHeight();
                res[i] = YLMeasureHelper.scaleDrawable(drawables[i], scaleFactor);
            }
        }
        setCompoundDrawablesWithIntrinsicBounds(res[0], res[1], res[2], res[3]);
    }
}
