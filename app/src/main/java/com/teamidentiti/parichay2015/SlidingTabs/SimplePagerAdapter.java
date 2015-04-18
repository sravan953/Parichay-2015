package com.teamidentiti.parichay2015.SlidingTabs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teamidentiti.parichay2015.Fragments.EventsFragment;
import com.teamidentiti.parichay2015.Fragments.PointsFragment;
import com.teamidentiti.parichay2015.Fragments.ResultsFragment;
import com.teamidentiti.parichay2015.Fragments.UpdatesFragment;

/**
 * Created by Sravan on 4/4/2015.
 */
public class SimplePagerAdapter extends FragmentPagerAdapter {
    public SimplePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new EventsFragment();
        else if(position==1)
            return new UpdatesFragment();
        else if(position==2)
            return new ResultsFragment();
        else
            return new PointsFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Events";
        else if(position==1)
            return "Updates";
        else if(position==2)
            return "Results";
        else
            return "Championship";
    }
}