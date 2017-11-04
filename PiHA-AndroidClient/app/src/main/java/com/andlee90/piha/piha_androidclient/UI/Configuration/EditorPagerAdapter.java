package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.andlee90.piha.piha_androidclient.UI.General.WrapperFragment;

import java.util.List;

class EditorPagerAdapter extends PagerAdapter
{
    private String tabTitles[] = new String[] { "Edit", "Devices", "Users", "Roles", "Rules" };
    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    EditorPagerAdapter(FragmentManager fm, List<Fragment> f)
    {
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
        return tabTitles.length;
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
        return tabTitles[position];
    }
}
