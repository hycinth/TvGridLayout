package com.owen.tvgridlayout;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2018/12/26
 */
public class ScrollHelper {
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SCROLLING = 1;

    protected ViewGroup mScrollView;
    protected ViewGroup mChildView;
    protected boolean mIsVertical;
    private float mSelectedScrollOffsetStart = 0;
    private float mSelectedScrollOffsetEnd = 0;
    private boolean mIsSelectedScrollCentered = false;

    private List<OnScrollChangeListener> mOnScrollChangeListeners = new ArrayList<>();
    /**
     * 上次滑动的时间
     */
    private long mLastScrollUpdate = -1;
    private boolean mIsScrolling = false;
    /**
     * Runnable延迟执行的时间
     */
    private long mDelayMillis = 25;
    private Runnable mScrollerTask = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - mLastScrollUpdate) > mDelayMillis) {
                mLastScrollUpdate = -1;
                onScrollEnd();
            } else {
                mScrollView.postDelayed(this, mDelayMillis);
            }
        }
    };

    public ScrollHelper(ViewGroup scrollView, ViewGroup childView, AttributeSet attrs, boolean isVerticalScroll) {
        mScrollView = scrollView;
        mChildView = childView;
        mIsVertical = isVerticalScroll;

        TypedArray a = scrollView.getContext().obtainStyledAttributes(attrs, R.styleable.ScrollHelper, 0, 0);
        try {
            setSelectedScrollOffsetStart(a.getDimensionPixelSize(R.styleable.ScrollHelper_selectedScrollOffsetStart, isVerticalScroll ? mScrollView.getPaddingTop() : mScrollView.getPaddingLeft()));
            setSelectedScrollOffsetEnd(a.getDimensionPixelSize(R.styleable.ScrollHelper_selectedScrollOffsetEnd, isVerticalScroll ? mScrollView.getPaddingBottom() : mScrollView.getPaddingRight()));
            setSelectedScrollCentered(a.getBoolean(R.styleable.ScrollHelper_selectedScrollCentered, false));
        }finally {
            a.recycle();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        mChildView.setPaddingRelative(start, top, end, bottom);
    }

    public void setPadding(int left, int top, int right, int bottom) {
        mChildView.setPadding(left, top, right, bottom);
    }

    public void addView(View child, int index) {
        mChildView.addView(child, index);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        mChildView.addView(child, index, params);
    }

    public void setSelectedScrollCentered(boolean selectedScrollCentered) {
        mIsSelectedScrollCentered = selectedScrollCentered;
    }

    public boolean isSelectedScrollCentered() {
        return mIsSelectedScrollCentered;
    }

    public void setSelectedScrollOffsetStart(float selectedScrollOffsetStart) {
        mSelectedScrollOffsetStart = selectedScrollOffsetStart;
    }

    public float getSelectedScrollOffsetStart() {
        return mSelectedScrollOffsetStart;
    }

    public void setSelectedScrollOffsetEnd(float selectedScrollOffsetEnd) {
        mSelectedScrollOffsetEnd = selectedScrollOffsetEnd;
    }

    public float getSelectedScrollOffsetEnd() {
        return mSelectedScrollOffsetEnd;
    }

    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return mIsVertical ? computeScrollDeltaToGetChildRectOnScreenWithVertical(rect)
                : computeScrollDeltaToGetChildRectOnScreenWithHorizontal(rect);
    }

    private int computeScrollDeltaToGetChildRectOnScreenWithVertical(Rect rect) {
        if (mScrollView.getChildCount() == 0 || null == rect)
            return 0;

        int height = mScrollView.getHeight();
        int screenTop = mScrollView.getScrollY();
        int screenBottom = screenTop + height;

        int childHeight = mScrollView.getChildAt(0).getHeight();
        int childPaddingBottom = mScrollView.getChildAt(0).getPaddingBottom();

        if(mIsSelectedScrollCentered && !rect.isEmpty()) {
            mSelectedScrollOffsetStart = height - mScrollView.getPaddingTop() - mScrollView.getPaddingBottom() - rect.height();
            mSelectedScrollOffsetStart /= 2f;
            mSelectedScrollOffsetEnd = mSelectedScrollOffsetStart;
        }

        if (rect.top > 0) {
            screenTop += mSelectedScrollOffsetStart;
        }
        if (rect.bottom < (childHeight - childPaddingBottom)) {
            screenBottom -= mSelectedScrollOffsetEnd;
        } else {
            screenBottom -= childPaddingBottom;
        }

        int scrollYDelta = 0;
        if (rect.bottom > screenBottom && rect.top > screenTop) {
            if (rect.height() > height) {
                scrollYDelta += (rect.top - screenTop);
            } else {
                scrollYDelta += (rect.bottom - screenBottom);
            }
            int bottom = mScrollView.getChildAt(0).getBottom()  - childPaddingBottom;
            int distanceToBottom = bottom - screenBottom;
            scrollYDelta = Math.min(scrollYDelta, distanceToBottom);
        } else if (rect.top < screenTop && rect.bottom < screenBottom) {
            if (rect.height() > height) {
                scrollYDelta -= (screenBottom - rect.bottom);
            } else {
                scrollYDelta -= (screenTop - rect.top);
            }
            scrollYDelta = Math.max(scrollYDelta, - mScrollView.getScrollY());
        }
        return scrollYDelta;
    }

    private int computeScrollDeltaToGetChildRectOnScreenWithHorizontal(Rect rect) {
        if (mScrollView.getChildCount() == 0 || null == rect)
            return 0;

        int width = mScrollView.getWidth();
        int screenLeft = mScrollView.getScrollX();
        int screenRight = screenLeft + width;

        if(mIsSelectedScrollCentered && !rect.isEmpty()) {
            mSelectedScrollOffsetStart = width - mScrollView.getPaddingLeft() - mScrollView.getPaddingRight() - rect.width();
            mSelectedScrollOffsetStart /= 2f;
            mSelectedScrollOffsetEnd = mSelectedScrollOffsetStart;
        }

        if (rect.left > 0) {
            screenLeft += mSelectedScrollOffsetStart;
        }
        if (rect.right < mScrollView.getChildAt(0).getWidth()) {
            screenRight -= mSelectedScrollOffsetEnd;
        }

        int scrollXDelta = 0;
        if (rect.right > screenRight && rect.left > screenLeft) {
            if (rect.width() > width) {
                scrollXDelta += (rect.left - screenLeft);
            } else {
                scrollXDelta += (rect.right - screenRight);
            }
            int right = mScrollView.getChildAt(0).getRight();
            int distanceToRight = right - screenRight;
            scrollXDelta = Math.min(scrollXDelta, distanceToRight);

        } else if (rect.left < screenLeft && rect.right < screenRight) {
            if (rect.width() > width) {
                scrollXDelta -= (screenRight - rect.right);
            } else {
                scrollXDelta -= (screenLeft - rect.left);
            }
            scrollXDelta = Math.max(scrollXDelta, - mScrollView.getScrollX());
        }
        return scrollXDelta;
    }


    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        notityOnScrollChange(l, t, oldl, oldt);

        if (mLastScrollUpdate == -1) {
            onScrollStart();
            mScrollView.postDelayed(mScrollerTask, mDelayMillis);
        }
        // 更新ScrollView的滑动时间
        mLastScrollUpdate = System.currentTimeMillis();
    }

    public boolean isScrolling() {
        return mIsScrolling;
    }

    private void notityOnScrollChange(int l, int t, int oldl, int oldt) {
        for(OnScrollChangeListener listener : mOnScrollChangeListeners) {
            listener.onScrollChange(mScrollView, l, t, oldl, oldt);
        }
    }

    private void onScrollEnd() {
        mIsScrolling = false;
        for(OnScrollChangeListener listener : mOnScrollChangeListeners) {
            listener.onScrollStateChanged(mScrollView, SCROLL_STATE_IDLE);
        }
    }

    private void onScrollStart() {
        mIsScrolling = true;
        for(OnScrollChangeListener listener : mOnScrollChangeListeners) {
            listener.onScrollStateChanged(mScrollView, SCROLL_STATE_SCROLLING);
        }
    }

    public void addOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListeners.add(onScrollChangeListener);
    }

    public void removeOnScrollChageListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListeners.remove(onScrollChangeListener);
    }

    public interface OnScrollChangeListener {
        /**
         * Called when the scroll position of a view changes.
         *
         * @param v The view whose scroll position has changed.
         * @param scrollX Current horizontal scroll origin.
         * @param scrollY Current vertical scroll origin.
         * @param oldScrollX Previous horizontal scroll origin.
         * @param oldScrollY Previous vertical scroll origin.
         */
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);

        void onScrollStateChanged(View v, int newState);
    }
}
