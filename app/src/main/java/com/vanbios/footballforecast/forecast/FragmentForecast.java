package com.vanbios.footballforecast.forecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.app.App;
import com.vanbios.footballforecast.common.enums.FragmentEnum;
import com.vanbios.footballforecast.common.listeners.OnForecastFilterSelected;
import com.vanbios.footballforecast.common.listeners.OnItemClickListener;
import com.vanbios.footballforecast.common.ui.activities.MainActivity;
import com.vanbios.footballforecast.common.utils.pagination.OnPagingIdIsNeeded;
import com.vanbios.footballforecast.common.utils.ui.ToastManager;
import com.vanbios.footballforecast.forecast_details.FragmentForecastDetails;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.Unbinder;

import static butterknife.ButterKnife.bind;

/**
 * @author Ihor Bilous
 */

public class FragmentForecast extends Fragment implements FragmentForecastMVP.View, OnPagingIdIsNeeded, OnForecastFilterSelected {

    @BindView(R.id.swipeFrgForecast)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerFrgForecast)
    RecyclerView recyclerView;

    private ForecastPagingRecyclerAdapter recyclerViewAdapter;

    private Unbinder unbinder;

    @Inject
    ToastManager toastManager;

    @Inject
    FragmentForecastMVP.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        setRetainInstance(true);

        ((App) getActivity().getApplication()).getComponent().inject(this);

        initUI(view, savedInstanceState);
        return view;
    }

    private void initUI(View view, Bundle savedInstanceState) {
        unbinder = bind(this, view);
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        if (savedInstanceState == null || recyclerViewAdapter == null) {
            recyclerViewAdapter = new ForecastPagingRecyclerAdapter(getActivity(), this, this);
        }
        recyclerView.setSaveEnabled(true);
        recyclerViewAdapter.setOnItemClickListener(onItemClickListener);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        presenter.setView(this);
        presenter.loadFilteredData(recyclerViewAdapter.getItemCount(), recyclerViewAdapter.getFilterType(), recyclerViewAdapter.getFilterDate());
        presenter.subscribeRxPagination(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(() ->
                presenter.refreshFilteredData(recyclerViewAdapter.getFilterType(), recyclerViewAdapter.getFilterDate())
        );
    }

    @Override
    public void onDestroyView() {
        presenter.unsubscribeRxPagination();
        if (recyclerView != null) recyclerView.setAdapter(null);
        unbinder.unbind();
        super.onDestroyView();
    }

    private OnItemClickListener onItemClickListener = id -> {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            FragmentForecastDetails frg = new FragmentForecastDetails();
            Bundle args = new Bundle();
            args.putSerializable(FragmentForecastDetails.FORECAST_ID, id);
            frg.setArguments(args);
            activity.addFragment(frg, FragmentEnum.FORECASTS_DETAIL.name());
        }
    };

    @Override
    public void setFilteredData(List<ForecastViewModel> forecastViewModelList) {
        if (forecastViewModelList != null) {
            recyclerViewAdapter.setItems(forecastViewModelList);
        }
    }

    @Override
    public void refreshFilteredData(List<ForecastViewModel> forecastViewModelList) {
        swipeRefreshLayout.setRefreshing(false);
        if (forecastViewModelList != null) {
            recyclerViewAdapter.setItems(forecastViewModelList);
        }
    }

    @Override
    public void addMoreFilteredData(List<ForecastViewModel> forecastViewModelList) {
        recyclerViewAdapter.addNewItems(forecastViewModelList);
        recyclerViewAdapter.notifyItemInserted(recyclerViewAdapter.getItemCount() - forecastViewModelList.size());
    }

    @Override
    public void showErrorMessage(String message) {
        toastManager.showClosableToast(getActivity(), message, ToastManager.LONG);
    }

    @Override
    public int getPagingId() {
        return presenter.getForecastIdForPaging();
    }

    @Override
    public void onSelectFilterType(int filterType, int filterDate) {
        presenter.loadFilteredData(0, filterType, filterDate);
    }
}