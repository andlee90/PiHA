package com.andlee90.piha.piha_androidclient.Networking;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import CommandObjects.Command;
import DeviceObjects.Device;

public class IssueCommandTask extends AsyncTask<Void, Void, Device>
{
    public TaskResultListener mListener = null;

    private ObjectOutputStream mOutputStream;
    private ObjectInputStream mInputStream;
    private Command mCommand;
    private Device mDevice;

    public IssueCommandTask(ObjectOutputStream outStream, ObjectInputStream inStream, Command command, TaskResultListener listener)
    {
        this.mOutputStream = outStream;
        this.mInputStream = inStream;
        this.mCommand = command;
        this.mListener = listener;
    }

    @Override
    protected Device doInBackground(Void... params)
    {
        try
        {
            mOutputStream.writeObject(mCommand);
            mDevice = (Device) mInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return mDevice;
    }

    @Override
    protected void onPostExecute(Device device)
    {
        if(mListener != null)
        {
            mListener.getResult(device);
        }
    }

    public interface TaskResultListener
    {
        void getResult(Device result);
    }
}