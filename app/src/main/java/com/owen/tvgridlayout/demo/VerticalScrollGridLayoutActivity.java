package com.owen.tvgridlayout.demo;

import android.os.Bundle;
import android.view.View;

import com.owen.focus.AbsFocusBorder;
import com.owen.tvgridlayout.ScrollHelper;
import com.owen.tvgridlayout.SimpleOnItemListener;
import com.owen.tvgridlayout.TvGridLayout;
import com.owen.tvgridlayout.TvVerticalScrollGridLayout;

public class VerticalScrollGridLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_scroll_grid_layout);
        initFocusBorder(AbsFocusBorder.Mode.TOGETHER);

        TvVerticalScrollGridLayout mScrollGridLayout = findViewById(R.id.tv_gridlayout);
        TvGridLayout mTvGridLayout = mScrollGridLayout.getTvGridLayout();
//        mTvGridLayout.setAdapter(); //当然也支持动态布局
        mTvGridLayout.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemClick(TvGridLayout parent, View itemView, int position) {
                parent.setSelection(16);
            }
        });
        mScrollGridLayout.addOnScrollChangeListener(new ScrollHelper.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }

            @Override
            public void onScrollStateChanged(View v, int newState) {

            }
        });
        mScrollGridLayout.isScrolling();
        mScrollGridLayout.setSelectedScrollCentered(false);
        mScrollGridLayout.setSelectedScrollOffsetStart(Utils.dp2px(getApplicationContext(), 40));
        mScrollGridLayout.setSelectedScrollOffsetEnd(Utils.dp2px(getApplicationContext(), 40));
    }

}
