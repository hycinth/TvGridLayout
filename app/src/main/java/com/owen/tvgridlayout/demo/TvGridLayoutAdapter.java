package com.owen.tvgridlayout.demo;

import android.support.annotation.NonNull;

import com.owen.tvgridlayout.TvGridLayout;

/**
 * @author ZhouSuQiang
 * @email zhousuqiang@126.com
 * @date 2018/12/19
 */
public class TvGridLayoutAdapter extends TvGridLayout.Adapter<String> {
    private int itemCount;

    public TvGridLayoutAdapter(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public int getItemLayout(int position, int viewType) {
        return position == 0 ? R.layout.item_layout : R.layout.item_layout2;
    }

    @Override
    public void onBindViewHolder(@NonNull TvGridLayout.ViewHolder holder, String item, int position, int viewType) {
        holder.colWeight = 1;
        holder.rowWeight = 1;
        holder.rowSpan = position == 0 || position == 5 ? 2 : 1;

//                holder.getView()
    }
}
