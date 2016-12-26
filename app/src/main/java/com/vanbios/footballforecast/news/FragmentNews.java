package com.vanbios.footballforecast.news;

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
import com.vanbios.footballforecast.common.listeners.OnItemClickListener;
import com.vanbios.footballforecast.common.utils.pagination.OnPagingIdIsNeeded;
import com.vanbios.footballforecast.common.ui.activities.MainActivity;
import com.vanbios.footballforecast.common.utils.ui.ToastManager;
import com.vanbios.footballforecast.news_details.FragmentNewsDetails;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.Unbinder;

import static butterknife.ButterKnife.bind;

/**
 * @author Ihor Bilous
 */

public class FragmentNews extends Fragment implements FragmentNewsMVP.View, OnPagingIdIsNeeded {

    @BindView(R.id.swipeFrgNews)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerFrgNews)
    RecyclerView recyclerView;

    private NewsPagingRecyclerAdapter recyclerViewAdapter;

    private Unbinder unbinder;

    @Inject
    ToastManager toastManager;

    @Inject
    FragmentNewsMVP.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
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
            recyclerViewAdapter = new NewsPagingRecyclerAdapter(getActivity(), this);
        }
        recyclerView.setSaveEnabled(true);
        recyclerViewAdapter.setOnItemClickListener(onItemClickListener);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        presenter.setView(this);
        presenter.loadInitialData(recyclerViewAdapter.getItemCount());
        presenter.subscribeRxPagination(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refreshData());
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
        FragmentNewsDetails frg = new FragmentNewsDetails();
        Bundle args = new Bundle();
        args.putInt(FragmentNewsDetails.NEWS_ID, id);
        frg.setArguments(args);
        activity.addFragment(frg, FragmentEnum.NEWS_DETAIL.name());
    };

    @Override
    public void refreshData(List<NewsViewModel> newsViewModelList) {
        swipeRefreshLayout.setRefreshing(false);
        if (newsViewModelList != null) {
            recyclerViewAdapter.clear();
            recyclerViewAdapter.addNewItems(newsViewModelList);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addMoreData(List<NewsViewModel> newsViewModelList) {
        recyclerViewAdapter.addNewItems(newsViewModelList);
        recyclerViewAdapter.notifyItemInserted(recyclerViewAdapter.getItemCount() - newsViewModelList.size());
    }

    @Override
    public void setInitialData(List<NewsViewModel> newsViewModelList) {
        recyclerViewAdapter.clear();
        recyclerViewAdapter.addNewItems(newsViewModelList);
    }

    @Override
    public void showErrorMessage(String message) {
        toastManager.showClosableToast(getActivity(), message, ToastManager.LONG);
    }


    @Override
    public int getPagingId() {
        return presenter.getNewsIdForPaging();
    }
}