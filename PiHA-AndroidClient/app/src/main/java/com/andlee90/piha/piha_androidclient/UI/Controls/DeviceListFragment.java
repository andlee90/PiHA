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
import android.widget.RelativeLayout;

import com.andlee90.piha.piha_androidclient.R;
import com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders.LedViewHolder;
import com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders.RelayModuleViewHolder;
import com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders.RgbLedViewHolder;
import com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders.StepperMotorViewHolder;
import com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders.ViewHolder;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import CommandObjects.LedCommand;
import CommandObjects.RelayModuleCommand;
import CommandObjects.RgbLedCommand;
import DeviceObjects.Device;
import DeviceObjects.Led;
import DeviceObjects.RelayModule;
import DeviceObjects.RgbLed;
import DeviceObjects.StepperMotor;

public class DeviceListFragment extends ListFragment
{
    private Context mContext;
    private DeviceListArrayAdapter mAdapter;
    private ArrayList<Device> mDevices = new ArrayList<>();

    private boolean isAttached = false;

    public static DeviceListFragment newInstance()
    {
        return new DeviceListFragment();
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

        RelativeLayout rl = v.findViewById(R.id.hidden_view);

        if(rl.getVisibility() == View.GONE)
        {
            rl.setVisibility(View.VISIBLE);

                for(int i = 0; i < l.getCount(); i++)
                {
                    View otherView = l.getChildAt(i);
                    if(otherView != null && otherView != v && position != i)
                    {
                        RelativeLayout rl2 = otherView.findViewById(R.id.hidden_view);
                        rl2.setVisibility(View.GONE);
                    }
                }
        }
        else
        {
            rl.setVisibility(View.GONE);
        }
    }

    public void setListView(ArrayList<Device> devices)
    {
        mDevices.addAll(devices);
        if(isAttached)
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
            final ViewHolder viewHolder;
            View view = views.get(position);

            if (view == null) {
                Device device = devices.get(position);

                switch (device.getDeviceType()) {
                    case LED:
                        view = mInflater.inflate(R.layout.list_led_items, null);
                        viewHolder = new LedViewHolder(view);
                        viewHolder.getDeviceName().setText(device.getDeviceName());
                        viewHolder.getServerId().setText("" + device.getHostServerId());

                        if (device.getDeviceMode() == Led.LedMode.OFF)
                            viewHolder.getDeviceSwitch().setChecked(false);
                        else viewHolder.getDeviceSwitch().setChecked(true);

                        viewHolder.getDeviceSwitch().setOnClickListener(view1 -> {
                            try {
                                if (viewHolder.getBlink().isChecked()) {
                                    if (!viewHolder.getDeviceSwitch().isChecked()) {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.TOGGLE));
                                    } else {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.BLINK));
                                    }
                                } else {
                                    ((MainActivity) getActivity()).mService.issueCommand(device,
                                            new LedCommand(LedCommand.LedCommandType.TOGGLE));
                                }
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        views.put(position, view);
                        break;

                    case RGB_LED:

                        RgbLedColor rgbLedColor = new RgbLedColor();
                        view = mInflater.inflate(R.layout.list_rgb_led_items, null);
                        viewHolder = new RgbLedViewHolder(view);
                        viewHolder.getDeviceName().setText(device.getDeviceName());
                        viewHolder.getServerId().setText("" + device.getHostServerId());

                        if (device.getDeviceMode() == RgbLed.RgbLedMode.OFF) {
                            viewHolder.getColorSelectButton().setTextColor(getResources().getColor(R.color.white));
                            viewHolder.getDeviceSwitch().setChecked(false);
                            viewHolder.getColorSelectButton().setEnabled(true);
                        } else {
                            int c = rgbLedColor.getColor((RgbLed.RgbLedMode) device.getDeviceMode());
                            viewHolder.getColorSelectButton().setTextColor(
                                    getResources().getColor(c));
                            viewHolder.getDeviceSwitch().setChecked(true);
                            viewHolder.getColorSelectButton().setEnabled(false);
                        }

                        viewHolder.getDeviceSwitch().setOnClickListener(view1 -> {
                            try {
                                int color = viewHolder.getColorSelectButton().getCurrentTextColor();
                                RgbLedCommand.RgbLedCommandType ct;
                                if (viewHolder.getBlink().isChecked()) {
                                    if (!viewHolder.getDeviceSwitch().isChecked()) {
                                        ct = getColorCommand(false, color);
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new RgbLedCommand(ct));
                                    } else {
                                        ct = getColorCommand(true, color);
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new RgbLedCommand(ct));
                                    }
                                } else {
                                    ct = getColorCommand(false, color);
                                    ((MainActivity) getActivity()).mService.issueCommand(device,
                                            new RgbLedCommand(ct));
                                }

                                if(viewHolder.getDeviceSwitch().isChecked())
                                    viewHolder.getColorSelectButton().setEnabled(false);
                                else viewHolder.getColorSelectButton().setEnabled(true);

                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        });

                        viewHolder.getColorSelectButton().setOnClickListener(view12 -> new SpectrumDialog.Builder(getContext())
                                .setColors(R.array.colors_array)
                                .setDismissOnColorSelected(true)
                                .setOutlineWidth(1)
                                .setSelectedColor(viewHolder.getColorSelectButton().getCurrentTextColor())
                                .setOnColorSelectedListener((positiveResult, color) -> {
                                    if(positiveResult)
                                    {
                                        viewHolder.getColorSelectButton().setTextColor(color);
                                    }
                                }).build().show(getFragmentManager(), "color_picker"));
                        views.put(position, view);
                        break;

                    case RELAY_MOD:
                        view = mInflater.inflate(R.layout.list_relay_mod_items, null);
                        viewHolder = new RelayModuleViewHolder(view);

                        viewHolder.getDeviceName().setText(device.getDeviceName());
                        viewHolder.getServerId().setText("" + device.getHostServerId());

                        // Somehow, the logic is backwards for the relay modules. I'll have to
                        // re-wire them at some point.
                        if ((device.getDeviceMode() == RelayModule.RelayModuleMode.OFF))
                            viewHolder.getDeviceSwitch().setChecked(false);
                        else viewHolder.getDeviceSwitch().setChecked(true);

                        viewHolder.getDeviceSwitch().setOnClickListener(view1 -> {
                            try {
                                ((MainActivity)getActivity()).mService.issueCommand(device,
                                        new RelayModuleCommand(RelayModuleCommand.RelayModuleCommandType.TOGGLE));
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        views.put(position, view);
                        break;

                    case STEP_MOTOR:
                        view = mInflater.inflate(R.layout.list_stepper_items, null);
                        viewHolder = new StepperMotorViewHolder(view);

                        viewHolder.getDeviceName().setText(device.getDeviceName());
                        viewHolder.getServerId().setText("" + device.getHostServerId());

                        if (device.getDeviceMode() == StepperMotor.StepperMotorMode.OFF)
                            viewHolder.getDeviceSwitch().setChecked(false);
                        else viewHolder.getDeviceSwitch().setChecked(true);

                        /*viewHolder.getDeviceSwitch().setOnClickListener(view1 -> {
                            try {
                                if(viewHolder.getBlink().isChecked()) {
                                    if(!viewHolder.getDeviceSwitch().isChecked()) {
                                        ((MainActivity)getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.TOGGLE));
                                    } else {
                                        ((MainActivity)getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.BLINK));
                                    }
                                } else {
                                    ((MainActivity)getActivity()).mService.issueCommand(device,
                                            new LedCommand(LedCommand.LedCommandType.TOGGLE));
                                }
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        });*/
                        views.put(position, view);
                        break;
                }
            }
            return view;
        }

        private class RgbLedColor
        {
            private int[] colors = {R.color.red, R.color.green, R.color.blue, R.color.magenta,
                    R.color.yellow, R.color.cyan, R.color.white};

            private RgbLedCommand.RgbLedCommandType[] blinkCommands = {
                    RgbLedCommand.RgbLedCommandType.BLINK_RED, RgbLedCommand.RgbLedCommandType.BLINK_GREEN,
                    RgbLedCommand.RgbLedCommandType.BLINK_BLUE, RgbLedCommand.RgbLedCommandType.BLINK_MAGENTA,
                    RgbLedCommand.RgbLedCommandType.BLINK_YELLOW, RgbLedCommand.RgbLedCommandType.BLINK_CYAN,
                    RgbLedCommand.RgbLedCommandType.BLINK_WHITE };

            private RgbLedCommand.RgbLedCommandType[] toggleCommands = {
                    RgbLedCommand.RgbLedCommandType.BLINK_RED, RgbLedCommand.RgbLedCommandType.BLINK_GREEN,
                    RgbLedCommand.RgbLedCommandType.BLINK_BLUE, RgbLedCommand.RgbLedCommandType.BLINK_MAGENTA,
                    RgbLedCommand.RgbLedCommandType.BLINK_YELLOW, RgbLedCommand.RgbLedCommandType.BLINK_CYAN,
                    RgbLedCommand.RgbLedCommandType.BLINK_WHITE };

            private RgbLed.RgbLedMode[] blinkModes = {
                    RgbLed.RgbLedMode.BLINKING_RED, RgbLed.RgbLedMode.BLINKING_GREEN,
                    RgbLed.RgbLedMode.BLINKING_BLUE, RgbLed.RgbLedMode.BLINKING_MAGENTA,
                    RgbLed.RgbLedMode.BLINKING_YELLOW, RgbLed.RgbLedMode.BLINKING_CYAN,
                    RgbLed.RgbLedMode.BLINKING_WHITE };

            private RgbLed.RgbLedMode[] onModes = {
                    RgbLed.RgbLedMode.ON_RED, RgbLed.RgbLedMode.ON_GREEN,
                    RgbLed.RgbLedMode.ON_BLUE, RgbLed.RgbLedMode.ON_MAGENTA,
                    RgbLed.RgbLedMode.ON_YELLOW, RgbLed.RgbLedMode.ON_CYAN,
                    RgbLed.RgbLedMode.ON_WHITE };

            public RgbLedCommand.RgbLedCommandType getColorBlinkCommand(int color)
            {
                RgbLedCommand.RgbLedCommandType ct  = null;
                for(int i = 0; i < colors.length; i++)
                {
                    if(colors[i] == color)
                    {
                        ct = blinkCommands[i];
                    }
                }
                return ct;
            }

            public RgbLedCommand.RgbLedCommandType getColorToggleCommand(int color)
            {
                RgbLedCommand.RgbLedCommandType ct  = null;
                for(int i = 0; i < colors.length; i++)
                {
                    if(colors[i] == color)
                    {
                        ct = toggleCommands[i];
                    }
                }
                return ct;
            }

            public int getColor(RgbLed.RgbLedMode mode)
            {
                int color = R.color.cyan;
                for(int i = 0; i < onModes.length; i++)
                {
                    if(onModes[i] == mode || blinkModes[i] == mode)
                    {
                        color = colors[i];
                    }
                }
                return color;
            }
        }

        private RgbLedCommand.RgbLedCommandType getColorCommand(boolean blink, int color)
        {
            RgbLedCommand.RgbLedCommandType ct  = null;
            if(blink) {
                if(color == getResources().getColor(R.color.red))
                    ct = RgbLedCommand.RgbLedCommandType.BLINK_RED;
                else if(color == getResources().getColor(R.color.magenta))
                    ct = RgbLedCommand.RgbLedCommandType.BLINK_MAGENTA;
                else  if(color == getResources().getColor(R.color.blue))
                    ct = RgbLedCommand.RgbLedCommandType.BLINK_BLUE;
                else if(color == getResources().getColor(R.color.green))
                    ct = RgbLedCommand.RgbLedCommandType.BLINK_GREEN;
                else if(color == getResources().getColor(R.color.yellow))
                    ct = RgbLedCommand.RgbLedCommandType.BLINK_YELLOW;
                else if(color == getResources().getColor(R.color.cyan))
                    ct = RgbLedCommand.RgbLedCommandType.BLINK_CYAN;
                else if(color == getResources().getColor(R.color.white))
                    ct = RgbLedCommand.RgbLedCommandType.BLINK_WHITE;
            } else {
                if(color == getResources().getColor(R.color.red))
                    ct = RgbLedCommand.RgbLedCommandType.TOGGLE_RED;
                else if(color == getResources().getColor(R.color.magenta))
                    ct = RgbLedCommand.RgbLedCommandType.TOGGLE_MAGENTA;
                else  if(color == getResources().getColor(R.color.blue))
                    ct = RgbLedCommand.RgbLedCommandType.TOGGLE_BLUE;
                else if(color == getResources().getColor(R.color.green))
                    ct = RgbLedCommand.RgbLedCommandType.TOGGLE_GREEN;
                else if(color == getResources().getColor(R.color.yellow))
                    ct = RgbLedCommand.RgbLedCommandType.TOGGLE_YELLOW;
                else if(color == getResources().getColor(R.color.cyan))
                    ct = RgbLedCommand.RgbLedCommandType.TOGGLE_CYAN;
                else if(color == getResources().getColor(R.color.white))
                    ct = RgbLedCommand.RgbLedCommandType.TOGGLE_WHITE;
            } return ct;
        }
    }
}
