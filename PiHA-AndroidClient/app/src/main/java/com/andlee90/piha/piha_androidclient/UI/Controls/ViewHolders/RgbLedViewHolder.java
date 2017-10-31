package com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

public class RgbLedViewHolder implements ViewHolder
{
    private final TextView deviceName;
    private final TextView serverId;
    private final CheckBox blink;
    private final Switch deviceSwitch;
    private final Button colorSelectButton;

    public RgbLedViewHolder(View view)
    {
        deviceName = view.findViewById(R.id.device_name);
        serverId = view.findViewById(R.id.server_id);
        blink = view.findViewById(R.id.blink_check);
        deviceSwitch = view.findViewById(R.id.device_switch);
        colorSelectButton = view.findViewById(R.id.color_select);
    }

    @Override
    public TextView getDeviceName()
    {
        return deviceName;
    }

    @Override
    public TextView getServerId()
    {
        return serverId;
    }

    @Override
    public CheckBox getBlink()
    {
        return blink;
    }

    @Override
    public Switch getDeviceSwitch()
    {
        return deviceSwitch;
    }

    @Override
    public Button getColorSelectButton()
    {
        return this.colorSelectButton;
    }
}
