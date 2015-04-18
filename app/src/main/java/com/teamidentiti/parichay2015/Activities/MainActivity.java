package com.teamidentiti.parichay2015.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.teamidentiti.parichay2015.EventsIntentService;
import com.teamidentiti.parichay2015.GCM.RegistrationsIntentService;
import com.teamidentiti.parichay2015.R;
import com.teamidentiti.parichay2015.SlidingTabs.SimplePagerAdapter;
import com.teamidentiti.parichay2015.SlidingTabs.SlidingTabLayout;

import java.util.List;

/**
 * Created by Sravan on 4/4/2015.
 */
public class MainActivity extends ActionBarActivity implements ViewPager.PageTransformer, ViewPager.OnPageChangeListener {
    private static ViewPager pager;
    public static List<String> eventsData;
    private static Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enabling Lollipop Activity transitions, and applying colors to nav bar, status bar
        window = getWindow();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorUpdatesFragmentDark));
            window.setNavigationBarColor(getResources().getColor(R.color.colorUpdatesFragmentDark));
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            window.setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorUpdatesFragment)));

        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager()));
        pager.setPageTransformer(false, this);
        pager.setCurrentItem(1);
        pager.setOffscreenPageLimit(3);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout)findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(pager);
        // Dynamic tab indicator
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                switch(position) {
                    case 0: return getResources().getColor(R.color.colorEventsFragment);
                    case 1: return getResources().getColor(R.color.colorUpdatesFragment);
                    case 2: return getResources().getColor(R.color.colorResultsFragment);
                    case 3: return getResources().getColor(R.color.colorPointsFragment);
                    default: return 0;
                }
            }
        });
        // If on Lollipop, change nav bar, status bar colors depending on active page
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position) {
                    if(position==0) {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorEventsFragment)));
                        window.setStatusBarColor(getResources().getColor(R.color.colorEventsFragmentDark));
                        window.setNavigationBarColor(getResources().getColor(R.color.colorEventsFragmentDark));
                    } else if(position==1) {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorUpdatesFragment)));
                        window.setStatusBarColor(getResources().getColor(R.color.colorUpdatesFragmentDark));
                        window.setNavigationBarColor(getResources().getColor(R.color.colorUpdatesFragmentDark));
                    } else if (position==2) {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorResultsFragment)));
                        window.setStatusBarColor(getResources().getColor(R.color.colorResultsFragmentDark));
                        window.setNavigationBarColor(getResources().getColor(R.color.colorResultsFragmentDark));
                    } else {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPointsFragment)));
                        window.setStatusBarColor(getResources().getColor(R.color.colorPointsFragmentDark));
                        window.setNavigationBarColor(getResources().getColor(R.color.colorPointsFragmentDark));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        }
        else {
            slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if(position==0) getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorEventsFragment)));
                    else if(position==1) getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorUpdatesFragment)));
                    else if (position==2) getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorResultsFragment)));
                    else getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPointsFragment)));
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

        gcmRegister();
        startService(new Intent(this, EventsIntentService.class));
    }

    @Override
    public void transformPage(View page, float positionOffset) {
        float inversePositionOffset = 1-Math.abs(positionOffset);
        if(inversePositionOffset>0.95)
            page.setScaleY(inversePositionOffset);
        else
            page.setScaleY(0.95f);
    }

    private void gcmRegister() {
        // Check if device is already registered for GCM
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        if(p.getBoolean("GCM_REGISTERED", false)) {}
        else {
            Intent i = new Intent(this, RegistrationsIntentService.class);
            startService(i);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        switch(position) {
            case 0: getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorEventsFragment)));
            case 1: getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorUpdatesFragment)));
            case 2: getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorResultsFragment)));
            case 3: getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPointsFragment)));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}