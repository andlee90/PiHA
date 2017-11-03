package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

public class ServerDevicesFragment extends Fragment
{
    public static ServerDevicesFragment newInstance()
    {
        return new ServerDevicesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
