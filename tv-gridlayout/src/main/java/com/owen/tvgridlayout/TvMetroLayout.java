package com.owen.tvgridlayout;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import java.lang.reflect.Field;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2018/12/19
 */
public class TvMetroLayout extends ScrollView {
    private SlidingMetroStrip mSlidingMetroStrip;
    ScrollMetroHelper mScrollHelper;
    private FixedSpeedScroller mScroller;

    public TvMetroLayout(Context context) {
        this(context, null);
    }

    public TvMetroLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvMetroLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setClipChildren(false);
        setClipToPadding(false);
        initScroller(context);
        initAttrs(context, attrs, defStyleAttr);

        mSlidingMetroStrip = new SlidingMetroStrip(context);
        mSlidingMetroStrip.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        mScrollHelper = new ScrollMetroHelper(this, mSlidingMetroStrip, attrs, true);
        super.addView(mSlidingMetroStrip, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        super.setPadding(0, 0, 0, 0);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        if(null != attrs) {
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TvMetroLayout, defStyleAttr, 0);
//            if(null != a) {
//                mSelectedItemOffsetStart = a.getDimension(R.styleable.TvVerticalScrollView_vsv_selected_offset_start, 0);
//                mSelectedItemOffsetEnd = a.getDimension(R.styleable.TvVerticalScrollView_vsv_selected_offset_end, 0);
//                mIsSelectedCentered = a.getBoolean(R.styleable.TvVerticalScrollView_vsv_is_selected_centered, false);
//                setScrollerDuration(a.getInt(R.styleable.TvVerticalScrollView_vsv_scroller_duration, 600));

//                a.recycle();
//            }
        }
    }

    private void initScroller(Context context) {
        if(null != mScroller)
            return;
        try {
            mScroller = new FixedSpeedScroller(context, new AccelerateInterpolator());
            Field field = this.getClass().getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mScrollHelper.setPadding(left, top, right, bottom);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        mScrollHelper.setPaddingRelative(start, top, end, bottom);
    }

//    /**
//     * 设置滚动时长
//     * @param duration
//     */
//    public void setScrollerDuration(int duration){
//        initScroller(getContext());
//        if(null != mScroller) {
//            mScroller.setScrollDuration(duration);
//        }
//    }
//
//    /**
//     * 获取滚动时长
//     * @return
//     */
//    public int getScrollerDuration() {
//        initScroller(getContext());
//        if(null != mScroller) {
//            return mScroller.getScrollDuration();
//        }
//        return 0;
//    }

    public void setSelectedScrollCentered(boolean selectedCentered) {
        mScrollHelper.setSelectedScrollCentered(selectedCentered);
    }

    public boolean isSelectedScrollCentered() {
        return mScrollHelper.isSelectedScrollCentered();
    }

    public void setSelectedScrollOffsetStart(float selectedItemOffsetStart) {
        mScrollHelper.setSelectedScrollOffsetStart(selectedItemOffsetStart);
    }

    public float getSelectedScrollOffsetStart() {
        return mScrollHelper.getSelectedScrollOffsetStart();
    }

    public void setSelectedScrollOffsetEnd(float selectedItemOffsetEnd) {
        mScrollHelper.setSelectedScrollOffsetEnd(selectedItemOffsetEnd);
    }

    public float getSelectedScrollOffsetEnd() {
        return mScrollHelper.getSelectedScrollOffsetEnd();
    }


    @Override
    public void addView(View child) {
        mScrollHelper.addView(child, -1);
    }

    @Override
    public void addView(View child, int index) {
        mScrollHelper.addView(child, index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        mScrollHelper.addView(child, -1, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        mScrollHelper.addView(child, index, params);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) || mScrollHelper.checkLayoutParams(p);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return mScrollHelper.generateLayoutParams(lp);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return mScrollHelper.generateLayoutParams(attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return mScrollHelper.generateDefaultLayoutParams();
    }

    int delta;
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int d = mScrollHelper.computeScrollDeltaToGetChildRectOnScreen(rect);
        delta = d;
        Log.i("qqq", "computeScrollDeltaToGetChildRectOnScreen " + rect + " delta="+delta + " getScrollY="+getScrollY());
        return d;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollHelper.onScrollChanged(l, t, oldl, oldt);
    }

    public boolean isScrolling() {
        return mScrollHelper.isScrolling();
    }

    public void addOnScrollChangeListener(ScrollHelper.OnScrollChangeListener onScrollChangeListener) {
        mScrollHelper.addOnScrollChangeListener(onScrollChangeListener);
    }

    public void removeOnScrollChageListener(ScrollHelper.OnScrollChangeListener onScrollChangeListener) {
        mScrollHelper.removeOnScrollChageListener(onScrollChangeListener);
    }

    /**
     * 固定速度的Scroller
     * */
    private static class FixedSpeedScroller extends OverScroller {
        private int mDuration = 600;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setScrollDuration(int time) {
            mDuration = time;
        }

        public int getScrollDuration() {
            return mDuration;
        }
    }

    protected static class SlidingMetroStrip extends LinearLayout {

        SlidingMetroStrip(Context context) {
            super(context);
            setClipChildren(false);
            setClipToPadding(false);
            setOrientation(LinearLayout.VERTICAL);
        }
    }

    interface IMetro {
        void setTvMetroLayout(TvMetroLayout layout);
    }
}
