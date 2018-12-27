package com.owen.tvgridlayout;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2018/12/26
 */
public class ScrollGridHelper extends ScrollHelper{

    public ScrollGridHelper(ViewGroup scrollView, ViewGroup childView, AttributeSet attrs, boolean isVerticalScroll) {
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
        if(!(params instanceof GridLayout.LayoutParams)) {
            params = new GridLayout.LayoutParams(params);
        }
        super.addView(child, index, params);
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return ((TvGridLayout)mChildView).checkLayoutParams(p);
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
        private GridLayout.LayoutParams mParams;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mParams = new GridLayout.LayoutParams(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            mParams = new GridLayout.LayoutParams(this);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            mParams = new GridLayout.LayoutParams(source);
        }
    }
}
