package com.andlee90.piha.piha_androidclient.Networking;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.andlee90.piha.piha_androidclient.Database.ServerItem;
import com.andlee90.piha.piha_androidclient.UI.MainActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

import CommandObjects.Command;
import DeviceObjects.Device;
import DeviceObjects.DeviceList;
import UserObjects.User;

/**
 * Manages all network activities via a series of AsyncTasks.
 */
public class ServerConnectionService extends Service
{
    private static Hashtable<Integer, Socket> sServerTable = new Hashtable<>();

    private volatile User mUser;
    private DeviceList mDevices;
    private Device mDevice;
    private Command mCommand;

    private volatile ObjectOutputStream mOutputStream;
    private volatile ObjectInputStream mInputStream;

    private final IBinder mBinder = new ServerConnectionBinder();

    public class ServerConnectionBinder extends Binder
    {
        public ServerConnectionService getService()
        {
            return ServerConnectionService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void establishConnection(ServerItem server) throws ExecutionException, InterruptedException
    {
        new EstablishConnectionTask(server).execute();
    }

    public void initializeController(Device device)
    {
        new InitializeControllerTask(device).execute();
    }

    public Device issueCommand(Command command) throws ExecutionException, InterruptedException
    {
        return new IssueCommandTask(command).execute().get();
    }

    private class EstablishConnectionTask extends AsyncTask<Void, Void, Void>
    {
        private ServerItem server;

        public EstablishConnectionTask(ServerItem s)
        {
            this.server = s;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            User user = new User(0, server.getUsername(), server.getPassword(), "", "", "", "");
            DeviceList devices = new DeviceList();

            try
            {
                Socket mSocket = new Socket(server.getAddress(), server.getPort());
                sServerTable.put(server.getId(), mSocket);

                mOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
                mInputStream = new ObjectInputStream(mSocket.getInputStream());

                mOutputStream.writeObject(user);
                mUser = (User) mInputStream.readObject();

                Intent userIntent = new Intent(MainActivity.RECEIVE_USER);
                userIntent.putExtra("user", mUser);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(userIntent);

                mOutputStream.writeObject(devices);
                mDevices = (DeviceList) mInputStream.readObject();

                Intent devicesIntent = new Intent(MainActivity.RECEIVE_DEVICES);
                devicesIntent.putExtra("devices", mDevices);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(devicesIntent);
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class InitializeControllerTask extends AsyncTask<Void, Void, Device>
    {
        public InitializeControllerTask(Device device)
        {
            mDevice = device;
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

    public class IssueCommandTask extends AsyncTask<Void, Void, Device>
    {
        public IssueCommandTask(Command command)
        {
            mCommand = command;
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
    }
}