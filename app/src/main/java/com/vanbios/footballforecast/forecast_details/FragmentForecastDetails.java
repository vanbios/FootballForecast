package com.vanbios.footballforecast.forecast_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.app.App;
import com.vanbios.footballforecast.common.ui.activities.MainActivity;
import com.vanbios.footballforecast.common.utils.date.DateManager;
import com.vanbios.footballforecast.forecast.models.Author;
import com.vanbios.footballforecast.forecast.models.Forecast;
import com.vanbios.footballforecast.forecast.models.Infograf;

import java.util.Locale;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

import static butterknife.ButterKnife.findById;

/**
 * @author Ihor Bilous
 */

public class FragmentForecastDetails extends Fragment implements FragmentForecastDetailsMVP.View {

    public static final String FORECAST_ID = "forecast_id";
    private Forecast forecast;
    private View view;
    private LinearLayout linLayInfograf;

    @Inject
    DateManager dateManager;

    @Inject
    FragmentForecastDetailsMVP.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forecast_details, container, false);

        ((App) getActivity().getApplication()).getComponent().inject(this);

        presenter.setView(this);
        presenter.loadData(getArguments().getInt(FORECAST_ID));

        return view;
    }

    private void initUI(View view) {
        ImageView ivHeaderBanner = findById(view, R.id.ivItemForecastDetailsHeaderBanner);
        TextView tvHeaderTitle = findById(view, R.id.tvItemForecastDetailsHeaderTitle);
        TextView tvHeaderSubTitle = findById(view, R.id.tvItemForecastDetailsHeaderSubTitle);

        TextView tvPlaceTournament = findById(view, R.id.tvItemForecastDetailsPlaceTournament);
        TextView tvPlaceStadium = findById(view, R.id.tvItemForecastDetailsPlaceStadium);
        TextView tvPlaceDate = findById(view, R.id.tvItemForecastDetailsPlaceDate);

        WebView webView = findById(view, R.id.webViewFrgForecastDetails);
        webView.setOnTouchListener((v, event) -> true);

        TextView tvAuthorCoeff = findById(view, R.id.tvItemForecastDetailsAuthorCoeff);
        TextView tvAuthorName = findById(view, R.id.tvItemForecastDetailsAuthorName);
        TextView tvAuthorInfo = findById(view, R.id.tvItemForecastDetailsAuthorInfo);
        CircleImageView ivAuthorImage = findById(view, R.id.ivItemForecastDetailsAuthorImage);

        linLayInfograf = findById(view, R.id.linLayFrgForecastDetailsInfograf);
        linLayInfograf.setVisibility(View.GONE);

        MainActivity activity = (MainActivity) getActivity();
        if (forecast != null && activity != null) {
            webView.loadData(forecast.getHtml(), "text/html; charset=utf-8", "UTF-8");

            if (forecast.isImageValid() && forecast.getImage().isSrcValid()) {
                Picasso.with(activity).load(forecast.getImage().getSrc()).into(ivHeaderBanner);
            }

            tvHeaderTitle.setText(
                    String.format(Locale.UK,
                            "%s - %s",
                            forecast.getT1name(),
                            forecast.getT2name()));
            if (forecast.isDateValid()) {
                tvHeaderSubTitle.setText(
                        String.format(Locale.getDefault(),
                                getString(R.string.forecast_for_date_placeholder),
                                dateManager.longFullDateToString(dateManager.dateToMillis(forecast.getDate()))));
            }

            if (forecast.isTextCoeffValid()) {
                tvAuthorCoeff.setText(forecast.getTextKoef());
            }

            tvPlaceTournament.setText(String.format(Locale.getDefault(),
                    getString(R.string.tournament_placeholder), forecast.getTournament()));
            tvPlaceStadium.setText(String.format(Locale.getDefault(),
                    getString(R.string.place_placeholder), forecast.getPlace()));
            tvPlaceDate.setText(String.format(Locale.getDefault(),
                    getString(R.string.date_placeholder),
                    dateManager.longFullDateAndTimeToString(dateManager.dateToMillis(forecast.getDate()))));

            Author author = forecast.getAuthor();
            if (author != null) {
                if (author.isImageValid()) {
                    Picasso.with(activity).load(author.getImage()).into(ivAuthorImage);
                }

                if (author.isNameValid()) {
                    tvAuthorName.setText(author.getName());
                }

                if (author.isHtmlValid()) {
                    Spanned spanned;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        spanned = Html.fromHtml(author.getHtml(), Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        spanned = Html.fromHtml(author.getHtml());
                    }
                    tvAuthorInfo.setText(spanned);
                }
            }

            LinearLayout linLayAboutUs = findById(view, R.id.linLayItemDetailsFooterAboutUs);
            LinearLayout linLayContacts = findById(view, R.id.linLayItemDetailsFooterContacts);
            LinearLayout linLayFullVersion = findById(view, R.id.linLayItemDetailsFooterFullVersion);

            linLayAboutUs.setOnClickListener(v -> {
                activity.popFragment();
                activity.openAboutUs();
            });

            linLayContacts.setOnClickListener(v -> {
                activity.popFragment();
                activity.openContacts();
            });

            if (forecast.isUrlValid()) {
                linLayFullVersion.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(forecast.getUrl()))));
            }
        }
    }

    @Override
    public void showForecast(Forecast forecast) {
        this.forecast = forecast;
        initUI(view);
    }

    @Override
    public void showInfograf(Infograf infograf) {
        fillInfografViews(infograf);
    }

    private void fillInfografViews(Infograf infograf) {
        linLayInfograf.setVisibility(View.VISIBLE);
        initInfografAdditionalStatistic(infograf);
        initInfografTournamentStatistic(infograf);
        initInfografGoalsStatistic(infograf);
        initInfografAverageProgress(infograf);
        initInfografAverageAge(infograf);
        initInfografKnowWhat(infograf);
    }

    private void initInfografAdditionalStatistic(Infograf infograf) {
        View additional = findById(view, R.id.itemForecastDetailsAdditional);
        if (infograf.hasAdditionalStatistic()) {
            TextView tvGreenName = findById(view, R.id.tvItemForecastDetailsAdditionalNameGreen);
            TextView tvBlueName = findById(view, R.id.tvItemForecastDetailsAdditionalNameBlue);
            TextView tvGreenWinCount = findById(view, R.id.tvItemForecastDetailsAdditionalWinCountGreen);
            TextView tvBlueWinCount = findById(view, R.id.tvItemForecastDetailsAdditionalWinCountBlue);
            TextView tvDrawCount = findById(view, R.id.tvItemForecastDetailsAdditionalDrawCount);

            tvGreenName.setText(forecast.getT1name());
            tvBlueName.setText(forecast.getT2name());

            tvGreenWinCount.setText(infograf.getAttr67());
            tvBlueWinCount.setText(infograf.getAttr68());
            tvDrawCount.setText(infograf.getAttr12());
        } else {
            additional.setVisibility(View.GONE);
        }
    }

    private void initInfografTournamentStatistic(Infograf infograf) {
        View tournament = findById(view, R.id.itemForecastDetailsTournamentStatistic);
        if (infograf.hasTournamentSeasonStatistic()) {
            TextView tvGreenName = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreenName);
            TextView tvGreen1 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen1);
            TextView tvGreen2 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen2);
            TextView tvGreen3 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen3);
            TextView tvGreen4 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen4);
            TextView tvGreen5 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen5);
            TextView tvGreen6 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen6);
            TextView tvGreen7 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen7);
            TextView tvGreen8 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen8);
            TextView tvGreen9 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticGreen9);
            TextView tvBlueName = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlueName);
            TextView tvBlue1 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue1);
            TextView tvBlue2 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue2);
            TextView tvBlue3 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue3);
            TextView tvBlue4 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue4);
            TextView tvBlue5 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue5);
            TextView tvBlue6 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue6);
            TextView tvBlue7 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue7);
            TextView tvBlue8 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue8);
            TextView tvBlue9 = findById(view, R.id.tvItemForecastDetailsTournamentStatisticBlue9);

            tvGreenName.setText(forecast.getT1name());
            tvBlueName.setText(forecast.getT2name());

            tvGreen1.setText(infograf.getAttr17());
            tvGreen2.setText(infograf.getAttr19());
            tvGreen3.setText(infograf.getAttr21());
            tvGreen4.setText(infograf.getAttr23());
            tvGreen5.setText(infograf.getAttr25());
            tvGreen6.setText(infograf.getAttr27());
            tvGreen7.setText(infograf.getAttr29());
            tvGreen8.setText(infograf.getAttr31());
            tvGreen9.setText(infograf.getAttr33());

            tvBlue1.setText(infograf.getAttr18());
            tvBlue2.setText(infograf.getAttr20());
            tvBlue3.setText(infograf.getAttr22());
            tvBlue4.setText(infograf.getAttr24());
            tvBlue5.setText(infograf.getAttr26());
            tvBlue6.setText(infograf.getAttr28());
            tvBlue7.setText(infograf.getAttr30());
            tvBlue8.setText(infograf.getAttr32());
            tvBlue9.setText(infograf.getAttr34());
        } else {
            tournament.setVisibility(View.GONE);
        }
    }

    private void initInfografGoalsStatistic(Infograf infograf) {
        View goals = findById(view, R.id.itemForecastDetailsGoals);
        if (infograf.hasGoalsStatistic()) {
            TextView tvGreenName = findById(view, R.id.tvItemForecastDetailsGoalsGreenName);
            TextView tvGreen1 = findById(view, R.id.tvItemForecastDetailsGoalsGreen1);
            TextView tvGreen2 = findById(view, R.id.tvItemForecastDetailsGoalsGreen2);
            TextView tvGreen3 = findById(view, R.id.tvItemForecastDetailsGoalsGreen3);
            TextView tvGreen4 = findById(view, R.id.tvItemForecastDetailsGoalsGreen4);
            TextView tvGreen5 = findById(view, R.id.tvItemForecastDetailsGoalsGreen5);
            TextView tvGreen6 = findById(view, R.id.tvItemForecastDetailsGoalsGreen6);

            TextView tvBlueName = findById(view, R.id.tvItemForecastDetailsGoalsBlueName);
            TextView tvBlue1 = findById(view, R.id.tvItemForecastDetailsGoalsBlue1);
            TextView tvBlue2 = findById(view, R.id.tvItemForecastDetailsGoalsBlue2);
            TextView tvBlue3 = findById(view, R.id.tvItemForecastDetailsGoalsBlue3);
            TextView tvBlue4 = findById(view, R.id.tvItemForecastDetailsGoalsBlue4);
            TextView tvBlue5 = findById(view, R.id.tvItemForecastDetailsGoalsBlue5);
            TextView tvBlue6 = findById(view, R.id.tvItemForecastDetailsGoalsBlue6);

            tvGreenName.setText(forecast.getT1name());
            tvBlueName.setText(forecast.getT2name());

            tvGreen1.setText(infograf.getAttr35());
            tvGreen2.setText(infograf.getAttr37());
            tvGreen3.setText(infograf.getAttr39());
            tvGreen4.setText(infograf.getAttr41());
            tvGreen5.setText(infograf.getAttr43());
            tvGreen6.setText(infograf.getAttr45());

            tvBlue1.setText(infograf.getAttr36());
            tvBlue2.setText(infograf.getAttr38());
            tvBlue3.setText(infograf.getAttr40());
            tvBlue4.setText(infograf.getAttr42());
            tvBlue5.setText(infograf.getAttr44());
            tvBlue6.setText(infograf.getAttr46());
        } else {
            goals.setVisibility(View.GONE);
        }
    }

    private void initInfografAverageProgress(Infograf infograf) {
        View averageProgress = findById(view, R.id.itemForecastDetailsAverageProgress);
        if (infograf.hasAverageProgress()) {

            TextView[] textViews = new TextView[]{
                    findById(view, R.id.tvItemForecastDetailsAverageProgress1),
                    findById(view, R.id.tvItemForecastDetailsAverageProgress2),
                    findById(view, R.id.tvItemForecastDetailsAverageProgress3),
                    findById(view, R.id.tvItemForecastDetailsAverageProgress4),
                    findById(view, R.id.tvItemForecastDetailsAverageProgress5),
                    findById(view, R.id.tvItemForecastDetailsAverageProgress6)
            };

            View[] views = new View[]{
                    findById(view, R.id.vItemForecastDetailsAverageProgress1),
                    findById(view, R.id.vItemForecastDetailsAverageProgress2),
                    findById(view, R.id.vItemForecastDetailsAverageProgress3),
                    findById(view, R.id.vItemForecastDetailsAverageProgress4),
                    findById(view, R.id.vItemForecastDetailsAverageProgress5),
                    findById(view, R.id.vItemForecastDetailsAverageProgress6)
            };

            double[] values = infograf.getAverageProgress();

            for (int i = 0; i < textViews.length; i++) {
                textViews[i].setText(String.valueOf(values[i]));
            }

            for (int i = 0; i < 5; i = i + 2) {
                if (values[i] != values[i + 1]) {
                    Pair<Double, Double> valuePair = new Pair<>(values[i], values[i + 1]);
                    Pair<Double, Boolean> ratioPair = getRatio(valuePair);
                    changeViewHeight(ratioPair.second ? views[i] : views[i + 1], ratioPair.first);
                }
            }
        } else {
            averageProgress.setVisibility(View.GONE);
        }
    }

    private void initInfografAverageAge(Infograf infograf) {
        View averageAge = findById(view, R.id.itemForecastDetailsAverageAge);
        if (infograf.hasAverageAge()) {
            LinearLayout linLayGreen = findById(view, R.id.linLayItemForecastDetailsAverageAgeGreenContainer);
            LinearLayout linLayBlue = findById(view, R.id.linLayItemForecastDetailsAverageAgeBlueContainer);
            TextView tvGreenName = findById(view, R.id.tvItemForecastDetailsAverageAgeGreenName);
            TextView tvBlueName = findById(view, R.id.tvItemForecastDetailsAverageAgeBlueName);
            TextView tvGreenValue = findById(view, R.id.tvItemForecastDetailsAverageAgeGreenValue);
            TextView tvBlueValue = findById(view, R.id.tvItemForecastDetailsAverageAgeBlueValue);

            Pair<Double, Double> valuePair = infograf.getAverageAgePair();

            tvGreenName.setText(forecast.getT1name());
            tvBlueName.setText(forecast.getT2name());

            tvGreenValue.setText(String.valueOf(valuePair.first));
            tvBlueValue.setText(String.valueOf(valuePair.second));

            if (!valuePair.first.equals(valuePair.second)) {
                Pair<Double, Boolean> ratioPair = getRatio(valuePair);
                changeLinLayWidth(ratioPair.second ? linLayGreen : linLayBlue, ratioPair.first);
            }
        } else {
            averageAge.setVisibility(View.GONE);
        }
    }

    private void initInfografKnowWhat(Infograf infograf) {
        LinearLayout linLayRoot = findById(view, R.id.linLayItemForecastDetailsKnowWhatRoot);
        int count = 0;
        if (infograf.getAttr57() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_green, R.id.tvItemKnowWhatFactGreenInfo, infograf.getAttr57());
            count++;
        }
        if (infograf.getAttr58() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_blue, R.id.tvItemKnowWhatFactBlueInfo, infograf.getAttr58());
            count++;
        }
        if (infograf.getAttr59() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_green, R.id.tvItemKnowWhatFactGreenInfo, infograf.getAttr59());
            count++;
        }
        if (infograf.getAttr60() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_blue, R.id.tvItemKnowWhatFactBlueInfo, infograf.getAttr60());
            count++;
        }
        if (infograf.getAttr61() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_green, R.id.tvItemKnowWhatFactGreenInfo, infograf.getAttr61());
            count++;
        }
        if (infograf.getAttr62() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_blue, R.id.tvItemKnowWhatFactBlueInfo, infograf.getAttr62());
            count++;
        }
        if (infograf.getAttr63() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_green, R.id.tvItemKnowWhatFactGreenInfo, infograf.getAttr63());
            count++;
        }
        if (infograf.getAttr64() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_blue, R.id.tvItemKnowWhatFactBlueInfo, infograf.getAttr64());
            count++;
        }
        if (infograf.getAttr65() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_green, R.id.tvItemKnowWhatFactGreenInfo, infograf.getAttr65());
            count++;
        }
        if (infograf.getAttr66() != null) {
            addViewToLinLayRoot(linLayRoot, R.layout.item_know_what_fact_blue, R.id.tvItemKnowWhatFactBlueInfo, infograf.getAttr66());
            count++;
        }
        if (count == 0) {
            View item = findById(view, R.id.itemForecastDetailsKnowWhat);
            item.setVisibility(View.GONE);
        }
    }

    private void addViewToLinLayRoot(LinearLayout root, int layoutId, int tvId, String text) {
        View factView = LayoutInflater.from(getActivity()).inflate(layoutId, root, false);
        TextView tvInfo = findById(factView, tvId);
        tvInfo.setText(text);
        root.addView(factView);
    }

    private void changeLinLayWidth(LinearLayout linLay, double ratio) {
        linLay.post(() ->
                linLay.setLayoutParams(new LinearLayout.LayoutParams((int) (linLay.getWidth() * ratio), LinearLayout.LayoutParams.WRAP_CONTENT))
        );
    }

    private Pair<Double, Boolean> getRatio(Pair<Double, Double> valuePair) {
        boolean b = valuePair.first < valuePair.second;
        return new Pair<>(b ? valuePair.first / valuePair.second : valuePair.second / valuePair.first, b);
    }

    private void changeViewHeight(View view, double ratio) {
        view.post(() ->
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (view.getHeight() * ratio)))
        );
    }
}