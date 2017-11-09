package com.andlee90.piha.piha_androidclient.UI.Controls;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import CommandObjects.DeviceCommands.LedCommand;
import CommandObjects.DeviceCommands.RelayModuleCommand;
import CommandObjects.DeviceCommands.RgbLedCommand;
import CommandObjects.DeviceCommands.StepperMotorCommand;
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
        else rl.setVisibility(View.GONE);
    }

    public void setListView(ArrayList<Device> devices)
    {
        mDevices.addAll(devices);
        if(isAttached) mAdapter.notifyDataSetChanged();
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
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getViewTypeCount()
        {
            return 4;
        }

        @Override
        public int getItemViewType(int position)
        {
            // This may be a cop-out, more research is needed.
            return IGNORE_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {
            final ViewHolder viewHolder;
            final Device device = devices.get(position);

            View view = views.get(position);

            if (view == null)
            {
                switch (device.getDeviceType())
                {
                    case LED:
                        view = mInflater.inflate(R.layout.list_led_items, null);
                        viewHolder = new LedViewHolder(view);
                        viewHolder.getDeviceName().setText(device.getDeviceName());

                        if (device.getDeviceMode() == Led.LedMode.OFF)
                        {
                            viewHolder.getDeviceSwitch().setChecked(false);
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.led_off));
                        }
                        else
                        {
                            viewHolder.getDeviceSwitch().setChecked(true);
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.led_blue));
                        }

                        viewHolder.getDeviceSwitch().setOnClickListener(view1 ->
                        {
                            try
                            {
                                if (viewHolder.getBlink().isChecked())
                                {
                                    if (!viewHolder.getDeviceSwitch().isChecked())
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.TOGGLE));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.led_off));
                                    }
                                    else
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.BLINK));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.led_blue));
                                    }
                                }
                                else
                                {
                                    if (!viewHolder.getDeviceSwitch().isChecked())
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.TOGGLE));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.led_off));
                                    }
                                    else
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new LedCommand(LedCommand.LedCommandType.TOGGLE));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.led_blue));
                                    }
                                }
                            }
                            catch (ExecutionException | InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        });
                        views.put(position, view);
                        break;

                    case RGB_LED:
                        view = mInflater.inflate(R.layout.list_rgb_led_items, null);
                        viewHolder = new RgbLedViewHolder(view);
                        RgbLedColor rgbLedColor = new RgbLedColor();
                        viewHolder.getDeviceName().setText(device.getDeviceName());

                        if (device.getDeviceMode() == RgbLed.RgbLedMode.OFF)
                        {
                            viewHolder.getColorSelectButton().setTextColor(getResources().getColor(R.color.white));
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.rgb_off));
                            viewHolder.getDeviceSwitch().setChecked(false);
                            viewHolder.getColorSelectButton().setEnabled(true);
                        }
                        else
                        {
                            int c = rgbLedColor.getColor((RgbLed.RgbLedMode) device.getDeviceMode());
                            viewHolder.getColorSelectButton().setTextColor(
                                    getResources().getColor(c));
                            int i = rgbLedColor.getImage(viewHolder.getColorSelectButton().getCurrentTextColor());
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(i));
                            viewHolder.getDeviceSwitch().setChecked(true);
                            viewHolder.getColorSelectButton().setEnabled(false);
                        }

                        viewHolder.getDeviceSwitch().setOnClickListener(view1 ->
                        {
                            try
                            {
                                int color = viewHolder.getColorSelectButton().getCurrentTextColor();
                                int i = rgbLedColor.getImage(color);
                                RgbLedCommand.RgbLedCommandType ct;

                                if (viewHolder.getBlink().isChecked())
                                {
                                    if (!viewHolder.getDeviceSwitch().isChecked())
                                    {
                                        ct = rgbLedColor.getColorToggleCommand(color);
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new RgbLedCommand(ct));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.rgb_off));
                                    }
                                    else
                                    {
                                        ct = rgbLedColor.getColorBlinkCommand(color);
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new RgbLedCommand(ct));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(i));
                                    }
                                }
                                else
                                {
                                    if (!viewHolder.getDeviceSwitch().isChecked())
                                    {
                                        ct = rgbLedColor.getColorToggleCommand(color);
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new RgbLedCommand(ct));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.rgb_off));
                                    }
                                    else
                                    {
                                        ct = rgbLedColor.getColorToggleCommand(color);
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new RgbLedCommand(ct));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(i));
                                    }
                                }

                                if (viewHolder.getDeviceSwitch().isChecked()) viewHolder.getColorSelectButton().setEnabled(false);
                                else viewHolder.getColorSelectButton().setEnabled(true);

                            }
                            catch (ExecutionException | InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        });

                        viewHolder.getColorSelectButton().setOnClickListener(view12 -> new SpectrumDialog.Builder(getContext())
                                .setColors(R.array.colors_array)
                                .setDismissOnColorSelected(true)
                                .setOutlineWidth(1)
                                .setSelectedColor(viewHolder.getColorSelectButton().getCurrentTextColor())
                                .setOnColorSelectedListener((positiveResult, color) ->
                                {
                                    if (positiveResult)
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

                        // Somehow, the logic is backwards for the relay modules. I'll have to
                        // re-wire them at some point.
                        if (!(device.getDeviceMode() == RelayModule.RelayModuleMode.OFF))
                        {
                            viewHolder.getDeviceSwitch().setChecked(false);
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.relay_off));
                        }
                        else
                        {
                            viewHolder.getDeviceSwitch().setChecked(true);
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.relay_on));
                        }

                        viewHolder.getDeviceSwitch().setOnClickListener(view1 ->
                        {
                            try
                            {
                                if (!viewHolder.getDeviceSwitch().isChecked())
                                {
                                    ((MainActivity) getActivity()).mService.issueCommand(device,
                                            new RelayModuleCommand(RelayModuleCommand.RelayModuleCommandType.TOGGLE));
                                    viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.relay_off));
                                }
                                else
                                {
                                    ((MainActivity) getActivity()).mService.issueCommand(device,
                                            new RelayModuleCommand(RelayModuleCommand.RelayModuleCommandType.TOGGLE));
                                    viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.relay_on));
                                }
                            }
                            catch (ExecutionException | InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        });
                        views.put(position, view);
                        break;

                    case STEP_MOTOR:
                        view = mInflater.inflate(R.layout.list_stepper_items, null);
                        viewHolder = new StepperMotorViewHolder(view);
                        viewHolder.getDeviceName().setText(device.getDeviceName());
                        String[] options = new String[] {"Close Up", "Half Up", "Open", "Half Down", "Close Down"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, options);
                        viewHolder.getModeSelectSpinner().setAdapter(adapter);

                        if ((device.getDeviceMode() == StepperMotor.StepperMotorMode.CLOSED_UP))
                        {
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_closed));
                            viewHolder.getModeSelectSpinner().setSelection(0);
                        }
                        else if (device.getDeviceMode() == StepperMotor.StepperMotorMode.HALF_UP)
                        {
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_half));
                            viewHolder.getModeSelectSpinner().setSelection(1);
                        }
                        else if (device.getDeviceMode() == StepperMotor.StepperMotorMode.OPEN)
                        {
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_open));
                            viewHolder.getModeSelectSpinner().setSelection(2);
                        }
                        else if (device.getDeviceMode() == StepperMotor.StepperMotorMode.HALF_DOWN)
                        {
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_half));
                            viewHolder.getModeSelectSpinner().setSelection(3);
                        }
                        else if ((device.getDeviceMode() == StepperMotor.StepperMotorMode.CLOSED_DOWN))
                        {
                            viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_closed));
                            viewHolder.getModeSelectSpinner().setSelection(4);
                        }

                        viewHolder.getModeSelectSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                        {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                            {
                                try
                                {
                                    if(viewHolder.getModeSelectSpinner().getSelectedItem().toString().equals(options[0]))
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new StepperMotorCommand(StepperMotorCommand.StepperMotorCommandType.CLOSE_UP));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_closed));
                                    }
                                    else if(viewHolder.getModeSelectSpinner().getSelectedItem().equals(options[1]))
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new StepperMotorCommand(StepperMotorCommand.StepperMotorCommandType.OPEN_HALF_UP));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_half));
                                    }
                                    else if(viewHolder.getModeSelectSpinner().getSelectedItem().equals(options[2]))
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new StepperMotorCommand(StepperMotorCommand.StepperMotorCommandType.OPEN));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_open));
                                    }
                                    else if(viewHolder.getModeSelectSpinner().getSelectedItem().equals(options[3]))
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new StepperMotorCommand(StepperMotorCommand.StepperMotorCommandType.OPEN_HALF_DOWN));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_half));
                                    }
                                    else if(viewHolder.getModeSelectSpinner().getSelectedItem().equals(options[4]))
                                    {
                                        ((MainActivity) getActivity()).mService.issueCommand(device,
                                                new StepperMotorCommand(StepperMotorCommand.StepperMotorCommandType.CLOSE_DOWN));
                                        viewHolder.getDeviceImage().setImageDrawable(getResources().getDrawable(R.drawable.motor_closed));
                                    }
                                }
                                catch (ExecutionException | InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView)
                            {
                                // Do nothing.
                            }
                        });
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

            private int[] images = {R.drawable.rgb_red, R.drawable.rgb_green, R.drawable.rgb_blue,
                    R.drawable.rgb_magenta, R.drawable.rgb_yellow, R.drawable.rgb_cyan,
                    R.drawable.rgb_white};

            private RgbLedCommand.RgbLedCommandType[] blinkCommands = {
                    RgbLedCommand.RgbLedCommandType.BLINK_RED, RgbLedCommand.RgbLedCommandType.BLINK_GREEN,
                    RgbLedCommand.RgbLedCommandType.BLINK_BLUE, RgbLedCommand.RgbLedCommandType.BLINK_MAGENTA,
                    RgbLedCommand.RgbLedCommandType.BLINK_YELLOW, RgbLedCommand.RgbLedCommandType.BLINK_CYAN,
                    RgbLedCommand.RgbLedCommandType.BLINK_WHITE };

            private RgbLedCommand.RgbLedCommandType[] toggleCommands = {
                    RgbLedCommand.RgbLedCommandType.TOGGLE_RED, RgbLedCommand.RgbLedCommandType.TOGGLE_GREEN,
                    RgbLedCommand.RgbLedCommandType.TOGGLE_BLUE, RgbLedCommand.RgbLedCommandType.TOGGLE_MAGENTA,
                    RgbLedCommand.RgbLedCommandType.TOGGLE_YELLOW, RgbLedCommand.RgbLedCommandType.TOGGLE_CYAN,
                    RgbLedCommand.RgbLedCommandType.TOGGLE_WHITE };

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

            RgbLedCommand.RgbLedCommandType getColorBlinkCommand(int color)
            {
                RgbLedCommand.RgbLedCommandType ct  = null;
                for(int i = 0; i < colors.length; i++)
                {
                    if(getResources().getColor(colors[i]) == color)
                    {
                        ct = blinkCommands[i];
                    }
                }
                return ct;
            }

            RgbLedCommand.RgbLedCommandType getColorToggleCommand(int color)
            {
                RgbLedCommand.RgbLedCommandType ct  = null;
                for(int i = 0; i < colors.length; i++)
                {
                    if(getResources().getColor(colors[i]) == color)
                    {
                        ct = toggleCommands[i];
                    }
                }
                return ct;
            }

            int getImage(int color)
            {
                int image = 0;
                for(int i = 0; i < colors.length; i++)
                {
                    if(getResources().getColor(colors[i]) == color)
                    {
                        image = images[i];
                    }
                }
                return image;
            }

            int getColor(RgbLed.RgbLedMode mode)
            {
                int color = 0;
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
    }
}
