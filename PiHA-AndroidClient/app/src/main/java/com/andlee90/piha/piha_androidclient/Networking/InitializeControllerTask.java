package com.andlee90.piha.piha_androidclient.Networking;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectOutputStream;

import DeviceObjects.Device;

public class InitializeControllerTask extends AsyncTask<Void, Void, Device>
{
    private ObjectOutputStream mOutputStream;
    private Device mDevice;

    public InitializeControllerTask(ObjectOutputStream outStream, Device device)
    {
        this.mOutputStream = outStream;
        this.mDevice = device;
    }

    @Override
    protected Device doInBackground(Void... params)
    {
        try
        {
            mOutputStream.writeObject(mDevice);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return mDevice;
    }
}
