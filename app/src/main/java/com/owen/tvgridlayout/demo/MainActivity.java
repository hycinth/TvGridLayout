package com.owen.tvgridlayout.demo;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    private TvMetroLayout mTvMetroLayout;

    FocusBorder mColorFocusBorder;
    private void initFocusBorder() {
        /** 颜色焦点框 */
        mColorFocusBorder = new FocusBorder.Builder().asColor()
                //阴影宽度(方法shadowWidth(18f)也可以设置阴影宽度)
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 20f)
                //阴影颜色
                .shadowColor(Color.parseColor("#3FBB66"))
                //边框宽度(方法borderWidth(2f)也可以设置边框宽度)
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 3f)
                //边框颜色
                .borderColor(Color.parseColor("#00FF00"))
                //padding值
                .padding(2f)
                //动画时长
                .animDuration(300)
                //不要闪光效果动画
//                .noShimmer()
                //闪光颜色
                .shimmerColor(Color.parseColor("#66FFFFFF"))
                //闪光动画时长
                .shimmerDuration(1000)
                //不要呼吸灯效果
//                .noBreathing()
                //呼吸灯效果时长
                .breathingDuration(3000)
                //边框动画模式
                .animMode(AbsFocusBorder.Mode.NOLL)
                .build(this);

        mColorFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Nullable
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if(focus) {
                    Log.i("zsq", "isScrolling");
                    return null;
                }
                return FocusBorder.OptionsFactory.get(1.2f, 1.2f);
            }
        });
    }

    boolean focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    mColorFocusBorder.onFocus(((ViewGroup)((ViewGroup)((ViewGroup)v).getFocusedChild()).getFocusedChild()).getFocusedChild(), FocusBorder.OptionsFactory.get(1.2f, 1.2f));
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
                    ((View)mColorFocusBorder).setAlpha(0);
                    mColorFocusBorder.setVisible(false);
                }
            }
        });
    }


}
