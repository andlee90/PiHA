package com.andlee90.piha.piha_androidclient.UI.Controls.ViewHolders;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public interface ViewHolder
{
    public ImageView getDeviceImage();       // All devices
    public TextView getDeviceName();         // All devices
    public CheckBox getBlink();              // Led and RgbLed only
    public Switch getDeviceSwitch();         // Led, RgbLed and Relay only
    public Button getColorSelectButton();    // RgbLed only
    public Spinner getModeSelectSpinner();   // Motor only
}
