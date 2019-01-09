package com.owen.tvgridlayout.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owen.focus.AbsFocusBorder;
import com.owen.focus.FocusBorder;
import com.owen.tvgridlayout.ScrollHelper;
import com.owen.tvgridlayout.SimpleOnItemListener;
import com.owen.tvgridlayout.TvGridLayout;
import com.owen.tvgridlayout.TvHorizontalScrollGridLayout;
import com.owen.tvgridlayout.TvMetroLayout;

public class MetroActivity extends BaseActivity {
    private TvMetroLayout mTvMetroLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);
        initFocusBorder(AbsFocusBorder.Mode.NOLL);

        mTvMetroLayout = findViewById(R.id.tv_metro_layout);
        TvHorizontalScrollGridLayout tvHorizontalScrollGridLayout = findViewById(R.id.horizontal_gridlayout);
        TvGridLayout tvGridLayout = findViewById(R.id.grid_layout);
        TvGridLayout tvGridLayout1 = findViewById(R.id.grid_layout1);
        TvGridLayout tvGridLayout2 = findViewById(R.id.grid_layout2);

        tvGridLayout.setAdapter(new RecommendAdapter());
        tvGridLayout1.setAdapter(new CategoryAdapter());
//        tvGridLayout2.setAdapter(new TvGridLayoutAdapter(60));

//        mTvMetroLayout.setSelectedScrollOffsetStart(Utils.dp2px(getApplicationContext(), 20));
//        mTvMetroLayout.setSelectedScrollOffsetEnd(Utils.dp2px(getApplicationContext(), 20));

        mTvMetroLayout.addOnScrollChangeListener(new ScrollHelper.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.i("qqq", "onScrollChange scrollY = " + scrollY + " getScrollY = " + mTvMetroLayout.getScrollY());
            }

            @Override
            public void onScrollStateChanged(View v, int newState) {
//                Log.i("qqq", "newState="+newState);
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

    }

    // 推荐数据
    class RecommendAdapter extends TvGridLayout.Adapter<String> {

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getItemLayout(int position, int viewType) {
            return R.layout.item_layout2;
        }

        @Override
        public void onBindViewHolder(@NonNull TvGridLayout.ViewHolder holder, String item, int position, int viewType) {
            holder.rowWeight = 1;
            holder.colWeight = 1;
            holder.gravity = Gravity.FILL;
            holder.rowSpan = 1;
            holder.colSpan = (position == 0 || position == 3 || position == 6) ? 2 : 1;

            holder.itemView.getLayoutParams().width = 0;
            holder.itemView.getLayoutParams().height = Utils.dp2px(holder.itemView.getContext(), 170);

            TextView textView = holder.getView(R.id.textview);
            textView.setText(position + "");
        }
    }

    // 分类数据
    class CategoryAdapter extends TvGridLayout.Adapter<String> {

        @Override
        public int getItemCount() {
            return 11;
        }

        @Override
        public int getItemLayout(int position, int viewType) {
            return R.layout.item_layout2;
        }

        @Override
        public void onBindViewHolder(@NonNull TvGridLayout.ViewHolder holder, String item, int position, int viewType) {
            holder.rowWeight = 1;
            holder.colWeight = 1;
            holder.gravity = Gravity.FILL;
            holder.rowSpan = (position == 0) ? 2 : 1;
            holder.colSpan = (position == 0 || position == 1 || position == 2 || position == 5 || position == 8) ? 2 : 1;

            holder.itemView.getLayoutParams().width = 0;
            holder.itemView.getLayoutParams().height = Utils.dp2px(holder.itemView.getContext(), 120);

            TextView textView = holder.getView(R.id.textview);
            textView.setText(position + "");
        }
    }
}
