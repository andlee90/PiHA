package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class EditorFragmentPagerAdapter extends FragmentPagerAdapter
{
    private final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Edit", "Devices", "Users", "Roles", "Rules" };

    EditorFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position)
    {
        if(position == 0)
        {
            return EditorFragment.newInstance();
        }
        else
        {
            return DevicesFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }
}
