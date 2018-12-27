package com.owen.tvgridlayout.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.owen.focus.AbsFocusBorder;
import com.owen.focus.FocusBorder;
import com.owen.tvgridlayout.ScrollHelper;
import com.owen.tvgridlayout.SimpleOnItemListener;
import com.owen.tvgridlayout.TvGridLayout;
import com.owen.tvgridlayout.TvMetroLayout;

public class MainActivity3 extends BaseActivity {
    private TvMetroLayout mTvMetroLayout;

    boolean focus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initFocusBorder();

        mTvMetroLayout = findViewById(R.id.tv_metro_layout);
        TvGridLayout tvGridLayout = findViewById(R.id.grid_layout);
        final TvGridLayout tvGridLayout1 = findViewById(R.id.grid_layout1);
        TvGridLayout tvGridLayout2 = findViewById(R.id.grid_layout2);

        tvGridLayout.setAdapter(new TvGridLayoutAdapter(10));
        tvGridLayout1.setAdapter(new TvGridLayoutAdapter(20));
        tvGridLayout2.setAdapter(new TvGridLayoutAdapter(60));

        mTvMetroLayout.setSelectedScrollOffsetStart(Utils.dp2px(getApplicationContext(), 20));
        mTvMetroLayout.setSelectedScrollOffsetEnd(Utils.dp2px(getApplicationContext(), 20));
        mTvMetroLayout.setScrollerDuration(13000);
        mTvMetroLayout.addOnScrollChangeListener(new ScrollHelper.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }

            @Override
            public void onScrollStateChanged(View v, int newState) {
//                Log.i("zsq", "newState="+newState);
                if(newState == ScrollHelper.SCROLL_STATE_IDLE && focus) {
                    focus = false;
                    mFocusBorder.onFocus(((ViewGroup)((ViewGroup)((ViewGroup)v).getFocusedChild()).getFocusedChild()).getFocusedChild(), FocusBorder.OptionsFactory.get(1.2f, 1.2f));
                }
            }
        });

        tvGridLayout.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemPreSelected(TvGridLayout parent, View itemView, int position) {
            }

            @Override
            public void onItemSelected(TvGridLayout parent, View itemView, int position) {
            }
        });


        tvGridLayout1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                Log.i("zsq", "onFocusChange hasFocus="+hasFocus);
                focus = hasFocus;
                if(hasFocus) {
                    ((View)mFocusBorder).setAlpha(0);
                    mFocusBorder.setVisible(false);
                }
            }
        });
    }


}
