package com.owen.tvgridlayout.demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owen.focus.AbsFocusBorder;
import com.owen.focus.FocusBorder;
import com.owen.tvgridlayout.ScrollHelper;
import com.owen.tvgridlayout.SimpleOnItemListener;
import com.owen.tvgridlayout.TvGridLayout;
import com.owen.tvgridlayout.TvMetroLayout;

public class MainActivity extends BaseActivity {
    private boolean isXmlMode = true; //表示是否从xml加载布局展示，否则将采用Adapter方式进行布局展示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFocusBorder();

        TvGridLayout tvGridLayout = findViewById(R.id.tv_gridlayout);
        if(!isXmlMode) {
            tvGridLayout.setAdapter(new GridAdapter());
        }
        tvGridLayout.setOnItemListener(new SimpleOnItemListener() {
            @Override
            public void onItemPreSelected(TvGridLayout parent, View itemView, int position) {
            }

            @Override
            public void onItemSelected(TvGridLayout parent, View itemView, int position) {
            }

            @Override
            public void onItemClick(final TvGridLayout parent, View itemView, int position) {
                Utils.showToast(itemView.getContext(), "onItemClick " + position);
                if(position == 1) {
                    isXmlMode = !isXmlMode;
                    parent.setAdapter(new GridAdapter(), true);
                }

                if(position == 0) {
                    startActivity(new Intent(getApplicationContext(), VerticalScrollGridLayoutActivity.class));
                }

                if(position == 4) {
                    startActivity(new Intent(getApplicationContext(), HorizontalScrollGridLayoutActivity.class));
                }
            }
        });
        tvGridLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }

    class GridAdapter extends TvGridLayout.Adapter<String> {

        @Override
        public int getItemCount() {
            return 7;
        }

        @Override
        public int getItemLayout(int position, int viewType) {
            return R.layout.item_main;
        }

        @Override
        public void onBindViewHolder(@NonNull TvGridLayout.ViewHolder holder, String item, int position, int viewType) {
            holder.rowWeight = 1;
            holder.colWeight = 1;
            holder.gravity = Gravity.FILL;

            if(isXmlMode) {
                //模拟xml中的布局
                holder.rowSpan = (position == 0 || position == 4 || position == 6) ? 2 : 1;
                holder.colSpan = (position == 2 || position == 3) ? 2 : 1;
            } else {
                holder.rowSpan = (position == 2 || position == 3 || position == 5) ? 2 : 1;
                holder.colSpan = (position == 0 || position == 4) ? 2 : 1;
            }
            TextView textView = holder.getView(R.id.textview);
            textView.setText(position + (position == 1 ? "点击切换布局" : ""));
        }
    }

}
