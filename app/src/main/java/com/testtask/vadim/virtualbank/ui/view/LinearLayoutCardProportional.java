package com.testtask.vadim.virtualbank.ui.view;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class LinearLayoutCardProportional extends LinearLayout {
    public static final double CARD_COEF = 1.57;

    public LinearLayoutCardProportional(Context context) {
        super(context);
    }

    public LinearLayoutCardProportional(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutCardProportional(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

        int widthPixels = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightPixels = View.MeasureSpec.getSize(heightMeasureSpec);

        if(isPortOrientation()) {
            heightPixels = (int) Math.round(widthPixels / CARD_COEF);
        } else {
            widthPixels = (int) Math.round(heightPixels * CARD_COEF);
        }

        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthPixels, widthMode);
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightPixels, heightMode);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private boolean isPortOrientation(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }
        return true;
    }
}
