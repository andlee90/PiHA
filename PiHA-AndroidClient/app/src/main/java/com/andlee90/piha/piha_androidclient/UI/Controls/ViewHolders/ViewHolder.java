package com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

public interface ViewHolder
{
    public TextView getDeviceName();
    public TextView getServerId();
    public CheckBox getBlink();
    public Switch getDeviceSwitch();
    public Button getColorSelectButton();
}
