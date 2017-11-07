package com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

public class StepperMotorViewHolder implements ViewHolder
{
    private final ImageView deviceImage;
    private final TextView deviceName;
    private final CheckBox blink;
    private final Switch deviceSwitch;

    public StepperMotorViewHolder(View view)
    {
        deviceImage = view.findViewById(R.id.device_image_view);
        deviceName = view.findViewById(R.id.device_name);
        blink = view.findViewById(R.id.blink_check);
        deviceSwitch = view.findViewById(R.id.device_switch);
    }

    @Override
    public ImageView getDeviceImage()
    {
        return this.deviceImage;
    }

    @Override
    public TextView getDeviceName()
    {
        return deviceName;
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
        return null;
    }
}
