package com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public interface ViewHolder
{
    public ImageView getDeviceImage();
    public TextView getDeviceName();
    public CheckBox getBlink();
    public Switch getDeviceSwitch();
    public Button getColorSelectButton();
}
