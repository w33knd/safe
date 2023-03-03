package com.example.safe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import androidx.core.widget.NestedScrollView;

public class myListView extends ListView {
    public myListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myListView(Context context) {
        super(context);
    }

    public myListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
