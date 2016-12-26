package com.vanbios.footballforecast.news_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.app.App;
import com.vanbios.footballforecast.common.ui.activities.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.Unbinder;

import static butterknife.ButterKnife.bind;

/**
 * @author Ihor Bilous
 */

public class FragmentNewsDetails extends Fragment implements FragmentNewsDetailsMVP.View {

    @BindView(R.id.tvItemNewsTime)
    TextView tvTime;
    @BindView(R.id.tvItemNewsDate)
    TextView tvDate;
    @BindView(R.id.ivItemNewsBanner)
    ImageView ivBanner;
    @BindView(R.id.tvFrgNewsDetailsInfo)
    TextView tvInfo;
    @BindView(R.id.linLayItemDetailsFooterAboutUs)
    LinearLayout linLayAboutUs;
    @BindView(R.id.linLayItemDetailsFooterContacts)
    LinearLayout linLayContacts;
    @BindView(R.id.linLayItemDetailsFooterFullVersion)
    LinearLayout linLayFullVersion;

    private Unbinder unbinder;

    @Inject
    FragmentNewsDetailsMVP.Presenter presenter;

    public static final String NEWS_ID = "news_id";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        setRetainInstance(true);

        ((App) getActivity().getApplication()).getComponent().inject(this);

        initUI(view);
        return view;
    }

    private void initUI(View view) {
        unbinder = bind(this, view);

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            linLayAboutUs.setOnClickListener(v -> {
                mainActivity.popFragment();
                mainActivity.openAboutUs();
            });

            linLayContacts.setOnClickListener(v -> {
                mainActivity.popFragment();
                mainActivity.openContacts();
            });

            presenter.setView(this);
            presenter.loadData(getArguments().getInt(NEWS_ID));
        }
    }

    @Override
    public void setData(NewsDetailsViewModel newsDetailsViewModel) {
        String imageUrl = newsDetailsViewModel.getImageUrl();
        if (imageUrl != null) {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                Picasso.with(mainActivity).load(imageUrl).into(ivBanner);
            }
        }
        String time = newsDetailsViewModel.getTime();
        if (time != null) {
            tvTime.setText(time);
        }
        String date = newsDetailsViewModel.getDate();
        if (date != null) {
            tvDate.setText(date);
        }
        String html = newsDetailsViewModel.getHtml();
        if (html != null) {
            Spanned spanned;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                spanned = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            } else {
                spanned = Html.fromHtml(html);
            }
            tvInfo.setText(spanned);
        }
        String url = newsDetailsViewModel.getUrl();
        if (url != null) {
            linLayFullVersion.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}