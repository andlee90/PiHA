package com.andlee90.piha.piha_androidclient.Networking;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import DeviceObjects.DeviceList;

public class FetchDevicesTask extends AsyncTask<Void, Void, DeviceList>
{
    public TaskResultListener mListener = null;

    private ObjectOutputStream mOutputStream;
    private ObjectInputStream mInputStream;
    private volatile DeviceList mDevices = new DeviceList();

    public FetchDevicesTask(ObjectOutputStream outStream, ObjectInputStream inStream, TaskResultListener listener)
    {
        this.mOutputStream = outStream;
        this.mInputStream = inStream;
        this.mListener = listener;
    }

    @Override
    protected DeviceList doInBackground(Void... params)
    {
        try
        {
            mOutputStream.writeObject(mDevices);
            mDevices = (DeviceList) mInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return mDevices;
    }

    @Override
    protected void onPostExecute(DeviceList devices)
    {
        if(mListener != null)
        {
            mListener.getResult(devices);
        }
    }

    public interface TaskResultListener
    {
        void getResult(DeviceList result);
    }
}
