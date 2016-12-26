package com.vanbios.footballforecast.common.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.vanbios.footballforecast.R;
import com.vanbios.footballforecast.common.app.App;
import com.vanbios.footballforecast.common.enums.FragmentEnum;
import com.vanbios.footballforecast.common.ui.adapters.NavigationDrawerRecyclerAdapter;
import com.vanbios.footballforecast.common.ui.fragments.FragmentAboutUs;
import com.vanbios.footballforecast.common.ui.fragments.FragmentContacts;
import com.vanbios.footballforecast.common.utils.ui.ToastManager;
import com.vanbios.footballforecast.forecast.FragmentForecast;
import com.vanbios.footballforecast.news.FragmentNews;

import javax.inject.Inject;

import static butterknife.ButterKnife.findById;

/**
 * @author Ihor Bilous
 */

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerNavDrawer;
    private static long backPressExitTime;
    private Toolbar toolbar;
    private final int TOOLBAR_DEFAULT = 1, TOOLBAR_DETAILS = 2;
    ActionBarDrawerToggle mDrawerToggle;
    private FragmentManager fragmentManager;

    @Inject
    ToastManager toastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getComponent().inject(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        initViews();
        addFragment(new FragmentForecast(), FragmentEnum.FORECASTS.name());
    }

    private void initViews() {
        toolbar = findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolbar(TOOLBAR_DEFAULT);

        drawerLayout = findById(this, R.id.navDrawerLayout);

        recyclerNavDrawer = findById(this, R.id.recyclerViewNavDrawer);
        if (recyclerNavDrawer != null) recyclerNavDrawer.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerNavDrawer.setLayoutManager(layoutManager);

        recyclerNavDrawer.setAdapter(new NavigationDrawerRecyclerAdapter(this));

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.setToolbarNavigationClickListener(view -> {
            String tag = getTopFragment().getTag();
            if (tag != null) {
                switch (FragmentEnum.valueOf(tag)) {
                    case NEWS_DETAIL:
                    case FORECASTS_DETAIL:
                        popFragment();
                        break;
                    default:
                        drawerLayout.openDrawer(Gravity.LEFT);
                }
            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_blue_24dp);

        mDrawerToggle.syncState();

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerNavDrawer.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = recyclerNavDrawer.getChildAdapterPosition(child);
                    drawerLayout.closeDrawers();

                    switch (position) {
                        case 1:
                            replaceFragmentIfExistOrAddNew(FragmentEnum.FORECASTS.name());
                            break;
                        case 2:
                            replaceFragmentIfExistOrAddNew(FragmentEnum.NEWS.name());
                            break;
                        case 3:
                            openContacts();
                            break;
                        case 4:
                            openAboutUs();
                            break;
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void initToolbar(int mode) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setLogo(R.drawable.ratingbet_logo);
            actionBar.setDisplayHomeAsUpEnabled(true);

            switch (mode) {
                case TOOLBAR_DEFAULT: {
                    toolbar.setNavigationIcon(R.drawable.ic_menu_blue_24dp);
                    break;
                }
                case TOOLBAR_DETAILS:
                    toolbar.setNavigationIcon(R.drawable.ic_chevron_left_blue_24dp);
                    break;
            }
        }
    }

    public void openAboutUs() {
        replaceFragmentIfExistOrAddNew(FragmentEnum.ABOUT_US.name());
    }

    public void openContacts() {
        replaceFragmentIfExistOrAddNew(FragmentEnum.CONTACTS.name());
    }

    private void replaceFragmentIfExistOrAddNew(String tag) {
        Fragment f = fragmentManager.findFragmentByTag(tag);
        if (f != null) replaceFragment(f, tag);
        else {
            switch (FragmentEnum.valueOf(tag)) {
                case FORECASTS:
                    f = new FragmentForecast();
                    break;
                case NEWS:
                    f = new FragmentNews();
                    break;
                case CONTACTS:
                    f = new FragmentContacts();
                    break;
                case ABOUT_US:
                    f = new FragmentAboutUs();
                    break;
            }
            if (f != null) addFragment(f, tag);
        }
    }

    public void addFragment(Fragment f, String tag) {
        treatFragment(f, tag, true);
    }

    public void replaceFragment(Fragment f, String tag) {
        treatFragment(f, tag, false);
    }

    public Fragment getTopFragment() {
        return fragmentManager.findFragmentById(R.id.fragment_container);
    }

    private void treatFragment(Fragment f, String tag, boolean addToBackStack) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, f, tag);
        if (addToBackStack) ft.addToBackStack(tag);
        ft.commit();
    }

    public void popFragment() {
        fragmentManager.popBackStackImmediate();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getTopFragment();
        if (fragment != null) {
            String tag = fragment.getTag();
            if (tag != null) {
                switch (FragmentEnum.valueOf(tag)) {
                    case FORECASTS_DETAIL:
                    case NEWS_DETAIL:
                        popFragment();
                        break;
                    default:
                        exitFromApp();
                }
            } else exitFromApp();
        }
    }

    private void exitFromApp() {
        if (backPressExitTime + 2000 > System.currentTimeMillis()) {
            this.finish();
        } else {
            toastManager.showClosableToast(this, getString(R.string.press_again_to_exit), ToastManager.SHORT);
            backPressExitTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = getTopFragment();
        if (fragment != null) {
            String tag = fragment.getTag();
            if (tag != null) {
                switch (FragmentEnum.valueOf(tag)) {
                    case FORECASTS:
                    case NEWS:
                    case ABOUT_US:
                    case CONTACTS:
                        initToolbar(TOOLBAR_DEFAULT);
                        break;
                    case NEWS_DETAIL:
                    case FORECASTS_DETAIL:
                        initToolbar(TOOLBAR_DETAILS);
                        break;
                }
            }
        }
    }
}