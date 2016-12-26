package com.vanbios.footballforecast.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.listeners.OnItemClickListener;
import com.vanbios.footballforecast.common.utils.pagination.OnPagingIdIsNeeded;
import com.vanbios.footballforecast.common.utils.pagination.PagingIdInterface;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import static butterknife.ButterKnife.findById;

/**
 * @author Ihor Bilous
 */

class NewsPagingRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements PagingIdInterface {

    private static final int REGULAR_VIEW = 1, HEADER = 2;
    private List<NewsViewModel> listElements;
    private Context context;
    @Setter
    private OnItemClickListener onItemClickListener;
    private OnPagingIdIsNeeded onPagingIdIsNeeded;


    NewsPagingRecyclerAdapter(Context context, OnPagingIdIsNeeded onPagingIdIsNeeded) {
        this.context = context;
        this.listElements = new ArrayList<>();
        this.onPagingIdIsNeeded = onPagingIdIsNeeded;
    }

    void addNewItems(List<NewsViewModel> items) {
        listElements.addAll(items);
    }

    void clear() {
        listElements.clear();
    }

    private NewsViewModel getItem(int position) {
        return listElements.get(position - 1);
    }

    @Override
    public int getItemCount() {
        return listElements.size() + 1;
    }

    @Override
    public int getIdForPaging() {
        return onPagingIdIsNeeded.getPagingId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case REGULAR_VIEW:
                return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
            case HEADER:
                return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_header, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : REGULAR_VIEW;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case REGULAR_VIEW:
                onBindRegularHolder(holder, position);
                break;
        }
    }

    private void onBindRegularHolder(RecyclerView.ViewHolder holder, int position) {
        MainViewHolder mainHolder = (MainViewHolder) holder;
        NewsViewModel model = getItem(position);
        if (model != null) {
            if (model.getTitle() != null) {
                mainHolder.tvTitle.setText(model.getTitle());
            }
            if (model.getImageUrl() != null) {
                Picasso.with(context).load(model.getImageUrl()).into(mainHolder.ivBanner);
            }
            if (model.getTime() != null) {
                mainHolder.tvTime.setText(model.getTime());
            }
            if (model.getDate() != null) {
                mainHolder.tvDate.setText(model.getDate());
            }
            mainHolder.view.setOnClickListener(v -> {
                if (onItemClickListener != null && model.getId() > 0) {
                    onItemClickListener.onItemClick(model.getId());
                }
            });
        }
    }

    private static class MainViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView ivBanner;
        TextView tvTitle, tvTime, tvDate;

        MainViewHolder(View view) {
            super(view);
            this.view = view;
            tvTitle = findById(view, R.id.tvItemNewsTitle);
            tvTime = findById(view, R.id.tvItemNewsTime);
            tvDate = findById(view, R.id.tvItemNewsDate);
            ivBanner = findById(view, R.id.ivItemNewsBanner);
        }
    }
}