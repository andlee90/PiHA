package com.andlee90.piha.piha_androidclient.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

public class LoadingFragment extends Fragment
{
    private TextView mServerTextView;
    private TextView mUserTextView;
    private TextView mDevicesTextView;

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
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar_status);
        mServerTextView = view.findViewById(R.id.textview_server);
        mUserTextView = view.findViewById(R.id.textview_user);
        mDevicesTextView = view.findViewById(R.id.textview_devices);

        return view;
    }

    public void updateServerTextView(String text)
    {
        if(mServerTextView != null)
        {
            this.mServerTextView.setText(text);
        }
    }

    public void updateUserTextView(String text)
    {
        if(mUserTextView != null)
        {
            this.mUserTextView.setText(text);
        }
    }

    public void updateDevicesTextView(String text)
    {
        if(mDevicesTextView != null)
        {
            this.mDevicesTextView.setText(text);
        }
    }
}