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
    private Resources res;
    public YLSegmentedRadioButton(Context context) {
        this(context, null);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public YLSegmentedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        this.res = getResources();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateButton();
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

    public void updateButton() {
        updateButtonAppearance();
        resizeButtonIcon();
    }

    private void updateButtonAppearance() {
        /* Set paddings but will prevent zero-padding by assign a default value */
        float verticalPadding = res.getDimension(R.dimen.default_radio_button_vertical_padding);
        float horizontalPadding = res.getDimension(R.dimen.default_radio_button_horizontal_padding);
        float paddingLeft = getPaddingLeft() <= 0 ? horizontalPadding : getPaddingLeft();
        float paddingRight = getPaddingRight() <= 0 ? horizontalPadding : getPaddingRight();
        float paddingTop = getPaddingTop() <= 0 ? verticalPadding : getPaddingTop();
        float paddingBottom = getPaddingBottom() <= 0 ? verticalPadding : getPaddingBottom();
        setPadding((int) horizontalPadding, (int) verticalPadding, (int) horizontalPadding, (int) verticalPadding);

        setMinHeight((int) res.getDimension(R.dimen.default_button_min_height));
        setGravity(Gravity.CENTER);
        setButtonDrawable(new ColorDrawable((Color.TRANSPARENT)));
    }

    private void resizeButtonIcon() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        Drawable[] results = new Drawable[4];
        Drawable[] drawables = getCompoundDrawables();

        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                float scaleFactor = (float) YLMeasureHelper.getTextBounds(getTextSize(), getText().toString()).height() * metrics.scaledDensity / (float)drawables[i].getIntrinsicHeight();
                results[i] = YLMeasureHelper.scaleDrawable(res, drawables[i], scaleFactor);
            }
        }
        setCompoundDrawablesWithIntrinsicBounds(results[0], results[1], results[2], results[3]);
    }
}
