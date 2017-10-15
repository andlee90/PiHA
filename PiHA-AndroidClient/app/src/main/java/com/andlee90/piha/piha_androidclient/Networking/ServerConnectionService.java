package com.andlee90.piha.piha_androidclient.Networking;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CommandObjects.Command;
import DeviceObjects.Device;
import DeviceObjects.DeviceList;
import UserObjects.User;

/**
 * Manages all network activities via a series of AsyncTasks.
 */
public class ServerConnectionService extends Service implements Runnable
{
    private String mServerAddress;
    private int mServerPort;
    private User mUser;
    private DeviceList mDevices;
    private Device mDevice;

    private volatile ObjectOutputStream mOutputStream;
    private volatile ObjectInputStream mInputStream;

    private final IBinder mBinder = new ServerConnectionBinder();

    public ServerConnectionService()
    {}

    public ServerConnectionService(String address, int port, String username, String password)
    {
        this.mServerAddress = address;
        this.mServerPort = port;
        this.mUser = new User(0, username, password, "", "", "", "");

        run(); //Set up socket & streams
    }

    /**
     * Initializes socket and streams on a background thread.
     */
    @Override
    public void run()
    {
        try
        {
            Socket mSocket = new Socket(mServerAddress, mServerPort);
            mOutputStream = new ObjectOutputStream(mSocket.getOutputStream());
            mInputStream = new ObjectInputStream(mSocket.getInputStream());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public class ServerConnectionBinder extends Binder
    {
        public ServerConnectionService getService()
        {
            return ServerConnectionService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    public User establishConnection()
    {
        new EstablishConnectionTask(mOutputStream, mInputStream, mUser,
                new EstablishConnectionTask.TaskResultListener() {
                    @Override
                    public void getResult(User result)
                    {
                        mUser = result;
                    }
                }).execute();

        return mUser;
    }

    public DeviceList fetchDevices()
    {
        new FetchDevicesTask(mOutputStream, mInputStream,
                new FetchDevicesTask.TaskResultListener() {
                    @Override
                    public void getResult(DeviceList result)
                    {
                        mDevices = result;
                    }
                }).execute();

        return mDevices;
    }

    public void initializeController(Device device)
    {
        new InitializeControllerTask(mOutputStream, device).execute();
    }

    public Device issueCommand(Command command)
    {
        new IssueCommandTask(mOutputStream, mInputStream, command,
                new IssueCommandTask.TaskResultListener() {
                    @Override
                    public void getResult(Device result)
                    {
                        mDevice = result;
                    }
                }).execute();

        return mDevice;
    }
}