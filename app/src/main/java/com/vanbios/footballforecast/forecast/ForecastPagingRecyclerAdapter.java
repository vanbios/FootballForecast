package com.vanbios.footballforecast.forecast;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.listeners.OnForecastFilterSelected;
import com.vanbios.footballforecast.common.listeners.OnItemClickListener;
import com.vanbios.footballforecast.common.utils.pagination.OnPagingIdIsNeeded;
import com.vanbios.footballforecast.common.utils.pagination.PagingIdInterface;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import static butterknife.ButterKnife.findById;

/**
 * @author Ihor Bilous
 */

class ForecastPagingRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements PagingIdInterface {

    private static final int REGULAR = 1, HEADER = 2;
    private List<ForecastViewModel> filteredList;
    private Context context;
    @Getter
    private int filterType = 0;
    @Getter
    private int filterDate = 0;
    @Setter
    private OnItemClickListener onItemClickListener;
    private OnPagingIdIsNeeded onPagingIdIsNeeded;
    private OnForecastFilterSelected onForecastFilterSelected;


    ForecastPagingRecyclerAdapter(Context context, OnPagingIdIsNeeded onPagingIdIsNeeded, OnForecastFilterSelected onForecastFilterSelected) {
        this.context = context;
        this.filteredList = new ArrayList<>();
        this.onPagingIdIsNeeded = onPagingIdIsNeeded;
        this.onForecastFilterSelected = onForecastFilterSelected;
    }

    void setItems(List<ForecastViewModel> items) {
        filteredList.clear();
        filteredList.addAll(items);
        notifyDataSetChanged();
    }

    void addNewItems(List<ForecastViewModel> items) {
        filteredList.addAll(items);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    private ForecastViewModel getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public int getIdForPaging() {
        return onPagingIdIsNeeded.getPagingId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case REGULAR:
                return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false));
            case HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast_of_day, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : REGULAR;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case REGULAR:
                onBindMainHolder(holder, position, true);
                break;
            case HEADER:
                onBindMainHolder(holder, position, false);
                onBindHeaderHolder(holder, position);
                break;
        }
    }

    private void onBindMainHolder(RecyclerView.ViewHolder holder, int position, boolean isRegular) {
        MainViewHolder mHolder = (MainViewHolder) holder;
        ForecastViewModel forecast = getItem(position);
        if (forecast != null) {
            if (forecast.getTitle() != null) {
                mHolder.tvTitle.setText(forecast.getTitle());
            }
            if (forecast.getSubTitle() != null) {
                mHolder.tvSubTitle.setText(forecast.getSubTitle());
            }
            if (forecast.getImageUrl() != null) {
                Picasso.with(context).load(forecast.getImageUrl()).into(mHolder.ivBanner);
            }
            mHolder.ivHot.setVisibility(forecast.isHot() ? View.VISIBLE : View.INVISIBLE);
            mHolder.ivInfogr.setVisibility(forecast.isHasInfograf() ? View.VISIBLE : View.INVISIBLE);

            if (isRegular) {
                mHolder.view.setOnClickListener((v) -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(forecast.getId());
                    }
                });
            }
        }
    }

    private void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder hHolder = (HeaderViewHolder) holder;

        ForecastViewModel forecast = getItem(position);
        if (forecast != null) {
            if (forecast.getTextKoef() != null) {
                hHolder.tvCoeff.setText(forecast.getTextKoef());
            }
            hHolder.linLayClickable.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(forecast.getId());
                }
            });
        }

        for (int i = 0; i < hHolder.tvFilterArray.length; i++) {
            final int j = i;
            hHolder.tvFilterArray[j].setOnClickListener(v -> setFilterTVArrayColors(j, hHolder));
        }

        for (int i = 0; i < hHolder.linLayFilterArray.length; i++) {
            final int j = i;
            hHolder.linLayFilterArray[j].setOnClickListener(v -> setFilterLLArrayColors(j, hHolder));
        }
    }

    private void setFilterTVArrayColors(int pos, HeaderViewHolder holder) {
        for (int i = 0; i < holder.tvFilterArray.length; i++)
            setFilterTVColor(holder.tvFilterArray[i], i == pos);
        filterDate = pos;
        onFilterSelected();
    }

    private void setFilterTVColor(TextView tv, boolean active) {
        tv.setTextColor(ContextCompat.getColor(context, active ? R.color.custom_green_regular : R.color.custom_text_gray_dark));
    }

    private void setFilterLLArrayColors(int pos, HeaderViewHolder holder) {
        for (int i = 0; i < holder.linLayFilterArray.length; i++)
            setFilterLLColor(holder.linLayFilterArray[i], i == pos);
        filterType = pos;
        onFilterSelected();
    }

    private void setFilterLLColor(LinearLayout ll, boolean active) {
        ll.setBackgroundColor(ContextCompat.getColor(context, active ? R.color.custom_green_regular : R.color.custom_gray_background));
    }

    private void onFilterSelected() {
        if (onForecastFilterSelected != null) {
            onForecastFilterSelected.onSelectFilterType(filterType, filterDate);
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView ivBanner, ivHot, ivInfogr;
        TextView tvTitle, tvSubTitle;

        MainViewHolder(View view, int titleId, int subTitleId, int bannerId, int hotId, int infogrId) {
            super(view);
            this.view = view;
            tvTitle = findById(view, titleId);
            tvSubTitle = findById(view, subTitleId);
            ivBanner = findById(view, bannerId);
            ivHot = findById(view, hotId);
            ivInfogr = findById(view, infogrId);
        }

        MainViewHolder(View view) {
            this(view,
                    R.id.tvItemForecastTitle,
                    R.id.tvItemForecastSubTitle,
                    R.id.ivItemForecastBanner,
                    R.id.ivItemForecastHot,
                    R.id.ivItemForecastInfogr);
        }
    }

    private static class HeaderViewHolder extends MainViewHolder {

        LinearLayout linLayClickable;
        TextView tvCoeff;
        TextView[] tvFilterArray;
        LinearLayout[] linLayFilterArray;

        HeaderViewHolder(View view) {
            super(view,
                    R.id.tvItemForecastOfDayTitle,
                    R.id.tvItemForecastOfDaySubTitle,
                    R.id.ivItemForecastOfDayBanner,
                    R.id.ivItemForecastOfDayHot,
                    R.id.ivItemForecastOfDayInfogr);

            linLayClickable = findById(view, R.id.linLayItemForecastOfDayClickable);

            tvCoeff = findById(view, R.id.tvItemForecastOfDayCoeff);

            tvFilterArray = new TextView[]{
                    findById(view, R.id.tvItemForecastFilter1),
                    findById(view, R.id.tvItemForecastFilter2),
                    findById(view, R.id.tvItemForecastFilter3)
            };

            linLayFilterArray = new LinearLayout[]{
                    findById(view, R.id.linLayItemForecastFilter1),
                    findById(view, R.id.linLayItemForecastFilter2),
                    findById(view, R.id.linLayItemForecastFilter3),
                    findById(view, R.id.linLayItemForecastFilter4),
                    findById(view, R.id.linLayItemForecastFilter5)
            };
        }
    }
}