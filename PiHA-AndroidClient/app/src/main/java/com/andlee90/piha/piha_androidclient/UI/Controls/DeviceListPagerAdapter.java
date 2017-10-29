package com.andlee90.piha.piha_androidclient.UI.Controls;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.andlee90.piha.piha_androidclient.Database.ServerItem;

import java.util.List;

class DeviceListPagerAdapter extends PagerAdapter
{
    private List<ServerItem> mServers;
    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    DeviceListPagerAdapter(FragmentManager fm, List<ServerItem> s, List<Fragment> f)
    {
        this.mServers = s;
        this.mFragments = f;
        this.mFragmentManager = fm;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        mFragmentManager.beginTransaction().remove(mFragments.get(position)).commit();
        mFragments.set(position, null);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Fragment fragment = getItem(position);
        mFragmentManager.beginTransaction()
                .add(container.getId(), fragment, "fragment:" + position)
                .commit();

        return fragment;
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return ((Fragment) object).getView() == view;
    }

    public Fragment getItem(int position)
    {
        if(mFragments.get(position) ==  null)
        {
            mFragments.set(position, WrapperFragment.newInstance());
        }
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mServers.get(position).getName();
    }
}
