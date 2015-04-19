package com.sanderdanielsson.android.simpleformula1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by sander on 2015-04-07.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {

    public static String[] tabNames = {"News", "Schedule", "Standings", "Shaker sensor"};


    public TabsAdapter(FragmentManager fm){
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new NewsFragment();
            case 1:
                return new ScheduleFragment();
            case 2:
                return new DriverStandingsFragment();
            case 3:
                return new ShakerFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
