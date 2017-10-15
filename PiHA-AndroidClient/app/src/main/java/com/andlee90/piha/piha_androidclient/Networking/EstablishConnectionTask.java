package com.andlee90.piha.piha_androidclient.Networking;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import UserObjects.User;

public class EstablishConnectionTask extends AsyncTask<Void, Void, User>
{
    public TaskResultListener mListener = null;

    private ObjectOutputStream mOutputStream;
    private ObjectInputStream mInputStream;
    private User user;

    public EstablishConnectionTask(ObjectOutputStream outStream, ObjectInputStream inStream, User user, TaskResultListener listener)
    {
        this.mOutputStream = outStream;
        this.mInputStream = inStream;
        this.user = user;
        this.mListener = listener;
    }

    @Override
    protected User doInBackground(Void... params)
    {
        try
        {
            mOutputStream.writeObject(user);
            user = (User) mInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    protected void onPostExecute(User user)
    {
        if(mListener != null)
        {
            mListener.getResult(user);
        }
    }

    public interface TaskResultListener
    {
        void getResult(User result);
    }
}
