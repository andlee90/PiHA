package com.andlee90.piha.piha_androidclient.UI.Controls;

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
import android.widget.Switch;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import CommandObjects.LedCommand;
import DeviceObjects.Device;
import DeviceObjects.Led;

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
        return inflater.inflate(R.layout.fragment_device_list, container, false);
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

        TextView itemTextView = v.findViewById(R.id.device_detail);

        if(itemTextView.getVisibility() == View.GONE)
        {
            itemTextView.setVisibility(View.VISIBLE);

            for(int i = 0; i < l.getCount(); i++)
            {
                View otherView = l.getChildAt(i);
                if(otherView != null && otherView != v && position != i)
                {
                    TextView otherTextView = otherView.findViewById(R.id.device_detail);
                    otherTextView.setVisibility(View.GONE);
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
            ViewHolder holder;
            View view = views.get(position);

            if (view == null)
            {
                view = mInflater.inflate(R.layout.list_device_items, null);
                holder = new ViewHolder(view);

                Device device = devices.get(position);
                holder.deviceName.setText(device.getDeviceName());
                holder.serverId.setText("" + device.getHostServerId());
                holder.deviceSwitch.setOnClickListener((View v) -> {
                    try {
                        ((MainActivity)getActivity()).mService.issueCommand(device, new LedCommand(LedCommand.LedCommandType.TOGGLE));
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                views.put(position, view);
            }

            return view;
        }

        private class ViewHolder
        {
            private final TextView deviceName;
            private final TextView serverId;
            private final TextView deviceDetail;
            private final Switch deviceSwitch;

            ViewHolder(View view)
            {
                deviceName = view.findViewById(R.id.device_name);
                serverId = view.findViewById(R.id.server_id);
                deviceDetail = view.findViewById(R.id.device_detail);
                deviceSwitch = view.findViewById(R.id.device_switch);
            }
        }
    }
}
