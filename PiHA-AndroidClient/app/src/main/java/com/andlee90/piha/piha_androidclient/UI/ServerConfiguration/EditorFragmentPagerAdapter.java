package com.andlee90.piha.piha_androidclient.UI.ServerConfiguration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

public class EditorFragmentPagerAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Edit", "Devices", "Users", "Roles", "Rules" };
    private Context context;
    private FragmentManager fm;

    public EditorFragmentPagerAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.fm = fm;
        this.context = context;
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
            return new EditorFragment();
        }
        else
        {
            return DevicesFragment.newInstance(position + 1);
        }
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }
}
