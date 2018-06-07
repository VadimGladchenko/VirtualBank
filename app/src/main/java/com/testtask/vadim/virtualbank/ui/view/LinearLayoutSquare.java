package com.testtask.vadim.virtualbank.ui.view;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LinearLayoutSquare extends LinearLayout {
    public LinearLayoutSquare(Context context) {
        super(context);
    }

    public LinearLayoutSquare(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutSquare(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
