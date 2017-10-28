package com.andlee90.piha.piha_androidclient.UI.Controls;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andlee90.piha.piha_androidclient.Database.ServerItem;

import java.util.List;

class DeviceListPagerAdapter extends FragmentPagerAdapter
{
    private List<ServerItem> mServers;
    private List<Fragment> mFragments;

    DeviceListPagerAdapter(FragmentManager fm, List<ServerItem> s, List<Fragment> f)
    {
        super(fm);
        this.mServers = s;
        this.mFragments = f;
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mServers.get(position).getName();
    }
}
