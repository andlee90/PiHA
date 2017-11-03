package com.andlee90.piha.piha_androidclient.UI.General;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andlee90.piha.piha_androidclient.R;

public class WrapperFragment extends Fragment
{
    public static WrapperFragment newInstance()
    {
        return new WrapperFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LoadingFragment loadingFragment = LoadingFragment.newInstance();

        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container, loadingFragment)
                .commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_wrapper, container, false);
    }

    public void swapFragment(Fragment fragment)
    {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
