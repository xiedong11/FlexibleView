package com.zhuandian.flexibleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MyScrollerView extends LinearLayout {

    /**
     * downY：手指按下时距离View顶部的距离
     * moveY：手指在屏幕上滑动的距离（不停变化）
     * movedY：手指在屏幕上总共滑动的距离（为了确定手指一共滑动了多少距离，不能超过可滑动的最大距离）
     */
    private int downY,moveY,movedY;

    //子View
    private View mChild;

    private Scroller mScroller;

    //可滑动的最大距离
    private int mScrollHeight;

    //子View是否在顶部
    private boolean isTop = false;

    //最初子View在View内可见的高度
    private float visibilityHeight;

    public MyScrollerView(Context context) {
        this(context,null);
    }

    public MyScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.MyScrollerView);
        visibilityHeight = ta.getDimension(R.styleable.MyScrollerView_visibility_height,200);
        ta.recycle();


        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mScrollHeight = (int) (mChild.getMeasuredHeight() - visibilityHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mChild.layout(0,mScrollHeight,mChild.getMeasuredWidth(),mChild.getMeasuredHeight() + mScrollHeight);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() == 0 || getChildAt(0) == null){
            throw new RuntimeException("没有子控件！");
        }
        if(getChildCount() > 1){
            throw new RuntimeException("只能有一个子控件！");
        }
        mChild = getChildAt(0);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下时距离View上面的距离
                downY = (int) event.getY();

                //如果子View不在顶部 && 按下的位置在子View没有显示的位置，则不消费此次滑动事件，否则消费
                if(!isTop && downY < mScrollHeight ){
                    return super.onTouchEvent(event);
                }
                return true;
            case MotionEvent.ACTION_MOVE:

                moveY = (int) event.getY();
                //deY是滑动的距离，向上滑时deY>0 ，向下滑时deY<0
                int deY = downY - moveY;

                //向上滑动时的处理
                if(deY > 0){
                    //将每次滑动的距离相加，为了防止子View的滑动超过View的顶部
                    movedY += deY;
                    if(movedY > mScrollHeight) movedY = mScrollHeight;

                    if(movedY < mScrollHeight){
                        scrollBy(0,deY);
                        downY = moveY;
                        return true;
                    }
                }

                //向下滑动时的处理，向下滑动时需要判断子View是否在顶部，如果不在顶部则不消费此次事件
                if(deY < 0 && isTop){
                    movedY += deY;
                    if(movedY < 0 ) movedY = 0;
                    if(movedY > 0){
                        scrollBy(0,deY);
                    }
                    downY = moveY;
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                //手指抬起时的处理，如果向上滑动的距离超过了最大可滑动距离的1/4，并且子View不在顶部，就表示想把它拉上去
                if(movedY > mScrollHeight / 4 && !isTop){
                    mScroller.startScroll(0,getScrollY(),0,(mScrollHeight - getScrollY()));
                    invalidate();
                    movedY = mScrollHeight;
                    isTop = true;
                }else {
                    //否则就表示放弃本次滑动，让它滑到最初的位置
                    mScroller.startScroll(0,getScrollY(),0, -getScrollY());
                    postInvalidate();
                    movedY = 0;
                    isTop = false;
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(0,mScroller.getCurrY());
            postInvalidate();
        }
    }
}
