package com.owen.tvgridlayout.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.owen.focus.FocusBorder;
import com.owen.tvgridlayout.ScrollHelper;
import com.owen.tvgridlayout.SimpleOnItemListener;
import com.owen.tvgridlayout.TvGridLayout;
import com.owen.tvgridlayout.TvMetroLayout;

public class MetroActivity extends BaseActivity {
    private TvMetroLayout mTvMetroLayout;

    boolean focus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);
        initFocusBorder();

        mTvMetroLayout = findViewById(R.id.tv_metro_layout);
        TvGridLayout tvGridLayout = findViewById(R.id.grid_layout);
        final TvGridLayout tvGridLayout1 = findViewById(R.id.grid_layout1);
        TvGridLayout tvGridLayout2 = findViewById(R.id.grid_layout2);

        tvGridLayout.setAdapter(new RecommendAdapter());
        tvGridLayout1.setAdapter(new CategoryAdapter());
//        tvGridLayout2.setAdapter(new TvGridLayoutAdapter(60));

//        mTvMetroLayout.setSelectedScrollOffsetStart(Utils.dp2px(getApplicationContext(), 20));
//        mTvMetroLayout.setSelectedScrollOffsetEnd(Utils.dp2px(getApplicationContext(), 20));
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
                Log.i("zsq", "onFocusChange hasFocus="+hasFocus + " count="+((TvGridLayout)v).getRowOrColumnCount());
                focus = hasFocus;
                if(hasFocus) {
                    ((View)mFocusBorder).setAlpha(0);
                    mFocusBorder.setVisible(false);
                }
            }
        });
    }

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
