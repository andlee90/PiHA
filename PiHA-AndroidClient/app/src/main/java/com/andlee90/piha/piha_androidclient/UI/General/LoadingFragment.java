package com.andlee90.piha.piha_androidclient.UI.General;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andlee90.piha.piha_androidclient.R;

public class LoadingFragment extends Fragment
{
    public static LoadingFragment newInstance()
    {
        return new LoadingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }
}
