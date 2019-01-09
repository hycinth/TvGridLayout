package com.owen.tvgridlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2018/12/26
 */
public class ScrollMetroHelper extends ScrollHelper{

    public ScrollMetroHelper(ViewGroup scrollView, ViewGroup childView, AttributeSet attrs, boolean isVerticalScroll) {
        super(scrollView, childView, attrs, isVerticalScroll);
    }

    @Override
    public void addView(View child, int index) {
        if (child == null) {
            throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = generateDefaultLayoutParams();
            if (params == null) {
                throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null");
            }
        }
        addView(child, index, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(params instanceof LayoutParams) {
            params = ((LayoutParams) params).mParams;
        }
        if(!(params instanceof LinearLayout.LayoutParams)) {
            params = new LinearLayout.LayoutParams(params);
        }

        if(child instanceof TvMetroLayout.IMetro) {
            ((TvMetroLayout.IMetro)child).setTvMetroLayout((TvMetroLayout) mScrollView);
        }

        super.addView(child, index, params);
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LinearLayout.LayoutParams;
    }

    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(mChildView.getContext(), attrs);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        private LinearLayout.LayoutParams mParams;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mParams = new LinearLayout.LayoutParams(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            mParams = new LinearLayout.LayoutParams(this);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            mParams = new LinearLayout.LayoutParams(source);
        }
    }
}
