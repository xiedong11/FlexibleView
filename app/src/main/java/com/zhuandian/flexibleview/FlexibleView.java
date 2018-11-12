package com.zhuandian.flexibleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * desc :
 * author：xiedong
 * data：2018/11/12
 */
public class FlexibleView extends LinearLayout {
    private int lastX;
    private int lastY;
    private Button button;
    private FlexibleView flexibleView;

    public FlexibleView(Context context) {
        this(context, null);
    }

    public FlexibleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexibleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        flexibleView = this;
        button = new Button(context);
        button.setGravity(Gravity.CENTER);
        button.setText("拖拽我滑动");
        flexibleView.addView(button);
        button.setOnTouchListener(new InnerViewOnTouchListener());
    }

    class InnerViewOnTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // 记录触摸点坐标
                    lastX = x;
                    lastY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 计算偏移量
                    int offsetX = x - lastX;
                    int offsetY = y - lastY;
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    int screenWidth = dm.widthPixels;
                    int screenHeight = dm.heightPixels;
//                if (screenHeight - getTop() > (screenHeight * 5 / 6) || screenHeight - getTop() < (screenHeight * 1 / 6)) {
//                    return super.onTouchEvent(event);
//                }
                    // 在当前left、top、right、bottom的基础上加上偏移量
                    layout(getLeft(),
                            getTop() + offsetY,
                            getRight(),
                            getBottom() + offsetY);
//                        offsetLeftAndRight(offsetX);
//                        offsetTopAndBottom(offsetY);

                    ViewGroup.LayoutParams layoutParams = flexibleView.getLayoutParams();
                    layoutParams.height = screenHeight - getTop();
                    flexibleView.setLayoutParams(layoutParams);

                    break;
            }
            return true;
        }
    }
}
