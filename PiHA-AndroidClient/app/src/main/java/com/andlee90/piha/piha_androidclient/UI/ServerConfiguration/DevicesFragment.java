package com.andlee90.piha.piha_androidclient.UI.ServerConfiguration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

public class DevicesFragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static DevicesFragment newInstance(int page)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        DevicesFragment fragment = new DevicesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        TextView textView = (TextView) view;
        textView.setText("Devices");
        return view;
    }
}
