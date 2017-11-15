package com.andlee90.piha.piha_androidclient.UI.Configuration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DeviceObjects.Device;

public class ServerDevicesFragment extends ListFragment
{
    private Context mContext;
    private DeviceListArrayAdapter mAdapter;
    private ArrayList<Device> mDevices = new ArrayList<>();

    private boolean isAttached = false;

    public static ServerDevicesFragment newInstance()
    {
        return new ServerDevicesFragment();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        isAttached = true;
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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new DeviceListArrayAdapter(mContext,
                android.R.layout.simple_list_item_1, mDevices);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

    public void setListView(ArrayList<Device> devices)
    {
        mDevices.addAll(devices);
        if (isAttached)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DeviceListArrayAdapter extends ArrayAdapter<Device>
    {
        private LayoutInflater mInflater;
        private List<Device> devices = null;
        private Map<Integer, View> views = new HashMap<Integer, View>();


        DeviceListArrayAdapter(Context context, int resourceId, List<Device> devices)
        {
            super(context, resourceId, devices);

            this.devices = devices;
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent)
        {
            Device device = devices.get(position);

            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.list_device_items, parent, false);
                convertView.setTag(new DeviceListArrayAdapter.ViewHolder(convertView));
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.deviceName.setText(device.getDeviceName());
            holder.deviceType.setText(device.getDeviceType().toString());

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView deviceName;
            private final TextView deviceType;

            ViewHolder(View view)
            {
                deviceName = view.findViewById(R.id.device_name);
                deviceType = view.findViewById(R.id.device_type);
            }
        }
    }
}
