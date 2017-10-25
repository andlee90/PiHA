package com.andlee90.piha.piha_androidclient.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

import java.util.ArrayList;
import java.util.List;

import DeviceObjects.Device;

public class DeviceListFragment extends ListFragment
{
    private ListView mListView;
    private Context mContext;
    private DeviceListArrayAdapter mAdapter;
    private ArrayList<Device> mDevices = new ArrayList<>();

    public static DeviceListFragment newInstance()
    {
        return new DeviceListFragment();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        mListView = view.findViewById(android.R.id.list);
        TextView emptyText = view.findViewById(R.id.empty_listview);
        mListView.setEmptyView(emptyText);

        mAdapter = new DeviceListArrayAdapter(mContext,
                android.R.layout.simple_list_item_1, mDevices);
        setListAdapter(mAdapter);

        return view;
    }

    public void setListView(ArrayList<Device> devices)
    {
        mDevices.addAll(devices);
        mAdapter.notifyDataSetChanged();
    }

    private class DeviceListArrayAdapter extends ArrayAdapter<Device>
    {
        private LayoutInflater mInflater;
        private List<Device> devices = null;

        DeviceListArrayAdapter(Context context, int resourceId, List<Device> devices)
        {
            super(context, resourceId, devices);

            this.devices = devices;
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {
            Device device = devices.get(position);

            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_devices, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.deviceName.setText(device.getDeviceName());

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView deviceName;

            ViewHolder(View view)
            {
                deviceName = view.findViewById(R.id.deviceName);
            }
        }
    }
}
