package com.andlee90.piha.piha_androidclient.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

import java.util.ArrayList;
import java.util.List;

import DeviceObjects.Device;

public class DeviceListFragment extends ListFragment
{
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

        mAdapter = new DeviceListArrayAdapter(mContext,
                android.R.layout.simple_list_item_1, mDevices);
        setListAdapter(mAdapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        TextView itemTextView = v.findViewById(R.id.device_detail);
        if(itemTextView.getVisibility() == View.GONE)
        {
            itemTextView.setVisibility(View.VISIBLE);
            for(int i = 0; i < mAdapter.getCount(); i++)
            {
                View otherView = l.getChildAt(i);
                if(otherView != null)
                {
                    TextView otherTextView = otherView.findViewById(R.id.device_detail);
                    if(i != position)
                    {
                        otherTextView.setVisibility(View.GONE);
                    }
                }
            }
        }
        else
        {
            itemTextView.setVisibility(View.GONE);
        }
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
                convertView = mInflater.inflate(R.layout.list_device_items, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.deviceName.setText(device.getDeviceName());
            holder.deviceSwitch.setOnClickListener(view -> {

            });

            return convertView;
        }

        private class ViewHolder
        {
            private final TextView deviceName;
            private final TextView deviceDetail;
            private final Switch deviceSwitch;

            ViewHolder(View view)
            {
                deviceName = view.findViewById(R.id.device_name);
                deviceDetail = view.findViewById(R.id.device_detail);
                deviceSwitch = view.findViewById(R.id.device_switch);
            }
        }
    }
}
