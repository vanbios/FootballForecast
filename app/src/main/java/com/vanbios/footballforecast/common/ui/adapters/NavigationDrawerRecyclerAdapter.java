package com.vanbios.footballforecast.common.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vanbios.footballforecast.R;

import static butterknife.ButterKnife.findById;

/**
 * @author Ihor Bilous
 */

public class NavigationDrawerRecyclerAdapter extends RecyclerView.Adapter<NavigationDrawerRecyclerAdapter.ViewHolder> {

    private final String navTitles[];
    private final static int TYPE_FOOTER = 0, TYPE_ITEM = 1, TYPE_HEADER = 2;


    public NavigationDrawerRecyclerAdapter(Context context) {
        navTitles = context.getResources().getStringArray(R.array.menu_items_string_array);
    }

    @Override
    public NavigationDrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
            case TYPE_ITEM:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_row, parent, false), viewType);
            case TYPE_HEADER:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nav_header, parent, false), viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerRecyclerAdapter.ViewHolder holder, int position) {
        if (holder.holderType != TYPE_HEADER) {
            holder.textView.setText(navTitles[position - 1]);
        }
    }

    @Override
    public int getItemCount() {
        return navTitles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) return TYPE_HEADER;
        if (isPositionFooter(position)) return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        int holderType;
        private TextView textView;

        ViewHolder(View itemView, int viewType) {
            super(itemView);
            holderType = viewType;
            if (holderType != TYPE_HEADER) {
                textView = findById(itemView, R.id.tvItemNavRow);
                View divider = findById(itemView, R.id.vItemNavRow);
                divider.setVisibility(holderType == TYPE_ITEM ? View.VISIBLE : View.GONE);
            }
        }
    }
}